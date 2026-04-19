package com.dstresdf.common.model;

import java.io.Serializable;

public class Person implements Serializable {
    private String name; //Поле не может быть null, Строка не может быть пустой
    private double weight; //Значение поля должно быть больше 0
    private EyeColor eyeColor; //Поле может быть null
    private HairColor hairColor; //Поле может быть null

    public boolean check() {
        boolean result = true;
        if (this.name == null || this.name == "") {
            result = false;
        }
        if (this.weight <= 0) {
            result = false;
        }
        if (this.eyeColor == null) {
            result = false;
        }
        if (this.hairColor == null) {
            result = false;
        }
        return result;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public EyeColor getEyeColor() {
        return eyeColor;
    }

    public void setEyeColor(EyeColor eyeColor) {
        this.eyeColor = eyeColor;
    }

    public HairColor getHairColor() {
        return this.hairColor;
    }

    public void setHairColor(HairColor hairColor) {
        this.hairColor = hairColor;
    }

    public String toString() {
        return "\n(Имя: " + this.name + "\n"
                + "Вес: " + this.weight + "\n"
                + "Цвет глаз: " + (this.eyeColor == null ? "NULL" + "\n" : this.eyeColor + "\n")
                + "Цвет волос: " + (this.hairColor == null ? "NULL" + "\n" : this.hairColor + ")");
    }
}
