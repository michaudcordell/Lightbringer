import java.io.IOException;
import java.util.Scanner;

public final class DisplayHandler {
    private static final int MAIN_MENU = 0;
    private static final int EXPLORATION = 1;
    private static final int BATTLE = 2;
    private static final int ENDING = 3;
    private static final int QUITTING = 4;
    private static final int DEATH = 5;
    private static final int LEVELUP = 6;
    private static final int HELP = 7;

    // screen settings
    private static final int screenLength = 210;
    private static final int screenHeight = 50;

    // exploration UI settings
    private static final int mapDispLength = 158; // 2 more characters comes from the vertical bounding box
    private static final int mapDispHeight = 38; // 2 more characters comes from the horizontal bounding box
    private static final int statsDispLength = screenLength - mapDispLength - 3; // 3 characters are taken up by the vertical bounding box
    private static final int statsDispHeight = 38; // 2 more characters comes from the horizontal bounding box
    private static final int descDispLength = screenLength - mapDispLength - 3; // 3 characters are taken up by the vertical bounding box
    private static final int descDispHeight = screenHeight - mapDispHeight - 3; // 3 characters are taken up by the horizontal boundign box

    private static final int viewDistLength = 24; // This is the distance from the player in both directions
    private static final int viewDistHeight = 12; // This is the distance from the player in both directions

    private static final String[] title3D =
    {
        " _     _____ _____  _   _ _________________ _____ _   _ _____  ___________ ",
        "| |   |_   _|  __ \\| | | |_   _| ___ \\ ___ \\_   _| \\ | |  __ \\|  ___| ___ \\",
        "| |     | | | |  \\/| |_| | | | | |_/ / |_/ / | | |  \\| | |  \\/| |__ | |_/ /",
        "| |     | | | | __ |  _  | | | | ___ \\    /  | | | . ` | | __ |  __||    / ",
        "| |_____| |_| |_\\ \\| | | | | | | |_/ / |\\ \\ _| |_| |\\  | |_\\ \\| |___| |\\ \\ ",
        "\\_____/\\___/ \\____/\\_| |_/ \\_/ \\____/\\_| \\_|\\___/\\_| \\_/\\____/\\____/\\_| \\_\\"
    };

    private static final String[] title3DPromptText =
    {
        "____________ _____ _____ _____   _____ _   _ _____ ___________   _____ _____   _____ _____ _   _ _____ _____ _   _ _   _ _____ ",
        "| ___ \\ ___ \\  ___/  ___/  ___| |  ___| \\ | |_   _|  ___| ___ \\ |_   _|  _  | /  __ \\  _  | \\ | |_   _|_   _| \\ | | | | |  ___|",
        "| |_/ / |_/ / |__ \\ `--.\\ `--.  | |__ |  \\| | | | | |__ | |_/ /   | | | | | | | /  \\/ | | |  \\| | | |   | | |  \\| | | | | |__  ",
        "|  __/|    /|  __| `--. \\`--. \\ |  __|| . ` | | | |  __||    /    | | | | | | | |   | | | | . ` | | |   | | | . ` | | | |  __| ",
        "| |   | |\\ \\| |___/\\__/ /\\__/ / | |___| |\\  | | | | |___| |\\ \\    | | \\ \\_/ / | \\__/\\ \\_/ / |\\  | | |  _| |_| |\\  | |_| | |___ ",
        "\\_|   \\_| \\_\\____/\\____/\\____/  \\____/\\_| \\_/ \\_/ \\____/\\_| \\_|   \\_/  \\___/   \\____/\\___/\\_| \\_/ \\_/  \\___/\\_| \\_/\\___/\\____/ "
};

    private static final String[] titleCharCreationPt1 =
    {
        " _____  _   _   ___  ______  ___  _____ _____ ___________ ",
        "/  __ \\| | | | / _ \\ | ___ \\/ _ \\/  __ \\_   _|  ___| ___ \\",
        "| /  \\/| |_| |/ /_\\ \\| |_/ / /_\\ \\ /  \\/ | | | |__ | |_/ /",
        "| |    |  _  ||  _  ||    /|  _  | |     | | |  __||    / ",
        "| \\__/\\| | | || | | || |\\ \\| | | | \\__/\\ | | | |___| |\\ \\ ",
        "\\____/\\_| |_/\\_| |_/\\_| \\_\\_| |_/\\____/ \\_/ \\____/\\_| \\_| "
    };

