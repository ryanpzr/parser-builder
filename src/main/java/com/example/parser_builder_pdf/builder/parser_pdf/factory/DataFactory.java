package com.example.parser_builder_pdf.builder.parser_pdf.factory;

import com.example.parser_builder_pdf.builder.parser_pdf.model.*;

import java.util.List;

public class DataFactory {
    ContactFactory contactFactory = new ContactFactory();
    CompetenceFactory competenceFactory = new CompetenceFactory(this);
    LanguageFactory languageFactory = new LanguageFactory(this);
    ResumeFactory resumeFactory = new ResumeFactory();
    ExperienceFactory experienceFactory = new ExperienceFactory();
    AcademicFactory academicFactory = new AcademicFactory();
    CertificationFactory certificationFactory = new CertificationFactory(this);
    PersonalInfoFactory personalInfoFactory = new PersonalInfoFactory(this);

    public DataFactory buildDataFactory(String text) {
        createData(text);

        return DataFactory.this;
    }

    public void createData(String text) {
        contactFactory.parseContact(text);
        languageFactory.parseLanguage(text);
        competenceFactory.parseCompetence(text);
        resumeFactory.parseResume(text);
//        experienceFactory.parseExperience(text);
        academicFactory.parseAcademicEducation(text);
        certificationFactory.parseCertification(text);
        personalInfoFactory.parsePersonalInfo(text);

    }

    public List<Contato> getContato() {
        return contactFactory.getContato();
    }

    public Resume getResume() {
        return resumeFactory.getResume();
    }

    public List<Competencias> getCompetence() {
        return competenceFactory.getCompetence();
    }

    public List<Language> getLanguage() {
        return languageFactory.getLanguage();
    }

    public List<Experience> getExperience() {
        return experienceFactory.getExperience();
    }

    public List<Formation> getAcademicFactory() {
        return academicFactory.getFormation();
    }

    public List<Certification> getCertification() {
        return certificationFactory.getCertification();
    }
}
