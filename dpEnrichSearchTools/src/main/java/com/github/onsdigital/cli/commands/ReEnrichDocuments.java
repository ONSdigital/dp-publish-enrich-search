package com.github.onsdigital.cli.commands;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.onsdigital.cli.model.Request;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.NameFileFilter;
import org.apache.commons.io.filefilter.NotFileFilter;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Created by guidof on 10/03/17.
 */
@Parameters(commandNames = ReEnrichDocuments.ACTION,
            commandDescription = "Re-enrich all files base the files on the file system")
public class ReEnrichDocuments implements Command {
    static final String ACTION = "enrich";
    private static final String FILE_URL_PREFIX = "file:";
    private static final Logger LOGGER = LoggerFactory.getLogger(ReEnrichDocuments.class);
    private static final ObjectMapper MAPPER = new ObjectMapper().setSerializationInclusion(JsonInclude.Include.NON_NULL);
    private static final String PATH_DELIMITER = "/";

    @Parameter(names = "--help",
               help = true)
    boolean help = false;

    @Parameter(names = {"--root", "-d"},
               required = true,
               description = "The root directory to search from")
    private String dir;

    @Parameter(names = {"--output", "-f"},
               required = true,
               description = "The file write to")
    private String file;


    @Override
    public void execute() throws IOException {

        File rootDir = new File(dir);
        final Collection<File> dataFiles = FileUtils.listFiles(rootDir,
                                                               new NameFileFilter("data.json"),
                                                               new NotFileFilter(new NameFileFilter("previous")));
        final File outputFile = new File(file);

        dataFiles.forEach(file -> {
            buildDataRequest(file, outputFile);
            processDownloads(file, outputFile);
        });


    }

    private void buildDataRequest(final File file, final File outputFile) {
        Map fileContent;
        try {
            fileContent = MAPPER.readValue(file, Map.class);
            String fileUri = (String) fileContent.get("uri");
            fileUri += StringUtils.endsWith(fileUri, "/") ? "" : "/";
            fileUri += file.getName();

            final Request request = new Request().setFileLocation(fileUri)
                                                 .setFileContent(MAPPER.writeValueAsString(fileContent));

            FileUtils.writeStringToFile(outputFile, MAPPER.writeValueAsString(request) + "\n", "UTF-8", true);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void  processDownloads(final File file, final File outputFile) {

        try {
            final Map<String, Object> map = MAPPER.readValue(file, Map.class);
            final List<Map<String, String>> downloads = (List<Map<String, String>>) map.get("downloads");
            final String url = (String) map.get("uri");

            if (null != downloads) {
                downloads.forEach(dwnld -> {
                    try {
                        final Request request = new Request().setS3Location(deriveLocation(dwnld.get("file"),
                                                                                           file.getParent()))
                                                             .setFileLocation(url);
                        FileUtils.writeStringToFile(outputFile,
                                                    MAPPER.writeValueAsString(request) + "\n",
                                                    "UTF-8",
                                                    true);
                    }
                    catch (IOException e) {
                        LOGGER.error("processDownloads([file, outputFile]) : {}", e.getMessage(), e);
                    }
                });
            }
        }
        catch (IOException e) {
            LOGGER.error("processDownloads([file, outputFile]) : {}", e.getMessage(), e);

        }
    }

    private String deriveLocation(final String file, final String url) {
        String fileLocation = file;
        if (StringUtils.startsWith(fileLocation, "/")) {
            //Assume full path is present
            fileLocation = FILE_URL_PREFIX + getDir() + fileLocation;

        }
        else if (StringUtils.contains(fileLocation, "/")) {

            fileLocation = FILE_URL_PREFIX + getDir() + fileLocation;
        }
        else {
            //If the fileLocation does not contain a "/" then we can assume that we need to add the full path
            fileLocation = FILE_URL_PREFIX + url + "/" + fileLocation;

        }
        return fileLocation;
    }

    private String getDir() {
        return StringUtils.endsWith(dir, "/") ? dir : dir + PATH_DELIMITER;
    }
}
