import java.util.HashMap;
import java.util.Scanner;
import java.io.*;
import java.util.ArrayList;

public class Map {

    private String mapName;


    private String collisionList = "|-~_/\\?!<>()[]@$";

    private ArrayList<String> visualMap;

    // For each character of the visualMap, the collisionMap will store true for collision, false for no collision.
    private boolean[][] collisionMap;

    private ArrayList<EncounterRegion> encMap;

    private ArrayList<DescRegion> descMap;

    private ArrayList<DescRegion> locMap;

    public boolean checkCollideAndBounds(int x_i, int y_i, char direction)
    {
        int x_f = x_i, y_f = y_i;
        if (direction == 'w') y_f = y_i - 1;
        else if (direction == 's') y_f = y_i + 1;
        else if (direction == 'a') x_f = x_i - 1;
        else if (direction == 'd') x_f = x_i + 1;
        boolean outOfBounds = (x_f < 0) || (y_f < 0) || (x_f >= collisionMap[y_i].length) || (y_f >= collisionMap.length);
        boolean willCollide = collisionMap[y_f][x_f];
        return outOfBounds || willCollide;
    }

    public String getDescText(int x, int y)
    {
        for (int i = 0; i < descMap.size(); i++)
        {
            if (descMap.get(i).checkBounded(x, y)) return descMap.get(i).getDescText();
        }
        return "";
    }

    public EncounterRegion getEncRegion(int x, int y)
    {
        for (int i = 0; i < encMap.size(); i++)
        {
            if (encMap.get(i).checkBounded(x, y) && Math.random() < encMap.get(i).getEncProb()) return encMap.get(i);
        }
        return null;
    }

    public String getLocText(int x, int y)
    {
        for (int i = 0; i < locMap.size(); i++)
        {
            if (locMap.get(i).checkBounded(x, y)) return locMap.get(i).getDescText();
        }
        return "";
    }

    public int getHeight()
    {
        return this.visualMap.size();
    }

    public int getLength()
    {
        return this.visualMap.get(0).length();
    }

    public char getCharAtPos(int x, int y)
    {
        return this.visualMap.get(y).charAt(x);
    }

    public Map(String filepath) throws FileNotFoundException
    {
        File mapFile = new File(filepath);
        Scanner in = new Scanner(mapFile);
        boolean addToMapName = false;
        boolean addToVisMap = false;
        boolean addToEncMap = false;
        boolean addToDescMap = false;
        boolean addToLocMap = false;

        visualMap = new ArrayList<String>();
        encMap = new ArrayList<EncounterRegion>();
        descMap = new ArrayList<DescRegion>();
        locMap = new ArrayList<DescRegion>();

        while(in.hasNextLine())
        {
            // TODO Change all .equals() to .contains
            String curLine = in.nextLine();
            if (curLine.equals("<Map Name>")) addToMapName = true;
            else if (curLine.equals("</Map Name>")) addToMapName = false;
            else if (curLine.equals("<Visual Map>")) addToVisMap = true;
            else if (curLine.equals("</Visual Map>"))
            {
                addToVisMap = false;
                // Process Visual Map.
                // Find the length of the longest string.
                int longest = visualMap.get(0).length();
                for (int i = 1; i < visualMap.size(); i++)
                {
                    if (longest < visualMap.get(i).length()) longest = visualMap.get(i).length();
                }
                // Add spaces to each string until all strings are of uniform length.
                for (int i = 0; i < visualMap.size(); i++)
                {
                    int curLength = visualMap.get(i).length();
                    int spacesToAdd = longest - curLength;
                    for (int space = 0; space < spacesToAdd; space++)
                    {
                        visualMap.set(i, visualMap.get(i) + " ");
                    }
                }
            }
            else if (curLine.equals("<Encounter Map>")) addToEncMap = true;
            else if (curLine.equals("</Encounter Map>")) addToEncMap = false;
            else if (curLine.equals("<Location Map>")) addToLocMap = true;
            else if (curLine.equals("</Location Map>")) addToLocMap = false;
            else if (curLine.equals("<Desc Map>")) addToDescMap = true;
            else if (curLine.equals("</Desc Map>")) addToDescMap = false;
            else
            {
                if (addToMapName) mapName = curLine;
                else if (addToVisMap) visualMap.add(curLine);
                else if (addToEncMap)
                {
                    String[] params = curLine.split(",");
                    int x = Integer.parseInt(params[0]);
                    int y = Integer.parseInt(params[1]);
                    int radius = Integer.parseInt(params[2]);
                    int enemyID = Integer.parseInt(params[3]);
                    double probEnc = Double.parseDouble(params[4]);
                    boolean repeated = Boolean.parseBoolean(params[5]);
                    encMap.add(new EncounterRegion(x, y, radius, enemyID, probEnc, repeated));
                }
                else if (addToDescMap)
                {
                    String[] params = curLine.split(",", 4);
                    int x = Integer.parseInt(params[0]);
                    int y = Integer.parseInt(params[1]);
                    int radius = Integer.parseInt(params[2]);
                    String descText = params[3];
                    descMap.add(new DescRegion(x, y, radius, descText));
                }
                else if (addToLocMap)
                {
                    String[] params = curLine.split(",", 4);
                    int x = Integer.parseInt(params[0]);
                    int y = Integer.parseInt(params[1]);
                    int radius = Integer.parseInt(params[2]);
                    String locText = params[3];
                    locMap.add(new DescRegion(x, y, radius, locText));
                }
            }
        }
        in.close();
        // Create the collision map.
        collisionMap = new boolean[visualMap.size()][visualMap.get(0).length()];
        // Iterating through each row, then each column, set element to true if corresponding character in visualMap is in the collision list.
        for (int i = 0; i < visualMap.size(); i++)
        {
            for (int j = 0; j < visualMap.get(i).length(); j++)
            {
                if (collisionList.contains("" + visualMap.get(i).charAt(j))) collisionMap[i][j] = true;
                else collisionMap[i][j] = false;
            }
        }
    }
}
