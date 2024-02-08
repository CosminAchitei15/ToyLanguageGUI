package com.example.toylanguagegui.src.Model;

public class StringType implements Type{
    @Override
    public Value defaultValue() {
        return new StringValue("");
    }

    @Override
    public Type deepcopy() {
        return new StringType();
    }

    @Override
    public boolean equals(Object obj){
        if(obj == null || obj.getClass() != this.getClass())
            return false;
        return true;
    }

    @Override
    public String toString(){
        return "str";
    }
}