    private static final String[] titleCharCreationPt2 =
    {
        " _____ ______ _____  ___ _____ _____ _____ _   _ ",
        "/  __ \\| ___ \\  ___|/ _ \\_   _|_   _|  _  | \\ | |",
        "| /  \\/| |_/ / |__ / /_\\ \\| |   | | | | | |  \\| |",
        "| |    |    /|  __||  _  || |   | | | | | | . ` |",
        "| \\__/\\| |\\ \\| |___| | | || |  _| |_\\ \\_/ / |\\  |",
        "\\____/\\_| \\_\\____/\\_| |_/\\_/  \\___/ \\___/\\_| \\_/ "
    };

    private static String[] titleGoodbye =
    {
        " _____ _   _   ___   _   _  _   __ _____  ______ ___________  ______ _       _____   _______ _   _ _____ ",
        "|_   _| | | | / _ \\ | \\ | || | / //  ___| |  ___|  _  | ___ \\ | ___ \\ |     / _ \\ \\ / /_   _| \\ | |  __ \\",
        "  | | | |_| |/ /_\\ \\|  \\| || |/ / \\ `--.  | |_  | | | | |_/ / | |_/ / |    / /_\\ \\ V /  | | |  \\| | |  \\/  ",
        "  | | |  _  ||  _  || . ` ||    \\  `--. \\ |  _| | | | |    /  |  __/| |    |  _  |\\ /   | | | . ` | | __   ",
        "  | | | | | || | | || |\\  || |\\  \\/\\__/ / | |   \\ \\_/ / |\\ \\  | |   | |____| | | || |  _| |_| |\\  | |_\\ \\  ",
        "  \\_/ \\_| |_/\\_| |_/\\_| \\_/\\_| \\_/\\____/  \\_|    \\___/\\_| \\_| \\_|   \\_____/\\_| |_/\\_/  \\___/\\_| \\_/\\____/  "
    };

    private static String[] titleDeath =
    {
           "__   _______ _   _   _   _   ___  _   _ _____  ______ _____ ___________ ",
           "\\ \\ / /  _  | | | | | | | | / _ \\| | | |  ___| |  _  \\_   _|  ___|  _  \\",
           " \\ V /| | | | | | | | |_| |/ /_\\ \\ | | | |__   | | | | | | | |__ | | | |",
           "  \\ / | | | | | | | |  _  ||  _  | | | |  __|  | | | | | | |  __|| | | |",
           "  | | \\ \\_/ / |_| | | | | || | | \\ \\_/ / |___  | |/ / _| |_| |___| |/ / ",
           "  \\_/  \\___/ \\___/  \\_| |_/\\_| |_/\\___/\\____/  |___/  \\___/\\____/|___/  "
    };

