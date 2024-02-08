package com.example.toylanguagegui.src.Model.Statement;

import com.example.toylanguagegui.src.Controller.ContainerException;
import com.example.toylanguagegui.src.Controller.ExpressionException;
import com.example.toylanguagegui.src.Controller.FileException;
import com.example.toylanguagegui.src.Controller.StatementException;
import com.example.toylanguagegui.src.Model.*;
import com.example.toylanguagegui.src.Model.Expressions.Exp;
import com.example.toylanguagegui.src.utils.MyIDictionary;
import com.example.toylanguagegui.src.utils.MyIList;
import com.example.toylanguagegui.src.utils.MyIStack;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;

public class ReadFileStatement implements IStmt{
    Exp expressionFileName;
    String nameIntValue;

    public ReadFileStatement(Exp e, String i){
        this.expressionFileName = e;
        this.nameIntValue = i;
    }

    @Override
    public PrgState execute(PrgState state) throws StatementException, ExpressionException, FileException, IOException, ContainerException {
        MyIStack<IStmt> exeStack = state.getExeStack();
        MyIDictionary<String, Value> symTable = state.getSymTable();
        MyIList<Value> output = state.getOut();
        MyIDictionary<StringValue, BufferedReader> fileTable = state.getFileTable();

        if(symTable.isDefined(nameIntValue)){
            Value expressionResult = expressionFileName.evaluate(symTable, state.getHeap());
            if(expressionResult instanceof StringValue expressionToString){
                BufferedReader fileDescriptor = fileTable.lookup(expressionToString);
                String line = fileDescriptor.readLine();
                int expectedValue = (line != null ? Integer.parseInt(line) : 0);
                IntValue convertedValue = new IntValue(expectedValue);
                symTable.update(nameIntValue, convertedValue);
                return null;
            }
            else
                throw new FileException("Expected string file name");
        }
        else
            throw new FileException("Name of file not found in FileTable");
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws StatementException, ExpressionException {
        Type typeVar = typeEnv.lookup(this.nameIntValue);
        Type typeExp = this.expressionFileName.typecheck(typeEnv);
        if(typeVar.equals(new IntType())) {
            if (typeExp.equals(new StringType())) {
                return typeEnv;
            }else
                throw new StatementException("ReadFile: filepath should be string");
        }else
            throw new StatementException("ReadFile: variableName not an int");
    }

    @Override
    public String toString() {
        return "ReadFile(" + this.nameIntValue + this.expressionFileName.toString() +")";
    }
}
