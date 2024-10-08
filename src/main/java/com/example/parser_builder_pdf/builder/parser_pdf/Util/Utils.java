package com.example.parser_builder_pdf.builder.parser_pdf.Util;

import java.util.Set;

public class Utils {

    public Utils() {
    }

    public static String getItem(String startIndexPtbr, String stopIndexPtbr, String text, Set<String> topics) {
        int start = text.indexOf(startIndexPtbr);
        int stop = text.indexOf(stopIndexPtbr);

        if (start == -1) {
            return null;
        }

        if (stop == -1) {
            for (String data : topics) {
                stop = text.indexOf(data);

                if (stop == -1) {
                    continue;

                } else {
                    String allText = text.substring(start, stop).trim();
                    return allText;
                }
            }

            String allText = text.substring(start).trim();
            return allText;
        }

        String allText = text.substring(start, stop).trim();
        return allText;
    }

    public static String[] getSplitItem(String text, String startIndexPtbr, String stopIndexPtbr, Set<String> topics) {
        int start = text.indexOf(startIndexPtbr);
        int stop = text.indexOf(stopIndexPtbr);

        if (start == -1) {
            return null;
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
}
