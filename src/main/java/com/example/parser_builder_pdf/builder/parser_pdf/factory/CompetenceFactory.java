package com.example.parser_builder_pdf.builder.parser_pdf.factory;

import com.example.parser_builder_pdf.builder.parser_pdf.Util.Utils;
import com.example.parser_builder_pdf.builder.parser_pdf.model.Competencias;
import java.util.*;

import static com.example.parser_builder_pdf.builder.parser_pdf.ParserBuilder.validateNUll;

public class CompetenceFactory {
    Competencias competencias = new Competencias();
    Set<String> topics = new LinkedHashSet<>();
    Utils utils = new Utils();

    public void parseCompetence(String text) {
        Collections.addAll(topics, "Languages", "Certificações", "Certifications", "Resumo", "Summary");
        String[] textList = utils.getSplitItem("Principais competências", "Linguas", text, "Top Skills", topics);

        if (validateNUll(textList)) return;

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
}
