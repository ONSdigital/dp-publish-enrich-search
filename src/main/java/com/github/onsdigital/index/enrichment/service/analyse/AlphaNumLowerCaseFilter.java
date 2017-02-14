package com.github.onsdigital.index.enrichment.service.analyse;

import java.io.IOException;

/**
 * Filter text to remove any purely numeric 'text'
 *
 * @author James Fawke
 */
class AlphaNumLowerCaseFilter {
    private static final TextAnalyser TEXT_ANALYSER = new TextAnalyser()
            .addFilterFactory(Filters.buildAlphaNumFilter())
            .addFilterFactory(Filters.buildRegexFilter())
            .addFilterFactory(Filters.buildLowerCaseFilter());

    /**
     * @param text
     * @param accumulator Its up to the caller to determine how they want to accumulate the data (i.e. as a set or list or String)
     * @throws IOException
     */
    public void filter(String text, Accumulator accumulator) throws IOException {
        TEXT_ANALYSER.analyse(text, accumulator);
    }

}


