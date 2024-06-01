public class LightCreature extends Creature 
{
    public LightCreature(LightCreatureType type, boolean token)
    {
        super(type.getName(), type.getPower(), type.hasSpecialWeapon(), token);
    }

    @Override
    public String toString() 
    {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("Light Creature:\n");
        stringBuilder.append("Name: ").append(this.getName()).append("\n");
        stringBuilder.append("Power: ").append(this.getPower()).append("\n");
        stringBuilder.append("Damage: ").append(this.getDamage()).append("\n");
        stringBuilder.append("Special Weapon: ").append(this.hasSpecialWeapon() ? "Yes" : "No").append("\n");
        stringBuilder.append("Alive: ").append(this.isAlive() ? "Yes" : "No").append("\n");

        return stringBuilder.toString();
    }

    @Override
    protected void handleException(String errMsg) 
    {
        System.err.println("[LightCreature]: " + errMsg);
        System.out.println("Error Occured, Exiting Program. Check Error Log");
        System.exit(1);
    }
}
