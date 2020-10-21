import java.io.FileNotFoundException;

public class Main {

    public static void main(String[] args) {
        if (args.length != 2)
            throw new IllegalArgumentException("Must be 2 input arguments:"
                    + "'obfuscate /deobfuscate' and input file path");
        try {
            Obfuscator.handleXML(args[0], args[1]);
        } catch (FileNotFoundException | IllegalArgumentException e) {
            e.printStackTrace();
        }
    }
}
