import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.function.Function;

public class Obfuscator {

    private static final String source
            ="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789$% -+*=?!/:;()[]^&#@'{}|\"<>";

    private static final String target
            ="KfqGLuW:gN7Y0vO)c+nB298*3|V\"SJ;UT6#?-l}DZMx>b(H=kCP@h^r1'{&ApF5$!<s X4w%]Ea/[ioIyetmdzRjQ";

    public static String obfuscate(final String s) {
        char[] result = new char[s.length()];

        for (int i = 0; i < s.length();++i) {
            char c = s.charAt(i);
            int index = source.indexOf(c);
            result[i] = target.charAt(index);
        }
        return new String(result);
    }

    public static String deobfuscate(final String s) {
        char[] result = new char[s.length()];

        for (int i = 0; i < s.length(); ++i) {
            char c = s.charAt(i);
            int index = target.indexOf(c);
            result[i] = source.charAt(index);
        }
        return new String(result);
    }

    public static void handleXML(final String operation, final String inputFile)
            throws IllegalArgumentException, FileNotFoundException {
        if (!operation.equals("obfuscate")
                && !operation.equals("deobfuscate")) {
            throw new IllegalArgumentException("First parameter is invalid");
        }  else if (!Files.exists(
                Paths.get(new File(inputFile).getAbsolutePath()))) {
            throw new FileNotFoundException("Input file doesn't exist");
        }

        final String outputFile;
        if (operation.equals("obfuscate")) {
            outputFile = new File("obfuscated.xml").getAbsolutePath();
        } else {
            outputFile = new File("deobfuscated.xml").getAbsolutePath();
        }

        Function<String, String> secureFunction = operation.equals("obfuscate")
                ? Obfuscator::obfuscate
                : Obfuscator::deobfuscate;

        SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
        try {
            SAXParser parser = saxParserFactory.newSAXParser();
            ObfuscatorXMLHandler handler
                    = new ObfuscatorXMLHandler(secureFunction, outputFile);
            parser.parse(new File(inputFile), handler);
        } catch (IOException | ParserConfigurationException
                | SAXException e) {
            e.printStackTrace();
        }
    }
}