    public static int updateDisplay(int state, Player player, Map map) throws IOException, InterruptedException // Return state to switch to after handling display
    {
        Scanner userin = new Scanner(System.in);
        clearScreen();
        switch (state)
        {
            case MAIN_MENU:
                displayMainMenu();
                userin.nextLine();
                // TODO Insert code for an opening cinematic

                // Character Creation

                // Choosing Name
                clearScreen();
                displayCharacterCreation();
                System.out.print("Enter character name: ");
                String playername = userin.nextLine();

                // Choosing Race
                String chosenRace = "";
                String options = "1234";
                do
                {
                    clearScreen();
                    displayCharacterCreation();
                    System.out.print("[1] Half-Orc  [2] Giant  [3] Elf  [4] Human\nSelect a race: ");
                    chosenRace = userin.nextLine();
                } while (!options.contains("" + chosenRace.charAt(0)));
                switch (chosenRace.charAt(0))
                {
                    case '1':
                        chosenRace = "Half-Orc";
                        break;
                    case '2':
                        chosenRace = "Giant";
                        break;
                    case '3':
                        chosenRace = "Elf";
                        break;
                    case '4':
                        chosenRace = "Human";
                        break;
                    default:
                        chosenRace = "Half-Orc";
                        break;
                }

                // Choosing Class
                String chosenClass = "";
                options = "1234";
                do
                {
                    clearScreen();
                    displayCharacterCreation();
                    System.out.print("[1] Zealot Berserker  [2] Holy Wall  [3] Martial Priest  [4] Priest of Balance\nSelect a class: ");
                    chosenClass = userin.nextLine();
                } while (!options.contains("" + chosenClass.charAt(0)));
                switch (chosenClass.charAt(0))
                {
                    case '1':
                        chosenClass = "Zealot Berserker";
                        break;
                    case '2':
                        chosenClass = "Holy Wall";
                        break;
                    case '3':
                        chosenClass = "Martial Priest";
                        break;
                    case '4':
                        chosenClass = "Priest of Balance";
                        break;
                    default:
                        chosenClass = "Zealot Berserker";
                        break;
                }

                // Assigning information to the player supplied by the Game class
                Player newPlayer = new Player(playername, chosenRace, chosenClass);
                player.setX(newPlayer.getX());
                player.setY(newPlayer.getY());
                player.setAgi(newPlayer.getAgi());
                player.setDef(newPlayer.getDef());
                player.setDex(newPlayer.getDex());
                player.setStr(newPlayer.getStr());
                player.setVit(newPlayer.getVit());
                player.setLvl(newPlayer.getLvl());
                player.setXp(newPlayer.getXp());
                player.setXpMax(newPlayer.getXpMax());
                player.setRace(newPlayer.getRace());
                player.setRPGClass(newPlayer.getRPGClass());
                player.setName(newPlayer.getName());
                player.setIcon(newPlayer.getIcon());
                clearScreen();
                displayCharacterOverview(player);
                userin.nextLine();
                return EXPLORATION;
            case EXPLORATION:
                displayExploration(map, player);
                return EXPLORATION;
            case LEVELUP:
                while (player.getTimesToLevel() > 0)
                {
                    char key;
                    options = "12345";
                    do
                    {
                        clearScreen();
                        displayLevelUp(player);
                        key = userin.nextLine().charAt(0);
                    } while (!options.contains("" + key));

                    switch (key)
                    {
                        case '1':
                            player.setStr(player.getStr() + 1);
                            break;
                        case '2':
                            player.setDex(player.getDex() + 1);
                            break;
                        case '3':
                            player.setVit(player.getVit() + 1);
                            break;
                        case '4':
                            player.setDef(player.getDef() + 1);
                            break;
                        case '5':
                            player.setAgi(player.getAgi() + 1);
                            break;
                        default:
                            player.setStr(player.getStr() + 1);
                            break;
                    }

                    player.setTimesToLevel(player.getTimesToLevel() - 1);
                }
                return EXPLORATION;
            case HELP:
                // TODO Display screen with controls and prompt to press enter.
                return EXPLORATION;
            case ENDING:
                // TODO Insert code for an ending display/animation, which will be shown upon completing the game, then a prompt to press enter.
                return QUITTING;
            case QUITTING:
                displayGoodbye();
                userin.nextLine();
                return QUITTING;
            case DEATH:
                displayDeath(player);
                userin.nextLine();
                return QUITTING;
            default:
                System.out.println("Invalid display state!: " + state);
                return EXPLORATION;
        }
    }

    private static int dispCoordsToMapCoordsX(Map map, Player player, int col)
    {
        // defining utility variables
        int mapPaddingLeft = (int) ((mapDispLength - (2 * viewDistLength + 1))/2);

        // converting column to x
        return (player.getX() - viewDistLength) + (col - mapPaddingLeft);
    }

    private static int dispCoordsToMapCoordsY(Map map, Player player, int row)
    {
        // defining utility variables
        int mapPaddingTop = (int) ((mapDispHeight - (2 * viewDistHeight + 1))/2);

        // converting column to x
        return (player.getY() - viewDistHeight) + (row - mapPaddingTop);
    }

    public static String centeredString(String text, int fieldLength)
    {
        String output = "";
        int leftPadding = (int) ((fieldLength - text.length()) / 2);
        int rightPadding = fieldLength - text.length() - leftPadding;
        for (int i = 0; i < leftPadding; i++)
        {
            output += " ";
        }
        output += text;
        for (int i = 0; i < rightPadding; i++)
        {
            output += " ";
        }

        return output;
    }

