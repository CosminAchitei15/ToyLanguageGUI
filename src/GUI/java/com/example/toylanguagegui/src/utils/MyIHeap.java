package com.example.toylanguagegui.src.utils;

import java.util.Map;

public interface MyIHeap<T> {
    T lookup(int addr);
    boolean isDefined(int addr);
    int put(T value);
    void update(int addr, T value);
    Map<Integer, T> getHeap();
    public void setHeap(Map<Integer, T> newHeap);
}
