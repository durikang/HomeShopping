package com.global.utils;

public class StringUtils {
    public static String stripHtml(String input) {
        if (input == null) {
            return null;
        }
        return input.replaceAll("<[^>]*>", "");
    }
}