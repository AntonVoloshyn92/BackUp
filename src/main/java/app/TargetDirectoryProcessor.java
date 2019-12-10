package app;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static app.Const.separator;
import static app.Const.splitToFileName;

@Component
public class TargetDirectoryProcessor {

    private final Logger log = Logger.getLogger(this.getClass().getName());
    private final SourceDirectoryProcessor sourceDirectoryProcessor;

    @Autowired
    TargetDirectoryProcessor(SourceDirectoryProcessor sourceDirectoryProcessor){
        this.sourceDirectoryProcessor = sourceDirectoryProcessor;
    }

    public Map<String, List<String>> mapPathFromTargetDirectory(String pathToTargetDir) {

        Map<String, List<String>> mapPathFromDir = new HashMap<>();

        List<String> fileNameList = sourceDirectoryProcessor.directoryContentFilter(pathToTargetDir);
        log.info("List file from target directory, list size " + fileNameList.size());

        for (String path : fileNameList) {
            String key = getKey(path);
            log.info("Key " + key);
            if (!mapPathFromDir.containsKey(key)) {
                mapPathFromDir.put(key, new ArrayList<>());
            }
            mapPathFromDir.get(key).add(path);
        }

        for (List<String> value : mapPathFromDir.values()) {
            sortBackupVersions(value);
            log.info("File in map list was sort");
        }

        log.info("Return map with size " + mapPathFromDir.size());
        return mapPathFromDir;
    }

    private String getKey(String pathString) {
        String nameFile = pathString.substring(pathString.indexOf(splitToFileName) + 1);//name file
        String removeFromName = pathString.substring(pathString.lastIndexOf(separator) + 1, pathString.indexOf(nameFile));//del from pathString
        return pathString.replace(removeFromName, "");
    }

    private void sortBackupVersions(List<String> list) {

        list.sort(new Comparator<String>() {
            @Override
            public int compare(String file1, String file2) {
                String dateString = file1.substring(file1.lastIndexOf(separator) + 1, file1.indexOf(splitToFileName));
                String dateString2 = file2.substring(file2.lastIndexOf(separator) + 1, file2.indexOf(splitToFileName));

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy-HH:mm:ss");

                try {
                    Date dateCreateFile1 = simpleDateFormat.parse(dateString);
                    Date dateCreateFile2 = simpleDateFormat.parse(dateString2);
                    if (dateCreateFile1.after(dateCreateFile2)) {
                        return 1;
                    } else if (dateCreateFile1.before(dateCreateFile2)) {
                        return -1;
                    } else {
                        return 0;
                    }
                } catch (ParseException e) {
                    log.error("ParseException ", e);
                    throw new RuntimeException(e);
                }
            }
        });
    }
}

