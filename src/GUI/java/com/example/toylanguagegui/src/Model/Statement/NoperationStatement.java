package com.example.toylanguagegui.src.Model.Statement;

import com.example.toylanguagegui.src.Controller.ExpressionException;
import com.example.toylanguagegui.src.Controller.StatementException;
import com.example.toylanguagegui.src.Model.PrgState;
import com.example.toylanguagegui.src.Model.Type;
import com.example.toylanguagegui.src.utils.MyIDictionary;

public class NoperationStatement implements IStmt{

    @Override
    public PrgState execute(PrgState state) throws StatementException {
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws StatementException, ExpressionException {
        return typeEnv;
    }

    @Override
    public String toString() {
        return "";
    }
}
