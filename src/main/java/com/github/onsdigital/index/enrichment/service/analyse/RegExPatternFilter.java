package com.github.onsdigital.index.enrichment.service.analyse;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.util.FilteringTokenFilter;

import java.io.IOException;
import java.util.regex.Pattern;

/**
 * Created by James Fawke removes terms that <b>DON'T</b> match the filter
 */
public class RegExPatternFilter extends FilteringTokenFilter {

    private final Pattern pattern;
    private final CharTermAttribute termAtt = addAttribute(CharTermAttribute.class);
    private boolean exclude;

    /**
     * Create a new {@link FilteringTokenFilter}.
     *
     * @param in      the {@link TokenStream} to consume
     * @param pattern reg expression of items that need to be removed
     * @param exclude when true the matching terms will be excluded, default is for non-matching items to be excluded
     */
    public RegExPatternFilter(final TokenStream in, String pattern, final boolean exclude) {
        super(in);
        this.pattern = Pattern.compile(pattern);
        this.exclude = exclude;
        ;

    }

    @Override
    protected boolean accept() throws IOException {
        return exclude != pattern.matcher(termAtt.toString())
                                 .matches();
    }

}
