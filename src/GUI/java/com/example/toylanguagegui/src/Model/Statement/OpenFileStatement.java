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
import java.io.FileNotFoundException;
import java.io.FileReader;

public class OpenFileStatement implements IStmt{

    Exp expression;
    public OpenFileStatement(Exp e){
        this.expression = e;
    }
    @Override
    public PrgState execute(PrgState state) throws StatementException, ExpressionException, FileException, FileNotFoundException, ContainerException {
        MyIStack<IStmt> executionStack = state.getExeStack();
        MyIDictionary<StringValue, BufferedReader> fileTable = state.getFileTable();

        Value expressionResult = expression.evaluate(state.getSymTable(), state.getHeap());
        if(expressionResult instanceof StringValue expressionToString){
            String expressionValue = expressionToString.getValue();
            if(fileTable.isDefined(expressionToString)){
                throw new FileException("File name already exists");
            }
            BufferedReader fileDescriptor = new BufferedReader(new FileReader(expressionValue));
            fileTable.put(expressionToString, fileDescriptor);
        }
        else
            throw new FileException("expected string for file name");
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws StatementException, ExpressionException {
        Type typeExp = this.expression.typecheck(typeEnv);
        if(!typeExp.equals(new StringType()))
            throw new StatementException("OpenFile: file path should be string");
        else
            return typeEnv;
    }

    @Override
    public String toString() {
        return "OpenFile(" + this.expression.toString() + ")";
    }
}
