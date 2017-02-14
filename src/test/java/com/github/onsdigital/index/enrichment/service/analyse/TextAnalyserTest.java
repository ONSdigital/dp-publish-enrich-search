package com.github.onsdigital.index.enrichment.service.analyse;

import com.beust.jcommander.internal.Lists;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import java.util.List;

import static com.github.onsdigital.index.enrichment.service.analyse.Filters.buildAlphaNumFilter;
import static com.github.onsdigital.index.enrichment.service.analyse.Filters.buildLowerCaseFilter;
import static com.github.onsdigital.index.enrichment.service.analyse.Filters.buildRegexFilter;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by James Fawke on 06/02/2017.
 *
 * @author James Fawke
 */
public class TextAnalyserTest {

  @Test
  public void testAnalyseAlphaNumber() throws Exception {
    TextAnalyser textAnalyser = new TextAnalyser()
        .addFilterFactory(buildAlphaNumFilter());

    List<String> expected = Lists.newArrayList("ABC", "xyz", "Poiu", "E10101", "12A", "T0mat0es", "12E06");
    List<String> removed = Lists.newArrayList("1.23", "123", "12342", "12");

    textAnalyser.analyse("ABC xyz. Poiu, 123 ,12342 12E06 1.23:E10101 12A T0mat0es", (t) -> {
      assertTrue(t + " not in", expected.remove(t));
      assertFalse(t + " should be removed", removed.remove(t));
    });
    assertEquals(StringUtils.join(expected, ","), 0, expected.size());
    assertEquals(StringUtils.join(removed, ","), 4, removed.size());
  }


  @Test
  public void testAnalyse() throws Exception {
    TextAnalyser textAnalyser = new TextAnalyser()
        .addFilterFactory(buildAlphaNumFilter())
        .addFilterFactory(buildRegexFilter())
        .addFilterFactory(buildLowerCaseFilter());

    List<String> expected = Lists.newArrayList("abc", "xyz", "poiu", "e10101", "a9999", "t0mat0es");
    List<String> removed = Lists.newArrayList("1.23", "123", "12342", "12E06", "12", "42b");
    int expectedRemoved = removed.size();
    textAnalyser.analyse("ABC xyz. Poiu, ,123,12342, 12E06 1.23:E10101 a9999 T0mat0es 42B", (t) -> {
      assertTrue(t + " not in", expected.remove(t));
      assertFalse(t + " should be removed", removed.remove(t));
    });
    assertEquals(StringUtils.join(expected, ","), 0, expected.size());
    assertEquals(StringUtils.join(removed, ","), expectedRemoved, removed.size());
  }

  @Test
  public void testAnalysePattern() throws Exception {
    TextAnalyser textAnalyser = new TextAnalyser()
        .addFilterFactory(buildRegexFilter());

    List<String> expected = Lists.newArrayList("ABC", "xyz", "Poiu", "E10101", "a9999", "T0mat0es");
    List<String> removed = Lists.newArrayList("1.23", "123", "12E06", "12342", "12", "42B");
    int expectedRemoved = removed.size();
    textAnalyser.analyse("ABC xyz. Poiu:1.235, 123 12342, 12E06 1.23:E10101 a9999 T0mat0es 42B", (t) -> {
      assertTrue("'" + t + "' not in", expected.remove(t));
      assertFalse(t + " should be removed", removed.remove(t));
    });
    assertEquals(StringUtils.join(expected, ","), 0, expected.size());
    assertEquals(StringUtils.join(removed, ","), expectedRemoved, removed.size());
  }
}