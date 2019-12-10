package app;

import app.SourceDirectoryProcessor;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class SourceDirectoryProcessorTest {

    private SourceDirectoryProcessor sourceDirectoryProcessor = new SourceDirectoryProcessor();

    @Test
    public void directoryContentFilterNullTest() {
        List<String> stringList = sourceDirectoryProcessor.directoryContentFilter(
                "./src/test/resources/TestNull");
        Assert.assertEquals(0, stringList.size());
    }

    @Test
    public void directoryContentFilterEqualsListTest() {
        List<String> listCheck = new ArrayList<>();
        listCheck.add("/546.txt");
        listCheck.add("/1111/1.txt");

        String sourceFile = "./src/test/resources/backupSourceRoot";
        List<String> listToCheck = sourceDirectoryProcessor.directoryContentFilter(sourceFile);

        Assert.assertEquals(listCheck, listToCheck);

    }


}
