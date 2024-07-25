package com.example.parser_builder_pdf.builder.parser_pdf.factory;

import com.example.parser_builder_pdf.builder.parser_pdf.model.Resume;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

public class ResumeFactory {
    Resume resume = new Resume();
    Set<String> topics = new LinkedHashSet<>();

    public void parseResume(String text) {
        Collections.addAll(topics, "Experience", "Formação acadêmica", "Education");
        String textList = getSplitItems("Resumo", "Experiência", text);

        if (textList == null) {
            return;
        }

        String[] textSplited = new String[1];

        textSplited[0] = textList;

        resume.setResume(textSplited[0]);
    }

    public Resume getResume() {
        return resume;
    }

    private String getSplitItems(String startIndexPtbr, String stopIndexPtbr, String text) {
        int start = text.indexOf(startIndexPtbr);
        int stop = text.indexOf(stopIndexPtbr);

        if (start == -1) {
            start = text.indexOf("Summary");

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
                    return allText;
                }
            }

            String allText = text.substring(start).trim();
            return allText;
        }

        String allText = text.substring(start, stop).trim();
        return allText;
    }
}
