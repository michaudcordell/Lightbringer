import java.util.Scanner;

public class BattleManager {

    // screen settings
    private static final int screenLength = DisplayHandler.getScreenLength();
    private static final int screenHeight = DisplayHandler.getScreenHeight();
    private static final int mapDispHeight = DisplayHandler.getMapDispHeight();

    private static String healthBar(Entity entity, int chunks, char chunkChar)
    {
        String bar = "";
        bar += "[";
        double healthFraction = (double) entity.health / (double) entity.healthMax;
        int filledChunks = (int) Math.round(healthFraction * chunks);
        int unfilledChunks = chunks - filledChunks;
        for (int i = 0; i < filledChunks; i++)
        {
            bar += chunkChar;
        }
        for (int i = 0; i < unfilledChunks; i++)
        {
            bar += " ";
        }
        bar += "]";
        return bar;
    }

    private static String fightUI(Player player, Enemy enemy, String[] desc)
    {
        String toDisp = "";
        toDisp += "|";
        for (int i = 1; i < screenLength - 1; i++)
        {
            toDisp += "-";
        }
        toDisp += "|\n";

        for (int row = 1; row <= mapDispHeight; row++)
        {
            toDisp += "|";
            switch (row)
            {
                case 5:
                    toDisp += DisplayHandler.centeredString("ENCOUNTER", screenLength - 2);
                    break;
                case 10:
                    toDisp += DisplayHandler.centeredString(enemy.getName(), screenLength - 2);
                    break;
                case 11:
                    toDisp += DisplayHandler.centeredString("Health: ", screenLength - 2);
                    break;
                case 12:
                    toDisp += DisplayHandler.centeredString(healthBar(enemy, 20, '#'), screenLength - 2);
                    break;
                case 17:
                    toDisp += DisplayHandler.centeredString(player.getName() + ", Lvl " + player.getLvl() + " " + player.getRPGClass(), screenLength - 2);
                    break;
                case 18:
                    toDisp += DisplayHandler.centeredString("Health: ", screenLength - 2);
                    break;
                case 19:
                    toDisp += DisplayHandler.centeredString(healthBar(player, 20, '#'), screenLength - 2);
                    break;
                case 23:
                    toDisp += DisplayHandler.centeredString("[1] Heavy Attack  [2] Light Attack  [3] Heal  [4] Flee", screenLength - 2);
                    break;
                default:
                    toDisp += DisplayHandler.centeredString("", screenLength - 2);
                    break;
            }
            toDisp += "|\n";
        }

        toDisp += "|";
        for (int i = 1; i < screenLength - 1; i++)
        {
            toDisp += "-";
        }
        toDisp += "|\n";

        // add player action information
        toDisp += "|";
        toDisp += DisplayHandler.centeredString(desc[0], screenLength - 2);
        toDisp += "|\n";
        // add enemy action information
        toDisp += "|";
        toDisp += DisplayHandler.centeredString(desc[1], screenLength - 2);
        toDisp += "|\n";
        // add final empty lines
        for (int line = 0; line < 8; line++)
        {
            toDisp += "|" + DisplayHandler.centeredString(" ", screenLength - 2) + "|\n";
        }

        //toDisp += DisplayHandler.multiLineString(desc[0], screenLength - 2, 2, "| ");
        //toDisp += "\n";
        //toDisp += DisplayHandler.multiLineString(desc[1], screenLength - 2, 2, "| ");
        //toDisp += "\n";

        toDisp += "|";
        for (int i = 1; i < screenLength - 1; i++)
        {
            toDisp += "-";
        }
        toDisp += "|\n";
        return toDisp;
    }

