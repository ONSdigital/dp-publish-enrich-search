package com.github.onsdigital.index.enrichment.service.analyse;

import com.google.common.collect.Sets;
import org.apache.lucene.analysis.core.LowerCaseFilterFactory;
import org.apache.lucene.analysis.pattern.PatternCaptureGroupFilterFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Filter text to remove any purely numeric 'text'
 *
 * @author James Fawke
 */
class AlphaNumLowerCaseFilter {
    /**
     * As a result of the StandardTokenizer the comma delimited 'touching' 'word' (i.e. '1.23,123') are extrracted
     * as a ALPHANUM field, so we use a Pattern to remove and break the comma up
     */
    public static final String ALPHA_ALPHANUM_REGEX = "([A-Za-z][\\w]+)";
    /**
     * By default we will only be excluding  any text that is <B>NOT</B> an alphanumeric as a result of the StandardTokenizer
     */
    private final static Set<String> DEFAULT_KEEP_TYPES = Sets.newHashSet("<ALPHANUM>");

    private static final TextAnalyser TEXT_ANALYSER = new TextAnalyser()
            .addFilterFactory(buildAlphaNumFilter())
            .addFilterFactory(buildPatternFilter())
            .addFilterFactory(buildLowerCaseFilter());


    /**
     * @param text
     * @param accumulator Its up to the caller to determine how they want to accumulate the data (i.e. as a set or list or String)
     * @throws IOException
     */
    public void filter(String text, Accumulator accumulator) throws IOException {


        TEXT_ANALYSER.analyse(text, accumulator);

    }

    private static ONSTypeTokenFilterFactory buildAlphaNumFilter() {
        return new ONSTypeTokenFilterFactory(DEFAULT_KEEP_TYPES, true);
    }

    private static LowerCaseFilterFactory buildLowerCaseFilter() {
        return new LowerCaseFilterFactory(new HashMap<>());
    }

    private static PatternCaptureGroupFilterFactory buildPatternFilter() {
        Map<String, String> patternArgs = new HashMap<>();
        patternArgs.put("pattern", ALPHA_ALPHANUM_REGEX);
        patternArgs.put("preserve_original", "false");
        return new PatternCaptureGroupFilterFactory(patternArgs);
    }

}


