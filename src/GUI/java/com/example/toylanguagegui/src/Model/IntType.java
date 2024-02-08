package com.example.toylanguagegui.src.Model;

public class IntType implements Type{

    @Override
    public Value defaultValue() {
        return new IntValue(0);
    }

    @Override
    public Type deepcopy() {
        return new IntType();
    }

    @Override
    public boolean equals(Object obj){
        if(obj == null || obj.getClass() != this.getClass())
            return false;
        return true;
    }

    @Override
    public String toString(){
        return "int";
    }
}
