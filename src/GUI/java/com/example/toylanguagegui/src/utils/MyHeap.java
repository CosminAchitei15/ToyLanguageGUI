package com.example.toylanguagegui.src.utils;

import java.util.HashMap;
import java.util.Map;

public class MyHeap<T> implements MyIHeap<T>{
    static int currentaddress = 1;
    Map<Integer, T> heap = new HashMap<>();
    @Override
    public T lookup(int addr) {
        return heap.get(addr);
    }

    @Override
    public boolean isDefined(int addr) {
        return heap.containsKey(addr);
    }

    @Override
    public int put(T value) {
        heap.put(currentaddress, value);
        currentaddress++;
        return currentaddress - 1;
    }

    @Override
    public void update(int addr, T value) {
        heap.put(addr, value);
    }

    @Override
    public Map<Integer, T> getHeap() {
        return heap;
    }

    @Override
    public void setHeap(Map<Integer, T> newHeap) {
        this.heap = newHeap;
    }

    @Override
    public String toString() {
        return "MyHeap{" +
                "heap=" + heap +
                '}';
    }
}
