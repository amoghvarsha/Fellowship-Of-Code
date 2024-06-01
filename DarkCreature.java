public class DarkCreature extends Creature 
{
    public DarkCreature(DarkCreatureType type)
    {
        super(type.getName(), type.getPower(), type.hasSpecialWeapon(), false);
        this.token = false;
    }

    @Override
    public String toString() 
    {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("Dark Creature:\n");
        stringBuilder.append("Name: ").append(this.getName()).append("\n");
        stringBuilder.append("Power: ").append(this.getPower()).append("\n");
        stringBuilder.append("Damage: ").append(this.getDamage()).append("\n");
        stringBuilder.append("Special Weapon: ").append(this.hasSpecialWeapon() ? "Yes" : "No").append("\n");
        stringBuilder.append("Alive: ").append(this.isAlive() ? "Yes" : "No").append("\n");
        stringBuilder.append("Token: ").append(this.hasToken() ? "Held" : "Not Held").append("\n");

        return stringBuilder.toString();
    }

    @Override
    protected void handleException(String errMsg) 
    {
        System.err.println("[DarkCreature]: " + errMsg);
        System.out.println("Error Occured, Exiting Program. Check Error Log");
        System.exit(1);
    }
}
