package com.example.parser_builder_pdf.builder.parser_pdf.factory;

import com.example.parser_builder_pdf.builder.parser_pdf.Util.Utils;
import com.example.parser_builder_pdf.builder.parser_pdf.model.Competencias;
import com.example.parser_builder_pdf.builder.parser_pdf.model.Contato;
import com.example.parser_builder_pdf.builder.parser_pdf.model.Language;

import java.util.*;

import static com.example.parser_builder_pdf.builder.parser_pdf.ParserBuilder.validateNUll;

public class LanguageFactory {
    Language language = new Language();
    Utils utils = new Utils();
    Set<String> topics = new LinkedHashSet<>();

    public void parseLanguage(String text) {
        Collections.addAll(topics, "Certifications", "Resumo", "Summary");
        String[] textList = utils.getSplitItem("Linguas", "Certificações", text, "Languages", topics);

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
}
