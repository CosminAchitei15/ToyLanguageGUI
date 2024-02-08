package com.example.toylanguagegui.src.Model.Expressions;

import com.example.toylanguagegui.src.Controller.ExpressionException;
import com.example.toylanguagegui.src.Model.Type;
import com.example.toylanguagegui.src.Model.Value;
import com.example.toylanguagegui.src.utils.MyIDictionary;
import com.example.toylanguagegui.src.utils.MyIHeap;

public class VariableExpression implements Exp{

    String id;

    public VariableExpression(String newId){
        this.id = newId;
    }
    @Override
    public Value evaluate(MyIDictionary<String, Value> symTable, MyIHeap<Value> heap) throws ExpressionException {
        return symTable.lookup(id);
    }

    @Override
    public Type typecheck(MyIDictionary<String, Type> typeEnv) throws ExpressionException {
        return typeEnv.lookup(this.id);
    }

    @Override
    public String toString(){
        return id;
    }
}
