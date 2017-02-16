package com.github.onsdigital.index.enrichment.service.extract;

import com.beust.jcommander.internal.Lists;
import com.github.onsdigital.index.enrichment.exception.EnrichServiceException;
import com.github.onsdigital.index.enrichment.exception.FileExtractException;
import com.github.onsdigital.index.enrichment.service.util.JsonToStringConverter;
import com.github.onsdigital.index.enrichment.service.util.ResourceUtils;
import org.apache.tika.config.TikaConfig;
import org.apache.tika.exception.TikaException;
import org.apache.tika.extractor.DocumentSelector;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.apache.tika.sax.WriteOutContentHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.github.onsdigital.index.enrichment.service.util.TextFilterUtil.extractAlphaNumericCaseSensitiveUniqueTokens;

/**
 * Extract text content a file
 *
 * @author James Fawke
 */
public class FileContentExtractUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileContentExtractUtil.class);


    private FileContentExtractUtil() {
        //DO NOT INSTANTIATE
    }


    /**
     * Use tika to extract File contents, if the content is from a tabular file (i.e. csv, opendocument-sheets or excel)
     * then the text is a unique list of all the alphanumeric terms
     *
     * @param downloadPath
     * @return String containing the text
     */

    public static List<String> extractText(final Resource downloadPath) throws EnrichServiceException {
        List<String> contentText = null;
        try {
            if (null != downloadPath) {
                //Tika does not handle json
                if (ResourceUtils.isJsonFile(downloadPath)) {
                    contentText = new JsonToStringConverter(ResourceUtils.readResourceToString(downloadPath)).extractText();
                }
                else {
                    ArrayList<Metadata> documentMetadatas = new ArrayList<>();

                    String str = extractRawText(downloadPath, documentMetadatas);

                    if (isTabularContentType(documentMetadatas) || isTabularFileName(documentMetadatas)) {
                        //TODO When we have a bigger Elastic server we can index the whole data
                        str = extractAlphaNumericCaseSensitiveUniqueTokens(str).stream()
                                                                               .collect(Collectors.joining(" "));
                    }
                    contentText = Lists.newArrayList(str);
                }
            }
            else {
                throw new FileExtractException("downloadPath is null");
            }
        }
        catch (IOException e) {
            LOGGER.error("extractContent([pageURI, filePath]) : error extracting file {} ", e.getMessage(), e);
            throw new FileExtractException("Filed to Extract Tokens", e);
        }

        return contentText;

    }

    /**
     * Does the collection of metadata object contain Content-Type(s) that represent a tabular dataset
     *
     * @param documentMetadatas
     * @return
     */
    static boolean isTabularContentType(final Collection<Metadata> documentMetadatas) {
        return documentMetadatas.stream()
                                .map(m -> m.get("Content-Type"))
                                .filter(Objects::nonNull)
                                .anyMatch(type -> type.contains("spreadsheet")
                                        || type.contains("text/csv")
                                        || type.contains("application/xml")
                                        || type.contains("excel")
                                        || type.contains("msaccess")
                                        || type.contains("x-123")
                                        || type.contains("lotus-1-2-3"));
    }

    /**
     * Does the collection of metadata object contain Filenames that represent a tabular dataform
     *
     * @param documentMetadatas
     * @return
     */
    static boolean isTabularFileName(final Collection<Metadata> documentMetadatas) {
        return documentMetadatas.stream()
                                .map(m -> m.get("resourceName"))
                                .filter(Objects::nonNull)
                                .anyMatch(name -> name.endsWith("csv")
                                        || name.endsWith("xls")
                                        || name.endsWith("xlsx")
                                        || name.endsWith("xml")
                                        || name.endsWith("123")
                                        || name.endsWith("ods"));
    }


    /**
     * Extract the String content from the file. If the file is a zip file it will extract the content recursively from the zip file.
     * The optional documentMetadatas parameter will report metadata on each file within the zip or just the parent document for normal content
     *
     * @param resource
     * @param documentMetadatas collection of Metadata object representing each file in the 'file'
     * @return the raw text from the file(s)
     * @throws IOException
     * @throws TikaException
     */

    private static String extractRawText(final Resource resource,
                                         final List<Metadata> documentMetadatas) throws FileExtractException {
        WriteOutContentHandler handler =
                new WriteOutContentHandler(Integer.MAX_VALUE);


        try (InputStream stream = resource.getInputStream()) {
            Metadata parentMetadata = new Metadata();
            parentMetadata.set(Metadata.RESOURCE_NAME_KEY, resource.getFilename());
            documentMetadatas.add(parentMetadata);

            ParseContext context = new ParseContext();

            context.set(DocumentSelector.class, metadata -> {
                documentMetadatas.add(metadata);
                return true;
            });

            final AutoDetectParser parser = new AutoDetectParser(TikaConfig.getDefaultConfig());
            context.set(Parser.class, parser);
            parser.parse(stream, new BodyContentHandler(handler), parentMetadata, context);
        }
        catch (SAXException | IllegalArgumentException | IOException | TikaException e) {
            throw new FileExtractException("Unexpected File processing failure :" + resource.getDescription(), e);
        }
        return handler.toString();
    }


}
