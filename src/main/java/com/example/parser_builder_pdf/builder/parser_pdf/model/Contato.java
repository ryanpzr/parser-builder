package com.example.parser_builder_pdf.builder.parser_pdf.model;

import java.util.List;

public class Contato {

    private String contato;
    private String email;
    private String linkLinkd;
    private String personal;
    private String blog;

    public Contato() {
    }

    public Contato(String contato, String email, String personal, String linkLinkd, String blog) {
        this.contato = contato;
        this.email = email;
        this.personal = personal;
        this.linkLinkd = linkLinkd;
        this.blog = blog;
    }

    public String getContato() {
        return contato;
    }

    public String getLinkLinkd() {
        return linkLinkd;
    }

    public String getEmail() {
        return email;
    }

    public String getPersonal() {
        return personal;
    }

    public String getBlog() {
        return blog;
    }

    public void setContato(String contato) {
        this.contato = contato;
    }

    public void setLinkLinkd(String linkLinkd) {
        this.linkLinkd = linkLinkd;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPersonal(String personal) {
        this.personal = personal;
    }

    public void setBlog(String blog) {
        this.blog = blog;
    }

    @Override
    public String toString() {
        return "Contato{" +
                "contato='" + contato + '\'' +
                ", email='" + email + '\'' +
                ", linkLinkd='" + linkLinkd + '\'' +
                ", personal='" + personal + '\'' +
                ", blog='" + blog + '\'' +
                '}';
    }
}
