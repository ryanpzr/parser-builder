package com.example.parser_builder_pdf.builder.parser_pdf.factory;

import com.example.parser_builder_pdf.builder.parser_pdf.Util.Utils;
import com.example.parser_builder_pdf.builder.parser_pdf.model.Experience;
import com.example.parser_builder_pdf.builder.parser_pdf.model.Formation;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

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

        String[] textList = Utils.getSplitItem(text, "--------------education", page, topics);
        List<String> textListAsList = new ArrayList<>(Arrays.asList(Objects.requireNonNull(textList)));

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

        try {
            for (int i = 1; i < textListAsList.size(); i += 2) {

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

        } catch (Exception ex) {
            System.err.println("Não foi possivel fazer o parser da Formação Academica. " + ex);
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
        try {
            Pattern pattern = Pattern.compile("\\d");
            Matcher matcher = pattern.matcher(texto);

            if (!matcher.find()) {
                return true;
            }

            int contador = 0;
            do {
                contador++;
            } while (matcher.find());

            return contador >= 8;

        } catch (PatternSyntaxException e) {
            System.out.println("Erro de sintaxe de regex: " + e.getMessage());
            return true;
        }
    }
}