    public static int fight(Player player, EncounterRegion encounter) // returns the state to go to after battle ends
    {
        final int MAIN_MENU = 0;
        final int EXPLORATION = 1;
        final int BATTLE = 2;
        final int ENDING = 3;
        final int QUITTING = 4;
        final int DEATH = 5;
        final int LEVELUP = 6;

        final int BLOCKED = -1;
        final int EVADED = -2;
        final int HEAVY = 0;
        final int LIGHT = 1;

        Scanner in = new Scanner(System.in);

        Enemy enemy = new Enemy(encounter.getenemyID(), player.getLvl());

        double enemyProbHeavy = enemy.getProbHeavy();
        double enemyProbLight = enemyProbHeavy + enemy.getProbLight();
        double enemyProbHeal = enemyProbLight + enemy.getProbHeal();
        double enemyProbNothing = enemyProbHeal + enemy.getProbNothing();

        // TODO Insert code for displaying encounter splash screen

        String[] descText = {enemy.getDesc() + " You ready your " + player.getWeapon() + ".", ""}; // descText[0] initially holds monster description, after one loop, it will start to take on the value of player action, and descText[1] will take on the value of the monster action.
        int[] playerBattleResult = new int[2];
        char key;
        do
        {
            System.out.println(fightUI(player, enemy, descText));
            String curLine = in.nextLine();
            if (curLine.length() != 0) key = curLine.charAt(0);
            else key = ' ';
            if (InputHandler.isFlee(key))
            {
                double fleeChance = enemy.getFleeChance();
                double roll = Math.random() * (1 / player.getAgi());
                if (roll < fleeChance)
                {
                    System.out.println("You flee successfully!");
                    break;
                }
                else
                {
                    descText[0] = "You failed to flee.";
                }
            }
            else if (InputHandler.isBattleKey(key))
            {
                switch (key)
                {
                    case '1':
                        // Heavy Attack
                        playerBattleResult = player.attack(enemy, "Heavy");
                        if (playerBattleResult[1] == BLOCKED)
                        {
                            descText[0] = "Your heavy attack is blocked by " + enemy.getName() + ".";
                        }
                        else if (playerBattleResult[1] == EVADED)
                        {
                            descText[0] = "Your heavy attack is evaded by " + enemy.getName() + ".";
                        }
                        else
                        {
                            descText[0] = "Your heavy attack deals " + playerBattleResult[1] + " damage to " + enemy.getName() + ".";
                            enemy.setHealth((double) (enemy.getHealth() - playerBattleResult[1]));
                        }
                        break;
                    case '2':
                        // Light Attack
                        playerBattleResult = player.attack(enemy, "Light");
                        if (playerBattleResult[1] == BLOCKED)
                        {
                            descText[0] = "Your light attack is blocked by " + enemy.getName() + ".";
                        }
                        else if (playerBattleResult[1] == EVADED)
                        {
                            descText[0] = "Your light attack is evaded by " + enemy.getName() + ".";
                        }
                        else
                        {
                            descText[0] = "Your light attack deals " + playerBattleResult[1] + " damage to " + enemy.getName() + ".";
                            enemy.setHealth((double) (enemy.getHealth() - playerBattleResult[1]));
                        }
                        break;
                    case '3':
                        // Heal
                        double amountToHeal = player.getHealRoll();
                        player.setHealth(player.getHealth() + amountToHeal);
                        descText[0] = "You cast a divine miracle, healing yourself for " + amountToHeal + " hit points.";
                        break;
                    default:
                        descText[0] = "You wait and observe " + enemy.getName() + "'s actions.";
                        break;
                }
            }
            double enemyRoll = Math.random();
            if (enemyRoll < enemyProbHeavy)
            {
                // Heavy Attack
                int[] enemyBattleResult = enemy.attack(player, "Heavy");
                if (enemyBattleResult[1] == BLOCKED)
                {
                    descText[1] = "You block " + enemy.getName() + "'s heavy attack.";
                }
                else if (enemyBattleResult[1] == EVADED)
                {
                    descText[1] = "You evade " + enemy.getName() + "'s heavy attack.";
                }
                else
                {
                    descText[1] = enemy.getName() + "'s heavy attack deals " + enemyBattleResult[1] + " damage to you.";
                    player.setHealth((double) (player.getHealth() - enemyBattleResult[1]));
                }
            }
            else if (enemyRoll < enemyProbLight && enemyProbLight != 0.0)
            {
                // Light Attack
                int[] enemyBattleResult = enemy.attack(player, "Light");
                if (enemyBattleResult[1] == BLOCKED)
                {
                    descText[1] = "You block " + enemy.getName() + "'s light attack.";
                }
                else if (enemyBattleResult[1] == EVADED)
                {
                    descText[1] = "You evade " + enemy.getName() + "'s light attack.";
                }
                else
                {
                    descText[1] = enemy.getName() + "'s light attack deals " + enemyBattleResult[1] + " damage to you.";
                    player.setHealth((double) (player.getHealth() - enemyBattleResult[1]));
                }
            }
            else if (enemyRoll < enemyProbHeal && enemyProbHeal != 0.0)
            {
                double amountToHeal = enemy.getHealRoll();
                enemy.setHealth(enemy.getHealth() + amountToHeal);
                descText[1] = enemy.getName() + " heals for " + amountToHeal + " hit points.";
            }
            else
            {
                descText[1] = enemy.getName() + " waits and observes your actions.";
            }

        }
        while(!InputHandler.isQuit(key) && !(player.getHealth() <= 0) && !(enemy.getHealth() <= 0));

        if (InputHandler.isFlee(key))
        {
            return EXPLORATION;
        }

        if (InputHandler.isQuit(key))
        {
            return QUITTING;
        }

        if (player.getHealth() <= 0)
        {
            return DEATH;
        }

        encounter.setEncountered(true);
        if (encounter.getenemyID() == 99) // If enemy is boss, return ENDING
        {
            player.setKills(player.getKills() + 1);
            return ENDING;
        }
        if (player.setXp(player.getXp() + enemy.getXpValue()))
        {
            player.setKills(player.getKills() + 1);
            return LEVELUP; // In DisplayHandler, the LEVELUP case will switch the state to EXPLORATION when necessary
        }

        player.setKills(player.getKills() + 1);
        return EXPLORATION;
    }
}
