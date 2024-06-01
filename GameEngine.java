import java.util.*;

/**
 * Main game engine for the Fellowship of Code game.
 * Handles the game logic, user interactions, and game state transitions.
 */
public class GameEngine 
{
    private static final int FELLOWSHIP_SIZE = 4;
    private static final int WIN_DAMAGE = 1;
    private static final int LOSE_DAMAGE = 4;

    private final GameInterface gameInterface;
    private boolean gameOver;

    private int numberOfTotalFights;
    private int numberOfFellowshipWins;
    private int numberOfSpecialFights;
    private int numberOfNormalFights;
    private int numberOfTokenExchanges;
    private int numberOfCavesVisited;

    public GameEngine() 
    {
        gameInterface = new GameInterface();
        gameReset();
    }

    private void gameReset()
    {
        numberOfTotalFights    = 0;
        numberOfFellowshipWins = 0;
        numberOfSpecialFights  = 0;
        numberOfNormalFights   = 0;
        numberOfTokenExchanges = 0;
        numberOfCavesVisited   = 0;

        gameOver = false;
    }

    private void setGameOver(boolean gameOver) 
    {
        this.gameOver = gameOver;
    }

    private boolean isGameOver() 
    {
        return this.gameOver;
    }

    public final void start() 
    {
        boolean playAgain;
        do 
        {
            gameInterface.printBanner();

            if (gameInterface.promptYesOrNo("Are you ready to embark on the quest with the Fellowship of Code?")) 
            {
                setGameOver(false);
                gameReset();
                runGameLoop();
                playAgain = gameInterface.promptYesOrNo("Do you want to embark on another quest?");
            } 
            else 
            {
                playAgain = false;
            }

        } while (playAgain);

        gameInterface.close();
    }

    private void runGameLoop() 
    {
        Labyrinth labyrinth = new Labyrinth();
        Fellowship fellowship = new Fellowship();

        labyrinth.load();
        int currentCaveId = labyrinth.getFirstCaveId();

        fellowship.addMember(LightCreatureType.HOBBIT, true);
        buildFellowship(fellowship);

        gameInterface.printMessage("\nThe Fellowship has been assembled. The quest begins!");

        while (!isGameOver()) 
        {
            processCave(fellowship, labyrinth, currentCaveId);
            
            checkGameOver(fellowship, labyrinth, currentCaveId);
            if (isGameOver()) 
            {
                break;
            }

            int nextCaveId = selectNextCave(labyrinth, currentCaveId);

            // Print status after selecting the next cave
            printGameStatus(fellowship, labyrinth);

            checkGameOver(fellowship, labyrinth, nextCaveId);
            if (isGameOver()) 
            {
                break;
            }

            currentCaveId = nextCaveId;
        }

        printGameStats();
    }

    private void printGameStatus(Fellowship fellowship, Labyrinth labyrinth) 
    {
        gameInterface.printStatusOfFellowship(fellowship);
        gameInterface.printStatusOfVisitedCaves(labyrinth);
    }

    private void printGameStats() 
    {
        gameInterface.printMessage("\nGame Over! Here are your game stats:");
        gameInterface.printMessage("\nTotal number of fights   : " + numberOfTotalFights);
        gameInterface.printMessage("Number of fellowship wins: " + numberOfFellowshipWins);
        gameInterface.printMessage("Number of special fights : " + numberOfSpecialFights);
        gameInterface.printMessage("Number of normal fights  : " + numberOfNormalFights);
        gameInterface.printMessage("Number of token exchanges: " + numberOfTokenExchanges);
        gameInterface.printMessage("Number of caves visited  : " + numberOfCavesVisited);

        double successRate = (numberOfTotalFights > 0) ? ((double) numberOfFellowshipWins * 100 / numberOfTotalFights) : 0;
        gameInterface.printMessage("Fellowship fight success rate: " + String.format("%.2f", successRate) + "%");
    }

    private void checkGameOver(Fellowship fellowship, Labyrinth labyrinth, int currentCaveId)
    {
        if (!fellowship.hasAliveMembers()) 
        {
            gameInterface.printMessage("\nYour fellowship lies defeated. The darkness prevails as their valiant efforts come to naught.");
            setGameOver(true);
        }
        else if (currentCaveId == labyrinth.getMountApiCaveId()) 
        {
            if (fellowship.hasToken()) 
            {
                gameInterface.printMessage("\nCongratulations! With the secret code secured, you deliver the code to the Java wizard atop Mount Api. Middle Earth is safe again!");
            } 
            else 
            {
                gameInterface.printMessage("\nUnfortunately, without the secret code, your quest ends in failure. The fate of Middle Earth remains uncertain.");
            }
            setGameOver(true);
        }
    }

