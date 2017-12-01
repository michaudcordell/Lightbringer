import java.util.ArrayList;
import java.util.HashMap;
import java.io.*;
import java.util.Scanner;

public class Enemy extends Entity {
    private String enemyDir = "";
    private String enemyFileType = ".dat";
    private HashMap<Integer, String> enemyMap = new HashMap<Integer, String>();
    private String desc;
    private int xpValue;
    private double healthMod;
    private double strMod;
    private double dexMod;
    private double vitMod;
    private double defMod;
    private double agiMod;
    private final double xpMod = 5;
    private double fleeChance;
    private double probHeavy;
    private double probLight;
    private double probHeal;
    private double probNothing;

    public int getXpValue()
    {
        return xpValue;
    }

    public String getDesc()
    {
        return this.desc;
    }

    public double getFleeChance()
    {
        return this.fleeChance;
    }

    public double getProbHeavy()
    {
        return probHeavy;
    }

    public double getProbLight()
    {
        return probLight;
    }

    public double getProbHeal()
    {
        return probHeal;
    }

    public double getProbNothing()
    {
        return probNothing;
    }

    public double getHealRoll()
    {
        double amountToHeal = 0.0;
        amountToHeal = (this.getHealthMax() * 0.5) * (this.vit / 21) * (Math.random() * 0.75 + 0.25);
        return amountToHeal;
    }

    public Enemy(int id, int playerLevel)
    {
        // Call default parent constructor to initialize values to 0 and "" as appropriate.
        super();

        // Initialize enemy HashMap links
        enemyMap.put(0, "Goblin");
        enemyMap.put(1, "Dire Wolf");
        enemyMap.put(2, "Reanimated Fighter");
        enemyMap.put(3, "Orc");
        enemyMap.put(4, "Corrupted Knight");
        enemyMap.put(5, "Corruption Tendril");
        enemyMap.put(6, "Tentacled Monstrosity");
        enemyMap.put(7, "Corruption Beast");
        enemyMap.put(8, "Daemon of Blight");
        enemyMap.put(99, "The Devouring Corruption");

        String name = enemyMap.get(id);
        String desc;

        // Read file and assign values to stats
        try {
            File enemyFile = new File(enemyDir + name + enemyFileType);
            Scanner reader = new Scanner(enemyFile);
            desc = reader.nextLine();
            String battleProbsLine = reader.nextLine();
            String[] battleProbsSegmented =
                {
                    battleProbsLine.substring(0, 2),
                    battleProbsLine.substring(5, 7),
                    battleProbsLine.substring(10, 12),
                    battleProbsLine.substring(15, 17),
                    battleProbsLine.substring(20, 22)
                };
            //String[] battleProbsSegmented = battleProbsLine.split(" | ", 5);
            this.fleeChance = Double.parseDouble(battleProbsSegmented[0]) / 100.0;
            this.probHeavy = Double.parseDouble(battleProbsSegmented[1]) / 100.0;
            this.probLight = Double.parseDouble(battleProbsSegmented[2]) / 100.0;
            this.probHeal = Double.parseDouble(battleProbsSegmented[3]) / 100.0;
            this.probNothing = Double.parseDouble(battleProbsSegmented[4]) / 100.0;
            reader.nextLine(); // Skip third line that is for human reading
            String statsLine = reader.nextLine();
            String[] statsStr =
                {
                        statsLine.substring(0, 3),
                        statsLine.substring(6, 8),
                        statsLine.substring(11, 13),
                        statsLine.substring(16, 18),
                        statsLine.substring(21, 23),
                        statsLine.substring(26, 28),
                        statsLine.substring(31, 34),
                        statsLine.substring(37, 39),
                        statsLine.substring(42, 44),
                        statsLine.substring(47, 49),
                        statsLine.substring(52, 54),
                        statsLine.substring(57, 59),
                        statsLine.substring(62, 64)
                };
            //String[] statsStr = reader.nextLine().split(" | ");
            ArrayList<Integer> stats = new ArrayList<Integer>();
            ArrayList<Double> statMods = new ArrayList<Double>();
            for (int i = 0; i < 7; i++)
            {
                stats.add(Integer.parseInt(statsStr[i]));
            }
            for (int i = 7; i < 13; i++)
            {
                statMods.add(Double.parseDouble(statsStr[i]));
            }
            // Assigning details and stats
            this.name = name;
            this.desc = desc;
            this.healthMax = health = stats.get(0);
            this.str = stats.get(1);
            this.dex = stats.get(2);
            this.vit = stats.get(3);
            this.def = stats.get(4);
            this.agi = stats.get(5);
            this.xpValue = stats.get(6);
            this.healthMod = statMods.get(0);
            this.strMod = statMods.get(1);
            this.dexMod = statMods.get(2);
            this.vitMod = statMods.get(3);
            this.defMod = statMods.get(4);
            this.agiMod = statMods.get(5);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            this.name = "";
            this.desc = "";
            this.fleeChance = 1.0;
            this.probHeavy = 0.0;
            this.probLight = 0.0;
            this.probHeal = 0.0;
            this.probNothing = 0.0;
            this.healthMax = 0;
            this.health = 0;
            this.str = 0;
            this.dex = 0;
            this.vit = 0;
            this.def = 0;
            this.agi = 0;
            this.xpValue = 0;
            this.healthMod = 0;
            this.strMod = 0;
            this.dexMod = 0;
            this.vitMod = 0;
            this.defMod = 0;
            this.agiMod = 0;
        }

        // Level up stats
        int additionalLevels = playerLevel - 1;
        this.str += (int) (strMod * additionalLevels);
        this.dex += (int) (dexMod * additionalLevels);
        this.vit += (int) (vitMod * additionalLevels);
        this.def += (int) (defMod * additionalLevels);
        this.agi += (int) (agiMod * additionalLevels);
        this.healthMax += (int) (healthMod * additionalLevels);
        this.health += (int) (healthMod * additionalLevels);
        this.xpValue += (int) (xpMod * additionalLevels);
    }
}
