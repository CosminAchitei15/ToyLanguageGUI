package com.example.toylanguagegui.src.Model.Expressions;

import com.example.toylanguagegui.src.Controller.ContainerException;
import com.example.toylanguagegui.src.Controller.ExpressionException;
import com.example.toylanguagegui.src.Model.*;
import com.example.toylanguagegui.src.utils.MyIDictionary;
import com.example.toylanguagegui.src.utils.MyIHeap;

public class RelationalExpression implements Exp{
    Exp expression1;
    Exp expression2;
    int operation;

    public RelationalExpression(Exp exp1, Exp exp2, int op){
        this.expression1 = exp1;
        this.expression2 = exp2;
        this.operation = op;
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
                    return new BoolValue(integer1 < integer2);
                else if(operation == 2)
                    return new BoolValue(integer1 <= integer2);
                else if(operation == 3)
                    return new BoolValue(integer1 == integer2);
                else if(operation == 4)
                    return new BoolValue(integer1 != integer2);
                else if(operation == 5)
                    return new BoolValue(integer1 > integer2);
                else if(operation == 6)
                    return new BoolValue(integer1 >= integer2);
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
        Type type1 = this.expression1.typecheck(typeEnv);
        Type type2 = this.expression2.typecheck(typeEnv);
        if(!type1.equals(new IntType()))
            throw new ExpressionException("First expression not an int");
        if(!type2.equals(new IntType()))
            throw new ExpressionException("Second expression not an int");
        return new BoolType();
    }

    @Override
    public String toString() {
        String op = "";
        if(operation == 1)
            op = " < ";
        if(operation == 2)
            op = " <= ";
        if(operation == 3)
            op = " == ";
        if(operation == 4)
            op = " != ";
        if(operation == 5)
            op = " > ";
        if(operation == 6)
            op = " >= ";
        return expression1.toString() + op + expression2.toString();
    }
}
