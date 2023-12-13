package com.example.clockfx;
import java.io.Serializable;
/**
 *
 * @author User
 */
public class Clock3Arrows extends Clock2Arrows implements Clock, Serializable {
    protected int seconds;
    public Clock3Arrows(){
        super();
        type = Types.THREEARROWS;
        seconds = 0;
    }

    public Clock3Arrows(String _brand, int _price, int _hours, int _minutes, int _seconds, int _id) /*throws TimeException*/{
        super(_brand,_price,_hours,_minutes, _id);
        /*if (_seconds >= 60 || _seconds < 0){
            throw new TimeException(16, "Wrong time argument");
        }*/
        seconds = _seconds;
    }

    @Override
    public String toString() {
        return "| Time: " + String.format("%02d:%02d:%02d", hours, minutes, seconds) + "\n| Brand: " + getBrand() + "\n| Price: " + getPrice() + "\n";
    }

    @Override
    public void skipTime(Types type, int val) throws TimeException{
        switch(type){
            case HOURS -> {
                hours = (hours + val) % 24;
            }
            case MINUTES -> {
                hours = (hours + ((minutes + val)/60)) % 24;
                minutes = (minutes + val) % 60;
            }
            case SECONDS->{
                hours = (hours + (minutes + ((seconds + val)/60))/60) % 24;
                minutes = (minutes + ((seconds + val)/60)) % 60;
                seconds = (seconds + val) % 60;
            }
            default -> throw new TimeException(17, "There are no arrow like this");
        }
    }

    @Override
    public String getBrand() {
        return super.getBrand();
    }

    @Override
    public int getPrice() {
        return super.getPrice();
    }

    @Override
    public void setPrice(int _price){
        super.setPrice(_price);
    }

    @Override
    public void setBrand(String _brand) {
        super.setBrand(_brand);
    }

    @Override
    public String getTime(){
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }

    @Override
    public void setTime(Types type, int val) throws TimeException{
        if (type.equals(Types.SECONDS)) {
            /*if (val < 0 || val >= 60) throw new TimeException(16, "Wrong time argument");*/
            this.seconds = val;
        }
        else {super.setTime(type, val);}
    }
    
    @Override
    public Types getType(){
        return type;
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
                return seconds;
            }
        }
        return 0;
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
