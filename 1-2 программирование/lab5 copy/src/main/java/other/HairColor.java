package other;

public enum HairColor {
    BLACK("ЧЕРНЫЕ"),
    BLUE("СИНИЕ"),
    WHITE("БЕЛЫЙ"),
    BROWN("РУСЫЙ");

    private final String text;

    HairColor(String text) {
        this.text = text;
    }

    public String getText() {
        return this.text;
    }
}
