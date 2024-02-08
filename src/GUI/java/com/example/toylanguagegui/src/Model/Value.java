package com.example.toylanguagegui.src.Model;

public interface Value {
    Type getType();
    Value deepcopy();
}
