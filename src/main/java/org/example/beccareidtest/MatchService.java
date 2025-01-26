package org.example.beccareidtest;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.apache.commons.text.similarity.LevenshteinDistance;

/**
 * Service class for finding the best match for a given input string.
 */
@Service
public class MatchService {

    // I don't love how many private methods there are in this class, could potentially look at separating into another utils class?

    private static final int THRESHOLD = 5;
    private final TextPreprocessorUtils textPreprocessor;
    // initially was initialising in the method however didn't want to initialise multiple times
    private final LevenshteinDistance levenshtein;
    // made this final so that it wasn't being initialised every time for performance reasons
    private final Map<String, JobTitles> wordToJobTitleMap;

    /**
     * Creates a new MatchService with the given TextPreprocessorUtils.
     *
     * @param textPreprocessor the TextPreprocessorUtils to use
     */
    @Autowired
    public MatchService(TextPreprocessorUtils textPreprocessor) {
        this.textPreprocessor = textPreprocessor;
        this.wordToJobTitleMap = buildWordToJobTitleMap();
        this.levenshtein = new LevenshteinDistance();
    }

    /**
     * Finds the best match for the given input.
     *
     * @param input the input string to match
     * @return the best match result
     */
    public MatchResult findBestMatch(String input) {
        if (input == null || input.isBlank()) {
            return new MatchResult("No match found", 0.0);
        }

        String normalizedInput = textPreprocessor.preprocess(input);
        return findClosestMatch(normalizedInput);
    }

    private MatchResult findClosestMatch(String normalizedInput) {
        JobTitles bestMatch = null;
        int minDistance = Integer.MAX_VALUE;
        int maxLength = 0;
        double bestScore = 0;

        // loops through the job titles to find the best match using levenshtein distance
        for (JobTitles jobTitle : JobTitles.values()) {
            String normalizedJobTitle = textPreprocessor.preprocess(jobTitle.getJobTitle());
            int distance = levenshtein.apply(normalizedInput, normalizedJobTitle);
            maxLength = Math.max(normalizedInput.length(), normalizedJobTitle.length());

            if (distance < minDistance && distance <= THRESHOLD) {
                minDistance = distance;
                bestMatch = jobTitle;
            }
        }

        if (bestMatch == null) {
            // if no match is found, check for individual words
            return checkForWords(normalizedInput);
        }

        return new MatchResult(bestMatch.getJobTitle(), calculateQualityScore(minDistance, maxLength));
    }

    private MatchResult checkForWords(String normalizedInput) {

        String[] inputWords = textPreprocessor.splitIntoWords(normalizedInput);
        String bestWord = null;
        JobTitles bestJobTitle = null;
        int minDistance = Integer.MAX_VALUE;
        int maxLength = 0;

        // loops through the input words and the job title words to find the best match
        for (String inputWord : inputWords) {
            for (Map.Entry<String, JobTitles> entry : wordToJobTitleMap.entrySet()) {
                int distance = levenshtein.apply(inputWord, entry.getKey());

                if (distance < minDistance && distance <= THRESHOLD) {
                    // the distnce here will be 0 if one word is a match, hence giving a quality score of 1.0.  I believe this could be improved,
                    // however wasn't sure the best way to reflext a quality score as I also don't think taking the whole string would be fair either.
                    minDistance = distance;
                    bestWord = entry.getKey();
                    bestJobTitle = entry.getValue();
                    maxLength = Math.max(bestWord.length(), normalizedInput.length());
                }
            }
        }

        if (bestJobTitle == null) {
            return new MatchResult("No match found", 0.0);
        }

        return new MatchResult(bestJobTitle.getJobTitle(), calculateQualityScore(minDistance, maxLength));
    }

    private double calculateQualityScore(int distance, int maxLength) {
        return 1 - ((double) distance / maxLength);
    }

    private Map<String, JobTitles> buildWordToJobTitleMap() {
        Map<String, JobTitles> map = new HashMap<>();

        for (JobTitles jobTitle : JobTitles.values()) {
            String[] jobTitleWords = textPreprocessor.splitIntoWords(jobTitle.getJobTitle());
            for (String word : jobTitleWords) {
                map.put(word, jobTitle);
            }
        }

        return map;
    }

}
