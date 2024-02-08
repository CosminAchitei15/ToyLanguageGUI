package com.example.toylanguagegui.src.Model.Statement;
import com.example.toylanguagegui.src.Controller.ContainerException;
import com.example.toylanguagegui.src.Controller.ExpressionException;
import com.example.toylanguagegui.src.Controller.StatementException;
import com.example.toylanguagegui.src.Model.*;
import com.example.toylanguagegui.src.Model.Expressions.Exp;
import com.example.toylanguagegui.src.utils.MyIDictionary;

public class AssignmentStatement implements IStmt{
    String id;
    Exp exp;
    public AssignmentStatement(String id, Exp newExp){
        this.id = id;
        this.exp = newExp;
    }

    @Override
    public PrgState execute(PrgState state) throws StatementException, ExpressionException, ContainerException {
        MyIDictionary<String, Value> symTable = state.getSymTable();
        if(symTable.isDefined(id)){
            Value val = exp.evaluate(symTable, state.getHeap());
            Type typeid = (symTable.lookup(id)).getType();
            if(val.getType().equals(typeid)){
                symTable.update(id, val);
            }
            else throw new StatementException("expression type and variable type mismatch \n");
        }
        else
            throw new StatementException("variable id not declared \n");
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws StatementException, ExpressionException {
        Type typeVariable = typeEnv.lookup(this.id);
        Type typeExpression = this.exp.typecheck(typeEnv);
        if(typeVariable.equals(typeExpression))
            return typeEnv;
        else throw new StatementException("Assignment: left and right side have different types");
    }

    @Override
    public String toString() {
        return this.id + "=" + this.exp.toString();
    }
}
