package com.github.onsdigital.index.enrichment.service.analyse;

import org.apache.commons.lang3.StringUtils;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.standard.StandardTokenizerFactory;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.util.TokenFilterFactory;
import org.apache.lucene.analysis.util.TokenizerFactory;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Generic analyser for parsing and preparing text prior to indexing
 * Add the Filter factories and then for each invocation the TextAnalyser will instantiate the Tokeniser and Filter pipeline and analyse the code
 *
 * @author James Fawke
 */
class TextAnalyser {

    private final List<TokenFilterFactory> filterFactories = new ArrayList<>();
    private TokenizerFactory tokenizer = new StandardTokenizerFactory(new HashMap<>());

    private List<TokenFilterFactory> getFilterFactories() {
        return filterFactories;
    }

    private TokenizerFactory getTokenizer() {
        return tokenizer;
    }

    /**
     * Default Tokenizer is @see StandardTokenizerFactory. Override if required
     *
     * @param tokenizer @see StandardTokenizerFactory
     * @return this instance
     */
    public TextAnalyser setTokenizer(TokenizerFactory tokenizer) {
        this.tokenizer = tokenizer;
        return this;
    }

    /**
     * Add the Filters to the pipeline, this needs to be in the order you need them to be applied
     *
     * @param filter
     * @return this instance
     */
    public TextAnalyser addFilterFactory(TokenFilterFactory filter) {
        this.filterFactories.add(filter);
        return this;
    }

    /**
     * Analyses (aka Tokenizing and Filtering) text with the lucence filters configured<p/>
     * <pre>
     * <code> TextAnalyser analyser =  new TextAnalyser().addFilterFactory(buildPatternFilter())
     *           .addFilterFactory(buildAlphaNumFilter())
     *           .addFilterFactory(buildLowerCaseFilter());
     *
     *  Collection<String> results = new ArrayList<>();
     *  analyser.filter(text, results::add);
     * </code>
     * </pre>1
     *
     * @param text        the text to filter
     * @param accumulator accumulator is called for each term <i>after</i> filtering
     * @throws IOException
     */
    public void analyse(String text, Accumulator accumulator) throws IOException {

        if (StringUtils.isNotBlank(text)) {
            Tokenizer tokenizer = this.tokenizer.create();
            tokenizer.setReader(new StringReader(text));
            TokenStream tokenStream = tokenizer;
            for (TokenFilterFactory filterFactory : getFilterFactories()) {
                tokenStream = filterFactory.create(tokenStream);
            }
            extractTokens(accumulator, tokenStream);
        }
    }

    /**
     * Extract the tokens built and contained inside the tokenStream into the accumulator
     *
     * @param accumulator
     * @param tokenStream
     * @throws IOException
     */
    private void extractTokens(final Accumulator accumulator, final TokenStream tokenStream) throws IOException {
        CharTermAttribute charTermAttribute = tokenStream.addAttribute(CharTermAttribute.class);
        tokenStream.reset();
        while (tokenStream.incrementToken()) {
            String s = charTermAttribute.toString();
            accumulator.add(s);

        }
    }

}
