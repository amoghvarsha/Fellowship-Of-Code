import java.util.*;

public class Fellowship 
{
    private final Map<Integer, LightCreature> MEMBERS = new HashMap<>();
    
    private boolean token;

    public Fellowship() 
    {
        this.token = false;
    }

    public final void addMember(LightCreatureType lightCreatureType, boolean token) 
    {
        if (lightCreatureType == null) 
        {
            handleException("Argument cannot be null");
        }
        else
        {
            int memberId = this.MEMBERS.size() + 1;
            LightCreature lightCreature = new LightCreature(lightCreatureType, token);

            MEMBERS.put(memberId, lightCreature);

            this.token |= token;
        }
    }

    public final LightCreature getMember(int memberId) 
    {
        LightCreature lightCreature = null;

        if (!MEMBERS.isEmpty()) 
        {
            if (MEMBERS.containsKey(memberId)) 
            {
                lightCreature = MEMBERS.get(memberId);
            } 
            else
            {
                handleException("No member found with ID: " + memberId);
            }
        }
        else 
        {
            handleException("No members in the fellowship");
        }

        return lightCreature;
    }

    public final Map<Integer, LightCreature> getMembers() 
    {
        Map<Integer, LightCreature> lightCreatues = null;

        if (!MEMBERS.isEmpty()) 
        {
            lightCreatues = MEMBERS;
        } 
        else 
        {
            handleException("No members in the fellowship");
        }

        return lightCreatues;
    }

    public final boolean hasAliveMembers() 
    {
        boolean anyAlive = false;

        if (!MEMBERS.isEmpty()) 
        {
            anyAlive = MEMBERS.values().stream().anyMatch(LightCreature::isAlive);
        } 
        else 
        {
            handleException("No members in the fellowship to check status");
        }

        return anyAlive;
    }

    public final int getSize() 
    {
        return MEMBERS.size();
    }

    public final void acquireToken() 
    {
        if (this.hasToken())
        {
            handleException("Token already acquired");
        }
        else
        {
            this.token = true;
        }
    }

    public final void loseToken() 
    {
        if (!this.hasToken())
        {
            handleException("Token already lost");
        }
        else
        {
            this.token = false;
            MEMBERS.values().stream().filter(LightCreature::hasToken).forEach(LightCreature::loseToken);
        }
    }

    public final boolean hasToken()
    {
        return this.token;
    }

    public final void recoverDamage() 
    {
        if (!MEMBERS.isEmpty()) 
        {
            for (LightCreature member : this.MEMBERS.values())
            {
                if (member.isAlive())
                {
                    member.recoverDamage();
                }
            }
        } 
        else 
        {
            handleException("No members in the fellowship to recover damage");
        }
    }

    @Override
    public String toString() 
    {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Fellowship:\n");
        stringBuilder.append("Token: ").append(this.hasToken() ? "Held" : "Not Held").append("\n");
        stringBuilder.append("Number of Members: ").append(MEMBERS.size()).append("\n");

        if (!MEMBERS.isEmpty()) 
        {
            stringBuilder.append("Members:\n");
            for (Map.Entry<Integer, LightCreature> entry : MEMBERS.entrySet()) 
            {
                stringBuilder.append("ID: ").append(entry.getKey()).append(" -> ").append(entry.getValue().toString()).append("\n");
            }
        } 
        else 
        {
            stringBuilder.append("No members in the fellowship.\n");
        }

        return stringBuilder.toString();
    }

    private void handleException(String errMsg) 
    {
        System.err.println("[Fellowship]: " + errMsg);
        System.out.println("Error Occured, Exiting Program. Check Error Log");
        System.exit(1);
    }
}
