package com.example.parser_builder_pdf.builder.parser_pdf.model;

import java.util.List;

public class Language {
    private String language;

    public Language() {
    }

    public Language(String language) {
        this.language = language;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    @Override
    public String toString() {
        return "Language{" +
                "language='" + language + '\'' +
                '}';
    }
}
