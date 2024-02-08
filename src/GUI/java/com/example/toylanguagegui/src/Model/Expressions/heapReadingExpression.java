package com.example.toylanguagegui.src.Model.Expressions;

import com.example.toylanguagegui.src.Controller.ContainerException;
import com.example.toylanguagegui.src.Controller.ExpressionException;
import com.example.toylanguagegui.src.Model.RefType;
import com.example.toylanguagegui.src.Model.RefValue;
import com.example.toylanguagegui.src.Model.Type;
import com.example.toylanguagegui.src.Model.Value;
import com.example.toylanguagegui.src.utils.MyIDictionary;
import com.example.toylanguagegui.src.utils.MyIHeap;

public class heapReadingExpression implements Exp{
    Exp expression;

    public heapReadingExpression(Exp expression) {
        this.expression = expression;
    }

    @Override
    public Value evaluate(MyIDictionary<String, Value> symTable, MyIHeap<Value> heap) throws ExpressionException, ContainerException {
        Value expressionResult = expression.evaluate(symTable, heap);
        int address = ((RefValue) expression.evaluate(symTable, heap)).getAddress();
        return heap.lookup(address);
    }

    @Override
    public Type typecheck(MyIDictionary<String, Type> typeEnv) throws ExpressionException {
        Type type = this.expression.typecheck(typeEnv);
        if(type instanceof RefType){
            RefType refType = (RefType) type;
            return refType.getInner();
        }
        else
            throw new ExpressionException("rH argument not a ref type");
    }

    @Override
    public String toString() {
        return "heapRead(" + expression.toString() + ")";
    }
}
