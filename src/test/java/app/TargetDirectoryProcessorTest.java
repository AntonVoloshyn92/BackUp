package app;

import app.SourceDirectoryProcessor;
import app.TargetDirectoryProcessor;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.*;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TargetDirectoryProcessorTest {

    private TargetDirectoryProcessor targetDirectoryProcessor;
    private String path = "/Users/antonvoloshyn/IdeaProjects/BackUp/src/test/resources/backupTargetRoot";

/*    @Before
    public void init() {
        targetDirectoryProcessor = new TargetDirectoryProcessor(sourceDirectoryProcessor);
//        when(sourceDirectoryProcessor.directoryContentFilter(anyString())).thenReturn();
    }*/

    @Test
    public void getKeyCorrectTest() {
        String key = "/TestFile.txt";

        Map<String, List<String>> mapFromTargetDir =
                targetDirectoryProcessor.mapPathFromTargetDirectory("./src/test/resources/targetDir");

        for (String keyEntry : mapFromTargetDir.keySet()) {
            Assert.assertEquals(key, keyEntry);
        }
    }

    @Test
    public void sortBackupVersionsTest() {
        List<String> listFileFromDir = new ArrayList<>();
        listFileFromDir.add("/01-11-2019-12:22:56@TestFile.txt");
        listFileFromDir.add("/01-11-2019-15:30:50@TestFile.txt");
        listFileFromDir.add("/01-11-2019-20:46:50@TestFile.txt");

        Map<String, List<String>> mapFromTargetDir =
                targetDirectoryProcessor.mapPathFromTargetDirectory("./src/test/resources/targetDir");
        String key = "/TestFile.txt";

        List<String> listFromTargetProcessor = mapFromTargetDir.get(key);

        Assert.assertEquals(listFileFromDir, listFromTargetProcessor);
        //todo
    }


    @Test
    public void mapPathFromTargetDirectoryEqualsTest() {
        Map<String, List<String>> mapForTest = targetDirectoryProcessor.mapPathFromTargetDirectory(path);
        Map<String, List<String>> expMap = new HashMap<>();

        String add1 = "/1111/26-10-2019-10:18:49@1.txt";
        String add2 = "/1111/26-10-2019-10:19:45@1.txt";
        String add3 = "/26-10-2019-10:18:49@546.txt";

        List<String> listOne = new ArrayList<>();
        listOne.add(add1);
        listOne.add(add2);
        Collections.sort(listOne);

        List<String> listTwo = new ArrayList<>();
        listTwo.add(add3);

        expMap.put("/1111/1.txt", listOne);
        expMap.put("/546.txt", listTwo);

        Assert.assertEquals(expMap, mapForTest);

    }


}
