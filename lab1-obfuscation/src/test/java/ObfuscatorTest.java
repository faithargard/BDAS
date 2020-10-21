import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.Assert.*;

public class ObfuscatorTest {

    private String operation;

    private final String inputPath = "src/test/resources/test.xml";

    @Test
    public void invalidOperationTest() {
        operation = "obfuscatee";
        Throwable thrown = assertThrows(IllegalArgumentException.class, () -> {
            Obfuscator.handleXML(operation, inputPath);
        });
        assertEquals("First parameter is invalid", thrown.getMessage());
    }

    @Test
    public void invalidInputFileTest() {
        operation = "obfuscate";
        String invalidInputFile = "src/test/resources/file.xml";
        Throwable thrown = assertThrows(FileNotFoundException.class, () -> {
            Obfuscator.handleXML(operation, invalidInputFile);
        });
        assertEquals("Input file doesn't exist", thrown.getMessage());
    }

    @Test
    public void obfuscatedOutputFileCreationTest() {
        operation = "obfuscate";
        try {
            Obfuscator.handleXML(operation, inputPath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        Path outputFile = Paths
                .get(new File("obfuscated.xml").getAbsolutePath());
        assertTrue(Files.exists(outputFile));

        try {
            Files.delete(outputFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void deobfuscatedOutputFileCreationTest() {
        operation = "deobfuscate";
        try {
            Obfuscator.handleXML(operation, inputPath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        Path outputFile = Paths
                .get(new File("deobfuscated.xml").getAbsolutePath());
        assertTrue(Files.exists(outputFile));

        try {
            Files.delete(outputFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void obfuscationTest() {
        String str = "String to be obfuscated";
        String obfuscatedStr = Obfuscator.obfuscate(str);
        assertNotEquals(str, obfuscatedStr);
    }

    @Test
    public void deobfuscationTest() {
        String str = "String to be \"obfuscated\"";
        String obfuscatedStr = Obfuscator.obfuscate(str);
        String deobfuscatedStr = Obfuscator.deobfuscate(obfuscatedStr);
        assertEquals(str, deobfuscatedStr);
    }

    @Test
    public void inputAndDeobfuscatedFilesEqualityTest()
    {
        String obfuscatedFile = "obfuscated.xml";
        try {
            operation = "obfuscate";
            Obfuscator.handleXML(operation, inputPath);
            operation = "deobfuscate";
            Obfuscator.handleXML(operation, obfuscatedFile);

            String deobfuscatedFile = new File("deobfuscated.xml")
                    .getAbsolutePath();
            assertTrue(FileUtils
                    .contentEquals(new File(inputPath), new File(deobfuscatedFile)));

            Files.delete( Paths
                    .get(new File(obfuscatedFile).getAbsolutePath()));
            Files.delete( Paths
                    .get(new File(deobfuscatedFile).getAbsolutePath()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
