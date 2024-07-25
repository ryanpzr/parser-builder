package com.example.parser_builder_pdf.builder.parser_pdf.factory;

import com.example.parser_builder_pdf.builder.parser_pdf.model.Contato;

import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

public class ContactFactory {
    Contato contato = new Contato();
    Set<String> topics = new LinkedHashSet<>();

    public void parseContact(String text) {
        Collections.addAll(topics, "Top Skills", "Linguas", "Language", "Certificação", "Certification", "Resumo", "Sumary");
        String[] textList = getSplitItems("Contato", "Principais competências", text);

        if (textList == null) {
            return;
        }

        for (String contact : textList) {
            if (contact.contains("(Mobile)")) {
                contato.setContato(contact);

            } else if (contact.contains("@")) {
                isValidateEmail(contact);

            } else if (contact.contains("www.")) {
                contato.setLinkLinkd(contact);
            }
        }
    }

    public Contato getContato() {
        return contato;
    }

    private String[] getSplitItems(String startIndexPtbr, String stopIndexPtbr, String text) {
        int start = text.indexOf(startIndexPtbr);
        int stop = text.indexOf(stopIndexPtbr);

        if (start == -1) {
            start = text.indexOf("Contact");

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

    private void isValidateLink(String contact, String allContacts) {
        if (contact.contains("(LinkedIn)")) {
            contato.setLinkLinkd(contact);
        } else {
            String[] secondPart = allContacts.split(contact);
            String[] secondPartSplitted = secondPart[1].split("\\r?\\n");

            if (secondPartSplitted[1].contains("(LinkedIn)")) {
                contato.setLinkLinkd(contact.concat(secondPartSplitted[1]));

            } else {
                String linkComplete = secondPartSplitted[1] + " " + secondPartSplitted[2];

                contato.setLinkLinkd(contact.concat(linkComplete));
            }
        }
    }

    private void isValidateEmail(String contact) {
        if (!contact.contains(".com")) {
            String emailOfc = contact + ".com";
            contato.setEmail(emailOfc);
        } else {
            contato.setEmail(contact);
        }
    }
}
