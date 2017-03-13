package com.github.onsdigital.index.enrichment.service.util;

import com.github.onsdigital.index.enrichment.model.Document;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.github.onsdigital.index.enrichment.model.ModelEnum.DOWNLOADS;
import static com.github.onsdigital.index.enrichment.model.ModelEnum.FILE;

/**
 * Created by fawks on 15/02/2017.
 */
public class PageUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(PageUtils.class);

    private PageUtils() {
        //DO NOT INSTANTIATE
    }

    public static Document attachDownload(final Map<String, Object> downloadContent, final Document document) {
        Object o = document.getSource()
                           .get(DOWNLOADS.getIndexDocProperty());

        List<Map<String, Object>> downloads = prepDownloadsList(document, o);

        Optional<Map<String, Object>> first = downloads.stream()
                                                       .filter(pageEntry -> isCurrentDownload(downloadContent, pageEntry))
                                                       .findFirst();
        if (first.isPresent()) {
            first.get()
                 .putAll(downloadContent);
        }
        else {
            downloads.add(downloadContent);
        }
        return document;

    }

    private static List<Map<String, Object>> prepDownloadsList(final Document document, final Object o) {
        List<Map<String, Object>> downloads;

        if (o instanceof List) {
            downloads = (List) o;
        }
        else {
            LOGGER.debug("putResource([downloadContent, page]) : downloads type was  '{}'",
                        null != o ? o.getClass()
                                     .getSimpleName() : "null");

            downloads = new ArrayList<>();
            document.getSource()
                    .put(DOWNLOADS.getIndexDocProperty(), downloads);
        }
        return downloads;
    }

    private static boolean isCurrentDownload( final Map<String, Object> newEntry, final Map<String, Object> pageEntry) {
        return StringUtils.equals((String) pageEntry.get(FILE.getIndexDocProperty()),
                                  (String) newEntry.get(FILE.getIndexDocProperty()));
    }


}
