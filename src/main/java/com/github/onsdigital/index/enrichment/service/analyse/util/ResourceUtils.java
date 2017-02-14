package com.github.onsdigital.index.enrichment.service.analyse.util;


import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.io.InputStream;


public class ResourceUtils {

    public static final String JSON = "json";

    private ResourceUtils() {
        //DO NOT INSTANTIATE
    }

    public static String readResourceToString(final Resource r) throws IOException {
        InputStream inputStream = r.getInputStream();
        return IOUtils.toString(inputStream);
    }

    public static boolean isJsonFile(final Resource downloadPath) {

        String extension = null;

        if (null != downloadPath && downloadPath.exists()) {
            extension = FilenameUtils.getExtension(downloadPath.getFilename());
        }
        return StringUtils.equalsIgnoreCase(extension, JSON);

    }

    public static String substituteFileName(String originalFile, String replacementFilename) {

        //Get the just the filename as the the file should be in the same directort as the dataJson
        String fileName = FilenameUtils.getName(replacementFilename);
        String fullPath = FilenameUtils.getFullPath(originalFile);

        return fullPath + fileName;
    }

    public static String concatenate(String fileBase, String extFileName) {

        //Get the just the filename as the the file should be in the same directort as the dataJson

        if (!StringUtils.endsWith(fileBase, "/")) {
            fileBase = fileBase + "/";
        }
        if (StringUtils.startsWith(extFileName, "/")) {
            extFileName = extFileName.substring(1);
        }

        return fileBase + extFileName;
    }

    public static boolean noProtocolDefinition(final String s3Location) {
        return !(StringUtils.startsWithIgnoreCase(s3Location, "s3:")
                || StringUtils.startsWithIgnoreCase(s3Location, "file:")
                || StringUtils.startsWithIgnoreCase(s3Location, "http:")
                || StringUtils.startsWithIgnoreCase(s3Location, "classpath:"));
    }

}
