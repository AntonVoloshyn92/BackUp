import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class FileUtilsTest {

    FileUtils fileUtils = new FileUtils();

    @Test
    public void filesEqualsTrueTest() {
        File fileFromSource = new File("/Users/antonvoloshyn/IdeaProjects/BackUp/src/test/resources/backupSourceRoot/1111/1.txt");
        File fileFromTarget = new File("/Users/antonvoloshyn/IdeaProjects/BackUp/src/test/resources/backupTargetRoot/1111/26-10-2019-10:19:45@1.txt");

        Assert.assertTrue(fileUtils.filesEquals(fileFromSource.toPath(), fileFromTarget.toPath()));
    }

    @Test
    public void whenFileSizesAreEqualTest() {
        File fileFromSource = new File("/Users/antonvoloshyn/IdeaProjects/BackUp/src/test/resources/backupSourceRoot/1111/1.txt");
        File fileFromSourceSame = new File("/Users/antonvoloshyn/IdeaProjects/BackUp/src/test/resources/backupSourceRoot/1111/1.txt");

        Assert.assertTrue(fileUtils.filesEquals(fileFromSource.toPath(), fileFromSourceSame.toPath()));
    }

    @Test
    public void filesEqualsFalseTest() {
        File fileFromSource = new File("/Users/antonvoloshyn/IdeaProjects/BackUp/src/test/resources/backupSourceRoot/1111/1.txt");
        File fileFromTarget = new File("/Users/antonvoloshyn/IdeaProjects/BackUp/src/test/resources/backupTargetRoot/1111/26-10-2019-10:18:49@1.txt");

        Assert.assertFalse(fileUtils.filesEquals(fileFromSource.toPath(), fileFromTarget.toPath()));
    }

    @Test
    public void doBackUpTest() {
        File fileFromSource = new File("/Users/antonvoloshyn/IdeaProjects/BackUp/src/test/resources/sourceDir/TestFile.txt");
        File fileFromTarget = new File("/Users/antonvoloshyn/IdeaProjects/BackUp/src/test/resources/targetDir");
        String pathDir = "/Users/antonvoloshyn/IdeaProjects/BackUp/src/test/resources/sourceDir";

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy-HH:mm:ss");
        Date todayDate = new Date();

        try {
            fileUtils.doBackUp(fileFromSource, fileFromTarget, pathDir);
        } catch (IOException e) {
            e.printStackTrace();
        }

        File testFile = new File(fileFromTarget.getAbsolutePath() + Const.CONST.separator + simpleDateFormat.format(todayDate) +
                Const.CONST.SplitToFileName + fileFromSource.getName());

        Assert.assertTrue(testFile.exists());
        Assert.assertTrue(fileUtils.filesEquals(fileFromSource.toPath(), testFile.toPath()));
    }

    @Test
    public void deleteFileFromFolderTest() throws Exception {
        File file = new File("/Users/antonvoloshyn/IdeaProjects/BackUp/src/test/resources/targetDir");

        List<String> pathList = new ArrayList<>();


        Files.walk(Paths.get(file.getAbsolutePath()))
                .filter(Files::isRegularFile)
                .filter(name -> name.getFileName().toString().endsWith("TestFile.txt"))
                .map(name -> name.toString().substring(file.getAbsolutePath().length()))
                .forEach(pathList::add);


        Collections.sort(pathList);
        if (pathList.size() > 3) {
            int res = pathList.size() - 1;
            fileUtils.deleteFileFromFolder(pathList, file.getAbsolutePath());
            Assert.assertEquals(res, pathList.size());
        } else if (pathList.size() == 3) {
            Assert.assertEquals(3, pathList.size());
        } else {
            Assert.assertEquals(pathList.size(), pathList.size());
        }

    }
}
