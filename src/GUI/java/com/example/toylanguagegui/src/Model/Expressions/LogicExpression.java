package com.example.toylanguagegui.src.Model.Expressions;

import com.example.toylanguagegui.src.Controller.ContainerException;
import com.example.toylanguagegui.src.Controller.ExpressionException;
import com.example.toylanguagegui.src.Model.BoolType;
import com.example.toylanguagegui.src.Model.BoolValue;
import com.example.toylanguagegui.src.Model.Type;
import com.example.toylanguagegui.src.Model.Value;
import com.example.toylanguagegui.src.utils.MyIDictionary;
import com.example.toylanguagegui.src.utils.MyIHeap;

public class LogicExpression implements Exp{
    Exp expression1, expression2;
    int operation;

    public LogicExpression(Exp exp, Exp exp2, int op){
        this.expression1 = exp;
        this.expression2 = exp2;
        this.operation = op;
    }
    @Override
    public Value evaluate(MyIDictionary<String, Value> symTable, MyIHeap<Value> heap) throws ExpressionException, ContainerException {
        Value value1, value2;
        value1 = expression1.evaluate(symTable, heap);
        if(value1.getType().equals(new BoolType())){
            value2 = expression2.evaluate(symTable, heap);
            if(value2.getType().equals(new BoolType())){
                BoolValue boolvalue1 = (BoolValue) value1;
                BoolValue boolvalue2 = (BoolValue) value2;
                boolean boolean1, boolean2;
                boolean1 = boolvalue1.getValue();
                boolean2 = boolvalue2.getValue();
                if(operation == 1)
                    return new BoolValue(boolean1 && boolean2);
                else if(operation == 2)
                    return new BoolValue(boolean1 || boolean2);
                else
                    throw new ExpressionException("invalid logic operator \n");
            }
            else throw new ExpressionException("second operand not a boolean \n");
        }
        else
            throw new ExpressionException("first operand not a boolean \n");
    }

    @Override
    public Type typecheck(MyIDictionary<String, Type> typeEnv) throws ExpressionException {
        Type type1 = this.expression1.typecheck(typeEnv);
        Type type2 = this.expression2.typecheck(typeEnv);
        if(!type1.equals(new BoolType()))
            throw new ExpressionException("First expression not a bool");
        if(!type2.equals(new BoolType()))
            throw new ExpressionException("Second expression not a bool");
        return new BoolType();
    }

    @Override
    public String toString(){
        String op = "";
        if(operation == 1)
            op = " and ";
        if(operation == 2)
            op = " or ";
        return expression1.toString() + op + expression2.toString();
    }
}
