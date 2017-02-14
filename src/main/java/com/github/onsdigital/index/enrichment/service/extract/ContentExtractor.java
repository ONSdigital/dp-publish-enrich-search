package com.github.onsdigital.index.enrichment.service.extract;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import javax.ws.rs.BadRequestException;
import java.io.IOException;
import java.util.List;

import static com.github.onsdigital.index.enrichment.service.analyse.util.ResourceUtils.concatenate;
import static com.github.onsdigital.index.enrichment.service.analyse.util.ResourceUtils.substituteFileName;

public class ContentExtractor {
  private static final Logger LOGGER = LoggerFactory.getLogger(ContentExtractor.class);

  private final String rootPath;
  private final String dataJsonLocation;
  private final String filePath;
  private final ResourceLoader resourceLoader;

  public ContentExtractor(final String rootPath, final String dataJsonLocation, final String filePath,
                          final ResourceLoader resourceLoader) {

    this.rootPath = rootPath;
    this.dataJsonLocation = dataJsonLocation;
    this.filePath = filePath;
    this.resourceLoader = resourceLoader;
  }

  public List<String> extract() {
    List<String> content = null;
    try {

      Resource downloadPath = resolveDownloadPath(dataJsonLocation, filePath);
      if (null != downloadPath) {
        content = extractText(downloadPath);
      }
      else {
        LOGGER.warn("extract([]) : Skipping  {} as not available for page {}",filePath,dataJsonLocation);
      }

    }
    catch (IOException | FileExtractException e) {
      LOGGER.error("extractContent([pageURI, filePath]) : failed to parser file '{}' with error {} for page {}",
                   filePath,
                   e.getMessage(),
                   dataJsonLocation);
    }
    return content;
  }

  /**
   * Use tika to extract File contents
   *
   * @param downloadPath
   * @return
   */

  private List<String> extractText(final Resource downloadPath) throws FileExtractException {
    return FileContentExtractUtil.extractText(downloadPath);
  }

  /**
   * Work out the path of the download document
   * <p>
   * <i>Sometimes the document contains the actual location and sometimes it just contains the name</i>
   *
   * @param dataJsonLocation
   * @param filePath
   * @return
   * @throws BadRequestException
   */
  private Resource resolveDownloadPath(final String dataJsonLocation,
                                       final String filePath) throws IOException {


    Resource downloadPath = null;

    if (null != dataJsonLocation) {
      //Get the just the filename as the the file should be in the same directort as the dataJson
      String concat = substituteFileName(dataJsonLocation, filePath);
      downloadPath = resourceLoader.getResource(concat);

    }
    //Check that the path supplied is valid, some of section paths include the path and some do not.
    if (null == downloadPath
        || !downloadPath.exists()) {

      String concat = concatenate(rootPath, filePath);
      downloadPath = resourceLoader.getResource(concat);


      if (!downloadPath.exists()) {
        LOGGER.info("resolveDownloadPath([Resource, String]) : not a accessible {}", downloadPath);
        downloadPath = null;

      }
    }
    return downloadPath;
  }

}