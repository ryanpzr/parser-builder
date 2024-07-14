package com.example.parser_builder_pdf.builder.parser_pdf;

import com.example.parser_builder_pdf.builder.parser_pdf.model.Competencias;
import com.example.parser_builder_pdf.builder.parser_pdf.model.Contato;
import com.example.parser_builder_pdf.builder.parser_pdf.model.Language;
import com.example.parser_builder_pdf.builder.parser_pdf.model.Resume;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class ParserBuilder {

    File file =  new File("src/main/java/com/example/parser_builder_pdf/sample/filesPdf/Profile.pdf");
    PDDocument document = PDDocument.load(file);
    PDFTextStripper pdfStripper = new PDFTextStripper();
    Competencias competencias = new Competencias();
    Contato contato = new Contato();
    Language language = new Language();
    Resume resume = new Resume();

    public ParserBuilder() throws IOException {
    }

    public void build() throws IOException {
        String text = pdfStripper.getText(document);

        parseContact(text);
        parseCompetence(text);
        parseLanguage(text);
        parseResume(text);

        System.out.println(contato.getContato() + "\n" + contato.getEmail() + "\n" + contato.getLinkLinkd() + "\n" +
                competencias.getCompetence() + "\n" + resume.getResume());

    }

    private void parseResume(String text) {
        String textList = getText("Resumo", "Experiência", text);

        if (textList == null) {
            return;
        }

        String[] textSplited = new String[10];
        
        if (textList.contains("Experiência")) {
            textSplited = textList.split("Experiência");
        } else if (textList.contains("Formação acadêmica")) {
            textSplited = textList.split("Formação acadêmica");
        } else {
            textSplited[0] = textList;
        }

        resume.setResume(textSplited[0]);
    }

    private void parseLanguage(String text) {
            String[] textList = getSplitItems("Language", "Certifications", text);

            if (textList == null) {
                return;
            }

            List<String> langList = new ArrayList<>(Arrays.asList(textList).subList(1, textList.length));
            language.setLanguage(langList);
    }

    private void parseCompetence(String text) {
        String[] textList = getSplitItems("Principais competências", "Languages", text);

        if (textList == null) {
            return;
        }

        List<String> compList = new ArrayList<>();

        for (int i = 1; i < 5; i++) {
            if (i == 4) {
                break;
            }
            String comp = Objects.requireNonNull(textList)[i];
            boolean validate = containsNameInEmail(comp, contato.getEmail());
            if (validate) {
                break;
            }
            compList.add(textList[i]);
        }

        competencias.setCompetence(compList);
    }


    public void parseContact(String text) {
        String[] contatosList = text.split("Principais competências");
        String allContacts = contatosList[0];
        String[] textList = allContacts.split("\\r?\\n");

        if (contatosList == null) {
            return;
        }

        for (String contact : textList) {
            if (contact.contains("(Mobile)")) {
                contato.setContato(contact);
            } else if (contact.contains("@")) {

                if (!contact.contains(".com")) {
                    String emailOfc = contact + ".com";
                    contato.setEmail(emailOfc);
                } else {
                    contato.setEmail(contact);
                }

            } else if (contact.contains("www.")) {
                contato.setLinkLinkd(contact);
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
            return allText.split("\\r?\\n");
        }

        String allText = text.substring(start, stop).trim();
        return allText.split("\\r?\\n");
    }

    private String getText(String startIndex, String stopIndex, String text) {
        int start = text.indexOf(startIndex);
        int stop = text.indexOf(stopIndex);

        if (stop == -1) {
            String allText = text.substring(start).trim();
            return allText;
        }

        String allText = text.substring(start, stop).trim();
        return allText;

    }

    private static boolean containsNameInEmail(String name, String email) {
        String[] nameParts = name.toLowerCase().split(" ");

        for (String part : nameParts) {
            if (email.contains(part)) {
                return true;
            }
        }
        return false;
    }
}
