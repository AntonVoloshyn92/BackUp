package utils;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.Date;
import static app.Const.splitToFileName;

@Component
public class FileUtils {

    private final DateUtils dateUtils;

    @Autowired
    public FileUtils(DateUtils dateUtils) {
        this.dateUtils = dateUtils;
    }

    private final Logger log = Logger.getLogger(FileUtils.class.getName());

    /**
     * this method do backup
     *
     * @param sourceFile
     * @param dest
     * @param sourceDir
     * @throws IOException
     */

    public void doBackUp(File sourceFile, File dest, String sourceDir) throws IOException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy-HH:mm:ss");
        Date todayDate = dateUtils.getDate();
        String dir = sourceFile.getAbsolutePath().substring(sourceDir.length());
        String pathToFile = dir.substring(0, dir.lastIndexOf(sourceFile.getName()) - 1);
        String pathToDest = dest.getAbsolutePath();

        File file;
        if (!pathToDest.contains(pathToFile)) {
            file = new File(pathToDest + pathToFile);
            log.info("File path + " + file.getAbsolutePath());
        } else {
            file = new File(pathToDest);
            log.info("File path + " + file.getAbsolutePath());
        }
        if (!file.exists()) {
            if (!file.mkdirs()) {
                log.error("No directory");
                throw new RuntimeException("Directory was not created");
            }
        }
        log.info("Doing copy");
        Files.copy(sourceFile.toPath(), file.toPath().resolve(simpleDateFormat.format(todayDate) +
                splitToFileName + sourceFile.getName()));
        System.out.println("File Copied, name file: " + sourceFile.getName());
    }

    /**
     * this method compares two files byte by size
     *
     * @param from
     * @param to
     * @return true if size are equal, false otherwise
     */
    public boolean filesEquals(Path from, Path to) { //if false file has been modified

        if (from.toFile().length() != to.toFile().length()) {
            log.info("files was changed");
            return false;
        }
        log.info("Files is equals");
        return from.toFile().length() == to.toFile().length() && byByteEqual(from, to);

    }

    /**
     * this method compares two files byte by byte
     *
     * @param from
     * @param to
     * @return true if all bytes are equal, false otherwise
     */
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
            log.error("IOException ", e);
            throw new RuntimeException(e);
        }
    }
}
