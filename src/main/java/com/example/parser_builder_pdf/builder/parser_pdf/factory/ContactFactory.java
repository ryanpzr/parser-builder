package com.example.parser_builder_pdf.builder.parser_pdf.factory;

import com.example.parser_builder_pdf.builder.parser_pdf.Util.Utils;
import com.example.parser_builder_pdf.builder.parser_pdf.model.Contato;

import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import static com.example.parser_builder_pdf.builder.parser_pdf.ParserBuilder.validateNUll;

public class ContactFactory {
    Contato contato = new Contato();
    Utils utils = new Utils();
    Set<String> topics = new LinkedHashSet<>();

    public void parseContact(String text) {
        Collections.addAll(topics, "Top Skills", "Linguas", "Language", "Certificação", "Certification", "Resumo", "Sumary");
        String[] textList = utils.getSplitItem("Contato", "Principais competências", text, "Contact", topics);

        if (validateNUll(textList)) return;

        for (String contact : textList) {
            if (contact.contains("(Mobile)")) {
                contato.setContato(contact);

            } else if (contact.contains("@")) {
                isValidateEmail(contact);

            } else if (contact.contains("www.")) {
                isValidateLink(contact, text);
            }
        }
    }

    public Contato getContato() {
        return contato;
    }

    private void isValidateLink(String contact, String allContacts) {
        if (contact.contains("(LinkedIn)")) {
            contato.setLinkLinkd(contact);
        } else {
            String[] secondPart = allContacts.split(contact);
            String[] secondPartSplitted = secondPart[1].split("\\r?\\n");

            if (secondPartSplitted[1].contains("(LinkedIn)")) {
                String linkComplete = secondPartSplitted[1] + " " + secondPartSplitted[1];
                contato.setLinkLinkd(contact.concat(linkComplete));

            } else if (secondPartSplitted[2].contains("(LinkedIn)")){
                String linkComplete = secondPartSplitted[1] + " " + secondPartSplitted[2];
                contato.setLinkLinkd(contact.concat(linkComplete));

            } else if (secondPartSplitted[3].contains("(LinkedIn)")) {
                String linkComplete = secondPartSplitted[1] + secondPartSplitted[2] + " " + secondPartSplitted[3];
                contato.setLinkLinkd(contact.concat(linkComplete));

            }
        }
    }

    private void isValidateEmail(String contact) {
        if (contact.contains(".com")) {
            String emailOfc = contact + ".com";
            contato.setEmail(emailOfc);
        } else {
            contato.setEmail(contact);
        }
    }
}
