package com.example.parser_builder_pdf.builder.parser_pdf.factory;

import com.example.parser_builder_pdf.builder.parser_pdf.Util.Utils;
import com.example.parser_builder_pdf.builder.parser_pdf.model.Competencias;
import com.example.parser_builder_pdf.builder.parser_pdf.model.Contato;

import java.util.*;

import static com.example.parser_builder_pdf.builder.parser_pdf.ParserBuilder.validateNUll;

public class CompetenceFactory {
    private final Set<String> topics = new LinkedHashSet<>();
    private final List<Competencias> compList = new ArrayList<>();
    private final DataFactory dataFactory;

    public CompetenceFactory(DataFactory dataFactory) {
        this.dataFactory = dataFactory;
    }

    public void parseCompetence(String text) {
        try {
            Collections.addAll(topics, "--------------certifications", "--------------honorsAwards", "--------------publications", "--------------resumo", "--------------experiencia", "--------------education");
            List<String> textList = List.of(Objects.requireNonNull(Utils.getSplitItem(text, "--------------competence", "--------------languages", topics)));

            for (int i = 2; i < textList.size(); i++) {
                if (textList.size() > 4) {
                    String comp = Objects.requireNonNull(textList).get(i);
                    boolean validate = containsNameInEmail(comp);
                    if (validate) {
                        break;
                    }
                }

                Competencias competence = new Competencias(textList.get(i));
                compList.add(competence);
            }

        } catch (Exception ex) {
            System.err.println("Não foi possivel fazer o parser das Certificações. " + ex);
        }
    }

    public boolean containsNameInEmail(String item) {
        List<Contato> contatoList = dataFactory.getContato();
        String[] itemsPart = item.toLowerCase().split(" ");

        for (String part : itemsPart) {
            for (Contato partContact : contatoList) {
                if (partContact.getPersonal().contains(part) || partContact.getContato().contains(part) ||
                        partContact.getLinkLinkd().contains(part) || partContact.getEmail().contains(part) ||
                        partContact.getBlog().contains(part)) {
                    return true;
                }
            }
        }

        return false;
    }

    public List<Competencias> getCompetence() {
        return compList;
    }
}
