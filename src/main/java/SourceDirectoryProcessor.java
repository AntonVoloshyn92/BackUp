
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class SourceDirectoryProcessor {

    public List<String> directoryContentFilter(String pathToFile) {



        File folder = new File(pathToFile);

        List<String> listPath = new ArrayList<>();
        try {
            Files.walk(Paths.get(folder.getAbsolutePath()))
                    .filter(Files::isRegularFile)
                    .filter((name) -> !name.getFileName().endsWith(Const.CONST.fileToDel))
                    .map(path -> path.toString().substring(folder.getAbsolutePath().length()))
                    .forEach(listPath::add);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return listPath;
    }


}
