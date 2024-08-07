package com.example.parser_builder_pdf.builder.parser_pdf.model;

public class Certification {

    private String certification;

    public Certification() {
    }

    public Certification(String certification) {
        this.certification = certification;
    }

    public String getCertification() {
        return certification;
    }

    public void setCertification(String certification) {
        this.certification = certification;
    }

    @Override
    public String toString() {
        return "Certification{" +
                "certification='" + certification + '\'' +
                '}';
    }
}
