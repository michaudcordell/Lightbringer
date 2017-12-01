public class Player extends Entity {
    private final int defaultX = 24;
    private final int defaultY = 57;
    private final char defaultIcon = '@';
    private int[] pos;
    private char icon;
    private int lvl;
    private int xp;
    private int xpMax;
    private int[] xpMaxes; // The level corresponds to index = level - 1
    private String rpgClass; // only determines the initial stat bonus
    private String race; // only determines the initial stat bonus
    private String weapon; // purely cosmetic
    private String armor; // purely cosmetic
    private int timesToLevel; // used by DisplayHandler during LEVELUP state
    private int kills = 0;

    public int getKills()
    {
        return kills;
    }

    public void setKills(int kills)
    {
        this.kills = kills;
    }

    public Player(String name, char icon, String rpgClass, String race, int initX, int initY, int str, int dex, int vit, int def, int agi)
    {
        super(name,vit * 10, str, dex, vit, def, agi);
        this.pos = new int[2];
        this.pos[0] = initX;
        this.pos[1] = initY;
        this.icon = icon;
        this.lvl = 1;
        this.xp = 0;
        this.xpMax = 10;
        this.rpgClass = rpgClass;
        this.race = race;
        this.weapon = "Purging Battleaxes";
        this.armor = "Priest's Cuirass";
        this.timesToLevel = 0;
    }

    public Player(String name, String race, String rpgClass)
    {
        super(name, 1, 1, 1, 1, 1, 1);
        this.pos = new int[2];
        this.pos[0] = defaultX;
        this.pos[1] = defaultY;
        this.icon = defaultIcon;
        this.xpMaxes = new int[10];
        for (int i = 0; i < xpMaxes.length; i++)
        {
            xpMaxes[i] = (int) Math.round (25 * Math.pow(1.5, i));
        }
        this.lvl = 1;
        this.xp = 0;
        this.xpMax = xpMaxes[this.lvl - 1];
        this.race = race;
        this.rpgClass = rpgClass;
        int strMod, dexMod, vitMod, defMod, agiMod;
        strMod = dexMod = vitMod = defMod = agiMod = 0;

        // Modifying mods based on class
        if (rpgClass.equals("Zealot Berserker"))
        {
            strMod += 3;
            dexMod += 3;
            this.weapon = "Purging Battleaxes";
            this.armor = "Priest's Cuirass";
        }
        else if (race.equals("Holy Wall"))
        {
            defMod += 2;
            vitMod += 2;
            strMod += 1;
            this.weapon = "Shining Hammer";
            this.armor = "Blinding Platebody";
        }
        else if (race.equals("Martial Priest"))
        {
            strMod += 2;
            dexMod += 2;
            agiMod += 2;
            this.weapon = "Purging Daggers";
            this.armor = "Priest's Leathers";
        }
        else if (race.equals("Priest of Balance"))
        {
            strMod += 1;
            dexMod += 1;
            vitMod += 2;
            defMod += 1;
            agiMod += 1;
            this.weapon = "Purging Dagger & Sword";
            this.armor = "Priest's Scalemail";
        }
        else
        {
            strMod += 3;
            dexMod += 3;
            this.weapon = "Purging Battleaxes";
            this.armor = "Priest's Cuirass";
        }

        // Modifying mods based on race
        if (race.equals("Half-Orc"))
        {
            strMod += 3;
            dexMod += 3;
        }
        else if (race.equals("Giant"))
        {
            defMod += 2;
            vitMod += 2;
            strMod += 1;
        }
        else if (race.equals("Elf"))
        {
            strMod += 2;
            dexMod += 2;
            agiMod += 2;
        }
        else if (race.equals("Human"))
        {
            strMod += 1;
            dexMod += 1;
            vitMod += 2;
            defMod += 1;
            agiMod += 1;
        }
        else
        {
            strMod += 3;
            dexMod += 3;
        }

        // allocating stats
        this.str = 1 + strMod;
        this.dex = 1 + dexMod;
        this.vit = 1 + vitMod;
        this.def = 1 + defMod;
        this.agi = 1 + agiMod;
        this.health = this.healthMax = this.vit * 10;

        this.timesToLevel = 0;
    }

    public int getX()
    {
        return pos[0];
    }

    public void setX(int x)
    {
        pos[0] = x;
    }

    public int getY()
    {
        return pos[1];
    }

    public void setY(int y)
    {
        pos[1] = y;
    }

    public char getIcon()
    {
        return icon;
    }

    public int getLvl()
    {
        return lvl;
    }

    public void setLvl(int lvl)
    {
        this.lvl = lvl;
    }

    public void setStr(int str)
    {
        this.str = str;
    }

    public void setDex(int dex)
    {
        this.dex = dex;
    }

    public void setVit(int vit)
    {
        this.vit = vit;
        this.healthMax = this.health = this.vit * 10;
    }

    public void setDef(int def)
    {
        this.def = def;
    }

    public void setAgi(int agi)
    {
        this.agi = agi;
    }

    public boolean setXp(int xp) // returns number of times player needs to level up
    {
        this.xp = xp;
        int timesToLevel = 0;
        while (this.xp > this.xpMax && this.lvl < 10)
        {
            this.lvl += 1;
            this.xpMax = this.xpMaxes[this.lvl - 1];
            timesToLevel += 1;
        }
        this.timesToLevel = timesToLevel;
        if (timesToLevel > 0) return true;
        return false;
    }

    public int getXp()
    {
        return xp;
    }

    public int getTimesToLevel()
    {
        return timesToLevel;
    }

    public void setTimesToLevel(int timesToLevel)
    {
        this.timesToLevel = timesToLevel;
    }

    public void setXpMax(int xpMax)
    {
        this.xpMax = xpMax;
    }

    public int getXpMax()
    {
        return xpMax;
    }

    public String getRPGClass()
    {
        return rpgClass;
    }

    public void setRPGClass(String rpgClass)
    {
        this.rpgClass = rpgClass;
    }

    public void setIcon(char icon)
    {
        this.icon = icon;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public void setRace(String race)
    {
        this.race = race;
    }

    public String getRace()
    {
        return this.race;
    }

    public String getWeapon()
    {
        return this.weapon;
    }

    public String getArmor()
    {
        return this.armor;
    }

    public void setWeapon(String weapon)
    {
        this.weapon = weapon;
    }

    public void setArmor(String armor)
    {
        this.armor = armor;
    }

    public double getHealRoll()
    {
        double amountToHeal = this.getHealthMax() * (0.5 * Math.random());
        //amountToHeal = (this.getHealthMax() * 0.50) * (this.vit / 10); //this.getHealthMax() * (0.75 * Math.random() * (1 / (17 - this.vit)));
        return amountToHeal;
    }

    public void updatePos(char direction)
    {
        switch (direction)
        {
            case 'w':
                setY(getY() - 1);
                break;
            case 's':
                setY(getY() + 1);
                break;
            case 'a':
                setX(getX() - 1);
                break;
            case 'd':
                setX(getX() + 1);
                break;
            default:
                break;
        }
    }
}