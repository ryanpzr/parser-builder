package com.example.parser_builder_pdf.builder.parser_pdf.factory;

import com.example.parser_builder_pdf.builder.parser_pdf.model.Competencias;
import com.example.parser_builder_pdf.builder.parser_pdf.model.Resume;

import java.util.*;

import static com.example.parser_builder_pdf.builder.parser_pdf.ParserBuilder.validateNUll;

public class CompetenceFactory {
    Competencias competencias = new Competencias();
    Set<String> topics = new LinkedHashSet<>();

    public void parseCompetence(String text) {
        Collections.addAll(topics, "Languages", "Certificações", "Certifications", "Resumo", "Summary");
        String[] textList = getSplitItems("Principais competências", "Linguas", text);

        if (textList == null) {
            return;
        }

        List<String> compList = new ArrayList<>();

        for (int i = 1; i < 5; i++) {
            if (i == 4) {
                break;
            }
            compList.add(textList[i]);
        }

        competencias.setCompetence(compList);

    }

    public Competencias getCompetence() {
        return competencias;
    }

    private String[] getSplitItems(String startIndexPtbr, String stopIndexPtbr, String text) {
        int start = text.indexOf(startIndexPtbr);
        int stop = text.indexOf(stopIndexPtbr);

        if (start == -1) {
            start = text.indexOf("Top Skills");

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
}
