package com.example.parser_builder_pdf.builder.parser_pdf.model;

public class Formation {

    private String instituicao;
    private String curso;

    public Formation(String instituicao, String curso) {
        this.instituicao = instituicao;
        this.curso = curso;
    }

    public String getInstituicao() {
        return instituicao;
    }

    public void setInstituicao(String instituicao) {
        this.instituicao = instituicao;
    }

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    @Override
    public String toString() {
        return "Formation{" +
                "instituicao='" + instituicao + '\'' +
                ", curso='" + curso + '\'' +
                '}';
    }
}