    public static String multiLineString(String text, int fieldLength, int height, String vertBorder)
    {
        String output = "";
        String[] lines = new String[height];
        for (int i = 0; i < lines.length; i++)
        {
            lines[i] = "";
        }

        boolean allLinesTrimmed = false;
        lines[0] = text;
        while(!allLinesTrimmed)
        {
            for (int i = 0; i < lines.length - 1; i++)
            {
                if (lines[i].length() > fieldLength - 2 * vertBorder.length())
                {
                    lines[i+1] = lines[i].substring(fieldLength, lines[i].length());
                    lines[i] = lines[i].substring(0, fieldLength);
                }
            }
            if (lines[lines.length - 1].length() > fieldLength)
            {
                lines[lines.length - 1] = lines[lines.length - 1].substring(0, fieldLength);
            }

            allLinesTrimmed = true;
            for (String str : lines)
            {
                if (str.length() > fieldLength - 2 * vertBorder.length()) allLinesTrimmed = false;
            }
        }

        for (int i = 0; i < lines.length; i++)
        {
            output += vertBorder + lines[i] + vertBorder;
            if (i < lines.length - 1) output += "\n";
        }

        return output;
    }

    private static void displayExploration(Map map, Player player)
    {
        // defining utility variables
        int mapPaddingLeft = (int) ((mapDispLength - (2 * viewDistLength + 1))/2);
        if (player.getX() - viewDistLength < 0) mapPaddingLeft += 0 - (player.getX() - viewDistLength);
        int mapPaddingRight = mapPaddingLeft + 1;
        if (player.getX() + viewDistLength > map.getLength() - 1) mapPaddingRight += (player.getX() + viewDistLength) - (map.getLength() - 1);
        int mapPaddingTop = (int) ((mapDispHeight - (2 * viewDistHeight + 1))/2);
        if (player.getY() - viewDistHeight < 0) mapPaddingTop += 0 - (player.getY() - viewDistLength);
        int mapPaddingBottom = mapPaddingTop + 1;
        if (player.getX() + viewDistHeight > map.getHeight() - 1) mapPaddingBottom += (player.getY() + viewDistLength) - (map.getHeight() - 1);

        // Initializing string to display
        String toDisp = "";

        // add first horizontal bounding line
        toDisp += "|";
        for (int i = 0; i < screenLength - 2; i++)
        {
            toDisp += "-";
        }
        toDisp += "|\n";

        // iterate through the rows until the next horizontal bounding line
        for (int row = 1; row <= mapDispHeight; row++)
        {
            toDisp += "|";
                for (int col = 1; col <= mapDispLength; col++) {
                    if (row <= mapPaddingTop || row >= mapDispHeight - mapPaddingBottom)
                    {
                        toDisp += " ";
                    }
                    else
                    {
                        if (col <= mapPaddingLeft || row >= mapDispLength - mapPaddingRight)
                        {
                            toDisp += " ";
                        }
                        else
                        {
                            if (player.getX() == dispCoordsToMapCoordsX(map, player, col) && player.getY() == dispCoordsToMapCoordsY(map, player, row))
                            {
                                toDisp += player.getIcon();
                            }
                            else
                            {
                                toDisp += map.getCharAtPos(dispCoordsToMapCoordsX(map, player, col), dispCoordsToMapCoordsY(map, player, row));
                            }
                        }
                    }
                }

                // add vertical bounding line
                toDisp += "|";
                switch (row)
                {
                    case 2:
                        toDisp += centeredString("* " + player.getName() + " *", statsDispLength);
                        break;
                    case 4:
                        toDisp += centeredString("Level " + player.getLvl() + " " + player.getRace() + " " + player.getRPGClass(), statsDispLength);
                        break;
                    case 6:
                        toDisp += centeredString("Stats", statsDispLength);
                        break;
                    case 7:
                        toDisp += centeredString("STR: " + player.getStr(), statsDispLength);
                        break;
                    case 8:
                        toDisp += centeredString("DEX: " + player.getDex(), statsDispLength);
                        break;
                    case 9:
                        toDisp += centeredString("VIT: " + player.getVit(), statsDispLength);
                        break;
                    case 10:
                        toDisp += centeredString("DEF: " + player.getDef(), statsDispLength);
                        break;
                    case 11:
                        toDisp += centeredString("AGI: " + player.getAgi(), statsDispLength);
                        break;
                    case 14:
                        toDisp += centeredString(String.format("Health: %3d/%3d", (int) player.getHealth(), (int) player.getHealthMax()), statsDispLength);
                        break;
                    case 15:
                        toDisp += centeredString(String.format("Exp: %3d/%3d", (int) player.getXp(), (int) player.getXpMax()), statsDispLength);
                        break;
                    case 17:
                        toDisp += centeredString(String.format("Weapon: %s", player.getWeapon()), statsDispLength); // Weapon determined by class in character creation
                        break;
                    case 18:
                        toDisp += centeredString(String.format("Armor: %s", player.getArmor()), statsDispLength); // Armor determined by class in character creation
                        break;
                    default:
                        for (int i = 0; i < statsDispLength; i++)
                        {
                            toDisp += " ";
                        }
                        break;
                }

                // add vertical bounding line and newline
                toDisp += "|\n";
        }

        // add second horizontal bounding line
        toDisp += "|";
        for (int i = 0; i < screenLength - 2; i++)
        {
            toDisp += "-";
        }
        toDisp += "|\n";

        // add location information
        toDisp += "|";
        toDisp += centeredString(map.getLocText(player.getX(), player.getY()), screenLength - 2);
        /*for (int i = 0; i < screenLength - 3 - (map.getLocText(player.getX(), player.getY())).length(); i++)
        {
            toDisp += " ";
        }*/
        toDisp += "|\n";

        // add descText
        toDisp += "|";
        toDisp += centeredString(map.getDescText(player.getX(), player.getY()), screenLength - 2);
        toDisp += "|\n";

        // add final empty lines
        for (int line = 0; line < 8; line++)
        {
            toDisp += "|" + centeredString(" ", screenLength - 2) + "|\n";
        }
        //toDisp += "|";
        //toDisp += centeredString(" ", screenLength - 2);
        //toDisp += "|\n";
        //toDisp += "|";
        //toDisp += centeredString(" ", screenLength - 2);
        //toDisp += "|\n";
        //toDisp += multiLineString(map.getDescText(player.getX(), player.getY()), descDispLength, descDispHeight, "| ");

        // add third horizontal bounding line
        toDisp += "|";
        for (int i = 0; i < screenLength - 2; i++)
        {
            toDisp += "-";
        }
        toDisp += "|";

        System.out.println(toDisp);
    }

