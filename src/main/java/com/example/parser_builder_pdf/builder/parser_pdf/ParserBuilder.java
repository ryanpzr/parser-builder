package com.example.parser_builder_pdf.builder.parser_pdf;

import com.example.parser_builder_pdf.builder.parser_pdf.model.*;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParserBuilder {

    File file =  new File("src/main/java/com/example/parser_builder_pdf/sample/filesPdf/Profile.pdf");
    PDDocument document = PDDocument.load(file);
    PDFTextStripper pdfStripper = new PDFTextStripper();
    Competencias competencias = new Competencias();
    Contato contato = new Contato();
    Language language = new Language();
    Resume resume = new Resume();
    List<Experience> experiences = new ArrayList<>();
    List<Formation> academicList = new ArrayList<>();

    public ParserBuilder() throws IOException {
    }

    public void builder() throws IOException {
        String text = pdfStripper.getText(document);

        parseContact(text);
        parseCompetence(text);
        parseLanguage(text);
        parseResume(text);
        parseExperience(text);
        parseAcademicEducation(text);

//        System.out.println(text);

        System.out.println(contato.getContato() + "\n" + contato.getEmail() + "\n" + contato.getLinkLinkd() + "\n" +
                competencias.getCompetence() + "\n" + resume.getResume() + "\n");

        for (Experience ex : experiences) {
            System.out.println(ex);
        }

        for (Formation fo : academicList) {
            System.out.println(fo);
        }

    }

    private void parseAcademicEducation(String text) {
        String[] textList = getSplitItems("Formação acadêmica", "%%", text);
        List<String> textListAsList = new ArrayList<>(Arrays.asList(textList));

        Iterator<String> iterator = textListAsList.iterator();
        while (iterator.hasNext()) {
            String data = iterator.next();
            if (data.contains("Page")) {
                iterator.remove();
            }
        }

        for (int i = 1; i < textListAsList.size(); i += 2) {
            boolean validate = validarNumeros(textList[i + 1]);

            if (!validate) {
                String cursoIncompleto = textListAsList.get(i + 1);
                String secondPart;

                try {
                    secondPart = textListAsList.get(i + 2);
                    String cursoCompletp = cursoIncompleto.concat(" " + secondPart);
                    Formation formation = new Formation(textListAsList.get(i), cursoCompletp);
                    academicList.add(formation);
                    i++;
                    continue;

                } catch (IndexOutOfBoundsException ex) {
                    Formation formation = new Formation(textListAsList.get(i), textListAsList.get(i + 1));
                    academicList.add(formation);
                    continue;
                }
            }

            Formation formation = new Formation(textListAsList.get(i), textListAsList.get(i + 1));
            academicList.add(formation);
        }
    }

    private void parseExperience(String text) {
        String[] textList = getSplitItems("Experiência", "Formação acadêmica", text);

        if (validateNUll(textList)) return;

        List<String> textListAsList = new ArrayList<>(Arrays.asList(textList));

        Iterator<String> iterator = textListAsList.iterator();
        while (iterator.hasNext()) {
            String data = iterator.next();
            if (data.contains("Page")) {
                iterator.remove();
            }
            if (data.contains("   ")) {
                iterator.remove();
            }
        }

        textList = textListAsList.toArray(new String[0]);

        for (int i = 1; i < textList.length; i += 4) {
            if (i + 3 < textList.length) {
                Experience exp = new Experience(textList[i], textList[i + 1], textList[i + 2], textList[i + 3]);
                experiences.add(exp);
            } else {
                System.err.println("Dados insuficientes para criar uma experiência completa.");
            }
        }
    }

    private void parseResume(String text) {
        String textList = getText("Resumo", "Experiência", text);

        if (textList == null) {
            return;
        }

        String[] textSplited = new String[1];
        
        if (textList.contains("Experiência")) {
            textSplited = textList.split("Experiência");
        } else if (textList.contains("Formação acadêmica")) {
            textSplited = textList.split("Formação acadêmica");
        } else {
            textSplited[0] = textList;
        }

        resume.setResume(textSplited[0]);
    }

    private void parseLanguage(String text) {
        String[] textList = getSplitItems("Language", "Certifications", text);

        if (validateNUll(textList)) return;

        List<String> langList = new ArrayList<>();

        for (int i = 1; i < textList.length; i++) {
            if (i == 4) {
                break;
            }
            String comp = Objects.requireNonNull(textList)[i];
            boolean validate = containsNameInEmail(comp, contato.getEmail());
            if (validate) {
                break;
            }
            langList.add(textList[i]);
        }

        language.setLanguage(langList);
    }

    private void parseCompetence(String text) {
        String[] textList = getSplitItems("Principais competências", "Languages", text);

        if (validateNUll(textList)) return;

        List<String> compList = new ArrayList<>();

        for (int i = 1; i < 5; i++) {
            if (i == 4) {
                break;
            }
            String comp = Objects.requireNonNull(textList)[i];
            boolean validate = containsNameInEmail(comp, contato.getEmail());
            if (validate) {
                break;
            }
            compList.add(textList[i]);
        }

        competencias.setCompetence(compList);
    }

    public void parseContact(String text) {
        String[] contatosList = text.split("Principais competências");
        String allContacts = contatosList[0];
        String[] textList = allContacts.split("\\r?\\n");

        if (validateNUll(contatosList)) return;

        for (String contact : textList) {
            if (contact.contains("(Mobile)")) {
                contato.setContato(contact);

            } else if (contact.contains("@")) {
                isValidateEmail(contact);

            } else if (contact.contains("www.")) {
                isValidateLink(contact, allContacts);
            }
        }
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

    private String[] getSplitItems(String startIndex, String stopIndex, String text) {
        int start = text.indexOf(startIndex);
        int stop = text.indexOf(stopIndex);

        if (start == -1) {
            return null;
        }

        if (stop == -1) {
            String allText = text.substring(start).trim();
            return allText.split("\\r?\\n");
        }

        String allText = text.substring(start, stop).trim();
        return allText.split("\\r?\\n");
    }

    private String getText(String startIndex, String stopIndex, String text) {
        int start = text.indexOf(startIndex);
        int stop = text.indexOf(stopIndex);

        if (stop == -1) {
            String allText = text.substring(start).trim();
            return allText;
        }

        String allText = text.substring(start, stop).trim();
        return allText;

    }

    private static boolean containsNameInEmail(String name, String email) {
        String[] nameParts = name.toLowerCase().split(" ");

        for (String part : nameParts) {
            if (email.contains(part)) {
                return true;
            }
        }
        return false;
    }

    private static boolean validateNUll(String[] textList) {
        if (textList == null) {
            return true;
        }
        return false;
    }

    public static boolean validarNumeros(String texto) {
        Pattern pattern = Pattern.compile("\\d");
        Matcher matcher = pattern.matcher(texto);

        int contador = 0;
        while (matcher.find()) {
            contador++;
            if (contador >= 8) {
                return true;
            }
        }
        return false;
    }
}
