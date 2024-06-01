import java.util.*;

public class Cave 
{
    private static final double SPAWN_PROBABILITY_THRESHOLD = 0.75;
    
    private final int    CAVE_ID;
    private final int[]  CONNECTED_CAVES;
    
    private DarkCreature darkCreature;
    private boolean      visited;

    public Cave(int CAVE_ID, int[] CONNECTED_CAVES) 
    {
        this.CAVE_ID         = CAVE_ID;
        this.CONNECTED_CAVES = CONNECTED_CAVES.clone(); // make a copy
        this.darkCreature    = null;
        this.visited         = false;

        this.trySpawnDarkCreatureRandomly();
    }

    public final boolean hasCreature() 
    {
        return (this.darkCreature != null);
    }

    public final boolean isVisited()
    {
        return this.visited;
    }

    public final int getCaveId() 
    {
        return this.CAVE_ID;
    }

    public final int[] getConnectedCaves() 
    {
        return this.CONNECTED_CAVES.clone();
    }

    public final DarkCreature getCreature() 
    {
        if (this.darkCreature == null) 
        {
            handleException("No creature in this cave");
        }
        
        return this.darkCreature;
    }

    public final void markAsVisited()
    {
        this.visited = true;
    }

    private void trySpawnDarkCreatureRandomly() 
    {
        if (this.darkCreature != null) 
        {
            handleException("There is already a creature in this cave");
        }

        DarkCreatureType[] darkCreatures = DarkCreatureType.values();
        if (darkCreatures.length == 0) 
        {
            handleException("No dark creature types available");
        }

        Random rand = new Random();

        if (rand.nextDouble() > SPAWN_PROBABILITY_THRESHOLD) 
        {
            this.darkCreature = null;
        } 
        else 
        {
            int index = rand.nextInt(darkCreatures.length);
            this.darkCreature = new DarkCreature(darkCreatures[index]);
        }
    }

    @Override
    public String toString() 
    {
        return String.format
            (
                "Cave - [ID: %d, Connected Caves: %s, Has Creature: %b, Visited: %b, Dark Creature: %s]",
                this.CAVE_ID,
                Arrays.toString(this.CONNECTED_CAVES),
                this.hasCreature() ? "Yes" : "No",
                this.isVisited() ? "Yes" : "No",
                this.hasCreature() ? this.darkCreature.getName() : "None"
            );
    }

    private void handleException(String errMsg) 
    {
        System.err.println("[Cave]: " + errMsg);
        System.out.println("Error Occured, Exiting Program. Check Error Log");
        System.exit(1);
    }
}