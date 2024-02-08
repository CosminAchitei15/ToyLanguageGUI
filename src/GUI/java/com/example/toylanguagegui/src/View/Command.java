package com.example.toylanguagegui.src.View;

import com.example.toylanguagegui.src.Controller.ContainerException;
import com.example.toylanguagegui.src.Controller.ExpressionException;
import com.example.toylanguagegui.src.Controller.StatementException;

import java.io.IOException;

public abstract class Command {
    private String key, description;
    public Command(String key, String description){
        this.key = key;
        this.description = description;
    }
    public abstract void execute() throws StatementException, ContainerException, IOException, ExpressionException;
    public String getKey(){
        return key;
    }
    public String getDescription(){
        return description;
    }
}
