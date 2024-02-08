package com.example.toylanguagegui.src.Model.Statement;

import com.example.toylanguagegui.src.Controller.ExpressionException;
import com.example.toylanguagegui.src.Controller.StatementException;
import com.example.toylanguagegui.src.Model.PrgState;
import com.example.toylanguagegui.src.Model.*;
import com.example.toylanguagegui.src.utils.MyIDictionary;

public class VariableDeclarationStatement implements IStmt{
    private String symbolName;
    private Type type;

    public VariableDeclarationStatement(String SymbolTable, Type type){
        this.symbolName = SymbolTable;
        this.type = type;
    }

    @Override
    public PrgState execute(PrgState state) throws StatementException {
        MyIDictionary<String, Value> symTable = state.getSymTable();
        if(symTable.isDefined(symbolName))
            throw new StatementException("symbol already defined");
        else{
            Value val;
            if(type.equals(new BoolType()))
                val = new BoolValue();
            else if(type.equals(new IntType()))
                val = new IntValue();
            else if(type.equals(new StringType()))
                val = new StringValue();
            else if(type instanceof RefType refType)
                val = new RefValue(0, refType.getInner());
            else
                throw new StatementException("invalid type");
            symTable.update(symbolName, val);
        }
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws StatementException, ExpressionException {
        typeEnv.put(this.symbolName, this.type);
        return typeEnv;
    }

    @Override
    public String toString() {
        return type.toString() + " " + this.symbolName;
    }
}
