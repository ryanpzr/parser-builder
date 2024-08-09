package com.example.parser_builder_pdf.builder.parser_pdf.factory;

import com.example.parser_builder_pdf.builder.parser_pdf.Util.Utils;
import com.example.parser_builder_pdf.builder.parser_pdf.model.Certification;
import com.example.parser_builder_pdf.builder.parser_pdf.model.Competencias;
import com.example.parser_builder_pdf.builder.parser_pdf.model.Language;
import com.example.parser_builder_pdf.builder.parser_pdf.model.PersonalInfo;

import java.util.*;

public class PersonalInfoFactory {

    private final Set<String> topics = new LinkedHashSet<>();
    private final PersonalInfo personalInfo = new PersonalInfo();
    private final DataFactory dataFactory;

    public PersonalInfoFactory(DataFactory dataFactory) {
        this.dataFactory = dataFactory;
    }

    public void parsePersonalInfo(String text) {
        if (dataFactory.getCertification() != null) {
            List<Certification> certificationList = dataFactory.getCertification();

            String[] partLastIndexCertification = getIndex(certificationList);

            Collections.addAll(topics, "--------------experiencia", "--------------education");
            String[] textList = Utils.getSplitItem(text, partLastIndexCertification[1], "--------------resumo", topics);

            personalInfo.setNome(Objects.requireNonNull(textList)[1]);

            InsertDatasMethod(textList);

        } else if (dataFactory.getLanguage() != null) {
            List<Language> languageList = dataFactory.getLanguage();

            String[] partLastIndexCertification = getIndex(languageList);

            Collections.addAll(topics, "--------------experiencia", "--------------education");
            String[] textList = Utils.getSplitItem(text, partLastIndexCertification[1], "--------------resumo", topics);

            personalInfo.setNome(Objects.requireNonNull(textList)[1]);

            InsertDatasMethod(textList);

        } else if (dataFactory.getCompetence() != null) {
            List<Competencias> competenciasList = dataFactory.getCompetence();

            String[] partLastIndexCertification = getIndex(competenciasList);

            Collections.addAll(topics, "--------------experiencia", "--------------education");
            String[] textList = Utils.getSplitItem(text, partLastIndexCertification[1], "--------------resumo", topics);

            personalInfo.setNome(Objects.requireNonNull(textList)[1]);

            InsertDatasMethod(textList);
        }

    }

    private void InsertDatasMethod(String[] textList) {
        if (textList.length > 3) {
            personalInfo.setDescricao(textList[2].concat(textList[3]));
            personalInfo.setLocalizacao(textList[4]);
        }

        if (textList.length == 3) {
            personalInfo.setDescricao(textList[2]);
            personalInfo.setLocalizacao(textList[3]);
        }
    }

    private static String[] getIndex(List<?> certificationList) {
        int lastIndex = certificationList.size() - 1;
        return certificationList.get(lastIndex).toString().split("'");
    }

    public PersonalInfo getPersonalInfo() {
        return personalInfo;
    }
}
