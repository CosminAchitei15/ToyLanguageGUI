package com.example.toylanguagegui.src.Model;

public class RefType implements Type{
    Type inner;
    public RefType(Type inner){
        this.inner = inner;
    }
    public RefType(){}

    public Type getInner(){
        return inner;
    }

    @Override
    public boolean equals(Object another){
        if(another instanceof RefType)
            return inner.equals(((RefType) another).getInner());
        else
            return false;
    }

    @Override
    public Value defaultValue() {
        return new RefValue(0, inner);
    }

    @Override
    public String toString() {
        return "Ref(" + inner.toString() + ")";
    }

    @Override
    public Type deepcopy() {
        return new RefType(inner);
    }
}
