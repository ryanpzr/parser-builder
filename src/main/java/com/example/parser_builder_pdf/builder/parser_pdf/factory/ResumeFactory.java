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
        try {
            Collections.addAll(topics, "--------------education");
            String textList = Utils.getItem("--------------resumo", "--------------experiencia", text,  topics);

            String[] textListSplitted = textList.split("--------------resumo");

            resume.setResume(textListSplitted[1]);

        } catch (Exception ex) {
            System.err.println("Não foi possivel fazer o parser do Resumo. " + ex);
        }
    }

    public Resume getResume() {
        return resume;
    }
}
