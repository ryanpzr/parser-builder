package com.example.parser_builder_pdf.builder.parser_pdf.factory;

import com.example.parser_builder_pdf.builder.parser_pdf.model.*;

import java.util.List;

public class DataFactory {
    ContactFactory contactFactory = new ContactFactory();
    CompetenceFactory competenceFactory = new CompetenceFactory();
    LanguageFactory languageFactory = new LanguageFactory();
    ResumeFactory resumeFactory = new ResumeFactory();
    ExperienceFactory experienceFactory = new ExperienceFactory();
    AcademicFactory academicFactory = new AcademicFactory();

    public void createData(String text) {
        resumeFactory.parseResume(text);
        languageFactory.parseLanguage(text);
        competenceFactory.parseCompetence(text);
        contactFactory.parseContact(text);
        experienceFactory.parseExperience(text);
        academicFactory.parseAcademicEducation(text);

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

    public List<Experience> getExperience() {
        return experienceFactory.getExperience();
    }

    public List<Formation> getAcademicFactory() {
        return academicFactory.getFormation();
    }
}
