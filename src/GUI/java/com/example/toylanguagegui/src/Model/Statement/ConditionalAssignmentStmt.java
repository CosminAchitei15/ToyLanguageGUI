package com.example.toylanguagegui.src.Model.Statement;

import com.example.toylanguagegui.src.Controller.ContainerException;
import com.example.toylanguagegui.src.Controller.ExpressionException;
import com.example.toylanguagegui.src.Controller.FileException;
import com.example.toylanguagegui.src.Controller.StatementException;
import com.example.toylanguagegui.src.Model.BoolType;
import com.example.toylanguagegui.src.Model.Expressions.Exp;
import com.example.toylanguagegui.src.Model.Expressions.VariableExpression;
import com.example.toylanguagegui.src.Model.PrgState;
import com.example.toylanguagegui.src.Model.Type;
import com.example.toylanguagegui.src.utils.MyIDictionary;

import java.io.IOException;

public class ConditionalAssignmentStmt implements IStmt{
    private String v;
    private Exp exp1;
    private Exp exp2;
    private Exp exp3;

    public ConditionalAssignmentStmt(String ve, Exp e1, Exp e2, Exp e3){
        this.v = ve;
        this.exp1 = e1;
        this.exp2 = e2;
        this.exp3 = e3;
    }

    @Override
    public PrgState execute(PrgState state) throws StatementException, ExpressionException, FileException, IOException, ContainerException {
        IStmt toPush = new IfStatement(exp1, new AssignmentStatement(v, exp2), new AssignmentStatement(v, exp3));
        state.getExeStack().push(toPush);
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws StatementException, ExpressionException {
        Type typev = new VariableExpression(v).typecheck(typeEnv);
        Type typexp1 = exp1.typecheck(typeEnv);
        Type typexp2 = exp2.typecheck(typeEnv);
        Type typexp3 = exp3.typecheck(typeEnv);

        if(typexp1.equals(new BoolType()) && typexp2.equals(typev) && typexp3.equals(typev))
            return typeEnv;
        else
            throw new ExpressionException("Expressions inside conditional assignment are invalid");
    }

    @Override
    public String toString() {
        return v + "=" + exp1.toString() + "?" + exp2.toString() + ":" + exp3.toString();
    }
}
