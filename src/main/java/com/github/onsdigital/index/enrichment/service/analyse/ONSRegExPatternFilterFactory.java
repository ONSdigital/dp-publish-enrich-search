package com.github.onsdigital.index.enrichment.service.analyse;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.util.TokenFilterFactory;

import java.util.HashMap;

/**
 * Custom ONS TypeTokenFilterFactory as I am surprised and I could find an out of the box solution
 *
 * @author James Fawke
 */
class ONSRegExPatternFilterFactory extends TokenFilterFactory {


  private final String regex;
  private boolean exclude;

  ONSRegExPatternFilterFactory(final String regex, final boolean exclude) {
    super(new HashMap<>());
    this.regex = regex;
    this.exclude = exclude;
  }


  /**
   * Creates the tokenFilter
   *
   * @param input
   * @return TypeTokenFilter as a TokenStream
   */
  @Override
  public TokenStream create(final TokenStream input) {
    return new RegExPatternFilter(input,
                                  this.regex,
                                  this.exclude);
  }
}
