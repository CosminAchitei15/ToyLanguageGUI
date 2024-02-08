package com.example.toylanguagegui.src.Model.Expressions;

import com.example.toylanguagegui.src.Controller.ContainerException;
import com.example.toylanguagegui.src.Controller.ExpressionException;
import com.example.toylanguagegui.src.Model.Type;
import com.example.toylanguagegui.src.Model.Value;
import com.example.toylanguagegui.src.utils.MyIDictionary;
import com.example.toylanguagegui.src.utils.MyIHeap;

public interface Exp {
    Value evaluate(MyIDictionary<String, Value> symTable, MyIHeap<Value> heap) throws ExpressionException, ContainerException;
    Type typecheck(MyIDictionary<String, Type> typeEnv) throws ExpressionException;
}