    private void processCave(Fellowship fellowship, Labyrinth labyrinth, int currentCaveId) 
    {
        gameInterface.printMessage("\nEntering Cave " + currentCaveId + "...");

        Cave currentCave = labyrinth.getCave(currentCaveId);
        
        if (!currentCave.isVisited()) 
        {
            numberOfCavesVisited++;
            currentCave.markAsVisited();
        }

        if (currentCave.hasCreature() && currentCave.getCreature().isAlive()) 
        {
            DarkCreature darkCreature = currentCave.getCreature();
            gameInterface.printMessage("\nYou have encountered " + darkCreature.getName() + "! Prepare for battle!");
            fight(fellowship, darkCreature);
        } 
        else 
        {
            gameInterface.printMessage("\nThe cave is empty. Take this time to recover and prepare for the journey ahead.");
            fellowship.recoverDamage();
        }
    }

    private void fight(Fellowship fellowship, DarkCreature darkCreature) 
    {
        LightCreature lightCreature = selectFellowshipMemberForFight(fellowship);

        if (lightCreature.hasSpecialWeapon()) 
        {
            boolean useSpecialWeapon = gameInterface.promptYesOrNo("Do you want to use the special weapon?");
            if (useSpecialWeapon) 
            {
                specialFight(fellowship, lightCreature, darkCreature);
            } 
            else 
            {
                normalFight(fellowship, lightCreature, darkCreature);
            }
        } 
        else 
        {
            normalFight(fellowship, lightCreature, darkCreature);
        }

        numberOfTotalFights++;
    }

    private void specialFight(Fellowship fellowship, LightCreature lightCreature, DarkCreature darkCreature) 
    {
        lightCreature.useSpecialWeapon();
        gameInterface.printMessage(lightCreature.getName() + " vanquished " + darkCreature.getName() + " with their special weapon!");
        darkCreature.kill();

        if (darkCreature.hasToken()) 
        {
            gameInterface.printMessage(lightCreature.getName() + " has recovered the secret code!");
            darkCreature.loseToken();
            fellowship.acquireToken();
            lightCreature.acquireToken();

            numberOfTokenExchanges++;
        }

        numberOfFellowshipWins++;
        numberOfSpecialFights++;
    }

    private void normalFight(Fellowship fellowship, LightCreature lightCreature, DarkCreature darkCreature) 
    {
        double probability = winProbabilityOfLightCreature(lightCreature, darkCreature);

        if (Math.random() <= probability) // Light Creature Won
        { 
            gameInterface.printMessage(lightCreature.getName() + " won the fight against " + darkCreature.getName() + "!");
            lightCreature.takeDamage(WIN_DAMAGE);
            darkCreature.takeDamage(LOSE_DAMAGE);

            if (darkCreature.hasToken()) 
            {
                gameInterface.printMessage(lightCreature.getName() + " has recovered the secret code!");
                darkCreature.loseToken();
                fellowship.acquireToken();
                lightCreature.acquireToken();

                numberOfTokenExchanges++;
            }

            numberOfFellowshipWins++;
        } 
        else // Dark Creature Won
        { 
            gameInterface.printMessage(darkCreature.getName() + " won the fight against " + lightCreature.getName() + "!");
            darkCreature.takeDamage(WIN_DAMAGE);
            lightCreature.takeDamage(LOSE_DAMAGE);

            if (fellowship.hasToken()) 
            {
                gameInterface.printMessage(darkCreature.getName() + " has stolen the secret code!");
                fellowship.loseToken();
                darkCreature.acquireToken();

                numberOfTokenExchanges++;
            }
        }

        numberOfNormalFights++;
    }

    private void buildFellowship(Fellowship fellowship) 
    {
        gameInterface.printMessage("\nSelect up to 3 members to join your Fellowship in addition to the hobbit leader.");
        gameInterface.printMessage("Choose wisely to balance power and strategy for your journey ahead.");

        List<LightCreatureType> lightCreatureTypes = Arrays.stream(LightCreatureType.values())
                .filter(type -> type != LightCreatureType.HOBBIT)
                .collect(Collectors.toList());

        List<String> lightCreatureTypeNames = lightCreatureTypes.stream()
                .map(LightCreatureType::getName)
                .collect(Collectors.toList());

        while (fellowship.getSize() < FELLOWSHIP_SIZE) 
        {
            boolean addMember = gameInterface.promptYesOrNo(String.format("Do you wish to add member %d to your Fellowship?", fellowship.getSize() + 1));
            if (!addMember) 
            {
                break;
            } 
            else 
            {
                addMemberToFellowship(fellowship, lightCreatureTypes, lightCreatureTypeNames);
            }
        }

        gameInterface.printStatusOfFellowship(fellowship);
    }

