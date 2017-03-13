package com.github.onsdigital.index.enrichment.service.analyse;

import com.beust.jcommander.internal.Lists;
import com.google.common.collect.Sets;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.core.TypeTokenFilter;
import org.apache.lucene.analysis.standard.StandardTokenizerFactory;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.util.TokenizerFactory;
import org.junit.Test;

import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Test.
 */
public class ONSTypeTokenFilterFactoryTest {
  private TokenizerFactory tokenizer = new StandardTokenizerFactory(new HashMap<>());

  @Test
  public void testFactory() {

    ONSTypeTokenFilterFactory onsTypeTokenFilterFactory = new ONSTypeTokenFilterFactory(Sets.newHashSet("<ALPHANUM>"),
                                                                                        true);
    TokenStream tokenStream = onsTypeTokenFilterFactory.create(tokenizer.create());
    assertEquals(TypeTokenFilter.class, tokenStream.getClass());

  }

  @Test
  public void testTypeFilter() throws IOException {

    ONSTypeTokenFilterFactory onsTypeTokenFilterFactory = new ONSTypeTokenFilterFactory(Sets.newHashSet("<ALPHANUM>"),
                                                                                        true);

    Tokenizer tokenizer = this.tokenizer.create();
    tokenizer.setReader(new StringReader("ABC xyz QRst 1234 1.234 asc2 e1010"));
    TokenStream tokenStream = onsTypeTokenFilterFactory.create(tokenizer);
    CharTermAttribute charTermAttribute = tokenizer.addAttribute(CharTermAttribute.class);
    tokenStream.reset();

    List<String> expected = Lists.newArrayList("ABC", "xyz", "QRst", "asc2", "e1010");
    List<String> removed = Lists.newArrayList("1234", "1.234");

    while (tokenStream.incrementToken()) {
      String s = charTermAttribute.toString();
      assertTrue(expected.contains(s));
      assertFalse(removed.contains(s));
    }

  }


}