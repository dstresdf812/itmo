package other;

public enum EyeColor {
    GREEN("ЗЕЛЕНЫЕ"),
    YELLOW("ЖЕЛТЫЕ"),
    BROWN("КАРИЕ");

    private final String text;

    EyeColor(String text) {
        this.text = text;
    }

    public String getText() {
        return this.text;
    }
}
