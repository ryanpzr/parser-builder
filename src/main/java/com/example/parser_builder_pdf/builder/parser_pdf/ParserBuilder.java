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

    private final File file =  new File("src/main/java/com/example/parser_builder_pdf/sample/filesPdf/Profile-1.pdf");
    private final PDDocument document = PDDocument.load(file);
    private final PDFTextStripper pdfStripper = new PDFTextStripper();
    private Resume resume = new Resume();
    private PersonalInfo personalInfo = new PersonalInfo();
    private List<Competencias> competencias = new ArrayList<>();
    private List<Language> language = new ArrayList<>();
    private List<Certification> certification = new ArrayList<>();
    private List<Contato> contato = new ArrayList<>();
    private List<Experience> experience = new ArrayList<>();
    private List<Formation> formation = new ArrayList<>();

    DataFactory dataFactory = new DataFactory();

    public ParserBuilder() throws IOException {
    }

    public void builder() throws IOException {
        String text = pdfStripper.getText(document);

        StringBuilder texto = getTextConcated(text);

        DataFactory data = dataFactory.buildDataFactory(texto.toString());

        contato = data.getContato();
        language = data.getLanguage();
        competencias = data.getCompetence();
        resume = data.getResume();
        experience = data.getExperience();
        formation = data.getAcademicFactory();
        certification = data.getCertification();
        personalInfo = data.getPersonalInfo();
    }

    private static StringBuilder getTextConcated(String text) {
        String[] sections = {"Contato", "Contact", "Principais competências", "Top Skills", "Languages", "Certificações", "Certifications", "Honors-Awards", "Publicações", "Publications", "Resumo", "Summary", "Experiência", "Experience", "Formação acadêmica", "Education"};
        String[] originalArray = text.split("\\r?\\n");

        // Contar o número de separadores necessários
        int separadores = sections.length;

        // Criar um novo array com espaço adicional para os separadores
        String[] newArray = new String[originalArray.length + separadores];

        // Índice do novo array
        int newIndex = 0;

        // Iterar pelo array original
        for (int i = 0; i < originalArray.length; i++) {
            // Verificar se a string atual é uma das seções
            String currentLine = originalArray[i];
            if (Arrays.asList(sections).contains(currentLine)) {
                // Adicionar separador antes da seção
                switch (currentLine) {
                    case "Contato":
                    case "Contact":
                        newArray[newIndex++] = "--------------contact";
                        break;
                    case "Principais competências":
                    case "Top Skills":
                        newArray[newIndex++] = "--------------competence";
                        break;
                    case "Linguas":
                    case "Languages":
                        newArray[newIndex++] = "--------------languages";
                        break;
                    case "Certificações":
                    case "Certifications":
                        newArray[newIndex++] = "--------------certifications";
                        break;
                    case "Honors-Awards":
                        newArray[newIndex++] = "--------------honorsAwards";
                        break;
                    case "Publicações":
                    case "Publications":
                        newArray[newIndex++] = "--------------publications";
                        break;
                    case "Resumo":
                    case "Summary":
                        newArray[newIndex++] = "--------------resumo";
                        break;
                    case "Experiência":
                    case "Experience":
                        newArray[newIndex++] = "--------------experiencia";
                        break;
                    case "Formação acadêmica":
                    case "Education":
                        newArray[newIndex++] = "--------------education";
                        break;
                }
            }
            // Adicionar a string ao novo array
            newArray[newIndex++] = originalArray[i];
        }

        // Transformar o array em texto
        StringBuilder texto = new StringBuilder();
        for (String str : newArray) {
            texto.append(str).append("\n");
        }
        return texto;
    }

    public void build() throws IOException {
        builder();

        System.out.println();

        System.out.println(personalInfo.getNome());
        System.out.println(personalInfo.getDescricao());
        System.out.println(personalInfo.getLocalizacao());

        for (Competencias com : competencias) {
            System.out.println(com);
        }

        for (Certification cer : certification) {
            System.out.println(cer);
        }

        for (Language lan : language) {
            System.out.println(lan);
        }

        for (Contato con : contato) {
            System.out.println(con);
        }

        for (Experience ex : experience) {
            System.out.println(ex);
        }

        for (Formation fo : formation) {
            System.out.println(fo);
        }

        if (resume != null) {
            System.out.println(resume.getResume());
        } else {
            System.out.println("Resume não foi preenchido");
        }

    }

    public static boolean validateNUll(String[] textList) {
        if (textList == null) {
            return true;
        }
        return false;
    }
}
