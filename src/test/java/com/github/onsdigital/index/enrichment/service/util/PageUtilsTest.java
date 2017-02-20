package com.github.onsdigital.index.enrichment.service.util;

import com.github.onsdigital.index.enrichment.model.ModelEnum;
import com.github.onsdigital.index.enrichment.model.Page;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Test.
 */
public class PageUtilsTest {
    @Test
    public void testAttachDownload() throws Exception {
        String testFileName = "TestFileName";
        String testContent = "TestContent";

        Map<String, Object> downloadMap = buildDownloadMap(testFileName, testContent);
        Page expected = new Page();

        Page actual = PageUtils.attachDownload(downloadMap, expected);
        assertEquals(expected, actual);
        List<Map<String, Object>> actualDownloads = (List<Map<String, Object>>) actual.getSource()
                                                                                      .get(ModelEnum.DOWNLOADS.getIndexDocProperty());
        assertNotNull(actualDownloads);
        assertEquals(1, actualDownloads.size());
        assertEquals(testFileName,
                     actualDownloads.get(0)
                                    .get(ModelEnum.FILE.getIndexDocProperty()));
        assertEquals(testContent,
                     actualDownloads.get(0)
                                    .get(ModelEnum.CONTENT.getIndexDocProperty()));
    }

    @Test
    public void testAttachDownloadUpdateToExisting() throws Exception {
        String fileName1 = "fileName1";
        String content1 = "content1";
        String fileName2 = "fileName1";
        String content2 = "content2";

        Map<String, Object> downloadMap1 = buildDownloadMap(fileName1, content1);
        Map<String, Object> downloadMap2 = buildDownloadMap(fileName2, content2);

        Page expected = new Page();
        PageUtils.attachDownload(downloadMap1, expected);
        Page actual = PageUtils.attachDownload(downloadMap2, expected);

        assertEquals(expected, actual);
        List<Map<String, Object>> actualDownloads = (List<Map<String, Object>>) actual.getSource()
                                                                                      .get(ModelEnum.DOWNLOADS.getIndexDocProperty());
        assertNotNull(actualDownloads);
        assertEquals(1, actualDownloads.size());
        assertEquals(fileName1,
                     actualDownloads.get(0)
                                    .get(ModelEnum.FILE.getIndexDocProperty()));
        assertNotEquals(content1,
                        actualDownloads.get(0)
                                       .get(ModelEnum.CONTENT.getIndexDocProperty()));
        assertEquals(content2,
                     actualDownloads.get(0)
                                    .get(ModelEnum.CONTENT.getIndexDocProperty()));
    }

    @Test
    public void testAttachDownloadAddToExisting() throws Exception {
        String fileName1 = "fileName1";
        String content1 = "content1";
        String fileName2 = "fileName2";
        String content2 = "content2";

        Map<String, Object> downloadMap1 = buildDownloadMap(fileName1, content1);
        Map<String, Object> downloadMap2 = buildDownloadMap(fileName2, content2);

        Page expected = new Page();
        PageUtils.attachDownload(downloadMap1, expected);
        Page actual = PageUtils.attachDownload(downloadMap2, expected);

        assertEquals(expected, actual);
        List<Map<String, Object>> actualDownloads = (List<Map<String, Object>>) actual.getSource()
                                                                                      .get(ModelEnum.DOWNLOADS.getIndexDocProperty());
        assertNotNull(actualDownloads);
        assertEquals(2, actualDownloads.size());
        assertEquals(fileName1,
                     actualDownloads.get(0)
                                    .get(ModelEnum.FILE.getIndexDocProperty()));
        assertEquals(content1,
                     actualDownloads.get(0)
                                    .get(ModelEnum.CONTENT.getIndexDocProperty()));

        assertEquals(fileName2,
                     actualDownloads.get(1)
                                    .get(ModelEnum.FILE.getIndexDocProperty()));
        assertEquals(content2,
                     actualDownloads.get(1)
                                    .get(ModelEnum.CONTENT.getIndexDocProperty()));
    }

    private Map<String, Object> buildDownloadMap(final String testOrigFileName, final String testOrigContent) {
        Map<String, Object> downloadMap = new HashMap<>();
        downloadMap.put(ModelEnum.FILE.getIndexDocProperty(), testOrigFileName);
        downloadMap.put(ModelEnum.CONTENT.getIndexDocProperty(), testOrigContent);
        return downloadMap;
    }

}