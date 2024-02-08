package com.example.toylanguagegui.src.Model;
import com.example.toylanguagegui.src.Model.*;

public class IntValue implements Value{
    private final int value;

    public IntValue(int value){
        this.value = value;
    }

    public IntValue(){
        this.value = 0;
    }
    @Override
    public Type getType() {
        return new IntType();
    }

    @Override
    public Value deepcopy() {
        return new IntValue(this.value);
    }

    @Override
    public boolean equals(Object obj){
        if(obj == null || obj.getClass() != this.getClass())
            return false;
        IntValue object_value = (IntValue) obj;
        return object_value.value == this.value;
    }

    public int getValue(){
        return this.value;
    }

    @Override
    public String toString() {
        return "" + value;
    }
}
