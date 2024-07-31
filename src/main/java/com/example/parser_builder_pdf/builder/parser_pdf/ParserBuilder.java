package com.example.parser_builder_pdf.builder.parser_pdf;

import com.example.parser_builder_pdf.builder.parser_pdf.factory.*;
import com.example.parser_builder_pdf.builder.parser_pdf.model.*;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParserBuilder {

    File file =  new File("src/main/java/com/example/parser_builder_pdf/sample/filesPdf/Profile.pdf");
    PDDocument document = PDDocument.load(file);
    PDFTextStripper pdfStripper = new PDFTextStripper();

    Competencias competencias = new Competencias();
    Contato contato = new Contato();
    Language language = new Language();
    Resume resume = new Resume();
    List<Experience> experience = new ArrayList<>();
    List<Formation> formation = new ArrayList<>();

    DataFactory dataFactory = new DataFactory();

    public ParserBuilder() throws IOException {
    }

    public void builder() throws IOException {
        String text = pdfStripper.getText(document);
//        System.out.println(text);

        try {
            dataFactory.createData(text);

        } catch (Exception ex) {
            System.out.println(ex);
        }

        contato = dataFactory.getContato();
        language = dataFactory.getLanguage();
        competencias = dataFactory.getCompetence();
        resume = dataFactory.getResume();
        experience = dataFactory.getExperience();
        formation = dataFactory.getAcademicFactory();
    }

    public void build() throws IOException {
        builder();

        if (contato != null) {
            System.out.println(contato.getContato());
            System.out.println(contato.getEmail());
            System.out.println(contato.getLinkLinkd());
        } else {
            System.out.println("Contato não foi preenchido");
        }

        if (competencias != null) {
            System.out.println(competencias.getCompetence());
        } else {
            System.out.println("Competencias não foram preenchidas");
        }

        if (resume != null) {
            System.out.println(resume.getResume());
        } else {
            System.out.println("Resume não foi preenchido");
        }

        for (Experience ex : experience) {
            System.out.println(ex);
        }

        for (Formation fo : formation) {
            System.out.println(fo);
        }

    }

    public static boolean validateNUll(String[] textList) {
        if (textList == null) {
            return true;
        }
        return false;
    }
}
