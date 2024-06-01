import java.io.*;

public class FileIO 
{
    public static final String readLine(String filename) 
    {
        String str = null;

        if (filename == null) 
        {
            handleException("Argument(s) cannot be null");
        }
        else
        {
            try (BufferedReader br = new BufferedReader(new FileReader(filename))) 
            {
                str = br.readLine();
            }
            catch (FileNotFoundException e)
            {
                String err = String.format("File not found: %s", e.getMessage());
                handleException(err);
            }
            catch (IOException e) 
            {
                String err = String.format("Error reading file: %s", e.getMessage());
                handleException(err);
            }
        }

        return str;
    }

    public static final String[] readLines(String filename) 
    {
        String[] str = null;

        if (filename  == null) 
        {
            handleException("Argument(s) cannot be null");
        }
        else
        {
            try (BufferedReader br = new BufferedReader(new FileReader(filename))) 
            {
                str = br.lines().toArray(String[]::new);
            }
            catch (FileNotFoundException e)
            {
                String err = String.format("File not found: %s", e.getMessage());
                handleException(err);
            }
            catch (IOException e) 
            {
                String err = String.format("Error reading file: %s", e.getMessage());
                handleException(err);
            }
        }

        return str;
    }

    public static final void writeLine(String filename, String line, boolean append) 
    {

        if (filename  == null) 
        {
            handleException("Argument(s) cannot be null");
        }
        else
        {
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename, append))) 
            {
                bw.write(line);
                bw.newLine();
            }
            catch (FileNotFoundException e)
            {
                String err = String.format("File not found: %s", e.getMessage());
                handleException(err);
            }
            catch (IOException e) 
            {
                String err = String.format("Error reading file: %s", e.getMessage());
                handleException(err);
            }
        }
    }

    public static final void writeLines(String filename, String[] lines, boolean append) 
    {
        if (filename  == null) 
        {
            handleException("Argument(s) cannot be null");
        }
        else
        {
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename, append))) 
            {
                for (String line : lines) 
                {
                    bw.write(line);
                    bw.newLine();
                }
            }
            catch (FileNotFoundException e)
            {
                String err = String.format("[FileIO]: File not found: %s", e.getMessage());
                handleException(err);
            }
            catch (IOException e) 
            {
                String err = String.format("[FileIO]: Error reading file: %s", e.getMessage());
                handleException(err);
            }
        }
    }

    private static void handleException(String errMsg) 
    {
        System.err.println("[FileIO]: " + errMsg);
        System.out.println("Error Occured, Exiting Program. Check Error Log");
        System.exit(1);
    }
}
