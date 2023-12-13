package com.example.clockfx;

public interface Clock {
    String getBrand();
    int getPrice();
    void setPrice(int _price);
    void setBrand(String _brand);
    Types getType();
    void setId(int id);
    int getId();
    String getTime();
    int getTime(Types type);
    void setTime(Types type, int val) throws TimeException;
    void setInfo(String _brand, int _price) throws TimeException;
    void skipTime(Types type, int val) throws TimeException;
}