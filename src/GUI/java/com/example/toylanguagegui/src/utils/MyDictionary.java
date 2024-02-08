package com.example.toylanguagegui.src.utils;
import java.util.*;
public class MyDictionary<K, V> implements MyIDictionary<K, V> {
    private Map<K, V> map;

    public MyDictionary(){
        map = new HashMap<>();
    }
    @Override
    public V lookup(K key) {
        return map.get(key);
    }

    @Override
    public boolean isDefined(K key) {
        return map.get(key) != null;
    }

    @Override
    public void put(K key, V value) {
        map.put(key, value);
    }

    @Override
    public void update(K key, V value) {
        map.put(key, value);
    }

    @Override
    public void remove(K key) {
        map.remove(key);
    }

    @Override
    public Map<K, V> getMap() {
        return map;
    }

    @Override
    public MyIDictionary<K, V> copydict() {
        MyIDictionary<K, V> dictionary = new MyDictionary<>();
        for(K key:this.map.keySet()){
            dictionary.put(key, map.get(key));
        }
        return dictionary;
    }

    @Override
    public String toString() {
        return "MyDictionary{" +
                "map=" + map +
                '}';
    }
}
