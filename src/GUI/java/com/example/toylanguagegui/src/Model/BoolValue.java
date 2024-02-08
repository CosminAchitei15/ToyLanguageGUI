package com.example.toylanguagegui.src.Model;
import com.example.toylanguagegui.src.Model.*;

public class BoolValue implements Value {
    private final boolean value;

    public BoolValue(boolean value) {
        this.value = value;
    }

    public BoolValue() {
        this.value = false;
    }

    @Override
    public Type getType() {
        return new BoolType();
    }

    @Override
    public Value deepcopy() {
        return new BoolValue(this.value);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || obj.getClass() != this.getClass())
            return false;
        BoolValue object_value = (BoolValue) obj;
        return object_value.value == this.value;
    }

    public boolean getValue() {
        return this.value;
    }

    @Override
    public String toString() {
        return this.value ? "true" : "false";
    }
}