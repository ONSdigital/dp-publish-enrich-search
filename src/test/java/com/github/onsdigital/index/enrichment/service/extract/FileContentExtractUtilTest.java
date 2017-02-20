package com.github.onsdigital.index.enrichment.service.extract;

import com.github.onsdigital.index.enrichment.exception.FileExtractException;
import com.github.onsdigital.index.enrichment.service.util.ResourceUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.tika.metadata.HttpHeaders;
import org.apache.tika.metadata.Metadata;
import org.junit.Test;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by fawks on 06/02/2017.
 */
public class FileContentExtractUtilTest {

  private final ResourceLoader rl = new DefaultResourceLoader();

  @Test (expected = FileExtractException.class)
  public void extractTextNotExist() throws Exception {
    Resource resource = rl.getResource("classpath:/doesnotexist.txt");
    List<String> strings = FileContentExtractUtil.extractText(resource);
    assertNull(strings);

  }

  @Test
  public void extractTextFromText() throws Exception {
    Resource resource = rl.getResource("classpath:/tobeornottobe.txt");
    List<String> strings = FileContentExtractUtil.extractText(resource);
    String s = ResourceUtils.readResourceToString(resource);
    assertEquals(s.trim(), strings.get(0)
                                  .trim());

  }

  @Test
  public void extractTextFromCSV() throws Exception {
    Resource resource = rl.getResource("classpath:/tobeornottobe.csv");
    List<String> strings = FileContentExtractUtil.extractText(resource);

    assertEquals(1, StringUtils.countMatches(strings.get(0), " be "));
    assertEquals(1, StringUtils.countMatches(strings.get(0), " mind "));
    assertEquals(1, StringUtils.countMatches(strings.get(0), " puzzles "));
    assertEquals(1, StringUtils.countMatches(strings.get(0), " to "));

  }

  @Test
  public void extractTextFromJson() throws Exception {
    Resource resource = rl.getResource("classpath:/tobeornottobe.json");
    List<String> strings = FileContentExtractUtil.extractText(rl.getResource("classpath:/tobeornottobe.json"));

    String s = ResourceUtils.readResourceToString(rl.getResource("classpath:/tobeornottobe.txt"));
    assertTrue(strings.get(0)
                      .startsWith("To be, or not to be:"));
    assertTrue(strings.get(0)
                      .trim()
                      .endsWith("Be all my sins remember’d."));


  }

  @Test
  public void isTabularContentTypeTrue() throws Exception {

    assertTrue(isTabularContentType("application/vnd.oasis.opendocument.spreadsheet"));
    assertTrue(isTabularContentType("text/csv"));
    assertTrue(isTabularContentType("application/vnd.ms-excel"));
    assertTrue(isTabularContentType("application/vnd.ms-excel.sheet.binary.macroenabled.12"));
    assertTrue(isTabularContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
    assertTrue(isTabularContentType("application/vnd.lotus-1-2-3"));

  }

  @Test
  public void isTabularContentTypeFalse() throws Exception {
    assertFalse(isTabularContentType("application/x-mswrite"));
    assertFalse(isTabularContentType("application/vnd.sun.xml.writer"));
    assertFalse(isTabularContentType("application/vnd.stardivision.writer"));
    assertFalse(isTabularContentType("application/msword"));
    assertFalse(isTabularContentType("application/vnd.lotus-wordpro"));
    assertFalse(isTabularContentType("application/vnd.openxmlformats-officedocument.wordprocessingml.document"));
  }

  private boolean isTabularContentType(final String contentType) {
    String key = HttpHeaders.CONTENT_TYPE;
    List<Metadata> metaDataCollection = buildMetadata(contentType, key);
    return FileContentExtractUtil.isTabularContentType(metaDataCollection);
  }

  @Test
  public void isTabularFileNameTrue() throws Exception {

    assertTrue(isTabularFileName("word.xlsx"));
    assertTrue(isTabularFileName("spreadsheet.123"));
    assertTrue(isTabularFileName("write.ods"));
    assertTrue(isTabularFileName("write.csv"));
    assertTrue(isTabularFileName("write.xml"));
  }

  @Test
  public void isTabularFileNameFalse() throws Exception {

    assertFalse(isTabularFileName("spreadsheet.txt"));
    assertFalse(isTabularFileName("word.docx"));
    assertFalse(isTabularFileName("write.odt"));
  }

  private boolean isTabularFileName(final String fileName) {
    String key = "resourceName";
    List<Metadata> metaDataCollection = buildMetadata(fileName, key);
    return FileContentExtractUtil.isTabularFileName(metaDataCollection);
  }

  private List<Metadata> buildMetadata(final String fileName, final String key) {
    List<Metadata> metaDataCollection = new ArrayList<>();
    Metadata metadata = new Metadata();
    metadata.set(key, fileName);
    metaDataCollection.add(metadata);
    return metaDataCollection;
  }

}