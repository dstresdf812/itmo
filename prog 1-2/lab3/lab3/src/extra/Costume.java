package extra;

import java.util.ArrayList;
import java.util.Objects;

public class Costume {
    private final ArrayList<ClothingItem> items;
    private final ClothingState state;

    public Costume(ArrayList<ClothingItem> items, ClothingState state) {
        this.items = new ArrayList<>(items);
        this.state = state;
    }

    public boolean restrictsMovement() {
        return state.restrictsMovement();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        Costume costume = (Costume) o;
        return items.equals(costume.items) && state.equals(costume.state);
    }

    @Override
    public int hashCode() {
        return Objects.hash(items, state);
    }

    @Override
    public String toString() {
        return "костюм состоит из: " + items;
    }
}