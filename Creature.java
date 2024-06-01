public abstract class Creature 
{
    protected final String  NAME;
    protected final int     POWER;

    protected int     damage;
    protected boolean specialWeapon;
    protected boolean alive;
    protected boolean token;

    public Creature(String NAME, int POWER, boolean specialWeapon, boolean token)
    {
        this.NAME          = NAME;
        this.POWER         = POWER;
    
        this.damage        = 0;
        this.specialWeapon = specialWeapon;
        this.alive         = true;
        this.token         = token;
    }

    public final int getDamage()
    {
        return this.damage;
    }

    public final String getName()
    {
        return this.NAME;
    }

    public final int getPower()
    {
        return this.POWER;
    }

    public final boolean hasSpecialWeapon()
    {
        return this.specialWeapon;
    }

    public final boolean isAlive()
    {
        return this.alive;
    }

    public final void kill()
    {
        if (this.isAlive())
        {
            this.alive  = false;
            this.damage = -1;
        }
        else
        {
            // Should never happen
            handleException("A dead creature cannot be killed");
        }
    }

    public final void recoverDamage()
    {
        if (this.isAlive())
        {
            if (this.damage > 0)
            {
                this.damage -= 1;
            }
        }
        else
        {
            // Should never happen
            handleException("A dead creature cannot recover damage");
        }
    }

    public final void takeDamage(int damage)
    {
        if (this.isAlive())
        {
            this.damage += damage;
            if (this.damage >= 10)
            {
                this.alive = false;
                this.damage = -1;
            }
        }
        else
        {
            // Should never happen
            handleException("A dead creature cannot take damage");
        }
    }

    public final void useSpecialWeapon()
    {
        if (this.hasSpecialWeapon())
        {
            this.specialWeapon = false;
        }
        else
        {
            // Should never happen
            handleException("A special weapon cannot be used again");
        }
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
        }
    }

    public final boolean hasToken()
    {
        return this.token;
    }

    @Override
    public String toString() 
    {
        return String.format
        (
            "Creature - [Name: %s, Power: %d, Damage: %d, Special Weapon: %s, Alive: %s]",
            this.getName(), 
            this.getPower(),
            this.getDamage(), 
            this.hasSpecialWeapon() ? "Yes" : "No",
            this.isAlive() ? "Yes" : "No"
        );
    }

    protected void handleException(String errMsg) 
    {
        System.err.println("[Creature]: " + errMsg);
        System.out.println("Error Occured, Exiting Program. Check Error Log");
        System.exit(1);
    }
}
