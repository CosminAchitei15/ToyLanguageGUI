package com.example.toylanguagegui;

import com.example.toylanguagegui.src.Model.*;
import javafx.beans.property.SimpleStringProperty;

public class symTableViewClass {
    private SimpleStringProperty name;
    private SimpleStringProperty value;

    public symTableViewClass(String name, Value value){
        this.name = new SimpleStringProperty(name);
        this.value = new SimpleStringProperty(value.toString());
    }
    public SimpleStringProperty nameProperty(){
        return this.name;
    }
    public SimpleStringProperty valueProperty(){
        return this.value;
    }

    public String getName() {
        return this.name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getValue() {
        return this.value.get();
    }

    public void setValue(String value) {
        this.value.set(value);
    }
}
