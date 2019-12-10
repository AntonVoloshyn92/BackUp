package app;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import utils.DateUtils;
import utils.FileUtils;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BackupRunnerTest {

    @Test
    public void firstBackupRunTest() throws IOException, ParseException {
        SourceDirectoryProcessor sourceDirectoryProcessor = new SourceDirectoryProcessor();
        TargetDirectoryProcessor targetDirectoryProcessor = mock(TargetDirectoryProcessor.class);
        DateUtils dateUtils = mock(DateUtils.class);

        String strDate = "2011-11-11";
        SimpleDateFormat simpleDateFormatFromString = new SimpleDateFormat("yyyy-MM-dd");
        Date date = simpleDateFormatFromString.parse(strDate);
        when(dateUtils.getDate()).thenReturn(date);
        FileUtils fileUtils = new FileUtils(dateUtils);

        BackupRunner backupRunner = new BackupRunner(sourceDirectoryProcessor, targetDirectoryProcessor, fileUtils);

        /*URL resource = BackupRunnerTest.class.getResource("/backupSourceRoot");
        backupRunner.setSourceDirPath(Paths.get(resource.toURI()).toString());

        URL resourceTarget = BackupRunnerTest.class.getResource("/firstBackUpTest");
        backupRunner.setToTargetDir(Paths.get(resourceTarget.toURI()).toString());*/

        backupRunner.setSourceDirPath("/Users/antonvoloshyn/IdeaProjects/BackUp/src/test/resources/backupSourceRoot");
        backupRunner.setToTargetDir("/Users/antonvoloshyn/IdeaProjects/BackUp/src/test/resources/firstBackUpTest");

        Map<String, List<String>> stringListMap = new HashMap<>();
        when(targetDirectoryProcessor.mapPathFromTargetDirectory(anyString())).thenReturn(stringListMap);

        backupRunner.doBackup();

        File file1 = new File("/Users/antonvoloshyn/IdeaProjects/BackUp/src/test/resources/firstBackUpTest/1111/11-11-2011-00:00:00@1.txt");
        Assert.assertTrue(file1.exists());
        File file = new File("/Users/antonvoloshyn/IdeaProjects/BackUp/src/test/resources/firstBackUpTest/1111");
        file.delete();
        file1.delete();
        File file2 = new File("/Users/antonvoloshyn/IdeaProjects/BackUp/src/test/resources/firstBackUpTest/11-11-2011-00:00:00@546.txt");
        Assert.assertTrue(file2.exists());
        file2.delete();
    }

    @Test
    public void BackupRunEqualsMapTest() throws ParseException, IOException {
        SourceDirectoryProcessor sourceDirectoryProcessor = new SourceDirectoryProcessor();
        TargetDirectoryProcessor targetDirectoryProcessor = mock(TargetDirectoryProcessor.class);
        DateUtils dateUtils = mock(DateUtils.class);
        String strDate = "2011-11-11";
        SimpleDateFormat simpleDateFormatFromString = new SimpleDateFormat("yyyy-MM-dd");
        Date date = simpleDateFormatFromString.parse(strDate);

        List<String> fileFromSourceDirectoryProcessor = new ArrayList<>();
        fileFromSourceDirectoryProcessor.add("/One.txt");
        fileFromSourceDirectoryProcessor.add("/Two.txt");
        Map<String, List<String>> mapFileFromTargetDirectoryProcessor = new HashMap<>();
        List<String> list = new ArrayList<>();
        list.add("/11-11-2011-00:00:00@One.txt");
        mapFileFromTargetDirectoryProcessor.put("/One.txt", list);

//        when(sourceDirectoryProcessor.directoryContentFilter(anyString())).thenReturn(fileFromSourceDirectoryProcessor);
        when(targetDirectoryProcessor.mapPathFromTargetDirectory(anyString())).thenReturn(mapFileFromTargetDirectoryProcessor);
        when(dateUtils.getDate()).thenReturn(date);

        FileUtils fileUtils = new FileUtils(dateUtils);

        BackupRunner backupRunner = new BackupRunner(sourceDirectoryProcessor, targetDirectoryProcessor, fileUtils);
//        backupRunner.setSourceDirPath("");
        backupRunner.setSourceDirPath("/Users/antonvoloshyn/IdeaProjects/BackUp/src/test/resources/backupRunEqualsMapTestSource");
//        backupRunner.setToTargetDir("");
        backupRunner.setToTargetDir("");
        backupRunner.doBackup();
// когда мокаешь обект, НЕ ПОНЯТНО КУДА ОН ДОЛЖЕН ВОЗВРАЩАТЬ ФАЙЛ
    }

}