    private static void clearScreen() throws IOException, InterruptedException
    {
        new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
    }

    private static void displayMainMenu()
    {
        String toDisp = "";

        toDisp += "|";
        for (int i = 1; i < screenLength - 1; i++)
        {
            toDisp += "-";
        }
        toDisp += "|\n";

        for (int row = 1; row < screenHeight - 1; row++)
        {
            toDisp += "|";
            switch(row)
            {

                case 6:
                    toDisp += centeredString(title3D[0], screenLength - 2);
                    break;
                case 7:
                    toDisp += centeredString(title3D[1], screenLength - 2);
                    break;
                case 8:
                    toDisp += centeredString(title3D[2], screenLength - 2);
                    break;
                case 9:
                    toDisp += centeredString(title3D[3], screenLength - 2);
                    break;
                case 10:
                    toDisp += centeredString(title3D[4], screenLength - 2);
                    break;
                case 11:
                    toDisp += centeredString(title3D[5], screenLength - 2);
                    break;
                case 14:
                    toDisp += centeredString(title3DPromptText[0], screenLength - 2);
                    break;
                case 15:
                    toDisp += centeredString(title3DPromptText[1], screenLength - 2);
                    break;
                case 16:
                    toDisp += centeredString(title3DPromptText[2], screenLength - 2);
                    break;
                case 17:
                    toDisp += centeredString(title3DPromptText[3], screenLength - 2);
                    break;
                case 18:
                    toDisp += centeredString(title3DPromptText[4], screenLength - 2);
                    break;
                case 19:
                    toDisp += centeredString(title3DPromptText[5], screenLength - 2);
                    break;
                default:
                    toDisp += centeredString("", screenLength - 2);
                    break;
            }
            toDisp += "|\n";
        }

        toDisp += "|";
        for (int i = 1; i < screenLength - 1; i++)
        {
            toDisp += "-";
        }
        toDisp += "|";
        System.out.println(toDisp);
    }

