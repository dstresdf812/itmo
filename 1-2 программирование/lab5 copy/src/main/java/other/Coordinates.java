package other;

import managers.Checkable;

public class Coordinates implements Checkable {
    private Long x; //Значение поля должно быть больше -23, Поле не может быть null
    private long y; //Максимальное значение поля: 316

    public boolean check() {
        if (this.x == null || x <= -23) {
            return false;
        }
        if (this.y > 316) {
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