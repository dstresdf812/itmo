package com.dstresdf.client.gui;

import java.util.Locale;
import java.util.ResourceBundle;

public class LocalizationManager {
    private Locale locale = new Locale("ru");
    private ResourceBundle resourceBundle = ResourceBundle.getBundle(getBundleName(locale));

    public void setLocale(String language) {
        if (language.equals("Русский")) {
            this.locale = new Locale("ru");
        }
        if (language.equals("Íslenska")) {
            this.locale = new Locale("is");
        }
        if (language.equals("Italiano")) {
            this.locale = new Locale("it");
        }
        if (language.equals("Español")) {
            this.locale = new Locale("es");
        }
        this.resourceBundle = ResourceBundle.getBundle(getBundleName(locale));
    }
    public Locale getLocale() {
        return locale;
    }

    public String get(String key) {
        return resourceBundle.getString(key);
    }

    private String getBundleName(Locale locale) {
        String language = locale.getLanguage();
        if (language.equals("ru")) {
            return "ru";
        }
        if (language.equals("es")) {
            return "es";
        }
        if (language.equals("it")) {
            return "it";
        }
        if (language.equals("is")) {
            return "is";
        }
        return "ru";
    }
}
