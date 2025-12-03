package extra;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Costume {
    private final ArrayList<ClothingItem> items;
    private final ClothingState state;
    private final String description;
    private final String name;

    public Costume(String name, ArrayList<ClothingItem> items, ClothingState state) {
        this.name = name;
        this.items = items;
        this.state = state;
        this.description = generateDescription();
    }

    private String generateDescription() {
        if (items.isEmpty()) return "naked :)";
        String s = " состоит из: ";
        for (ClothingItem item : items) {
            s += item.getName() + ", ";
        }
        return s;
    }

    public boolean restrictsMovement() {
        if (state.restrictsMovement()) return true;

        for (ClothingItem item : items) {
            if (item.restrictsMovement()) {
                return true;
            }
        }
        return false;
    }

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }

    public ArrayList<ClothingItem> getItems() {
        return new ArrayList<>(items);
    }

    public int getItemCount() {
        return items.size();
    }

    public void addItem(ClothingItem item) {
        items.add(item);
    }

    public void removeItem(ClothingItem item) {
        items.remove(item);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        Costume costume = (Costume) o;
        return items.equals(costume.items) && state.equals(costume.state) && name.equals(costume.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, items, state);
    }

    @Override
    public String toString() {
        return description + " [ограничение движения: " + restrictsMovement() + "]" + (restrictsMovement() ? " (мешает движению)" : "");
    }
}