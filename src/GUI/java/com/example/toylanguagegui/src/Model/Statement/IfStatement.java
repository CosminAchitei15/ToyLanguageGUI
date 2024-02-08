package com.example.toylanguagegui.src.Model.Statement;

import com.example.toylanguagegui.src.Controller.ContainerException;
import com.example.toylanguagegui.src.Controller.ExpressionException;
import com.example.toylanguagegui.src.Controller.StatementException;
import com.example.toylanguagegui.src.Model.Expressions.Exp;
import com.example.toylanguagegui.src.Model.PrgState;
import com.example.toylanguagegui.src.Model.*;
import com.example.toylanguagegui.src.utils.MyIDictionary;
import com.example.toylanguagegui.src.utils.MyIStack;

public class IfStatement implements IStmt{
    Exp expression;
    IStmt thenStmt;
    IStmt elseStmt;

    public IfStatement(Exp exp, IStmt then, IStmt elseS){
        this.expression = exp;
        this.thenStmt = then;
        this.elseStmt = elseS;
    }

    @Override
    public PrgState execute(PrgState state) throws StatementException, ExpressionException, ContainerException {
        MyIStack<IStmt> exeStack = state.getExeStack();
        MyIDictionary<String, Value> symTable = state.getSymTable();
        if(!expression.evaluate(symTable, state.getHeap()).getType().equals(new BoolType()))
            throw new StatementException("conditional if does not contain boolean");
        Value result = expression.evaluate(symTable, state.getHeap());
        if(((BoolValue) result).getValue())
            exeStack.push(thenStmt);
        else
            exeStack.push(elseStmt);
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws StatementException, ExpressionException {
        Type typeExp = this.expression.typecheck(typeEnv);
        if(typeExp.equals(new BoolType())){
            this.thenStmt.typecheck(typeEnv.copydict());
            this.elseStmt.typecheck(typeEnv.copydict());
            return typeEnv;
        }
        else
            throw new StatementException("If: condition is not a boolean");
    }

    @Override
    public String toString() {
        return "if(" + expression.toString() + ") then(" + thenStmt.toString() + ") else(" + elseStmt.toString() + ")";
    }
}
