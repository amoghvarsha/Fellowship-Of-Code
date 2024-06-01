/*
* Code taken from: https://www.geeksforgeeks.org/how-to-print-colored-text-in-java-console/#
*/

public class ColoredConsole 
{
    private static final String ANSI_RESET  = "\u001B[0m";
    private static final String ANSI_BLUE   = "\u001B[34m";
    private static final String ANSI_CYAN   = "\u001B[36m"; // Added cyan color
    private static final String ANSI_GREEN  = "\u001B[32m";
    private static final String ANSI_PURPLE = "\u001B[35m";
    private static final String ANSI_RED    = "\u001B[31m";
    private static final String ANSI_YELLOW = "\u001B[33m";

    // Methods to set color without printing
    public static final void resetColor() 
    {
        System.out.print(ANSI_RESET);
    }

    public static final void setBlue() 
    {
        System.out.print(ANSI_BLUE);
    }

    public static final void setCyan() // Added cyan color method
    {
        System.out.print(ANSI_CYAN);
    }

    public static final void setGreen() 
    {
        System.out.print(ANSI_GREEN);
    }

    public static final void setPurple() 
    {
        System.out.print(ANSI_PURPLE);
    }

    public static final void setRed() 
    {
        System.out.print(ANSI_RED);
    }

    public static final void setYellow() 
    {
        System.out.print(ANSI_YELLOW);
    }

    // New methods to print messages in specified colors
    public static final void printColored(String message, String color) 
    {
        System.out.print(color + message + ANSI_RESET);
    }

    public static final void printlnColored(String message, String color) 
    {
        System.out.println(color + message + ANSI_RESET);
    }

    public static final void printBlue(String message) 
    {
        printColored(message, ANSI_BLUE);
    }

    public static final void printlnBlue(String message) 
    {
        printlnColored(message, ANSI_BLUE);
    }

    public static final void printCyan(String message) 
    {
        printColored(message, ANSI_CYAN);
    }

    public static final void printlnCyan(String message)
    {
        printlnColored(message, ANSI_CYAN);
    }

    public static final void printGreen(String message) 
    {
        printColored(message, ANSI_GREEN);
    }

    public static final void printlnGreen(String message) 
    {
        printlnColored(message, ANSI_GREEN);
    }

    public static final void printPurple(String message) 
    {
        printColored(message, ANSI_PURPLE);
    }

    public static final void printlnPurple(String message) 
    {
        printlnColored(message, ANSI_PURPLE);
    }

    public static final void printRed(String message) 
    {
        printColored(message, ANSI_RED);
    }

    public static final void printlnRed(String message) 
    {
        printlnColored(message, ANSI_RED);
    }

    public static final void printYellow(String message) 
    {
        printColored(message, ANSI_YELLOW);
    }

    public static final void printlnYellow(String message) 
    {
        printlnColored(message, ANSI_YELLOW);
    }
}
