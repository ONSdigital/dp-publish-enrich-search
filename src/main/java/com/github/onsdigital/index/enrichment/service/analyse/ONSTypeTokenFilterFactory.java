package com.github.onsdigital.index.enrichment.service.analyse;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.core.TypeTokenFilter;
import org.apache.lucene.analysis.util.TokenFilterFactory;

import java.util.HashMap;
import java.util.Set;

/**
 * Custom ONS TypeTokenFilterFactory as Lucene version expects types to be defined in a file not in the arguments
 * @author James Fawke
 */
class ONSTypeTokenFilterFactory extends TokenFilterFactory {

    private Set<String> types;
    private boolean useWhiteList;

    protected ONSTypeTokenFilterFactory(final Set<String> types, final boolean useWhiteList) {
        super(new HashMap<>());
        this.types = types;
        this.useWhiteList = useWhiteList;
    }


    /**
     * Creates the tokenFilter
     *
     * @param input
     * @return TypeTokenFilter as a TokenStream
     */
    @Override
    public TokenStream create(final TokenStream input) {
        return new TypeTokenFilter(input,
                                   this.types,
                                   this.useWhiteList);
    }
}
