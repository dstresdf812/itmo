package extra;

import java.util.Objects;

public class ClothingItem {
    private final String name;
    private final boolean restrictsMovement;


    public ClothingItem(String name, boolean restrictsMovement) {
        this.name = name;
        this.restrictsMovement = restrictsMovement;
    }

    public String getName() {
        return name;
    }

    public boolean restrictsMovement() {
        return restrictsMovement;
    }

    @Override
    public String toString() {
        return name + (restrictsMovement ? " [ограничивает движение]" : "");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClothingItem that = (ClothingItem) o;
        return restrictsMovement == ((ClothingItem) o).restrictsMovement && name.equals(((ClothingItem) o).name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, restrictsMovement);
    }
}