    private void addMemberToFellowship(Fellowship fellowship, List<LightCreatureType> lightCreatureTypes, List<String> lightCreatureTypeNames) 
    {
        int index = gameInterface.promptOptionsMenu(lightCreatureTypeNames);
        LightCreatureType selectedType = lightCreatureTypes.get(index);
        gameInterface.printMessage("You have selected " + selectedType.getName() + " to join your Fellowship.");
        fellowship.addMember(selectedType, false);
    }

    private int selectNextCave(Labyrinth labyrinth, int currentCaveId) 
    {
        Cave currentCave = labyrinth.getCave(currentCaveId);
        int[] connectedCaves = currentCave.getConnectedCaves();
    
        // Identify connected caves
        boolean north = connectedCaves[0] != 0;
        boolean east  = connectedCaves[1] != 0;
        boolean south = connectedCaves[2] != 0;
        boolean west  = connectedCaves[3] != 0;
    
        // Build the cave map UI
        String caveMap = buildCaveMap(north, east, south, west);

        gameInterface.printMessage("\nYou have explored Cave " + currentCaveId + ".");
        gameInterface.printMessage("Now, you must choose the next cave to venture into.");
        gameInterface.printMessage("Each cave holds its own secrets and challenges. Choose wisely.");
        gameInterface.printMessage(caveMap);
    
        // Prepare list of options for the user to select
        List<String> directions = new ArrayList<>();
        List<Integer> nextCaveIds = new ArrayList<>();
    
        if (north) 
        {
            directions.add("Proceed North to Cave " + connectedCaves[0]);
            nextCaveIds.add(connectedCaves[0]);
        }
        if (east) {
            directions.add("Proceed East to Cave " + connectedCaves[1]);
            nextCaveIds.add(connectedCaves[1]);
        }
        if (south) {
            directions.add("Proceed South to Cave " + connectedCaves[2]);
            nextCaveIds.add(connectedCaves[2]);
        }
        if (west) {
            directions.add("Proceed West to Cave " + connectedCaves[3]);
            nextCaveIds.add(connectedCaves[3]);
        }
    
        // Prompt the user to select a direction
        int index = gameInterface.promptOptionsMenu(directions);
        int nextCaveId = nextCaveIds.get(index);
    
        gameInterface.printMessage("The Fellowship advances to Cave " + nextCaveId + ", ready for the unknown.");
        return nextCaveId;
    }
    
    private String buildCaveMap(boolean north, boolean east, boolean south, boolean west) {
        StringBuilder caveMap = new StringBuilder();
    
        caveMap.append("\n");
        caveMap.append(String.format("            +--%s--+     \n" , north ? "N" :"-"));
        caveMap.append(String.format("            |     |      \n"));
        caveMap.append(String.format("       +----+     +----+  \n"));
        caveMap.append(String.format("       %s       @       %s \n", west ? "W" : "|", east ? "E" : "|"));
        caveMap.append(String.format("       +----+     +----+  \n"));
        caveMap.append(String.format("            |     |      \n"));
        caveMap.append(String.format("            +--%s--+       " , south ? "S" : "-"));
    
        return caveMap.toString();
    }
    
    private LightCreature selectFellowshipMemberForFight(Fellowship fellowship) 
    {
        gameInterface.printMessage("\nSelect a member of your Fellowship to fight the creature.");
        gameInterface.printMessage("Consider the strengths and special abilities of each member.");
        
        Map<Integer, LightCreature> members = fellowship.getMembers();
        List<LightCreature> aliveMembers = members.values().stream()
                .filter(LightCreature::isAlive)
                .collect(Collectors.toList());

        List<String> aliveMemberNames = aliveMembers.stream()
                .map(LightCreature::getName)
                .collect(Collectors.toList());

        int index = gameInterface.promptOptionsMenu(aliveMemberNames);
        LightCreature selectedLightCreature = aliveMembers.get(index);
        gameInterface.printMessage("Brave " + selectedLightCreature.getName() + " steps forward to face the challenge.");
        return selectedLightCreature;
    }

    private double winProbabilityOfLightCreature(LightCreature lightCreature, DarkCreature darkCreature) 
    {
        double probability;

        int lightCreaturePower = lightCreature.getPower();
        int darkCreaturePower = darkCreature.getPower();
        int powerDifference = Math.abs(lightCreaturePower - darkCreaturePower);

        if (powerDifference >= 4) 
        {
            probability = 0.9;
        } 
        else if (powerDifference >= 3) 
        {
            probability = 0.8;
        } 
        else if (powerDifference >= 2) 
        {
            probability = 0.7;
        } 
        else if (powerDifference >= 1) 
        {
            probability = 0.6;
        } 
        else 
        {
            probability = 0.5;
        }

        if (darkCreaturePower > lightCreaturePower) 
        {
            probability = 1.0 - probability;
        }

        return probability;
    }
}
