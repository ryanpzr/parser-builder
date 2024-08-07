package com.example.parser_builder_pdf.builder.parser_pdf.factory;

import com.example.parser_builder_pdf.builder.parser_pdf.Util.Utils;
import com.example.parser_builder_pdf.builder.parser_pdf.model.Certification;
import com.example.parser_builder_pdf.builder.parser_pdf.model.Contato;
import com.example.parser_builder_pdf.builder.parser_pdf.model.Language;

import java.util.*;

import static com.example.parser_builder_pdf.builder.parser_pdf.ParserBuilder.validateNUll;

public class CertificationFactory {
    Set<String> topics = new LinkedHashSet<>();
    List<Certification> certificationList = new ArrayList<>();
    private final DataFactory dataFactory;

    public CertificationFactory(DataFactory dataFactory) {
        this.dataFactory = dataFactory;
    }

    public void parseCertification(String text) {
        try {
            Collections.addAll(topics, "--------------experiencia", "--------------education");
            List<String> textList = List.of(Objects.requireNonNull(Utils.getSplitItem( text,"--------------certifications", "--------------resumo", topics)));

            for (int i = 2; i < textList.size(); i++) {

                String comp = Objects.requireNonNull(textList).get(i);
                boolean validate = containsNameInEmail(comp);
                if (validate) {
                    break;
                }

                Certification certification = new Certification(textList.get(i));
                certificationList.add(certification);
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

    public List<Certification> getCertification() {
        return certificationList;
    }
}
