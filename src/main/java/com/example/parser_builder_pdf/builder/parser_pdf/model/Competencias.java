package com.example.parser_builder_pdf.builder.parser_pdf.model;

import java.util.List;

public class Competencias {

    private String competence;

    public Competencias() {
    }

    public Competencias(String competence) {
        this.competence = competence;
    }

    public String getCompetence() {
        return competence;
    }

    public void setCompetence(String competence) {
        this.competence = competence;
    }

    @Override
    public String toString() {
        return "Competencias{" +
                "competence='" + competence + '\'' +
                '}';
    }
}
