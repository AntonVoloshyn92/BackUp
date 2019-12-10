package app;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import static app.Const.fileToDel;

@Component
public class SourceDirectoryProcessor {
    private final Logger log = Logger.getLogger(SourceDirectoryProcessor.class.getName());

    public List<String> directoryContentFilter(String pathToFile) {

        File folder = new File(pathToFile);
        log.info("File path for directoryContentFilter " + folder.getAbsolutePath());

        List<String> listPath = new ArrayList<>();
        try {
            Files.walk(Paths.get(folder.getAbsolutePath()))
                    .filter(Files::isRegularFile)
                    .filter((name) -> !name.getFileName().endsWith(fileToDel))
                    .map(path -> path.toString().substring(folder.getAbsolutePath().length()))
                    .forEach(listPath::add);
        } catch (IOException e) {
            log.error("IOException ", e);
            e.printStackTrace();
        }
        log.info("return list file from source path, list size = " + listPath.size());
        return listPath;
    }


}
