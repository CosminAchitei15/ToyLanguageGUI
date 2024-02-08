package com.example.toylanguagegui.src.Model;

public class RefValue implements Value{
    int address;
    Type locationType;

    public RefValue(int address, Type locationType) {
        this.address = address;
        this.locationType = locationType;
    }

    public int getAddress() {
        return address;
    }

    public void setAddress(int address) {
        this.address = address;
    }

    public Type getLocationType() {
        return locationType;
    }

    public void setLocationType(Type locationType) {
        this.locationType = locationType;
    }

    @Override
    public Type getType() {
        return new RefType(locationType);
    }

    @Override
    public Value deepcopy() {
        return new RefValue(address, locationType);
    }

    @Override
    public String toString() {
        return "RefValue{" +
                "address=" + address +
                ", locationType=" + locationType +
                '}';
    }
}
