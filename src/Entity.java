public class Entity {
    protected String name;
    protected double healthMax;
    protected double health;
    protected int str;
    protected int dex;
    protected int vit;
    protected int def;
    protected int agi;

    public Entity()
    {
        this.name = "";
        this.healthMax = 0;
        this.health = 0;
        this.str = 0;
        this.dex = 0;
        this.vit = 0;
        this.def = 0;
        this.agi = 0;
    }

    public Entity(String name, double healthMax, int str, int dex, int vit, int def, int agi)
    {
        this.name = name;
        this.healthMax = healthMax;
        this.health = healthMax;
        this.str = str;
        this.dex = dex;
        this.vit = vit;
        this.def = def;
        this.agi = agi;
    }

    public String getName()
    {
        return name;
    }

    public int getStr()
    {
        return str;
    }

    public int getDex()
    {
        return dex;
    }

    public int getVit()
    {
        return vit;
    }

    public int getDef()
    {
        return def;
    }

    public int getAgi()
    {
        return agi;
    }

    public double getHealthMax()
    {
        return this.healthMax;
    }

    public double getHealth()
    {
        return this.health;
    }

    public void setHealth(double health)
    {
        this.health = health;
        if (this.health < 0)
        {
            this.health = 0;
        }
        else if (this.health > this.healthMax)
        {
            this.health = this.healthMax;
        }
    }

    public int[] attack(Entity other, String attackType)
    {
        final int[] result = {0, 0};
        final int BLOCKED = -1;
        final int EVADED = -2;
        final int HEAVY = 0;
        final int LIGHT = 1;
        int roll = 0;
        if (attackType.equals("Heavy"))
        {
            roll = (int) (Math.random() * (0.5 * (this.str + 1)));
            result[0] = HEAVY;
        }
        else
        {
            roll = (int) (Math.random() * (this.dex + 1));
            result[0] = LIGHT;
        }

        if (roll < other.getDefRoll())
        {
            result[1] = BLOCKED;
        }
        else if (roll < other.getAgiRoll())
        {
            result[1] = EVADED;
        }
        else
        {
            result[1] = attackType.equals("Heavy") ? (roll * 4) : (roll); // If the attack is not blocked or evaded, set result[1] to the amount of damage done.
        }

        return result;
    }

    public int getDefRoll()
    {
        int roll = (int) (Math.random() * (this.def + 1));
        return roll;
    }
    public int getAgiRoll()
    {
        int roll = (int) (Math.random() * (this.agi + 1));
        return roll;
    }
}
