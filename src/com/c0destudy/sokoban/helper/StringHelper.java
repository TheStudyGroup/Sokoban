package com.c0destudy.sokoban.helper;

public class StringHelper
{
    public static String trimLeft(final String string) {
        return string.replaceAll("^\\s+","");
    }

    public static String trimRight(final String string) {
        return string.replaceAll("\\s+$","");
    }

    public static String trimVertical(final String string) {
        final String[] lines = string.split("\\n");
        int start = 0, end = lines.length - 1;
        for (; start < lines.length; start++) {
            if (!lines[start].trim().equals("")) break;
        }
        for (; end >= 0; end--) {
            if (!lines[end].trim().equals("")) break;
        }
        final StringBuilder result = new StringBuilder();
        for (int i = start; i <= end; i++) {
            result.append(lines[i]);
            if (i != end) {
                result.append("\n");
            }
        }
        return result.toString();
    }
}
