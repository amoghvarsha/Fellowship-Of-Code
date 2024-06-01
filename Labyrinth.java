import java.util.*;

public class Labyrinth 
{
    private final int                MOUNT_API_CAVE_ID = 100;
    private final String             FILENAME = "labyrinth.txt";
    private final Map<Integer, Cave> CAVES = new LinkedHashMap<>();
    
    private int firstCaveId;

    public Labyrinth()
    {
        this.firstCaveId = -1;
    }

    public final Cave getCave(int caveId)
    {
        Cave cave = null;

        if (CAVES.containsKey(caveId))
        {
            cave = CAVES.get(caveId);
        } 
        else 
        {
            handleException("Invalid caveId: " + caveId);
        }

        return cave;
    }

    public final int getFirstCaveId()
    {
        int caveId = -1;

        if (this.firstCaveId == -1)
        {
            handleException("Value not initialized");
        }
        else
        {
            caveId = this.firstCaveId;
        }

        return caveId;
    }

    public final int getMountApiCaveId()
    {
        return this.MOUNT_API_CAVE_ID;
    }

    public final Map<Integer, Cave> getCaves()
    {
        Map<Integer, Cave> caves = null;

        if (!CAVES.isEmpty())
        {
            caves = CAVES;
        }
        else 
        {
            handleException("No caves found in the labyrinth");
        }

        return caves;
    }

    public final Map<Integer, Cave> getVisitedCaves() 
    {
        Map<Integer, Cave> visitedCaves = null;

        if (!CAVES.isEmpty())
        {
            Map<Integer, Cave> tempVisitedCaves = new HashMap<>();
            for (Map.Entry<Integer, Cave> entry : CAVES.entrySet()) 
            {
                if (entry.getValue().isVisited()) 
                {
                    tempVisitedCaves.put(entry.getKey(), entry.getValue());
                }
            }
            visitedCaves = tempVisitedCaves;
        }
        else 
        {
            handleException("No caves found in the labyrinth");
        }

        return visitedCaves;
    }

    public final void load() 
    {
        try 
        {
            String[] lines = FileIO.readLines(this.FILENAME);

            for (String line : lines) 
            {
                String[] values = line.split(",");
            
                int caveId = Integer.parseInt(values[0]);
                int north  = Integer.parseInt(values[1]);
                int east   = Integer.parseInt(values[2]);
                int south  = Integer.parseInt(values[3]);
                int west   = Integer.parseInt(values[4]);
                
                if (caveId != MOUNT_API_CAVE_ID)
                {
                    int[] connectedCaves = {north, east, south, west};
                
                    Cave cave = new Cave(caveId, connectedCaves);
                    
                    CAVES.put(caveId, cave);
                }

                if (firstCaveId == -1)
                {
                    firstCaveId = caveId;
                }
            }
        }
        catch (NumberFormatException e)
        {
            String err = String.format("Invalid integer in '%s'", this.FILENAME);
            handleException(err);
        }
        catch (Exception e)
        {
            handleException(e.getMessage());
        }
    }

    private void handleException(String errMsg) 
    {
        System.err.println("[Labyrinth]: " + errMsg);
        System.out.println("Error Occured, Exiting Program. Check Error Log");
        System.exit(1);
    }

    @Override
    public String toString() 
    {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Labyrinth:\n");
        stringBuilder.append("First Cave ID: ").append(firstCaveId != -1 ? firstCaveId : "None").append("\n");
        stringBuilder.append("Mount API Cave ID: ").append(MOUNT_API_CAVE_ID).append("\n");
        stringBuilder.append("Total Number of Caves: ").append(CAVES.size()).append("\n");

        if (!CAVES.isEmpty()) 
        {
            stringBuilder.append("Caves:\n");
            for (Map.Entry<Integer, Cave> entry : CAVES.entrySet()) 
            {
                stringBuilder.append("ID: ").append(entry.getKey()).append(" -> ").append(entry.getValue().toString()).append("\n");
            }
        } 
        else 
        {
            stringBuilder.append("No caves found in the labyrinth.\n");
        }

        return stringBuilder.toString();
    }
}
