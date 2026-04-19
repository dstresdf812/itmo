package extra;

public class ClothingItem {
    private final String name;

    public ClothingItem(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}