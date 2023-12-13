package com.example.clockfx;

public class TimeException extends Exception{
    private int i = 0;
    public TimeException(int i, String message) {
        super(message);
        this.i = i;
    }
    @Override
    public String toString() {
        return "TimeException(" + i + "):" + this.getMessage();
    }
}

