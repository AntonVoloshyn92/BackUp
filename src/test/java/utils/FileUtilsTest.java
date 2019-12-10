package utils;

import app.Const;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static app.Const.splitToFileName;
import static app.Const.separator;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class FileUtilsTest {

    DateUtils dateUtils = mock(DateUtils.class);


    private FileUtils fileUtils = new FileUtils(dateUtils);

    @Test
    public void filesEqualsTrueTest() {
        File fileFromSource = new File("./src/test/resources/backupSourceRoot/1111/1.txt");
        File fileFromTarget = new File("./src/test/resources/backupTargetRoot/1111/26-10-2019-10:19:45@1.txt");

        Assert.assertTrue(fileUtils.filesEquals(fileFromSource.toPath(), fileFromTarget.toPath()));
    }

    @Test
    public void whenFileSizesAreEqualTest() {
        File fileFromSource = new File("./src/test/resources/backupSourceRoot/1111/1.txt");
        File fileFromSourceSame = new File("./src/test/resources/backupSourceRoot/1111/1.txt");

        Assert.assertTrue(fileUtils.filesEquals(fileFromSource.toPath(), fileFromSourceSame.toPath()));
    }

    @Test
    public void filesEqualsFalseTest() {
        File fileFromSource = new File("./src/test/resources/backupSourceRoot/1111/1.txt");
        File fileFromTarget = new File("./src/test/resources/backupTargetRoot/1111/26-10-2019-10:18:49@1.txt");

        Assert.assertFalse(fileUtils.filesEquals(fileFromSource.toPath(), fileFromTarget.toPath()));
    }

    @Test
    public void doBackUpTest() throws ParseException, IOException {

        String strDate = "2011-11-11";
        SimpleDateFormat simpleDateFormatFromString = new SimpleDateFormat("yyyy-MM-dd");
        Date date = simpleDateFormatFromString.parse(strDate);
        when(dateUtils.getDate()).thenReturn(date);

        SimpleDateFormat simpleDateFormatOne = new SimpleDateFormat("dd-MM-yyyy-HH:mm:ss");

        File fileFromSource = new File("/Users/antonvoloshyn/IdeaProjects/BackUp/src/test/resources/sourceDir/TestFile.txt");
        File fileFromTarget = new File("/Users/antonvoloshyn/IdeaProjects/BackUp/src/test/resources/targetDir");
        String pathDir = "/Users/antonvoloshyn/IdeaProjects/BackUp/src/test/resources/sourceDir";

        String dateForNameFile = simpleDateFormatOne.format(dateUtils.getDate());

        System.out.println(dateForNameFile);

        fileUtils.doBackUp(fileFromSource, fileFromTarget, pathDir);

        File testFile = new File(fileFromTarget.getAbsolutePath() + separator + dateForNameFile +
                splitToFileName + fileFromSource.getName());

        Assert.assertTrue(testFile.exists());
        Assert.assertTrue(fileUtils.filesEquals(fileFromSource.toPath(), testFile.toPath()));

        if (testFile.delete()) {
            System.out.println("file was delete");
        }

    }
}
