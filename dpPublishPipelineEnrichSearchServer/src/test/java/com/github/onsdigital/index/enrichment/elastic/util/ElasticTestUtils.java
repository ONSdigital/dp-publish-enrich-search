package com.github.onsdigital.index.enrichment.elastic.util;

import com.github.onsdigital.index.enrichment.elastic.api.Request;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;

import java.io.IOException;
import java.io.StringWriter;
import java.nio.charset.Charset;

/**
 * Created by guidof on 09/03/17.
 */
public class ElasticTestUtils {

    public static String extractString(final Request build) throws IOException {
        HttpEntity entity = build.getPayload();
        StringWriter writer = new StringWriter();
        IOUtils.copy(entity.getContent(), writer, Charset.defaultCharset());
        return writer.toString();
    }
}
