package com.org.html;

import com.org.dto.HtmlContentList;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class HtmlParser {

    private static final String HTML_PATH = "./HTMLFiles";

    public Set<String> listFiles(String dir) {
        return Stream.of(Objects.requireNonNull(new File(dir).listFiles()))
                .filter(file -> !file.isDirectory())
                .map(File::getName)
                .collect(Collectors.toSet());
    }

    public HtmlContentList parseHtmlFiles() throws IOException {
        System.out.println("Parsing HTML Files");
        Set<String> files = listFiles(HTML_PATH);
        List<String> htmlContents = new ArrayList<>();
        for (String file : files) {
            htmlContents.add(String.join("\n", Files.readAllLines(Paths.get(HTML_PATH + "/" + file))));
        }

        return new HtmlContentList(htmlContents);
    }


}
