package com.github.onsdigital.index.enrichment.service.extract;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

/**
 * Created by fawks on 03/02/2017.
 */
@Component
public class ContentExtractorFactory {

    @Autowired
    private ResourceLoader resourceLoader;
    @Value("${root-dir}")
    private String rootFolder;

    public String getRootFolder() {
        return rootFolder;
    }

    public ContentExtractorFactory setRootFolder(final String rootFolder) {
        this.rootFolder = rootFolder;
        return this;
    }

    public ContentExtractor getInstance(final String dataJsonLocation, final String filePath) {
        return new ContentExtractor(getRootFolder(), dataJsonLocation, filePath, getResourceLoader());
    }


    public ResourceLoader getResourceLoader() {
        return resourceLoader;
    }

    public ContentExtractorFactory setResourceLoader(final ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
        return this;
    }
}
