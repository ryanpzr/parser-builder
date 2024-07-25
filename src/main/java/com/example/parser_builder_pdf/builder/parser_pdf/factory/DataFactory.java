package com.example.parser_builder_pdf.builder.parser_pdf.factory;

import com.example.parser_builder_pdf.builder.parser_pdf.model.Competencias;
import com.example.parser_builder_pdf.builder.parser_pdf.model.Contato;
import com.example.parser_builder_pdf.builder.parser_pdf.model.Language;
import com.example.parser_builder_pdf.builder.parser_pdf.model.Resume;

public class DataFactory {
    ContactFactory contactFactory = new ContactFactory();
    CompetenceFactory competenceFactory = new CompetenceFactory();
    LanguageFactory languageFactory = new LanguageFactory();
    ResumeFactory resumeFactory = new ResumeFactory();

    public void createData(String text) {
        resumeFactory.parseResume(text);
        languageFactory.parseLanguage(text);
        competenceFactory.parseCompetence(text);
        contactFactory.parseContact(text);

    }

    public Contato getContato() {
        return contactFactory.getContato();
    }

    public Resume getResume() {
        return resumeFactory.getResume();
    }

    public Competencias getCompetence() {
        return competenceFactory.getCompetence();
    }

    public Language getLanguage() {
        return languageFactory.getLanguage();
    }

}
