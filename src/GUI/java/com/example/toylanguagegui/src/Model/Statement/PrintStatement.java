package com.example.toylanguagegui.src.Model.Statement;

import com.example.toylanguagegui.src.Controller.ContainerException;
import com.example.toylanguagegui.src.Controller.ExpressionException;
import com.example.toylanguagegui.src.Controller.StatementException;
import com.example.toylanguagegui.src.Model.Expressions.Exp;
import com.example.toylanguagegui.src.Model.PrgState;
import com.example.toylanguagegui.src.Model.*;
import com.example.toylanguagegui.src.utils.MyIDictionary;
import com.example.toylanguagegui.src.utils.MyIList;

public class PrintStatement implements IStmt{

    Exp expression;

    public PrintStatement(Exp newExpression){
        this.expression = newExpression;
    }

    public void setExpression(Exp newExpression){
        this.expression = newExpression;
    }

    @Override
    public PrgState execute(PrgState state) throws StatementException, ExpressionException, ContainerException {
        MyIList<Value> out = state.getOut();
        MyIDictionary<String, Value> symTable = state.getSymTable();
        out.add(expression.evaluate(symTable, state.getHeap()));
        System.out.println(out);
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws StatementException, ExpressionException {
        this.expression.typecheck(typeEnv);
        return typeEnv;
    }

    @Override
    public String toString() {
        return "print(" + this.expression.toString() + ")";
    }
}
