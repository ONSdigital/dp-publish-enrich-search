package com.github.onsdigital.index.enrichment.service.analyse;

import com.beust.jcommander.internal.Lists;
import com.google.common.collect.Sets;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by fawks on 06/02/2017.
 */
public class TextFilterUtilTest {
  private String listOfVegetables = "artichoke,arugula     asparagus artichoke avocado      bamboo_shoots basil    artichoke beans beets";

  @Test
  public void testExtractAlphaNumericString() throws Exception {
    assertEquals("artichoke arugula asparagus artichoke avocado bamboo_shoots basil artichoke beans beets ",
                 TextFilterUtil.extractAlphaNumericString(listOfVegetables));
  }

  @Test
  public void testExtractAlphaNumericOrderedTokens() throws Exception {
    assertEquals(Lists.newArrayList(
        "artichoke arugula asparagus artichoke avocado bamboo_shoots basil artichoke beans beets".split(" ")),
                 TextFilterUtil.extractAlphaNumericOrderedTokens(listOfVegetables));
  }

  @Test
  public void extractAlphaNumericCaseSensitiveUniqueTokens() throws Exception {
    assertEquals(Sets.newHashSet(
        "artichoke arugula asparagus artichoke avocado bamboo_shoots basil artichoke beans beets".split(" ")),
                 TextFilterUtil.extractAlphaNumericCaseSensitiveUniqueTokens(listOfVegetables));

  }

}