    private static void displayCharacterCreation()
    {
        String toDisp = "";

        toDisp += "|";
        for (int i = 1; i < screenLength - 1; i++)
        {
            toDisp += "-";
        }
        toDisp += "|\n";

        for (int row = 1; row < screenHeight - 1; row++)
        {
            toDisp += "|";
            switch(row)
            {

                case 6:
                    toDisp += centeredString(titleCharCreationPt1[0], screenLength - 2);
                    break;
                case 7:
                    toDisp += centeredString(titleCharCreationPt1[1], screenLength - 2);
                    break;
                case 8:
                    toDisp += centeredString(titleCharCreationPt1[2], screenLength - 2);
                    break;
                case 9:
                    toDisp += centeredString(titleCharCreationPt1[3], screenLength - 2);
                    break;
                case 10:
                    toDisp += centeredString(titleCharCreationPt1[4], screenLength - 2);
                    break;
                case 11:
                    toDisp += centeredString(titleCharCreationPt1[5], screenLength - 2);
                    break;
                case 14:
                    toDisp += centeredString(titleCharCreationPt2[0], screenLength - 2);
                    break;
                case 15:
                    toDisp += centeredString(titleCharCreationPt2[1], screenLength - 2);
                    break;
                case 16:
                    toDisp += centeredString(titleCharCreationPt2[2], screenLength - 2);
                    break;
                case 17:
                    toDisp += centeredString(titleCharCreationPt2[3], screenLength - 2);
                    break;
                case 18:
                    toDisp += centeredString(titleCharCreationPt2[4], screenLength - 2);
                    break;
                case 19:
                    toDisp += centeredString(titleCharCreationPt2[5], screenLength - 2);
                    break;
                default:
                    toDisp += centeredString("", screenLength - 2);
                    break;
            }
            toDisp += "|\n";
        }

        toDisp += "|";
        for (int i = 1; i < screenLength - 1; i++)
        {
            toDisp += "-";
        }
        toDisp += "|";

        System.out.println(toDisp);
    }

    private static void displayCharacterOverview(Player player)
    {
        String toDisp = "";

        toDisp += "|";
        for (int i = 1; i < screenLength - 1; i++)
        {
            toDisp += "-";
        }
        toDisp += "|\n";

        for (int row = 1; row < screenHeight - 1; row++)
        {
            toDisp += "|";
            switch(row)
            {
                case 10:
                    toDisp += centeredString("*** CHARACTER OVERVIEW ***", screenLength - 2);
                    break;
                case 13:
                    toDisp += centeredString("Biographical Details", screenLength - 2);
                    break;
                case 14:
                    toDisp += centeredString(String.format("Character Name: %s", player.getName()), screenLength - 2);
                    break;
                case 15:
                    toDisp += centeredString(String.format("Race: %s", player.getRace()), screenLength - 2);
                    break;
                case 16:
                    toDisp += centeredString(String.format("Class: %s", player.getRPGClass()), screenLength - 2);
                    break;
                case 18:
                    toDisp += centeredString("Equipment", screenLength - 2);
                    break;
                case 19:
                    toDisp += centeredString(String.format("Weapon: %s", player.getWeapon()), screenLength - 2);
                    break;
                case 20:
                    toDisp += centeredString(String.format("Armor: %s", player.getArmor()), screenLength - 2);
                    break;
                case 22:
                    toDisp += centeredString("Stats", screenLength - 2);
                    break;
                case 23:
                    toDisp += centeredString(String.format("Strength: %d", player.getStr()), screenLength - 2);
                    break;
                case 24:
                    toDisp += centeredString(String.format("Dexterity: %d", player.getDex()), screenLength - 2);
                    break;
                case 25:
                    toDisp += centeredString(String.format("Vitality: %d", player.getVit()), screenLength - 2);
                    break;
                case 26:
                    toDisp += centeredString(String.format("Defense: %d", player.getDef()), screenLength - 2);
                    break;
                case 27:
                    toDisp += centeredString(String.format("Agility: %d", player.getAgi()), screenLength - 2);
                    break;
                case 30:
                    toDisp += centeredString("Press enter to continue.", screenLength - 2);
                    break;
                default:
                    toDisp += centeredString("", screenLength - 2);
                    break;
            }
            toDisp += "|\n";
        }

        toDisp += "|";
        for (int i = 1; i < screenLength - 1; i++)
        {
            toDisp += "-";
        }
        toDisp += "|";

        System.out.println(toDisp);
    }

