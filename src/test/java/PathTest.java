import org.junit.Test;

import java.io.File;
import java.nio.file.FileSystem;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

public class PathTest {
    @Test
    public void test1(){
        Path path = Paths.get("Z:\\My_Files");
        File file = path.toFile();
        File[] files = file.listFiles();
        for (File file1 : files) {
            System.out.println(file1.getName()+ new Date(file1.lastModified()));
        }
    }
    @Test
    public void test2(){
        Path path = Paths.get("Z:\\My_Files");
        System.out.println(path.getParent().toString());
    }
}
