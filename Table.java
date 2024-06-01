import java.util.*;

public class Table 
{
    private final String         TITLE;
    private final String[]       HEADER;
    private final List<String[]> ROWS = new ArrayList<>();

    public Table(String TITLE, String[] HEADER) 
    {
        if (TITLE == null || HEADER == null) 
        {
            handleException("Argument(s) cannot be null");
        }
        
        this.TITLE  = TITLE;
        this.HEADER = HEADER;
    }

    public final void addRow(String[] row) 
    {
        if (row == null) 
        {
            handleException("Argument cannot be null");
        }
        else
        {
            if (row.length != HEADER.length) 
            {
                handleException("Row does not have the same number of elements as the HEADER");
            }

            ROWS.add(row);
        }
    }
    
    public final void displayTable() 
    {
        int[] columnWidths = calculateColumnWidths(HEADER, ROWS);

        printSeparator(columnWidths); 
        printTitle(TITLE, columnWidths);
        printSeparator(columnWidths);
        printRow(HEADER, columnWidths);
        printSeparator(columnWidths);
        
        for (String[] row : ROWS) 
        {
            printRow(row, columnWidths);
            printSeparator(columnWidths);
        }
    }

    private int[] calculateColumnWidths(String[] HEADER, List<String[]> ROWS) 
    {
        int[] columnWidths = new int[HEADER.length];
        
        for (int i = 0; i < HEADER.length; i++)
        {
            columnWidths[i] = HEADER[i].length();
        }
        
        for (String[] row : ROWS) 
        {
            for (int i = 0; i < row.length; i++) 
            {
                if (row[i].length() > columnWidths[i]) 
                {
                    columnWidths[i] = row[i].length();
                }
            }
        }
        
        return columnWidths;
    }

    private void printRow(String[] row, int[] columnWidths) 
    {
        System.out.print("|");
        for (int i = 0; i < row.length; i++) 
        {
            String format = " %-" + columnWidths[i] + "s |";
            System.out.printf(format, row[i]);
        }
        System.out.println();
    }

    private void printSeparator(int[] columnWidths) 
    {
        System.out.print("+");
        for (int width : columnWidths) 
        {
            System.out.print("-".repeat(width + 2) + "+");
        }
        System.out.println();
    }

    private void printTitle(String TITLE, int[] columnWidths) 
    {
        int titleWidth = TITLE.length();

        int totalWidth = Arrays.stream(columnWidths).sum() + 3 * columnWidths.length + 1; 
        
        // Subtract 2 for the border characters
        int padding = (totalWidth - titleWidth - 2) / 2; 
        String space = " ".repeat(Math.max(0, padding));
        
        int extraPadding = (totalWidth - titleWidth - 2) % 2; 
        String extraSpace = " ".repeat(Math.max(0, extraPadding));

        System.out.println("|" + space + TITLE + space + extraSpace + "|");
    }    
    
    private void handleException(String errMsg) 
    {
        System.err.println("[Table]: " + errMsg);
        System.out.println("Error Occured, Exiting Program. Check Error Log");
        System.exit(1);
    }
}