    private static void displayGoodbye()
    {
        String toDisp = "";

        toDisp += "|";
        for (int i = 1; i < screenLength - 1; i++)
        {
            toDisp += "-";
        }
        toDisp += "|\n";

        for (int row = 1; row < screenHeight - 1; row++)
        {
            toDisp += "|";
            switch(row)
            {

                case 6:
                    toDisp += centeredString(titleGoodbye[0], screenLength - 2);
                    break;
                case 7:
                    toDisp += centeredString(titleGoodbye[1], screenLength - 2);
                    break;
                case 8:
                    toDisp += centeredString(titleGoodbye[2], screenLength - 2);
                    break;
                case 9:
                    toDisp += centeredString(titleGoodbye[3], screenLength - 2);
                    break;
                case 10:
                    toDisp += centeredString(titleGoodbye[4], screenLength - 2);
                    break;
                case 11:
                    toDisp += centeredString(titleGoodbye[5], screenLength - 2);
                    break;
                case 14:
                    toDisp += centeredString(title3DPromptText[0], screenLength - 2);
                    break;
                case 15:
                    toDisp += centeredString(title3DPromptText[1], screenLength - 2);
                    break;
                case 16:
                    toDisp += centeredString(title3DPromptText[2], screenLength - 2);
                    break;
                case 17:
                    toDisp += centeredString(title3DPromptText[3], screenLength - 2);
                    break;
                case 18:
                    toDisp += centeredString(title3DPromptText[4], screenLength - 2);
                    break;
                case 19:
                    toDisp += centeredString(title3DPromptText[5], screenLength - 2);
                    break;
                default:
                    toDisp += centeredString("", screenLength - 2);
                    break;
            }
            toDisp += "|\n";
        }

        toDisp += "|";
        for (int i = 1; i < screenLength - 1; i++)
        {
            toDisp += "-";
        }
        toDisp += "|";
        System.out.println(toDisp);
    }

    private static void displayDeath(Player player)
    {
        // TODO Insert code for displaying a game-over screen with character overview stats (like number of kills, xp earned, etc.) for the playthrough
        String toDisp = "";

        toDisp += "|";
        for (int i = 1; i < screenLength - 1; i++)
        {
            toDisp += "-";
        }
        toDisp += "|\n";

        for (int row = 1; row < screenHeight - 1; row++)
        {
            toDisp += "|";
            switch(row)
            {

                case 6:
                    toDisp += centeredString(titleDeath[0], screenLength - 2);
                    break;
                case 7:
                    toDisp += centeredString(titleDeath[1], screenLength - 2);
                    break;
                case 8:
                    toDisp += centeredString(titleDeath[2], screenLength - 2);
                    break;
                case 9:
                    toDisp += centeredString(titleDeath[3], screenLength - 2);
                    break;
                case 10:
                    toDisp += centeredString(titleDeath[4], screenLength - 2);
                    break;
                case 11:
                    toDisp += centeredString(titleDeath[5], screenLength - 2);
                    break;
                case 15:
                    toDisp += centeredString("You have failed your sacred duty as Archpriest of Ravenfell.", screenLength - 2);
                    break;
                case 16:
                    toDisp += centeredString("Due to your failure to act, you have left this world to its doom", screenLength - 2);
                    break;
                case 17:
                    toDisp += centeredString("at the mercy of the invading forces of corruption.", screenLength - 2);
                    break;
                case 21:
                    toDisp += centeredString("Character Overview", screenLength - 2);
                    break;
                case 23:
                    toDisp += centeredString(player.getName() + ", Lvl " + player.getLvl() + " " + player.getRace() + " " + player.getRPGClass(), screenLength - 2);
                    break;
                case 24:
                    toDisp += centeredString("XP earned: " + player.getXp(), screenLength - 2);
                    break;
                case 25:
                    toDisp += centeredString("Total kills: " + player.getKills(), screenLength - 2);
                    break;
                case 30:
                    toDisp += centeredString(title3DPromptText[0], screenLength - 2);
                    break;
                case 31:
                    toDisp += centeredString(title3DPromptText[1], screenLength - 2);
                    break;
                case 32:
                    toDisp += centeredString(title3DPromptText[2], screenLength - 2);
                    break;
                case 33:
                    toDisp += centeredString(title3DPromptText[3], screenLength - 2);
                    break;
                case 34:
                    toDisp += centeredString(title3DPromptText[4], screenLength - 2);
                    break;
                case 35:
                    toDisp += centeredString(title3DPromptText[5], screenLength - 2);
                    break;
                default:
                    toDisp += centeredString("", screenLength - 2);
                    break;
            }
            toDisp += "|\n";
        }

        toDisp += "|";
        for (int i = 1; i < screenLength - 1; i++)
        {
            toDisp += "-";
        }
        toDisp += "|";
        System.out.println(toDisp);
    }

