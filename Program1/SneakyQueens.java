// Oleksii Kulyk, ol576586
// COP 3503, Fall 2022
// Using Gedit + Linux Bash Shell on Ubuntu 22.04.1 LTS

import java.util.ArrayList;
import java.lang.Math;

public class SneakyQueens
{
   
  public static boolean allTheQueensAreSafe(ArrayList<String> coordinateStrings, int boardSize)
  {  
    // lines and cols under attack:
    // x     Q     Q
    // y Q
    // 0 1 2 3 4 5 6 7 8 9 ...
    char[][] strai = new char[2][2 * boardSize + 1];

    
    // diags under attack:
    // y - x        Q       Q
    // y + x  Q                   Q
    //   ... -5 -4 -3 -2 -1 0 1 2 3 4
    char[][] diags = new char[2][2 * boardSize + 1];

    for (String coords : coordinateStrings)
    {
      int[] normalcoords = normalizeCoords(coords);

      //System.out.println(coords);
      //System.out.println(normalcoords[0]);
      //System.out.println(normalcoords[1]);
      

      // lines and columns
      // return true if a column is not safe; if the column is safe, mark the column as not safe
      if (strai[0][normalcoords[0]] == 'Q')
        return false;
      else
        strai[0][normalcoords[0]] = 'Q';

      // return true if a row is not safe; if the row is safe, mark the row as not safe
      if (strai[1][normalcoords[1]] == 'Q')
        return false;
      else
        strai[1][normalcoords[1]] = 'Q';


      // diagonals
      // return true if diag y = x + c1 is not safe; if the diag is safe, mark the diag as not safe
      if (diags[0][normalcoords[1] - normalcoords[0] + boardSize] == 'Q')
      {
        return false;
      }
      else
      {
        diags[0][normalcoords[1] - normalcoords[0] + boardSize] = 'Q';
      }
      //System.out.println(diags[0][normalcoords[1] - normalcoords[0] + boardSize]);

      // return true if diag y = - x + c2 is not safe; if the diag is safe, mark the diag as not safe
      if (diags[1][normalcoords[1] + normalcoords[0]] == 'Q')
        return false;
      else
        diags[1][normalcoords[1] + normalcoords[0]] = 'Q';
      //System.out.println(diags[1][normalcoords[1] + normalcoords[0]]);
    }

    return true;
  }
  
  public static int[] normalizeCoords(String xcoord)
  {
    int[] normalx = {0 ,0};
    int pwrx = 0, pwry = 0;
    char[] xcoordChar = xcoord.toCharArray();
    for (int i = xcoordChar.length - 1; i >= 0 ; i--)
    {
      int ch = (int) (xcoordChar[i] - '`');
      // if a letter, parse column coords
      if (ch > 0)
      {
        normalx[0] += ch * Math.pow(26, pwrx);
        pwrx ++;
      }
      // if a number, parse row coords
      else
      {
        normalx[1] += (xcoordChar[i] - '0') * Math.pow(10, pwry);
        pwry ++;
      }
    }

    return normalx;
  }

  public static double difficultyRating()
  {    
    return 3;
  }

  public static double hoursSpent()
  {   
    return 6;
  }
  
}
// TODO Remove: Do not use block-style comments: /* comment */

