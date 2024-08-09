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
            Collections.addAll(topics, "--------------publications", "--------------resumo", "--------------experiencia", "--------------education");
            List<String> textList = List.of(Objects.requireNonNull(Utils.getSplitItem( text,"--------------certifications", "--------------honorsAwards", topics)));

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

        for (Contato partContact : contatoList) {
            if (partContact.getPersonal().contains(itemsPart[0]) || partContact.getContato().contains(itemsPart[0]) ||
                    partContact.getLinkLinkd().contains(itemsPart[0]) || partContact.getEmail().contains(itemsPart[0]) ||
                    partContact.getBlog().contains(itemsPart[0])) {
                return true;
            }
        }

        return false;
    }

    public List<Certification> getCertification() {
        return certificationList;
    }
}
