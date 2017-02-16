package com.github.onsdigital.index.enrichment.service.util;

import com.beust.jcommander.internal.Lists;
import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.File;
import java.util.List;

import static com.github.onsdigital.index.enrichment.service.util.ResourceUtils.deriveUriFromJsonFileLocation;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by fawks on 14/02/2017.
 */
public class ResourceUtilsTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(ResourceUtilsTest.class);
    private final ResourceLoader loader = new DefaultResourceLoader();


    @Test
    public void testReadResourceToString() throws Exception {
        String fileLoc = "src/test/resources/tobeornottobe.txt";
        Resource resource = buildResource(fileLoc);
        String actual = ResourceUtils.readResourceToString(resource);
        String expected = FileUtils.readFileToString(new File(fileLoc));
        assertEquals(expected, actual);
    }

    @Test
    public void testIsJsonFileTrue() throws Exception {
        String fileLoc = "src/test/resources/tobeornottobe.json";
        Resource resource = buildResource(fileLoc);
        assertTrue(ResourceUtils.isJsonFile(resource));
    }

    @Test
    public void testIsJsonFileFalse() throws Exception {
        String fileLoc = "src/test/resources/tobeornottobe.txt";
        Resource resource = buildResource(fileLoc);
        assertFalse(ResourceUtils.isJsonFile(resource));
    }

    private Resource buildResource(final String fileLoc) {
        String resourceUrl = "file:" + fileLoc;
        return loader.getResource(resourceUrl);
    }

    @Test
    public void substituteFileName() throws Exception {
        String fileName = ResourceUtils.substituteFileName("foo/foo1/foo2/foo3.txt", "blah/blah1/blah2/blah3.txt");
        assertEquals("foo/foo1/foo2/blah3.txt", fileName);
    }

    @Test
    public void concatenateTrailingAndStartingSlash() throws Exception {
        String baseDir = "/foo/foo1/";
        String child = "/bar/bar1/bar2.txt";
        String actual = ResourceUtils.concatenate(baseDir, child);
        assertEquals("/foo/foo1/bar/bar1/bar2.txt", actual);
    }

    @Test
    public void concatenateTrailingSlash() throws Exception {
        String baseDir = "/foo/foo1/";
        String child = "bar/bar1/bar2.txt";
        String actual = ResourceUtils.concatenate(baseDir, child);
        assertEquals("/foo/foo1/bar/bar1/bar2.txt", actual);
    }

    @Test
    public void concatenateStartingSlash() throws Exception {
        String baseDir = "/foo/foo1";
        String child = "/bar/bar1/bar2.txt";
        String actual = ResourceUtils.concatenate(baseDir, child);
        assertEquals("/foo/foo1/bar/bar1/bar2.txt", actual);
    }

    @Test
    public void concatenateStartingNoSlash() throws Exception {
        String baseDir = "/foo/foo1";
        String child = "bar/bar1/bar2.txt";
        String actual = ResourceUtils.concatenate(baseDir, child);
        assertEquals("/foo/foo1/bar/bar1/bar2.txt", actual);
    }


    @Test
    public void testNoProtocolDefinition() throws Exception {
        assertTrue(ResourceUtils.noProtocolDefinition("/blah/blah1"));
        assertTrue(ResourceUtils.noProtocolDefinition("g:/blah/blah1"));
        assertTrue(ResourceUtils.noProtocolDefinition("s://blah/blah1"));
        assertFalse(ResourceUtils.noProtocolDefinition("s3://blah/blah1"));
        assertFalse(ResourceUtils.noProtocolDefinition("file:blah/blah1"));
        assertFalse(ResourceUtils.noProtocolDefinition("http://blah/blah1"));
        assertFalse(ResourceUtils.noProtocolDefinition("classpath:blah/blah1"));
    }


    @Test
    public void testDeriveUriFromJsonFileLocationRootJsons() {
        String expected = "/";
        List<String> testFileLocations = Lists.newArrayList("/",
                                                            "",
                                                            "data.json",
                                                            "Data.json",
                                                            "/DATA.JSON",
                                                            "/data.json",
                                                            "/datA.json");
        testFileLocations.forEach(fl -> {
            String actual = deriveUriFromJsonFileLocation(fl);
            assertEquals(expected, actual);
        });

    }

    @Test
    public void testDeriveUriFromJsonFileLocationRootChartJsons() {
        String expected = "/ABCDEF";
        List<String> testFileLocations = Lists.newArrayList("ABCDEF.json",
                                                            "/ABCDEF.JSON",
                                                            "/ABCDEF.jsoN");
        testFileLocations.forEach(fl -> {
            String actual = deriveUriFromJsonFileLocation(fl);
            assertEquals(expected, actual);
        });

    }


    @Test
    public void testDeriveUriFromJsonFileLocationBusinessChartsDataJsons() {
        String expected = "/business/charts";
        List<String> testFileLocations = Lists.newArrayList("/business/charts/",
                                                            "business/charts",
                                                            "/business/charts/data.json",
                                                            "/business/charts/DATA.json",
                                                            "business/charts/data.JSON",
                                                            "business/charts/data.jSon");
        testFileLocations.forEach(fl -> {
            String actual = deriveUriFromJsonFileLocation(fl);
            assertEquals(expected, actual);
        });

    }

    @Test
    public void testDeriveUriFromJsonFileLocationBusinessChartsJsons() {
        String expected = "/business/charts/123456";
        List<String> testFileLocations = Lists.newArrayList("/business/charts/123456.json",
                                                            "business/charts/123456.json",
                                                            "business/charts/123456.JSON",
                                                            "business/charts/123456.jSon");
        testFileLocations.forEach(fl -> {
            String actual = deriveUriFromJsonFileLocation(fl);
            assertEquals(expected, actual);
        });
    }
}