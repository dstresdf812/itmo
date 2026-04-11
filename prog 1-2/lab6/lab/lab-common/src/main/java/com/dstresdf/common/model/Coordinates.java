package com.dstresdf.common.model;

import java.io.Serializable;

public class Coordinates implements Serializable {
    private Long x; //Значение поля должно быть больше -23, Поле не может быть null
    private long y; //Максимальное значение поля: 316

    private final int minX = -23;
    private final int maxY = 316;
    public boolean check() {
        if (this.x == null || x <= minX) {
            return false;
        }
        if (this.y > maxY) {
            return false;
        }
        return true;
    }

    public Long getX() {
        return x;
    }

    public void setX(Long x) {
        this.x = x;
    }

    public Long getY() {
        return y;
    }

    public void setY(Long y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return "(X = " + this.x + "; Y = " + this.y + ")";
    }
}
