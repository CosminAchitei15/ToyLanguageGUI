package com.example.toylanguagegui.src.utils;
import java.util.Stack;
import java.util.List;
public interface MyIStack <T>{
    T pop();
    void push(T v);
    boolean isEmpty();
    List<T> reverse();

}