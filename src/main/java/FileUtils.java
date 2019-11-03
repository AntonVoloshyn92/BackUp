import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class FileUtils {

/*    public static class DateUtils {
        public Date getDate() {
            return new Date();
        }
    }

    private DateUtils;*/



    public void doBackUp(File sourceFile, File dest, String sourceDir) throws IOException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy-HH:mm:ss");
        Date todayDate = new Date();

        String dir = sourceFile.getAbsolutePath().substring(sourceDir.length());
        String pathToFile = dir.substring(0, dir.lastIndexOf(sourceFile.getName()) - 1);
        String pathToDest = dest.getAbsolutePath();

        File file;
        if (!pathToDest.contains(pathToFile)) {
            file = new File(pathToDest + pathToFile);
        } else {
            file = new File(pathToDest);
        }
        if (!file.exists()) {
            if (!file.mkdirs()) {
                throw new RuntimeException("Directory was not created");
            }
        }
        Files.copy(sourceFile.toPath(), file.toPath().resolve(simpleDateFormat.format(todayDate) +
                Const.CONST.SplitToFileName + sourceFile.getName()));
        System.out.println("File Copied, name file: " + sourceFile.getName());
    }

    protected Date getDate() {
        return new Date();
    }

    /**
     * this method compares two files byte by byte
     * @param from
     * @param to
     * @return true if all bytes are equal, false otherwise
     */
    public boolean filesEquals(Path from, Path to) { //if false file has been modified

        if (from.toFile().length() != to.toFile().length()) {
            return false;
        }
        return from.toFile().length() == to.toFile().length() && byByteEqual(from, to);

    }


    private boolean byByteEqual(Path from, Path to) {
        try (FileInputStream inputStreamFrom = new FileInputStream(from.toFile());
             FileInputStream inputStreamTo = new FileInputStream(to.toFile())) {


            while (inputStreamFrom.available() > 0) {
                int countFrom = inputStreamFrom.read();

                int countTo = inputStreamTo.read();

                if (countFrom != countTo) {
                    return false;
                }
            }

            return true;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteFileFromFolder(List<String> pathList, String absolutePath) {
        if (pathList.size() > 3) {
            File file = Paths.get(absolutePath, pathList.get(0)).toFile();
            if (file.exists()) {
                if (file.delete()) {
                    System.out.println("File was delete " + file.getAbsolutePath());
                }
            }
            pathList.remove(0);
        }
    }
}
