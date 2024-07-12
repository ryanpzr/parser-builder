package com.example.parser_builder_pdf.builder.parser_pdf.model;

import java.util.List;

public class Contato {

    private String contato;
    private String email;
    private String linkLinkd;

    public Contato() {
    }

    public Contato(String contato, String email, String linkLinkd, List<Competencias> competencias, String language) {
        this.contato = contato;
        this.email = email;
        this.linkLinkd = linkLinkd;
    }

    public String getContato() {
        return contato;
    }

    public void setContato(String contato) {
        this.contato = contato;
    }

    public String getLinkLinkd() {
        return linkLinkd;
    }

    public void setLinkLinkd(String linkLinkd) {
        this.linkLinkd = linkLinkd;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
