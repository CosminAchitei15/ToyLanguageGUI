package com.example.toylanguagegui.src.Model;

public class StringValue implements Value{
    private final String value;
    public StringValue(String value){
        this.value = value;
    }
    public StringValue(){
        this.value = "";
    }

    @Override
    public Type getType() {
        return new StringType();
    }

    @Override
    public Value deepcopy() {
        return new StringValue(this.value);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || obj.getClass() != this.getClass())
            return false;
        StringValue object_value = (StringValue) obj;
        return object_value.value.equals(this.value);
    }

    public String getValue() {
        return this.value;
    }

    @Override
    public String toString() {
        return this.value;
    }
}
