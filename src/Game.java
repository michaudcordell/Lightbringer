import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class Game {
    public static void main(String[] args) throws FileNotFoundException, IOException, InterruptedException
    {
        final int MAIN_MENU = 0;
        final int EXPLORATION = 1;
        final int BATTLE = 2;
        final int ENDING = 3;
        final int QUITTING = 4;
        final int DEATH = 5;
        final int LEVELUP = 6;
        final int HELP = 7;

        Scanner in = new Scanner(System.in);

        String mapFilepath = "maps\\overworld.dat";
        Player player = new Player("", "", "");
        Map map = new Map(mapFilepath);
        boolean isRunning = true;
        int prevState;
        int state = MAIN_MENU;
        EncounterRegion curEncounter = null;
        prevState = state;
        state = DisplayHandler.updateDisplay(state, player, map);
        while(state != QUITTING)
        {
            if (state == BATTLE)
            {
                prevState = state;
                state = BattleManager.fight(player, curEncounter);
                curEncounter = null;
                if (state == DEATH)
                {
                    DisplayHandler.updateDisplay(state, player, map);
                    break;
                }
                else if (state == QUITTING)
                {
                    break;
                }
                else if (state == ENDING)
                {
                    DisplayHandler.updateDisplay(state, player, map);
                    break;
                }
                else if (state == LEVELUP)
                {
                    prevState = state;
                    state = DisplayHandler.updateDisplay(state, player, map);
                }

            }

            prevState = state;
            state = DisplayHandler.updateDisplay(state, player, map);

            String curLine = in.nextLine();
            char key;
            if (curLine.length() != 0) key = curLine.charAt(0);
            else key = ' ';

            if (InputHandler.isQuit(key))
            {
                prevState = state;
                state = QUITTING;
            }
            else if (InputHandler.isDirection(key))
            {
                boolean collided = map.checkCollideAndBounds(player.getX(), player.getY(), key);
                if (!collided)
                {
                    player.updatePos(key);
                    curEncounter = map.getEncRegion(player.getX(), player.getY());
                    if (curEncounter != null)
                    {
                        if ( (!curEncounter.getRepeatable() && !curEncounter.getEncountered() ) || (curEncounter.getRepeatable()))
                        {
                            prevState = state;
                            state = BATTLE;
                        }
                        else
                        {
                            curEncounter = null;
                        }
                    }
                }
            }
            else if (InputHandler.isHelp(key))
            {
                state = HELP;
                prevState = state;
                state = DisplayHandler.updateDisplay(state, player, map);
            }
        }

        DisplayHandler.updateDisplay(QUITTING, player, map);
    }
}
