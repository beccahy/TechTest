package org.example.beccareidtest;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

/**
 * Utility class for preprocessing text.
 */
@Component
public class TextPreprocessorUtils {

    /**
     * Preprocesses the input text by removing special characters and converting it to lowercase.
     *
     * @param input the input text to preprocess
     * @return the preprocessed text
     */
    public String preprocess(String input) {
        if (input == null) {
            return null;
        }
        return removeSpecialCharacters(input).toLowerCase().trim();
    }

    /**
     * Splits the input text into individual words and converts to lower case.
     *
     * @param input the input text to split
     * @return an array of individual words
     */
    public String[] splitIntoWords(String input) {
        if (input == null || input.isBlank()) {
            return new String[0];
        }
        return input.toLowerCase().trim().split("\\s+");
    }

    private String removeSpecialCharacters(String input) {
        return input.replaceAll("[^a-zA-Z0-9\\s]", "").trim();
    }
}