import java.util.*;

public class GameInterface {
    private final String BANNER_FILENAME = "banner.txt";
    private final Scanner SCANNER;

    public GameInterface() {
        this.SCANNER = new Scanner(System.in);
    }

    public final void close() {
        SCANNER.close();
    }

    public final void quit() {
        ColoredConsole.printlnRed("\nExiting game...\n");
        System.exit(0);
    }

    private void printTableOfOptions(List<String> options) {
        String title = "Menu";
        String[] header = {"Key", "Option"};

        Table table = new Table(title, header);

        Integer key = 1;
        for (String option : options) {
            String[] row = {key.toString(), option};
            table.addRow(row);

            key += 1;
        }

        System.out.println();

        ColoredConsole.setCyan();
        table.displayTable();
        ColoredConsole.resetColor();
    }

    public final int promptOptionsMenu(List<String> options) {
        int choice = -1;
        boolean validChoice = false;

        int lengthOfList = options.size();

        printTableOfOptions(options);

        do {
            try {
                ColoredConsole.printBlue("\n>> Select an option from the menu: ");

                String input = SCANNER.nextLine().trim().toLowerCase();
                if (input.equals("q")) {
                    quit();
                }

                choice = Integer.parseInt(input);
                if (choice >= 1 && choice <= lengthOfList) {
                    validChoice = true;
                } else {
                    ColoredConsole.printlnRed("Invalid option. Please choose again");
                }
            } catch (NumberFormatException e) {
                ColoredConsole.printlnRed("Invalid input. Please enter an integer");
            }
        } while (!validChoice);

        return (choice - 1);
    }

    public final boolean promptYesOrNo(String message) {
        boolean choice = false;
        boolean validChoice = false;

        do {
            ColoredConsole.printBlue("\n>> " + message + " (yes/no): ");

            String response = SCANNER.nextLine().trim().toLowerCase();
            if (response.equals("q")) {
                quit();
            }

            switch (response) {
                case "yes":
                case "y":
                    choice = true;
                    validChoice = true;
                    break;
                case "no":
                case "n":
                    choice = false;
                    validChoice = true;
                    break;
                default:
                    ColoredConsole.printlnRed("Invalid input. Please enter 'yes' or 'no'");
                    break;
            }
        } while (!validChoice);

        return choice;
    }

    public final void printBanner() {
        printFile(BANNER_FILENAME);
    }

    private void printFile(String filename) {
        try {
            String[] lines = FileIO.readLines(filename);

            System.out.println();
            for (String line : lines) {
                ColoredConsole.printlnGreen(line);
            }
        } catch (Exception e) {
            handleException(e.getMessage());
        }
    }

    public final void printMessage(String msg) {
        ColoredConsole.printlnPurple(msg);
    }

    public final void printStatusOfFellowship(Fellowship fellowship) {
        Map<Integer, LightCreature> members = fellowship.getMembers();

        String title = "Fellowship Status Table";
        String[] header = {"Name", "Power", "Damage", "Special Weapon", "Alive", "Code"};

        Table table = new Table(title, header);

        for (Map.Entry<Integer, LightCreature> entry : members.entrySet()) {
            Creature member = entry.getValue();
            String[] row = new String[header.length];

            row[0] = member.getName();
            row[1] = Integer.toString(member.getPower());
            row[2] = Integer.toString(member.getDamage());
            row[3] = member.hasSpecialWeapon() ? "Yes" : "No";
            row[4] = member.isAlive() ? "Yes" : "No";
            row[5] = member.hasToken() ? "Held" : "Not Held";

            table.addRow(row);
        }

        String tokenStatus = String.format("\nThe Fellowship is %sin possession of the code.", fellowship.hasToken() ? "" : "not ");

        ColoredConsole.setCyan();
        System.out.println(tokenStatus);
        System.out.println();
        table.displayTable();
        ColoredConsole.resetColor();
    }

    public final void printStatusOfVisitedCaves(Labyrinth labyrinth) {
        Map<Integer, Cave> visitedCaves = labyrinth.getVisitedCaves();

        String title = "Visted Caves Table";
        String[] header = {"Cave ID", "North Cave", "East Cave", "South Cave", "West Cave", "Creature", "Alive", "Damage", "Code"};

        Table table = new Table(title, header);

        for (Map.Entry<Integer, Cave> entry : visitedCaves.entrySet()) {
            Cave cave = entry.getValue();
            String[] row = new String[header.length];

            int[] connectedCaves = cave.getConnectedCaves();

            row[0] = Integer.toString(cave.getCaveId());

            for (int i = 0; i < connectedCaves.length; i++) {
                Integer value = connectedCaves[i];
                row[i + 1] = (value == 0) ? "None" : value.toString();
            }

            if (cave.hasCreature()) {
                DarkCreature darkCreature = cave.getCreature();

                row[5] = darkCreature.getName();
                row[6] = darkCreature.isAlive() ? "Yes" : "No";
                row[7] = Integer.toString(darkCreature.getDamage());
                row[8] = darkCreature.hasToken() ? "Held" : "Not Held";
            } else {
                row[5] = row[6] = row[7] = row[8] = "*****";
            }

            table.addRow(row);
        }

        ColoredConsole.setCyan();
        System.out.println();
        table.displayTable();
        ColoredConsole.resetColor();
    }

    private void handleException(String errMsg) {
        System.err.println("[GameInterface]: " + errMsg);
        System.out.println("Error Occurred, Exiting Program. Check Error Log");
        System.exit(1);
    }
}
