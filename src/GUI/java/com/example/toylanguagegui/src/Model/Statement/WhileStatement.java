package com.example.toylanguagegui.src.Model.Statement;

import com.example.toylanguagegui.src.Controller.ContainerException;
import com.example.toylanguagegui.src.Controller.ExpressionException;
import com.example.toylanguagegui.src.Controller.FileException;
import com.example.toylanguagegui.src.Controller.StatementException;
import com.example.toylanguagegui.src.Model.*;
import com.example.toylanguagegui.src.Model.Expressions.Exp;
import com.example.toylanguagegui.src.utils.MyIDictionary;
import com.example.toylanguagegui.src.utils.MyIHeap;
import com.example.toylanguagegui.src.utils.MyIStack;

import java.io.IOException;

public class WhileStatement implements IStmt{
    private Exp expression;
    private IStmt statement;

    public WhileStatement(Exp expression, IStmt statement) {
        this.expression = expression;
        this.statement = statement;
    }

    public Exp getExpression() {
        return expression;
    }

    public void setExpression(Exp expression) {
        this.expression = expression;
    }

    public IStmt getStatement() {
        return statement;
    }

    public void setStatement(IStmt statement) {
        this.statement = statement;
    }

    @Override
    public String toString() {
        return "while(" + expression.toString() + ") {" + statement.toString() + "}";
    }

    @Override
    public PrgState execute(PrgState state) throws StatementException, ExpressionException, FileException, IOException, ContainerException {
        MyIStack<IStmt> exeStack = state.getExeStack();
        MyIDictionary<String, Value> symTable = state.getSymTable();
        MyIHeap<Value> heap = state.getHeap();
        Value expressionResult = expression.evaluate(symTable, heap);

        if (expressionResult.getType() instanceof BoolType) {
            boolean executor = ((BoolValue) expressionResult).getValue();
            if (executor) {
                exeStack.push(new WhileStatement(expression, statement));
                exeStack.push(statement);
            }
            return null;
        }
        else throw new ExpressionException("Expression was not evaluated as a boolean");
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws StatementException, ExpressionException {
        Type typeExp = this.expression.typecheck(typeEnv);
        if(typeExp.equals(new BoolType())){
            this.statement.typecheck(typeEnv.copydict());
            return typeEnv;
        }
        else
            throw new StatementException("While: condition is not a boolean");
    }
}
