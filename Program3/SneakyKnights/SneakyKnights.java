// Oleksii Kulyk, ol576586
// COP 3503, Fall 2022
// Using Gedit + Linux Bash Shell on Ubuntu 22.04.1 LTS

import java.util.LinkedList;
import java.util.ArrayList;

class CoordPair
    {
        int x,y;

        CoordPair (int x, int y)
        {
            this.x = x;
            this.y = y;
        }
    }

public class SneakyKnights
{   
    public static boolean allTheKnightsAreSafe( ArrayList<String> coordinateStrings, int boardSize )
    {
        boardSize = boardSize + 2;
        ArrayList<LinkedList<CoordPair>> xArrofLists = new ArrayList<>(boardSize);
        for (int i = 0; i <= boardSize; i++)
            xArrofLists.add(new LinkedList<>());
        
        CoordPair nKnight;

        for (String coords : coordinateStrings)
        {
            nKnight = normalizeCoords(coords);
            System.out.println(nKnight.x);
            System.out.println(nKnight.y);
            if (xArrofLists.get(nKnight.x).contains(nKnight))
                return false;
            else
                insertKnight(xArrofLists, nKnight);
        }

        return true;
    }

    public static void insertKnight( ArrayList<LinkedList<CoordPair>> xArrofLists, CoordPair nKnight)
    {
        if (nKnight.x > 1)
        {
            xArrofLists.get(nKnight.x - 2).add( new CoordPair(nKnight.x - 2, nKnight.y - 1) );
            xArrofLists.get(nKnight.x - 2).add( new CoordPair(nKnight.x - 2, nKnight.y + 1) );
        }
        if (nKnight.x > 0)
        {
            xArrofLists.get(nKnight.x - 1).add( new CoordPair(nKnight.x - 1, nKnight.y - 2) );
            xArrofLists.get(nKnight.x - 1).add( new CoordPair(nKnight.x - 1, nKnight.y + 2) );
        }
        xArrofLists.get(nKnight.x + 1).add( new CoordPair(nKnight.x + 1, nKnight.y - 2) );
        xArrofLists.get(nKnight.x + 1).add( new CoordPair(nKnight.x + 1, nKnight.y + 2) );
        xArrofLists.get(nKnight.x + 2).add( new CoordPair(nKnight.x + 2, nKnight.y - 1) );
        xArrofLists.get(nKnight.x + 2).add( new CoordPair(nKnight.x + 2, nKnight.y + 1) );
    }

    public static CoordPair normalizeCoords(String coords)
    {
        CoordPair nKnight = new CoordPair(0, 0);
        int pwrx = 1, pwry = 1;
        char[] coordChar = coords.toCharArray();
        for (int i = coordChar.length - 1; i >= 0 ; i--)
        {
            int ch = (int) (coordChar[i] - '`');
            // if a letter, parse column coords
            if (ch > 0)
            {
                nKnight.x += ch * pwrx;
                pwrx *= 26;
            }
            // if a number, parse row coords
            else
            {
                nKnight.y += (coordChar[i] - '0') * pwry;
                pwry *= 10;
            }
        }

        return nKnight;
    }

    public static double difficultyRating()
    {
        return 4.5;
    }

    public static double hoursSpent()
    {
        return 12;
    }

}