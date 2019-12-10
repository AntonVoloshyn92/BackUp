package app;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import utils.FileUtils;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

import static app.Const.separator;

@Component()
public class BackupRunner {

    private final Logger log = Logger.getLogger(this.getClass().getName());
    private final SourceDirectoryProcessor sourceFolderProcessor;
    private final FileUtils fileUtils;
    private final TargetDirectoryProcessor targetDirectoryProcessor;


    @Autowired
    public BackupRunner(SourceDirectoryProcessor sourceDirectoryProcessor,
                        TargetDirectoryProcessor targetDirectoryProcessor,
                        FileUtils fileUtils) {
        this.sourceFolderProcessor = sourceDirectoryProcessor;
        this.targetDirectoryProcessor = targetDirectoryProcessor;
        this.fileUtils = fileUtils;
    }

    @Value("${folderPaths.sourceDirPath}")
    private  String sourceDirPath;


    @Value("${folderPaths.toTargetDir}")
    private  String toTargetDir;

    public void setSourceDirPath(String sourceDirPath) {
        this.sourceDirPath = sourceDirPath;
    }

    public void setToTargetDir(String toTargetDir) {
        this.toTargetDir = toTargetDir;
    }

    @Scheduled(fixedRate = 10_000)
    public void doBackup() throws IOException {

        List<String> listPathFromRootFolder = sourceFolderProcessor.directoryContentFilter(sourceDirPath);
        Map<String, List<String>> mapPathFromTargetDir = targetDirectoryProcessor.mapPathFromTargetDirectory(toTargetDir);

        if (listPathFromRootFolder.isEmpty()) {
            return;
        }
        if (mapPathFromTargetDir.size() == 0) { //first copy directory
            log.info("Target directory is empty, do back up.");
            for (String nameFromDir : listPathFromRootFolder) {
                Path sourceFile = Paths.get(sourceDirPath, nameFromDir);
                log.info("Copy file " + sourceFile);
                fileUtils.doBackUp(sourceFile.toFile(), new File(toTargetDir), sourceDirPath);
            }
        } else {
            log.info("Directory is not empty");
            for (String pathFromListFolder : listPathFromRootFolder) {//when all is OK
                List<String> pathList = mapPathFromTargetDir.get(pathFromListFolder);
                Path sourceFile = Paths.get(sourceDirPath, pathFromListFolder);
                if (pathList != null) {
                    String lastPathFromMap = pathList.get(pathList.size() - 1);
                    if (fileUtils.filesEquals(sourceFile, Paths.get(toTargetDir, lastPathFromMap))) {
                        log.info("do filesEquals: " + sourceFile + " " + Paths.get(toTargetDir, lastPathFromMap));
                        continue;
                    }
                    if (pathList.size() >= 3) {
                        log.info("List size >= 3");
                        if (Paths.get(toTargetDir, pathList.get(0)).toFile().delete()) {
                            System.out.println(Paths.get(toTargetDir, pathList.get(0)) + " was delete");
                            log.info("file " + Paths.get(toTargetDir, pathList.get(0) + " delete"));
                        }
                    }
                }
                log.info("fist copy of file " + sourceFile.getFileName().toFile());
                fileUtils.doBackUp(sourceFile.toFile(), new File(toTargetDir +
                        pathFromListFolder.substring(0, pathFromListFolder.lastIndexOf(separator))), sourceDirPath);
                log.info("Copy file " + sourceFile);

            }
        }
    }
}
