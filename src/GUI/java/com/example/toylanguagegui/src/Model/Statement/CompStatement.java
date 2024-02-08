package com.example.toylanguagegui.src.Model.Statement;

import com.example.toylanguagegui.src.Controller.ExpressionException;
import com.example.toylanguagegui.src.Controller.StatementException;
import com.example.toylanguagegui.src.Model.PrgState;
import com.example.toylanguagegui.src.Model.Type;
import com.example.toylanguagegui.src.utils.MyIDictionary;
import com.example.toylanguagegui.src.utils.MyIStack;

public class CompStatement implements IStmt{
    private IStmt head;
    private IStmt tail;

    public CompStatement(IStmt head, IStmt tail){
        this.head = head;
        this.tail = tail;
    }

    public IStmt getHead(){
        return this.head;
    }
    public IStmt getTail(){
        return this.tail;
    }

    @Override
    public PrgState execute(PrgState state) throws StatementException {
        MyIStack<IStmt> exeStack = state.getExeStack();
        exeStack.push(tail);
        exeStack.push(head);
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws StatementException, ExpressionException {
        return this.tail.typecheck(this.head.typecheck(typeEnv));
    }

    @Override
    public String toString() {
        return this.head.toString() + "; " + this.tail.toString();
    }
}
