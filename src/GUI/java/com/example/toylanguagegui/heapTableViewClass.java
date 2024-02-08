package com.example.toylanguagegui;

import com.example.toylanguagegui.src.Model.*;
import javafx.beans.property.SimpleStringProperty;

public class heapTableViewClass {
    private SimpleStringProperty address;
    private SimpleStringProperty value;

    public heapTableViewClass(Integer address, Value value){
        this.address = new SimpleStringProperty(Integer.toString(address));
        this.value = new SimpleStringProperty(value.toString());
    }

    public SimpleStringProperty addressProperty(){
        return this.address;
    }

    public SimpleStringProperty valueProperty(){
        return this.value;
    }

    public String getAddress() {
        return this.address.get();
    }

    public void setAddress(Integer address) {
        this.address.set(String.valueOf(address));
    }

    public String getValue() {
        return this.value.get();
    }

    public void setValue(Value value) {
        this.value.set(value.toString());
    }
}
