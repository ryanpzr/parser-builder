package com.example.parser_builder_pdf.builder.parser_pdf.model;

import java.util.List;

public class Language {
    private List<String> language;

    public Language() {
    }

    public Language(List<String> language) {
        this.language = language;
    }

    public List<String> getLanguage() {
        return language;
    }

    public void setLanguage(List<String> language) {
        this.language = language;
    }
}
