// Oleksii Kulyk
// COP 3503, Fall 2022
// Using Gedit + Linux Bash Shell on Ubuntu 22.04.1 LTS

import java.util.*;

class RunLikeHell
{

  // we run from 0eth block to last, keeping track of the sum
  // recursion accounts for all possible selections of blocks
  public static int maxGainrecurse(int [] blocks)
  {
    
    // can either start at 0eth or 1st block
    // pick whichever choice leads to greater sum
    return Math.max( maxGainrecurse(blocks, 0, 0),
                     maxGainrecurse(blocks, 0, 1) );
  }
  
  private static int maxGainrecurse(int [] blocks, int Sum, int index)
  {
    // if we ran out of blocks, return the sum and KEEP RUNNING FOR LIFE!!!
    if (index >= blocks.length)
      return Sum;
    
    // at this branch of recursion, add the currenty hit block
    Sum = Sum + blocks[index];
    
    // choose which block to hit next and recurse
    return Math.max( maxGainrecurse(blocks, Sum, index + 2),
                     maxGainrecurse(blocks, Sum, index + 3) );
  }
  
  // the last block out guy can hit it either n-1 or n-2 where n is the number of blocks
  // (start with 0eth block)
  public static int maxGainO_n(int [] blocks)
  {
    // principle of induction can guarantee that this algorithm always picks the max sum
    // when at ith iteration of the for loop
    int [] Sum = new int[blocks.length];
    
    // start with the base of induction
    Sum[0] = blocks[0];
    Sum[1] = blocks[1];
    Sum[2] = blocks[2] + Sum[0];
    
    // pick the max for Sum(4), then Sum(5), so on
    for(int i = 3; i < blocks.length; i++)
      // if we are at the ith block, we could arrive to it from i-2nd or i-3rd block
      Sum[i] = blocks[i] + Math.max( Sum[i-2], Sum[i-3] );
    
    // the max sum is then in the last block or one before it
    return Math.max( Sum[blocks.length - 1], Sum[blocks.length - 2] );
  }
  
  
  // noticed that we only we the last three spaces of the Sum array
  // mirror the functionality of maxGainO_n
  // but use only three spaces of the Sum array by overwriting previous ones
  public static int maxGain(int [] blocks)
  {
    int Sum[] = new int [3];
    Sum[0] = blocks[0];
    Sum[1] = blocks[1];
    Sum[2] = blocks[2] + Sum[0];
    
    for(int i = 3; i < blocks.length; i++)
      Sum[ i%3 ] = blocks[i] + Math.max( Sum[ (i-2)%3 ], Sum[ (i-3)%3 ] );
      
    return Math.max( Math.max( Sum[0], Sum[1]) , Sum[2] );
  }
  
  public static double difficultyRating() { return 2.0;}
  public static double hoursSpent() { return 6;}
}
