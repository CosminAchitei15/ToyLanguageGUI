package com.example.toylanguagegui.src.Model.Statement;

import com.example.toylanguagegui.src.Controller.ContainerException;
import com.example.toylanguagegui.src.Controller.ExpressionException;
import com.example.toylanguagegui.src.Controller.FileException;
import com.example.toylanguagegui.src.Controller.StatementException;
import com.example.toylanguagegui.src.Model.*;
import com.example.toylanguagegui.src.Model.Expressions.Exp;
import com.example.toylanguagegui.src.utils.MyIDictionary;
import com.example.toylanguagegui.src.utils.MyIHeap;

import java.io.IOException;

public class HeapWritingStatement implements IStmt{
    String heapVarName;
    Exp expression;

    public HeapWritingStatement(String heapVarName, Exp expression) {
        this.heapVarName = heapVarName;
        this.expression = expression;
    }

    @Override
    public PrgState execute(PrgState state) throws StatementException, ExpressionException, FileException, IOException, ContainerException {
        MyIDictionary<String, Value> symTable = state.getSymTable();
        MyIHeap<Value> heap = state.getHeap();
        if(!symTable.isDefined(heapVarName))
            throw new ContainerException("variable not defined");
        //Type returnedType = symTable.lookup(heapVarName).getType();
        //Type returnedHeapType = heap.lookup(((RefValue) symTable.lookup(heapVarName)).getAddress()).getType();
        int elementHeapAddress = ((RefValue) symTable.lookup(heapVarName)).getAddress();
        Value expressionResult = expression.evaluate(state.getSymTable(), state.getHeap());
        heap.update(elementHeapAddress, expressionResult);
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws StatementException, ExpressionException {
        Type typeVar = typeEnv.lookup(this.heapVarName);
        Type typeExp = this.expression.typecheck(typeEnv);
        if(typeVar.equals(new RefType(typeExp)))
            return typeEnv;
        else
            throw new StatementException("HeapWriting: expression cannot be evaluated");
    }

    @Override
    public String toString() {
        return "HeapWriting(" + this.heapVarName + ";" + this.expression.toString() + ")";
    }
}
