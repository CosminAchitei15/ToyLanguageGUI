package com.example.toylanguagegui.src.Model.Expressions;

import com.example.toylanguagegui.src.Controller.ContainerException;
import com.example.toylanguagegui.src.Controller.ExpressionException;
import com.example.toylanguagegui.src.Model.IntType;
import com.example.toylanguagegui.src.Model.IntValue;
import com.example.toylanguagegui.src.Model.Type;
import com.example.toylanguagegui.src.Model.Value;
import com.example.toylanguagegui.src.utils.MyIDictionary;
import com.example.toylanguagegui.src.utils.MyIHeap;

public class ArithmeticExpression implements Exp{

    Exp expression1, expression2;
    int operation;

    public ArithmeticExpression(Character _op, Exp exp1, Exp exp2) throws ExpressionException {
        switch(_op){
            case '+':
                this.operation = 1;
                break;
            case '-':
                this.operation = 2;
                break;
            case '*':
                this.operation = 3;
                break;
            case '/':
                this.operation = 4;
                break;
            default:
                throw new ExpressionException("Invalid operator");
        }
        this.expression1 = exp1;
        this.expression2 = exp2;
    }

    @Override
    public Value evaluate(MyIDictionary<String, Value> symTable, MyIHeap<Value> heap) throws ExpressionException, ContainerException {
        Value value1, value2;
        value1 = expression1.evaluate(symTable, heap);
        if(value1.getType().equals(new IntType())){
            value2 = expression2.evaluate(symTable, heap);
            if(value2.getType().equals(new IntType())){
                IntValue int_val1 = (IntValue) value1;
                IntValue int_val2 = (IntValue) value2;
                int integer1;
                int integer2;
                integer1 = int_val1.getValue();
                integer2 = int_val2.getValue();
                if(operation == 1)
                    return new IntValue(integer1 + integer2);
                else if(operation == 2)
                    return new IntValue(integer1 - integer2);
                else if(operation == 3)
                    return new IntValue(integer1 * integer2);
                else if(operation == 4) {
                    if (integer2 == 0)
                        throw new ExpressionException("division by 0 \n");
                    else
                        return new IntValue(integer1 / integer2);
                }
                else
                    throw new ExpressionException("invalid operation");
            }
            else
                throw new ExpressionException("second operand is not an integer \n");
        }
        else
            throw new ExpressionException("first operand is not an integer \n");
    }

    @Override
    public Type typecheck(MyIDictionary<String, Type> typeEnv) throws ExpressionException {
        Type type1;
        Type type2;
        type1 = this.expression1.typecheck(typeEnv);
        type2 = this.expression2.typecheck(typeEnv);

        if(type1.equals(new IntType())) {
            if (type2.equals(new IntType())) {
                return new IntType();
            } else
                throw new ExpressionException("Invalid type for second expression");
        }else
            throw new ExpressionException("Invalid type of first expression");
    }

    @Override
    public String toString(){
        String op = "";
        switch(operation){
            case 1:
                op = " + ";
                break;
            case 2:
                op = " - ";
                break;
            case 3:
                op = " * ";
                break;
            case 4:
                op = " / ";
                break;
        }
        return expression1.toString() + op + expression2.toString();
    }
}
