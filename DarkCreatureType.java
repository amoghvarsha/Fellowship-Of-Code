public enum DarkCreatureType 
{
    ORC    ("Orc",    5, false),
    GOBLIN ("Goblin", 9, false),
    TROLL  ("Troll",  3, false);

    private final String  NAME;
    private final int     POWER;
    private final boolean SPECIAL_WEAPON;

    DarkCreatureType(String NAME, int POWER, boolean SPECIAL_WEAPON) 
    {
        this.NAME           = NAME;
        this.POWER          = POWER;
        this.SPECIAL_WEAPON = SPECIAL_WEAPON;
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
        return this.SPECIAL_WEAPON;
    }

    @Override
    public String toString() 
    {
        StringBuilder stringBuilder = new StringBuilder();
        
        stringBuilder.append("Dark Creature Type:\n");
        stringBuilder.append("Name: ").append(this.getName()).append("\n");
        stringBuilder.append("Power: ").append(this.getPower()).append("\n");
        stringBuilder.append("Special Weapon: ").append(this.hasSpecialWeapon() ? "Yes" : "No").append("\n");

        return stringBuilder.toString();
    }
}
