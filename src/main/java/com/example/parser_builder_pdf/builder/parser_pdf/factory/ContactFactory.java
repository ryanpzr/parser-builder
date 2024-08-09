package com.example.parser_builder_pdf.builder.parser_pdf.factory;

import com.example.parser_builder_pdf.builder.parser_pdf.Util.Utils;
import com.example.parser_builder_pdf.builder.parser_pdf.model.Contato;

import java.util.*;

import static com.example.parser_builder_pdf.builder.parser_pdf.ParserBuilder.validateNUll;

public class ContactFactory {
    List<Contato> contatoList = new ArrayList<>();
    Contato contato = new Contato();
    Utils utils = new Utils();
    Set<String> topics = new LinkedHashSet<>();

    String contatoCell = "Informação inexistente";
    String email = "Informação inexistente";
    String linkedin = "Informação inexistente";
    String personal = "Informação inexistente";
    String blog = "Informação inexistente";

    public void parseContact(String text) {
        try {
            Collections.addAll(topics, "--------------languages", "--------------certifications", "--------------honorsAwards", "--------------publications", "--------------resumo", "--------------experiencia", "--------------education");
            String[] textList = Utils.getSplitItem(text, "--------------contact", "--------------competence", topics);

            for (String contact : Objects.requireNonNull(textList)) {
                if (contact.contains("(Mobile)")) {
                    contatoCell = contact;

                } else if (contact.contains("@")) {
                    isValidateEmail(contact);

                } else if (contact.contains("www.linkedin")) {
                    linkedin = isValidateLink(contact, text, "(LinkedIn)");

                } else if (contact.contains("www.facebook") || (contact.contains("www.instagram"))) {
                    blog = isValidateLink(contact, text, "(Blog)");

                } else if (contact.contains("www.")) {
                    personal = isValidateLink(contact, text, "(Personal)");
                }
            }

            Contato contato = new Contato(contatoCell, email, personal, linkedin, blog);
            contatoList.add(contato);

        } catch (Exception ex) {
            System.err.println("Não foi possivel fazer o parser das Certificações. " + ex);
        }
    }

    public List<Contato> getContato() {
        return contatoList;
    }

    private String isValidateLink(String contact, String allContacts, String midia) {
        if (contact.contains(midia)) {
            if (contact.equals(midia)) {
                String linkComplete = " " + contact;
                return contact.concat(linkComplete);
            }

            return contact;

        } else {
            String[] secondPart = allContacts.split(contact);
            String[] secondPartSplitted = secondPart[1].split("\\r?\\n");

            if (secondPartSplitted[1].contains(midia)) {
                if (secondPartSplitted[1].equals(midia)) {
                    String linkComplete = " " + secondPartSplitted[1];
                    return contact.concat(linkComplete);
                }

                return contact.concat(secondPartSplitted[1]);

            } else if (secondPartSplitted[2].contains(midia)){
                String linkComplete = secondPartSplitted[1] + " " + secondPartSplitted[2];
                return contact.concat(linkComplete);

            } else if (secondPartSplitted[3].contains(midia)) {
                String linkComplete = secondPartSplitted[1] + secondPartSplitted[2] + " " + secondPartSplitted[3];
                return contact.concat(linkComplete);

            }
        }

        return "";
    }

    private void isValidateEmail(String contact) {
        if (contact.contains(".com")) {
            email = contact;

        } else {
            email = contact + ".com";
        }
    }
}
