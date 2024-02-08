package com.example.toylanguagegui.src.Model.Statement;

import com.example.toylanguagegui.src.Controller.ContainerException;
import com.example.toylanguagegui.src.Controller.ExpressionException;
import com.example.toylanguagegui.src.Controller.FileException;
import com.example.toylanguagegui.src.Controller.StatementException;
import com.example.toylanguagegui.src.Model.PrgState;
import com.example.toylanguagegui.src.Model.Type;
import com.example.toylanguagegui.src.utils.MyIDictionary;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface IStmt {
    PrgState execute(PrgState state) throws StatementException, ExpressionException, FileException, IOException, ContainerException;
    MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws StatementException, ExpressionException;
}