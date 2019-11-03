import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.TimerTask;

public class BackupRunner extends TimerTask {
    private SourceDirectoryProcessor sourceFolderProcessor = new SourceDirectoryProcessor();
    private FileUtils fileUtils = new FileUtils();
    private TargetDirectoryProcessor targetDirectoryProcessor = new TargetDirectoryProcessor();

    public void doBackup() throws IOException {
        String sourceDirPath = "/Users/antonvoloshyn/Desktop/TestForBackUP";
        String toTargetDir = "/Users/antonvoloshyn/Desktop/BackUp";
        List<String> listPathFromRootFolder = sourceFolderProcessor.directoryContentFilter(sourceDirPath);
        Map<String, List<String>> mapPathFromTargetDir = targetDirectoryProcessor.mapPathFromTargetDirectory(toTargetDir);

        if (mapPathFromTargetDir.size() == 0) { //first copy directory
            System.out.println("Target directory is empty, do back up.");
            for (String nameFromDir : listPathFromRootFolder) {
                Path sourceFile = Paths.get(sourceDirPath, nameFromDir);
                fileUtils.doBackUp(sourceFile.toFile(), new File(toTargetDir), sourceDirPath);
            }
        } else {
            for (String pathFromListFolder : listPathFromRootFolder) {//when all is OK
                List<String> pathList = mapPathFromTargetDir.get(pathFromListFolder);
                Path sourceFile = Paths.get(sourceDirPath, pathFromListFolder);
                if (pathList != null) {
                    String lastPathFromMap = pathList.get(pathList.size() - 1);
                    if (fileUtils.filesEquals(sourceFile, Paths.get(toTargetDir, lastPathFromMap))) {
                        continue;
                    }
                    if (pathList.size() >= 3) {
                        Paths.get(toTargetDir, pathList.get(0)).toFile().delete();
                    }
                }
                fileUtils.doBackUp(sourceFile.toFile(), new File(toTargetDir +
                            pathFromListFolder.substring(0, pathFromListFolder.lastIndexOf(Const.CONST.separator))), sourceDirPath);

            }
        }



    }

    @Override
    public void run() {
            System.out.println("run");
            BackupRunner backupRunner = new BackupRunner();
            try {
                backupRunner.doBackup();
            } catch (IOException e) {
                e.printStackTrace();
            }
    }
}
