package com.example.parser_builder_pdf.builder.parser_pdf.model;

import java.util.List;

public class Experience {

    private String empresa;
    private String cargo;
    private String data;
    private String localizacao;
    private List<String> competencias;

    public Experience(String empresa, String cargo, String data, String localizacao, List<String> competencias) {
        this.empresa = empresa;
        this.cargo = cargo;
        this.data = data;
        this.localizacao = localizacao;
        this.competencias = competencias;
    }

    public Experience() {
    }

    public List<String> getCompetencias() {
        return competencias;
    }

    public void setCompetencias(List<String> competencias) {
        this.competencias = competencias;
    }

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(String localizacao) {
        this.localizacao = localizacao;
    }

    @Override
    public String toString() {
        return "Experience{" +
                "empresa='" + empresa + '\'' +
                ", cargo='" + cargo + '\'' +
                ", data='" + data + '\'' +
                ", localizacao='" + localizacao + '\'' +
                '}';
    }
}
