package com.github.onsdigital.index.enrichment.service.util;

import com.github.onsdigital.index.enrichment.service.analyse.AlphaNumLowerCaseFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

/**
 * Helper Utils that wraps the AlphaNumLowerCaseFilter, that makes it easy to switch between extracting filtered tokens as a single long string, a list or a set.
 */

public class TextFilterUtil {

    private TextFilterUtil() {
        //DO NOT INSTANTIATE
    }

    /**
     * Extracts the tokens in order and removes inter-non-alphanumeric tokens
     *
     * @param text
     * @return
     * @throws IOException
     */
    public static String extractAlphaNumericString(String text) throws IOException {
        StringBuilder results = new StringBuilder();
        new AlphaNumLowerCaseFilter().filter(text,
                                             s -> results.append(s)
                                                         .append(" "));
        return results.toString();
    }

    /**
     * Extracts all the AlphaNumeric tokens in order and leaves duplicates
     *
     * @param text
     * @return
     * @throws IOException
     */
    public static Collection<String> extractAlphaNumericOrderedTokens(String text) throws IOException {

        Collection<String> results = new ArrayList<>();
        new AlphaNumLowerCaseFilter().filter(text, results::add);
        return results;
    }

    /**
     * Extracts all distinct AlphaNumeric Tokens and order is ignored
     *
     * @param text
     * @return
     * @throws IOException
     */
    public static Collection<String> extractAlphaNumericCaseSensitiveUniqueTokens(String text) throws IOException {

        Collection<String> results = new HashSet<>();
        new AlphaNumLowerCaseFilter().filter(text, results::add);
        return results;
    }


}
