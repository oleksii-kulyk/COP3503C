// Oleksii Kulyk
// COP 3503, Fall 2022
// Using Gedit + Linux Bash Shell on Ubuntu 22.04.1 LTS

import java.io.*;
import java.util.*;

class ConstrainedTopoSort
{
    ArrayList<LinkedList<Integer>> graph;
    int[] incoming;
    int[] incomingcln;
    int V;
    int cnt;
    // visited X flag is at idx 0, visited Y is at idx 1
    boolean visitedX;

    public ConstrainedTopoSort (String filename) throws IOException
    {
        Scanner in = new Scanner(new File(filename));
        // number of vertices
        V = in.nextInt();
        // LinkedList implementation to store the graph
        graph = new ArrayList<>(V + 2);
        graph.add(null);
        incoming = new int[V + 2];
        for (int i = 1; i <= V; i++)
        {
            int len = in.nextInt();
            graph.add(i, new LinkedList<>());
            for (int j = 0; j < len; j++)
            {   
                // read in adjecent nodes
                int nodeNum = in.nextInt();
                graph.get(i).add(nodeNum);
                // increment incoming edges for adjecent nodes
                incoming[nodeNum]++;
            }
        }
    }

    public boolean hasConstrainedTopoSort(int x, int y)
    {
        Deque<Integer> q = new ArrayDeque<>();
        incomingcln = incoming.clone();
        visitedX = false;
        
        for (int i = 1; i <= V; i++)
            if (incomingcln[i] == 0)
                q.add(i);
                
        if (q.contains(x))
        {
            q.remove(x);
            q.addFirst(x);
        }    
        if (q.contains(y))
        {
            q.remove(y);
            q.addLast(y);
        }

        while (!q.isEmpty())
        {
            int curNode = q.remove();
            ++cnt;
            
            if (curNode == y && visitedX == true)
                return true;

            if (curNode == x)
                visitedX = true;

            for (int node : graph.get(curNode))
                if (--incomingcln[node] == 0)
                    q.add(node);
            
            if (q.contains(x))
            {
                q.remove(x);
                q.addFirst(x);
            }    
            if (q.contains(y))
            {
                q.remove(y);
                q.addLast(y);
            }   
        }
        if (cnt != V) return false;
        return false;
    }

    public static double difficultyRating()
    {return 3.0;}

    public static double hoursSpent()
    {return 12.0;}

}