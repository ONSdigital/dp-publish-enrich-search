package com.github.onsdigital.index.enrichment.service;

import com.beust.jcommander.internal.Lists;
import com.github.onsdigital.index.enrichment.model.payload.UpdatePageDataPayload;
import com.github.onsdigital.index.enrichment.model.payload.UpdateResourcePayload;
import com.github.onsdigital.index.enrichment.model.request.EnrichPageRequest;
import com.github.onsdigital.index.enrichment.model.request.EnrichResourceRequest;
import com.github.onsdigital.index.enrichment.service.extract.ContentExtractor;
import com.github.onsdigital.index.enrichment.service.extract.ContentExtractorFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import static com.github.onsdigital.index.enrichment.model.ModelEnum.CONTENT;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

/**
 * Created by fawks on 16/02/2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class ExtractContentServiceTest {

    @Mock
    ContentExtractorFactory extractFactory;
    @Mock
    ContentExtractor extractor;
    private ExtractContentService service = new ExtractContentService();

    @Before
    public void init() {
        service.setExtractorFactory(extractFactory);
    }


    @Test
    public void getExtractorFactory() throws Exception {
        ExtractContentService service = new ExtractContentService();
        ContentExtractorFactory expected = new ContentExtractorFactory();
        assertEquals(expected,
                     service.setExtractorFactory(expected)
                            .getExtractorFactory());
    }

    @Test
    public void extractEnrichResourceRequest() throws Exception {

        when(extractFactory.getInstance(anyString(), anyString())).thenReturn(extractor);
        List<String> testContent = Lists.newArrayList("testContent");
        when(extractor.extract()).thenReturn(testContent);

        EnrichResourceRequest request = new EnrichResourceRequest();
        String s3Location = "s3://location";
        String testURI = "testURI";

        request.setS3Location(s3Location);
        request.setUri(testURI);

        UpdateResourcePayload payload = service.extract(request);

        assertNotNull(payload);
        assertNotNull(payload.getContent());
        assertEquals(testContent,
                     payload.getContent()
                            .get(CONTENT.getIndexDocProperty()));
        assertEquals(s3Location, payload.getS3Location());
        assertEquals(testURI, payload.getUri());
    }

    @Test
    public void extractEnrichPageRequest() throws Exception {


        String fileLocation = "/fileLocation";
        String fileContent = "{\"test\":\"content1\",\"test2\":\"content2\"}";


        UpdatePageDataPayload payload = service.extract(new EnrichPageRequest()
                                                                .setFileContent(fileContent)
                                                                .setFileLocation(fileLocation));
        assertEquals("content1\tcontent2",
                     payload.getContent()
                            .get(0)
                            .trim());
        assertEquals(fileLocation, payload.getUri());
    }

}