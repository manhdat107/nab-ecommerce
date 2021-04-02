package com.vdc.ecommerce.model;

public enum Color {
    RED("red"),
    BLUE("blue"),
    BLACK("black"),
    YELLOW("yellow"),
    PINK("pink");

    private final String value;

    Color(String value) {
        this.value = value;
    }

    public static Color lookup(String abbr) {
        for (Color v : values()) {
            if (v.value.equals(abbr.toLowerCase())) {
                return v;
            }
        }
        return null;
    }
}
