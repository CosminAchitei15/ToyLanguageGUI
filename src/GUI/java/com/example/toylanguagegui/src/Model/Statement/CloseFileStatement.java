package com.example.toylanguagegui.src.Model.Statement;

import com.example.toylanguagegui.src.Controller.ContainerException;
import com.example.toylanguagegui.src.Controller.ExpressionException;
import com.example.toylanguagegui.src.Controller.FileException;
import com.example.toylanguagegui.src.Controller.StatementException;
import com.example.toylanguagegui.src.Model.*;
import com.example.toylanguagegui.src.Model.Expressions.Exp;
import com.example.toylanguagegui.src.utils.MyIDictionary;
import com.example.toylanguagegui.src.utils.MyIStack;

import java.io.BufferedReader;
import java.io.IOException;

public class CloseFileStatement implements IStmt{
    Exp expression;
    public CloseFileStatement(Exp e){
        this.expression = e;
    }
    @Override
    public PrgState execute(PrgState state) throws StatementException, ExpressionException, FileException, IOException, ContainerException {
        MyIStack<IStmt> exeStack = state.getExeStack();
        MyIDictionary<StringValue, BufferedReader> fileTable = state.getFileTable();

        Value expressionResult = expression.evaluate(state.getSymTable(), state.getHeap());
        if(expressionResult instanceof StringValue expressionToString){
            BufferedReader fileDescriptor = fileTable.lookup(expressionToString);
            fileDescriptor.close();
            fileTable.remove(expressionToString);
            return null;
        }
        else
            throw new FileException("Expected string for file name");
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws StatementException, ExpressionException {
        Type typeExp = this.expression.typecheck(typeEnv);
        if(!typeExp.equals(new StringType()))
            throw new StatementException("CloseFile: file path not a stringValue");
        return typeEnv;
    }

    @Override
    public String toString() {
        return "CloseFile(" + this.expression.toString() + ")";
    }
}
