package com.c0destudy.sokoban.helper;

import java.awt.*;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Properties;

public class RichProperties extends Properties
{
    public RichProperties(final String path) {
        super();
        try {
            final FileReader file = new FileReader(path);
            load(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getString(final String key) {
        return getProperty(key, "");
    }

    public String getString(final String key, final String defaultValue) {
        return getProperty(key, defaultValue);
    }

    public int getInteger(final String key, final int defaultValue) {
        return Integer.parseInt(getString(key, Integer.toString(defaultValue)));
    }

    public Color getColor(final String key, final String defaultValue) {
        try {
            final String[] strings = getString(key, defaultValue).split(",");
            final int[]    values  = Arrays.stream(strings).mapToInt(Integer::parseInt).toArray();
            switch (values.length) {
                case 3:
                    return new Color(values[0], values[1], values[2]);
                case 4:
                    return new Color(values[0], values[1], values[2], values[3]);
                default:
                    return null;
            }
        } catch (Exception e) {
            return null;
        }
    }
}
