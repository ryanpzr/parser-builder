package com.example.parser_builder_pdf.builder.parser_pdf.factory;

import com.example.parser_builder_pdf.builder.parser_pdf.Util.Utils;
import com.example.parser_builder_pdf.builder.parser_pdf.model.Experience;
import com.example.parser_builder_pdf.builder.parser_pdf.model.Formation;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.example.parser_builder_pdf.builder.parser_pdf.ParserBuilder.validateNUll;

public class AcademicFactory {
    Utils utils = new Utils();
    Set<String> topics = new LinkedHashSet<>();
    List<Formation> formationList = new ArrayList<>();

    public List<Formation> getFormation() {
        return formationList;
    }

    public void parseAcademicEducation(String text) {
        Collections.addAll(topics, "Pagina");

        String page = getLastIndexOfPage(text);

        String[] textList = utils.getSplitItem("Formação acadêmica", page, text, "Education", topics);
        List<String> textListAsList = new ArrayList<>(Arrays.asList(textList));

        if (validateNUll(textList)) return;

        Iterator<String> iterator = textListAsList.iterator();
        while (iterator.hasNext()) {
            String data = iterator.next();
            if (data.contains("Formação acadêmica") || data.contains("Education")) {
                iterator.remove();
            }

            if (data.contains("Page")) {
                iterator.remove();
            }
            if (data.contains("   ")) {
                iterator.remove();
            }
            if (data.equals(" ")) {
                iterator.remove();
            }
        }

        for (int i = 0; i < textListAsList.size(); i += 2) {

            boolean validate = validarNumeros(textListAsList.get(i + 1));

            if (!validate) {
                String cursoIncompleto = textListAsList.get(i + 1);
                String secondPart;

                try {
                    secondPart = textListAsList.get(i + 2);
                    String cursoCompletp = cursoIncompleto.concat(" " + secondPart);
                    Formation formation = new Formation(textListAsList.get(i), cursoCompletp);
                    formationList.add(formation);
                    i++;
                    continue;

                } catch (IndexOutOfBoundsException ex) {
                    Formation formation = new Formation(textListAsList.get(i), textListAsList.get(i + 1));
                    formationList.add(formation);
                    continue;
                }
            }

            Formation formation = new Formation(textListAsList.get(i), textListAsList.get(i + 1));
            formationList.add(formation);
        }
    }

    private String getLastIndexOfPage(String text) {
        String[] textSplitted = text.split("\\n");
        LinkedList<String> pageList = new LinkedList<>();

        for (String data : textSplitted) {
            if (data.contains("Page")) {
                pageList.add(data);
            }
        }

        int endIndice = pageList.size() -1;
        return pageList.get(endIndice);
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
