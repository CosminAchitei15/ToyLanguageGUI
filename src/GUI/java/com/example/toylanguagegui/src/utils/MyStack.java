package com.example.toylanguagegui.src.utils;
import java.util.*;

public class MyStack<T> implements MyIStack<T>{
    private Stack<T> stack;
    public MyStack(){
        this.stack = new Stack<T>();
    }
    @Override
    public T pop(){
        return stack.pop();
    }

    @Override
    public void push(T v){
        stack.push(v);
    }
    @Override
    public List<T> reverse(){
        List<T> items;
        items = Arrays.asList((T[])stack.toArray());

        Collections.reverse(items);
        return items;
    }

    @Override
    public boolean isEmpty(){
        return stack.isEmpty();
    }

    @Override
    public String toString(){
        return "MyStack{" + "stack=" + stack + '}';
    }
}
