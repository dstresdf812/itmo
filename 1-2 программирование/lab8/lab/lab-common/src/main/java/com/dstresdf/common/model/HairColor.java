package com.dstresdf.common.model;

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
