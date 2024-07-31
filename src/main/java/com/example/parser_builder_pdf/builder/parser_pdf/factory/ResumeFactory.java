package com.example.parser_builder_pdf.builder.parser_pdf.factory;

import com.example.parser_builder_pdf.builder.parser_pdf.Util.Utils;
import com.example.parser_builder_pdf.builder.parser_pdf.model.Resume;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

public class ResumeFactory {
    Resume resume = new Resume();
    Utils utils = new Utils();
    Set<String> topics = new LinkedHashSet<>();

    public void parseResume(String text) {
        Collections.addAll(topics, "Experience", "Formação acadêmica", "Education");
        String textList = utils.getItem("Resumo", "Experiência", text, "Summary", topics);

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
}
