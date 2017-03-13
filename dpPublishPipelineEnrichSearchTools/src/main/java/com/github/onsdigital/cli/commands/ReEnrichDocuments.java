package com.github.onsdigital.cli.commands;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.onsdigital.cli.model.Request;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.NameFileFilter;
import org.apache.commons.io.filefilter.NotFileFilter;

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

    public static final String ACTION = "enrich";
    private static final ObjectMapper MAPPER = new ObjectMapper();
    @Parameter(names = "--help",
               help = true)
    boolean help = false;

    @Parameter(names = {"--root", "-d"},
               required = true,
               description = "The root directory to search from")
    private String dir;

    @Parameter(names = {"--output", "-f"},
               required = true,
               description = "The filwrite to")
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
        String fileContent = null;
        try {
            fileContent = FileUtils.readFileToString(file, "UTF-8");

            final Request request = new Request().setFileLocation("file:" + file.getAbsolutePath())
                                                 .setFileContent(fileContent);
            FileUtils.writeStringToFile(outputFile, MAPPER.writeValueAsString(request)+"\n", "UTF-8",true);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private List<Request> processDownloads(final File file, final File outputFile) {
        List<Request> downloadRequests = null;
        try {
            final Map<String, Object> map = MAPPER.readValue(file, Map.class);
            final List<Map<String, String>> downloads = (List<Map<String, String>>) map.get("downloads");
            final String url = (String) map.get("uri");

            if (null != downloads) {
                downloads.forEach(dwnld -> {
                    try {
                        final Request request = new Request().setFileContent(dwnld.get("file"))
                                                             .setFileLocation(url);
                        FileUtils.writeStringToFile(outputFile, MAPPER.writeValueAsString(request)+"\n", "UTF-8", true);
                    }
                    catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return downloadRequests;
    }
}
