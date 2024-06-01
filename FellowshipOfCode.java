import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;

public class FellowshipOfCode 
{
    private static final String ERROR_FILE = "error.log";

    private static void redirectErrorToFile(String errorFile) throws Exception
    {
        File file = new File(errorFile);
		FileOutputStream fileOutputStream = new FileOutputStream(file);
		PrintStream printStream = new PrintStream(fileOutputStream);
		System.setErr(printStream);
    }

    public static void main(String[] args) throws Exception 
    {
        redirectErrorToFile(ERROR_FILE);

        GameEngine game = new GameEngine();
        game.start();
    }
}
