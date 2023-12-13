package com.example.clockfx;

public class ClockShopFabrica {
    static ClockShop clockShop = new ClockShop();

    public static ClockShop build() {
        return clockShop;
    }
}
