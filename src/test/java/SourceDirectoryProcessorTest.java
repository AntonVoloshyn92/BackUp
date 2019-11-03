import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class SourceDirectoryProcessorTest {

    private SourceDirectoryProcessor sourceDirectoryProcessor = new SourceDirectoryProcessor();
    ListEquals listEquals = new ListEquals();

    @Test
    public void directoryContentFilterNullTest() {
        List<String> stringList = sourceDirectoryProcessor.directoryContentFilter(
                "/Users/antonvoloshyn/IdeaProjects/BackUp/src/test/resources/TestNull");
        Assert.assertEquals(0, stringList.size());
    }

    @Test(expected = ExceptionInInitializerError.class)
    public void directoryContentFilterEmptyPathToFileTest() {
        List<String> stringList = sourceDirectoryProcessor.directoryContentFilter("");
    }

    @Test
    public void directoryContentFilterEqualsListTest() {
        List<String> listCheck = new ArrayList<>();
        listCheck.add("/546.txt");
        listCheck.add("/1111/1.txt");

        String sourceFile = "/Users/antonvoloshyn/IdeaProjects/BackUp/src/test/resources/backupSourceRoot";
        List<String> listToCheck = sourceDirectoryProcessor.directoryContentFilter(sourceFile);

        Assert.assertTrue(listEquals.listEquals(listCheck, listToCheck));

//        Assert.assertTrue(listCheck.containsAll(listToCheck));
    }


}
