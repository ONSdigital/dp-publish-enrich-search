package com.github.onsdigital.index.enrichment.service.analyse;

import com.google.common.collect.Sets;
import org.apache.lucene.analysis.core.LowerCaseFilterFactory;
import org.apache.lucene.analysis.util.TokenFilterFactory;

import java.util.HashMap;
import java.util.Set;

public class Filters {

    /**
     * As a result of the StandardTokenizer the comma delimited 'touching' 'word' (i.e. '1.23,123') are extrracted
     * as a ALPHANUM field, so we use a Pattern to remove and break the comma up
     */
    static final String ALPHA_ALPHANUM_REGEX = "^[A-Za-z]\\w+$";
    /**
     * By default we will only be excluding  any text that is <B>NOT</B> an alphanumeric as a result of the StandardTokenizer
     */
    private final static Set<String> DEFAULT_KEEP_TYPES = Sets.newHashSet("<ALPHANUM>");


    static TokenFilterFactory buildAlphaNumFilter() {
        return new ONSTypeTokenFilterFactory(DEFAULT_KEEP_TYPES, true);
    }

    static TokenFilterFactory buildLowerCaseFilter() {
        return new LowerCaseFilterFactory(new HashMap<String, String>());
    }

    static TokenFilterFactory buildRegexFilter() {
        return new ONSRegExPatternFilterFactory(ALPHA_ALPHANUM_REGEX, false);
    }
}