    private static void displayLevelUp(Player player)
    {
        String toDisp = "";

        toDisp += "|";
        for (int i = 1; i < screenLength - 1; i++)
        {
            toDisp += "-";
        }
        toDisp += "|\n";

        for (int row = 1; row < screenHeight - 1; row++)
        {
            toDisp += "|";
            switch(row)
            {
                case 10:
                    toDisp += centeredString("*** CHARACTER OVERVIEW ***", screenLength - 2);
                    break;
                case 13:
                    toDisp += centeredString("Biographical Details", screenLength - 2);
                    break;
                case 14:
                    toDisp += centeredString(String.format("Character Name: %s", player.getName()), screenLength - 2);
                    break;
                case 15:
                    toDisp += centeredString(String.format("Race: %s", player.getRace()), screenLength - 2);
                    break;
                case 16:
                    toDisp += centeredString(String.format("Class: %s", player.getRPGClass()), screenLength - 2);
                    break;
                case 18:
                    toDisp += centeredString("Equipment", screenLength - 2);
                    break;
                case 19:
                    toDisp += centeredString(String.format("Weapon: %s", player.getWeapon()), screenLength - 2);
                    break;
                case 20:
                    toDisp += centeredString(String.format("Armor: %s", player.getArmor()), screenLength - 2);
                    break;
                case 22:
                    toDisp += centeredString("Stats", screenLength - 2);
                    break;
                case 23:
                    toDisp += centeredString(String.format("Strength: %d", player.getStr()), screenLength - 2);
                    break;
                case 24:
                    toDisp += centeredString(String.format("Dexterity: %d", player.getDex()), screenLength - 2);
                    break;
                case 25:
                    toDisp += centeredString(String.format("Vitality: %d", player.getVit()), screenLength - 2);
                    break;
                case 26:
                    toDisp += centeredString(String.format("Defense: %d", player.getDef()), screenLength - 2);
                    break;
                case 27:
                    toDisp += centeredString(String.format("Agility: %d", player.getAgi()), screenLength - 2);
                    break;
                case 32:
                    toDisp += centeredString("Choose a stat to levelup.", screenLength - 2);
                    break;
                case 33:
                    toDisp += centeredString("[1] STR  |  [2] DEX  |  [3] VIT", screenLength - 2);
                    break;
                case 34:
                    toDisp += centeredString("[4] DEF  |  [5] AGI", screenLength - 2);
                    break;
                default:
                    toDisp += centeredString("", screenLength - 2);
                    break;
            }
            toDisp += "|\n";
        }

        toDisp += "|";
        for (int i = 1; i < screenLength - 1; i++)
        {
            toDisp += "-";
        }
        toDisp += "|";

        System.out.println(toDisp);
    }

    public static int getScreenLength()
    {
        return screenLength;
    }

    public static int getScreenHeight()
    {
        return screenHeight;
    }

    public static int getMapDispLength()
    {
        return mapDispLength;
    }

    public static int getMapDispHeight() {
        return mapDispHeight;
    }

    public static int getDescDispHeight()
    {
        return descDispHeight;
    }
}
