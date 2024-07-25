package com.example.parser_builder_pdf.builder.parser_pdf.factory;

import com.example.parser_builder_pdf.builder.parser_pdf.model.Competencias;
import com.example.parser_builder_pdf.builder.parser_pdf.model.Contato;
import com.example.parser_builder_pdf.builder.parser_pdf.model.Language;

import java.util.*;

import static com.example.parser_builder_pdf.builder.parser_pdf.ParserBuilder.validateNUll;

public class LanguageFactory {
    Language language = new Language();
    Set<String> topics = new LinkedHashSet<>();

    public void parseLanguage(String text) {
        Collections.addAll(topics, "Certifications", "Resumo", "Summary");
        String[] textList = getSplitItems("Linguas", "Certificações", text);

            if (validateNUll(textList)) return;

            List<String> langList = new ArrayList<>();

            for (int i = 1; i < textList.length; i++) {
                if (i == 4) {
                    break;
                }

                langList.add(textList[i]);
            }

            language.setLanguage(langList);
    }

    public Language getLanguage() {
        return language;
    }

    private String[] getSplitItems(String startIndexPtbr, String stopIndexPtbr, String text) {
        int start = text.indexOf(startIndexPtbr);
        int stop = text.indexOf(stopIndexPtbr);

        if (start == -1) {
            start = text.indexOf("Languages");

            if (start == -1) {
                return null;
            }
        }

        if (stop == -1) {
            for (String data : topics) {
                stop = text.indexOf(data);

                if (stop == -1) {
                    continue;

                } else {
                    String allText = text.substring(start, stop).trim();
                    return allText.split("\\r?\\n");

                }
            }

            String allText = text.substring(start).trim();
            return allText.split("\\r?\\n");
        }

        String allText = text.substring(start, stop).trim();
        return allText.split("\\r?\\n");
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
