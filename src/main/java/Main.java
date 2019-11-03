import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.List;
import java.util.Map;
import java.util.Timer;

import static java.nio.file.StandardCopyOption.COPY_ATTRIBUTES;


public class Main {

 /* /Users/antonvoloshyn/IdeaProjects/BackUp/src/test/resources/backupSourceRoot
    /Users/antonvoloshyn/IdeaProjects/BackUp/src/test/resources/backupTargetRoot*/

    public static void main(String[] args) throws Exception {
       /* BackupRunner runner = new BackupRunner();
//        runner.doBackup();
        Thread run = new Thread(new Runnable() {
            @Override
            public void run() {
                while(true){
                    try {
                        runner.doBackup();
                        Thread.sleep(10_000); //1000 - 1 sek
                    } catch (InterruptedException | IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
        run.start();
*/

        Timer timer = new Timer();
        timer.schedule(new BackupRunner(), 0 , 10_000);
    }


}


