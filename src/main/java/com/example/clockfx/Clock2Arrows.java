package com.example.clockfx;

import java.io.Serializable;


public class Clock2Arrows implements Clock,Serializable {
    protected int price;
    protected String brand;
    protected int hours;
    protected int minutes;
    protected Types type;
    protected int id;

    public Clock2Arrows(){
        this.type = Types.TWOARROWS;
        brand = "Unknown brand";
        price = 0;
        hours = 0;
        minutes = 0;
        id = 0;
    }

    public Clock2Arrows(String _brand, int _price, int _hours, int _minutes, int _id) /*throws TimeException*/{
        this.type = Types.TWOARROWS;
        /*if (_hours >= 24 || _hours < 0 || _minutes >= 60 || _minutes < 0 || _price < 0){
            throw new TimeException(16, "Wrong time argument");
        }*/
        brand = _brand;
        price = _price;
        hours = _hours;
        minutes = _minutes;
        id = _id;
    }

    @Override
    public String toString() {
        return "| Time: " + String.format("%02d:%02d", hours, minutes) + "\n| Brand: " + getBrand() + "\n| Price: " + getPrice() + "\n";
    }

    @Override
    public void setInfo(String _brand, int _price) {
        brand = _brand;
        price = _price;
    }

    @Override
    public String getBrand() {
        return brand;
    }

    @Override
    public void setBrand(String _brand){
        brand = _brand;
    }

    @Override
    public int getPrice() {
        return price;
    }

    @Override
    public void setPrice(int _price){
        price = _price;
    }

    @Override
    public String getTime(){
        return String.format("%02d:%02d", hours, minutes);
    }

    @Override
    public int getTime(Types type) {
        switch (type){
            case HOURS -> {
                return hours;
            }
            case MINUTES -> {
                return minutes;
            }
            case SECONDS -> {
                return 0;
            }
        }
        return 0;
    }

    @Override
    public void setTime(Types type, int val) throws TimeException { ////
        switch(type){
            case HOURS -> {
                if (val < 0 || val >= 24) throw new TimeException(16, "Wrong time argument");
                this.hours = val;
            }
            case MINUTES -> {
                if (val < 0 || val >= 60) throw new TimeException(16, "Wrong time argument");
                this.minutes = val;
            }
            case SECONDS -> {
            }
            default -> throw new TimeException(17, "There are no arrow like this");
        }
    }

    @Override
    public void skipTime(Types type, int val) throws TimeException {
        switch(type){
            case HOURS -> {
                hours = (hours + val) % 24;
            }
            case MINUTES -> {
                hours = (hours + ((minutes+ val)/60)) % 24;
                minutes = (val + minutes) % 60;
            }
            case SECONDS -> {
            }
            default -> throw new TimeException(17, "There are no arrow like this");
        }
    }

    @Override
    public Types getType() {
        return type;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int getId() {
        return id;
    }
}

