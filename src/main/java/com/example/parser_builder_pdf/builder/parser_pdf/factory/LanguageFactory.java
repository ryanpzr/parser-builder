package com.example.parser_builder_pdf.builder.parser_pdf.factory;

import com.example.parser_builder_pdf.builder.parser_pdf.Util.Utils;
import com.example.parser_builder_pdf.builder.parser_pdf.model.Competencias;
import com.example.parser_builder_pdf.builder.parser_pdf.model.Contato;
import com.example.parser_builder_pdf.builder.parser_pdf.model.Language;

import java.util.*;

import static com.example.parser_builder_pdf.builder.parser_pdf.ParserBuilder.validateNUll;

public class LanguageFactory {
    Utils utils = new Utils();
    Set<String> topics = new LinkedHashSet<>();
    List<Language> langList = new ArrayList<>();
    private DataFactory dataFactory;

    public LanguageFactory(DataFactory dataFactory) {
        this.dataFactory = dataFactory;
    }

    public void parseLanguage(String text) {
        try {
            Collections.addAll(topics, "--------------resumo", "--------------experiencia", "--------------education");
            List<String> textList = List.of(Objects.requireNonNull(Utils.getSplitItem(text, "--------------languages", "--------------certifications", topics)));

            for (int i = 2; i < textList.size(); i++) {
                if (textList.size() > 5) {
                    String comp = Objects.requireNonNull(textList).get(i);
                    boolean validate = containsNameInEmail(comp);
                    if (validate) {
                        break;
                    }
                }

                Language language = new Language(textList.get(i));
                langList.add(language);
            }

        } catch (Exception ex) {
            System.err.println("Não foi possivel fazer o parser da Lingua. " + ex);
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

    public List<Language> getLanguage() {
        return langList;
    }
}
