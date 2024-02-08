package com.example.toylanguagegui.src.Model;

public class BoolType implements Type{
    @Override
    public Value defaultValue() {
        return new BoolValue(false);
    }

    @Override
    public Type deepcopy() {
        return new BoolType();
    }

    @Override
    public boolean equals(Object obj){
        if(obj == null || obj.getClass() != this.getClass())
            return false;
        return true;
    }

    @Override
    public String toString(){
        return "bool";
    }
}
