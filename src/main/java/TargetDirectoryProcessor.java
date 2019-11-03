
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class TargetDirectoryProcessor {

    public Map<String, List<String>> mapPathFromTargetDirectory(String pathToTargetDir) {
        Map<String, List<String>> mapPathFromDir = new HashMap<>();

//        File folderFile = new File(pathToTargetDir).getAbsoluteFile();

        List<String> fileNameList = new SourceDirectoryProcessor().directoryContentFilter(pathToTargetDir);

    /*    List<String> fileNameList = new ArrayList<>();
        try {
            Files.walk(Paths.get(folderFile.getAbsolutePath()))
                    .filter(Files::isRegularFile)
                    .filter((name) -> !name.getFileName().endsWith(Const.CONST.fileToDel))
                    .map(path -> path.toString().substring(folderFile.getAbsolutePath().length()))
                    .forEach(fileNameList::add);
        } catch (IOException e) {
            e.printStackTrace();
        }*/


//        System.out.println(fileNameList.size());

        for (String path : fileNameList) {
            String key = getKey(path);
            if (!mapPathFromDir.containsKey(key)) {
                mapPathFromDir.put(key, new ArrayList<>());
            }
            mapPathFromDir.get(key).add(path);
        }

        for (List<String> value : mapPathFromDir.values()) {
            sortBackupVersions(value);
//            deleteFileFromFolder(value, pathToTargetDir);
        }


        return mapPathFromDir;
    }


    private String getKey(String pathString) {
        String nameFile = pathString.substring(pathString.indexOf(Const.CONST.SplitToFileName) + 1);//name file
        String removeFromName = pathString.substring(pathString.lastIndexOf(Const.CONST.separator) + 1, pathString.indexOf(nameFile));//del from pathString
        return pathString.replace(removeFromName, "");
    }

    private void sortBackupVersions(List<String> list) {

        list.sort(new Comparator<String>() {
            @Override
            public int compare(String file1, String file2) {
                String dateString = file1.substring(file1.lastIndexOf(Const.CONST.separator) + 1, file1.indexOf(Const.CONST.SplitToFileName));
                String dateString2 = file2.substring(file2.lastIndexOf(Const.CONST.separator) + 1, file2.indexOf(Const.CONST.SplitToFileName));

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
                    throw new RuntimeException(e);
                }


            }
        });
    }


   /* private void deleteFileFromFolder(List<String> pathList, String absolutePath) {
        if (pathList.size() > 3) {
            File file = Paths.get(absolutePath, pathList.get(0)).toFile();
            if (file.exists()) {
                if (file.delete()) {
                    System.out.println("File was delete " + file.getAbsolutePath());
                }
            }
            pathList.remove(0);
        }
    }*/
}

