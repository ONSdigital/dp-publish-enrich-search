package com.github.onsdigital.index.enrichment.service.analyse;

import com.google.common.collect.Sets;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;

/**
 * Created by fawks on 06/02/2017.
 */
public class AlphaNumLowerCaseFilterTest {

  @Test
  public void extractAlphaNumericCommaTokens() throws Exception {
    final Set<String> actual = new HashSet<>();

    Set<String> expected = Sets.newHashSet("hello", "world", "b4nana", "peter", "piper", "bananas", "e45");
    new AlphaNumLowerCaseFilter().filter("Hello 123 world    B4nana 1.234 Peter,Piper,4.5,45,Bananas,E45",
                                         actual::add);
    assertEquals(expected, actual);
  }


  @Test
  public void extractAlphaNumericTokens() throws Exception {
    final Set<String> actual = new HashSet<>();

    Set<String> expected = Sets.newHashSet("hello", "world", "b4nana");
    new AlphaNumLowerCaseFilter().filter("Hello 123 world    B4nana 1.234", actual::add);
    assertEquals(expected, actual);
  }

  @Test
  public void extractAlphaNumericTokensNullText() throws Exception {
    final Set<String> actual = new HashSet<>();

    Set<String> expected = Sets.newHashSet();
    new AlphaNumLowerCaseFilter().filter(null, actual::add);
    assertEquals(expected, actual);
  }

  @Test
  public void extractAlphaNumericTokensBlankText() throws Exception {
    final Set<String> actual = new HashSet<>();

    Set<String> expected = Sets.newHashSet();
    new AlphaNumLowerCaseFilter().filter(null, actual::add);
    assertEquals(expected, actual);
  }

  @Test
  public void extractAlphaNumericTokensCaseSensitive() throws Exception {
    final Set<String> actual = new HashSet<>();

    Set<String> expected = Sets.newHashSet("hello");
    new AlphaNumLowerCaseFilter().filter("Hello 123 hello hellO 1.234", actual::add);
    assertEquals(expected, actual);
  }
}