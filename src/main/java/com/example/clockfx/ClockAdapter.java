package com.example.clockfx;

import com.google.gson.*;

import java.lang.reflect.Type;


public class ClockAdapter implements JsonSerializer<Clock>, JsonDeserializer<Clock> {
    @Override
    public JsonElement serialize(Clock clock, Type type, JsonSerializationContext context) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("hour", clock.getTime(Types.HOURS));
        jsonObject.addProperty("min", clock.getTime(Types.MINUTES));
        jsonObject.addProperty("cost", clock.getPrice());
        jsonObject.addProperty("mark", clock.getBrand());
        jsonObject.addProperty("ID", clock.getId());
        jsonObject.addProperty("type", clock.getType().toString());
        if(clock.getType().equals("HMS"))
            jsonObject.addProperty("sec", clock.getTime(Types.SECONDS));
        return jsonObject;
    }
    @Override
    public Clock deserialize(JsonElement json, Type type, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        int hour = jsonObject.get("hour").getAsInt();
        int min = jsonObject.get("min").getAsInt();
        Clock t;
        int cost = jsonObject.get("cost").getAsInt();
        String mark = jsonObject.get("mark").getAsString();
        int id = jsonObject.get("ID").getAsInt();
        if(jsonObject.get("type").getAsString().equals(Types.THREEARROWS.toString()))
        {   int sec = jsonObject.get("sec").getAsInt();
            return t=new Clock3Arrows(mark, cost,hour, min, sec,id);}
        else {return t=new Clock2Arrows(mark, cost,hour, min,id);}
    }


    //@Override
    /*public JsonElement serialize(Clock clock, Type type, JsonSerializationContext context) {
        JsonObject jsonObject = new JsonObject();
        if (clock.getType().toString().equals(Types.THREEARROWS.toString())) {
            jsonObject.addProperty("seconds", clock.getTime(Types.SECONDS));
        }
        jsonObject.addProperty("hours", clock.getTime(Types.HOURS));
        jsonObject.addProperty("minutes", clock.getTime(Types.MINUTES));
        jsonObject.addProperty("price", clock.getPrice());
        jsonObject.addProperty("brand", clock.getBrand());
        jsonObject.addProperty("ID", clock.getId());
        jsonObject.addProperty("type",clock.getType().toString());
        return jsonObject;
    }*/

    //@Override
    /*public Clock deserialize(JsonElement json, Type type, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        int hour = jsonObject.get("hours").getAsInt();
        int min = jsonObject.get("minutes").getAsInt();
        int cost = jsonObject.get("price").getAsInt();
        String mark = jsonObject.get("brand").getAsString();
        int id = jsonObject.get("ID").getAsInt();
        if (jsonObject.get("type").getAsString().equals(Types.TWOARROWS.toString())) {
            int sec = jsonObject.get("seconds").getAsInt();
            return new Clock3Arrows(mark, cost, hour, min, sec, id);
        } else {
            return new Clock2Arrows(mark, cost, hour, min, id);
        }
    }*/
    /*@Override
    public Clock deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        String clockType = jsonObject.get("type").getAsString();
        int hours = jsonObject.get("hours").getAsInt();
        int minutes = jsonObject.get("minutes").getAsInt();
        int price = jsonObject.get("price").getAsInt();
        String brand = jsonObject.get("brand").getAsString();
        int ID = jsonObject.get("ID").getAsInt();
        Clock c;
        *//*if (clockType.equals(Types.THREEARROWS.toString())){
            int seconds = jsonObject.get("seconds").getAsInt();
            try {
                c = new Clock3Arrows(brand, price, hours, minutes, seconds);
                c.setId(ID);
                return c;
            } catch (TimeException e){
                throw new RuntimeException(e);
            }
        }
        else {
            try {
                c = new Clock2Arrows(brand, price, hours, minutes);
                c.setId(ID);
                return c;
            } catch (TimeException e){
                throw new RuntimeException(e);
            }
        }*//*
    }*/
}