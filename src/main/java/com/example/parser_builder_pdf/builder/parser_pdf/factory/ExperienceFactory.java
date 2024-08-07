package com.example.parser_builder_pdf.builder.parser_pdf.factory;

import com.example.parser_builder_pdf.builder.parser_pdf.Util.Utils;
import com.example.parser_builder_pdf.builder.parser_pdf.model.Experience;

import java.util.*;

import static com.example.parser_builder_pdf.builder.parser_pdf.ParserBuilder.validateNUll;

public class ExperienceFactory {
    Utils utils = new Utils();
    Set<String> topics = new LinkedHashSet<>();
    List<Experience> experienceList = new ArrayList<>();

    public void parseExperience(String text) {
        try {
            Collections.addAll(topics, "Page");
            String[] textList = Utils.getSplitItem(text,"--------------experiencia", "--------------education",  topics);

            List<String> textListAsList = new ArrayList<>(Arrays.asList(Objects.requireNonNull(textList)));

            Iterator<String> iterator = textListAsList.iterator();
            while (iterator.hasNext()) {
                String data = iterator.next();

                if (data.contains("Experience") || data.contains("Experiência")) {
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

            for (int i = 0; i < textListAsList.size();) {
                String empresa = textListAsList.get(i);
                i++;

                String cargo = textListAsList.get(i);
                i++;

                String data = textListAsList.get(i);
                i++;

                String loc = textListAsList.get(i);
                i++;

                List<String> responsibilities = new ArrayList<>();
                while (i < textListAsList.size() && isResponsibility(textListAsList.get(i))) {
                    responsibilities.add(textListAsList.get(i).trim());
                    i++;
                }

               Experience newExperience = new Experience(empresa, cargo, data, loc, responsibilities);
               experienceList.add(newExperience);

            }

        } catch (Exception ex) {
            System.err.println("Não foi possivel fazer o parser da Experiẽncia. " + ex);
        }

//        for (int i = 1; i < textList.length; i += 4) {
//            if (i + 3 < textList.length) {
//                Experience exp = new Experience(textList[i], textList[i + 1], textList[i + 2], textList[i + 3]);
//                experiences.add(exp);
//            } else {
//                System.err.println("Dados insuficientes para criar uma experiência completa.");
//            }
//        }
    }

    private static boolean isResponsibility(String line) {
        return !line.trim().isEmpty() && line.contains("-");
    }

    public List<Experience> getExperience() {
        return experienceList;
    }
}
