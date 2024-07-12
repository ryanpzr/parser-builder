package com.example.parser_builder_pdf.builder.parser_pdf;

import com.example.parser_builder_pdf.builder.parser_pdf.model.Competencias;
import com.example.parser_builder_pdf.builder.parser_pdf.model.Contato;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ParserBuilder {

    File file =  new File("src/main/java/com/example/parser_builder_pdf/sample/filesPdf/Profile.pdf");
    PDDocument document = PDDocument.load(file);
    PDFTextStripper pdfStripper = new PDFTextStripper();
    Competencias competencias = new Competencias();
    Contato contato = new Contato();

    public ParserBuilder() throws IOException {
    }

    public void build() throws IOException {
        String text = pdfStripper.getText(document);

        parseContact(text);
        parseCompetence(text);

        System.out.println(contato.getContato() + competencias.getCompetencia02());

    }

    private void parseCompetence(String text) {
        int startIndex = text.indexOf("Principais competências");
        int stopIndex = text.indexOf("Languages");
        String allCompetences = text.substring(startIndex, stopIndex).trim();
        String[] textList = allCompetences.split("\\r?\\n");
        List<String> compList = new ArrayList<>();

        for (String competence : textList) {
            compList.add(competence);
        }

        competencias.setCompetencia01(String.valueOf(compList.get(1)));
        competencias.setCompetencia02(String.valueOf(compList.get(2)));
        competencias.setCompetencia03(String.valueOf(compList.get(3)));

    }


    public void parseContact(String text) {
        String[] contatosList = text.split("Principais competências");
        String allContacts = contatosList[0];
        String[] textList = allContacts.split("\\r?\\n");

        for (String contact : textList) {
            if (contact.contains("(Mobile)")) {
                String fone = contact.toString();
                contato.setContato(fone);
            } else if (contact.contains("@")) {
                String email = contact.toString();
                contato.setEmail(email);
            } else if (contact.contains("(LinkedIn)")) {
                String link = contact.toString();
                contato.setLinkLinkd(link);
            }
        }
    }
}
