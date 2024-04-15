import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import interfaces.Token;

public class Test {

    public static void listFiles(String path, List<String> filesList) {
        File folder = new File(path);
        File[] files = folder.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isFile() && file.getName().endsWith(".ads")) {
                    filesList.add(file.getAbsolutePath());
                } else if (file.isDirectory()) {
                    listFiles(file.getAbsolutePath(), filesList);
                }
            }
        }
    }

    public static void main(String[] args) {
        List<String> testFiles = new ArrayList<>();
        listFiles("Tests", testFiles); // Adjust the directory path as needed

        for (String testFile : testFiles) {
            System.out.println("Testing file: " + testFile);
            try {
                // Read the content of the test file
                String sourceCode = new String(Files.readAllBytes(Paths.get(testFile)));

                // Tokenize the content
                List<Token> tokens = lexer.tokenize(sourceCode);

                // Print the tokens
                System.out.println("Tokens:");
                for (Token token : tokens) {
                    System.out.println("Value: " + token.getValue() + ", Type: " + token.getType());
                }

                System.out.println();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
