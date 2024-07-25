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
    List<Experience> experiences = new ArrayList<>();
    List<Formation> academicList = new ArrayList<>();

    DataFactory dataFactory = new DataFactory();

    public ParserBuilder() throws IOException {
    }

    public void builder() throws IOException {
        String text = pdfStripper.getText(document);
        dataFactory.createData(text);

        contato = dataFactory.getContato();
        language = dataFactory.getLanguage();
        competencias = dataFactory.getCompetence();
        resume = dataFactory.getResume();

//        parseExperience(text);
//        parseAcademicEducation(text);

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

//      for (Experience ex : experiences) {
//          System.out.println(ex);
//      }
//
//      for (Formation fo : academicList) {
//          System.out.println(fo);
//      }

    }

    private void parseAcademicEducation(String text) {
        String[] textList = getSplitItems("Formação acadêmica", "%%", text);
        List<String> textListAsList = new ArrayList<>(Arrays.asList(textList));

        Iterator<String> iterator = textListAsList.iterator();
        while (iterator.hasNext()) {
            String data = iterator.next();
            if (data.contains("Page")) {
                iterator.remove();
            }
        }

        for (int i = 1; i < textListAsList.size(); i += 2) {
            boolean validate = validarNumeros(textList[i + 1]);

            if (!validate) {
                String cursoIncompleto = textListAsList.get(i + 1);
                String secondPart;

                try {
                    secondPart = textListAsList.get(i + 2);
                    String cursoCompletp = cursoIncompleto.concat(" " + secondPart);
                    Formation formation = new Formation(textListAsList.get(i), cursoCompletp);
                    academicList.add(formation);
                    i++;
                    continue;

                } catch (IndexOutOfBoundsException ex) {
                    Formation formation = new Formation(textListAsList.get(i), textListAsList.get(i + 1));
                    academicList.add(formation);
                    continue;
                }
            }

            Formation formation = new Formation(textListAsList.get(i), textListAsList.get(i + 1));
            academicList.add(formation);
        }
    }

    private void parseExperience(String text) {
        String[] textList = getSplitItems("Experiência", "Formação acadêmica", text);

        if (validateNUll(textList)) return;

        List<String> textListAsList = new ArrayList<>(Arrays.asList(textList));

        Iterator<String> iterator = textListAsList.iterator();
        while (iterator.hasNext()) {
            String data = iterator.next();
            if (data.contains("Page")) {
                iterator.remove();
            }
            if (data.contains("   ")) {
                iterator.remove();
            }
        }

        textList = textListAsList.toArray(new String[0]);

        for (int i = 1; i < textList.length; i += 4) {
            if (i + 3 < textList.length) {
                Experience exp = new Experience(textList[i], textList[i + 1], textList[i + 2], textList[i + 3]);
                experiences.add(exp);
            } else {
                System.err.println("Dados insuficientes para criar uma experiência completa.");
            }
        }
    }

    private String[] getSplitItems(String startIndex, String stopIndex, String text) {
        int start = text.indexOf(startIndex);
        int stop = text.indexOf(stopIndex);

        if (start == -1) {
            return null;
        }

        if (stop == -1) {
            String allText = text.substring(start).trim();
            allText.split("\\r?\\n");
        }

        String allText = text.substring(start, stop).trim();
        return allText.split("\\r?\\n");
    }

    public static boolean validateNUll(String[] textList) {
        if (textList == null) {
            return true;
        }
        return false;
    }

    public static boolean validarNumeros(String texto) {
        Pattern pattern = Pattern.compile("\\d");
        Matcher matcher = pattern.matcher(texto);

        int contador = 0;
        while (matcher.find()) {
            contador++;
            if (contador >= 8) {
                return true;
            }
        }
        return false;
    }
}
