package com.example.toylanguagegui.src.Model.Statement;

import com.example.toylanguagegui.src.Controller.ContainerException;
import com.example.toylanguagegui.src.Controller.ExpressionException;
import com.example.toylanguagegui.src.Controller.FileException;
import com.example.toylanguagegui.src.Controller.StatementException;
import com.example.toylanguagegui.src.Model.*;
import com.example.toylanguagegui.src.Model.Expressions.Exp;
import com.example.toylanguagegui.src.utils.*;

import java.io.IOException;

public class HeapAllocationStatement implements IStmt{
    Exp expression;
    String variable_name;

    public HeapAllocationStatement(Exp expression, String variable_name){
        this.expression = expression;
        this.variable_name = variable_name;
    }
    @Override
    public PrgState execute(PrgState state) throws StatementException, ExpressionException, FileException, IOException, ContainerException {
        MyIDictionary<String, Value> symTable = state.getSymTable();
        MyIHeap<Value> heap = state.getHeap();
        if(!symTable.isDefined(variable_name)){
            throw new ContainerException(variable_name + " is not in SymTable");
        }
        Value expressionResult = expression.evaluate(state.getSymTable(), state.getHeap());
        Value valueOfVariableName = symTable.lookup(variable_name);
        int address = heap.put(expressionResult);
        symTable.update(variable_name, new RefValue(address, ((RefValue) valueOfVariableName).getLocationType()));
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws StatementException, ExpressionException {
        Type typeVar = typeEnv.lookup(this.variable_name);
        Type typeExp = expression.typecheck(typeEnv);
        if(typeVar.equals(new RefType(typeExp)))
            return typeEnv;
        else
            throw new StatementException("HeapAlloc: left and right side data type mismatch");
    }

    @Override
    public String toString() {
        return "new(" + this.variable_name + ";" + this.expression.toString() + ")";
    }
}
