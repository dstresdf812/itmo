package com.dstresdf.common.model;

public enum FormOfEducation {
    DISTANCE_EDUCATION("ЗАОЧНОЕ"),
    FULL_TIME_EDUCATION("ОЧНОЕ"),
    EVENING_CLASSES("ВЕЧЕРНЕЕ");

    private final String text;
    FormOfEducation(String text) {
        this.text = text;
    }

    public String toString() {
        return this.text;
    }
}
