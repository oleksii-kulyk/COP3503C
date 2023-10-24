// Oleksii Kulyk
// COP 3503, Fall 2022
// Using Gedit + Linux Bash Shell on Ubuntu 22.04.1 LTS

import java.io.*;
import java.util.*;

class ConstrainedTopoSort3
{
    ArrayList<LinkedList<Integer>> graph;
    int[] incoming;
    int[] incomingcln;
    int V;
    // visited X flag is at idx 0, visited Y is at idx 1
    boolean visitedX;

    public ConstrainedTopoSort3 (String filename) throws IOException
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

        return backTrackTopoSort(q, x, y);
    }

    private boolean backTrackTopoSort(Deque<Integer> q, int x, int y)
    {
        while (!q.isEmpty())
        {
            int curNode = q.remove();
            if (curNode == y)
                return visitedX;

            if (curNode == x)
                visitedX = true;

                Deque<Integer> qu = new ArrayDeque<>();

            for (int node : graph.get(curNode))
                if (--incomingcln[node] == 0)
                    qu.add(node);

            if (qu.contains(x))
            {
                qu.remove(x);
                qu.addFirst(x);
            }    
            if (qu.contains(y))
            {
                qu.remove(y);
                qu.addLast(y);
            }
            
            if (backTrackTopoSort(qu, x, y))
                return true;
        }
        return false;
    }

    public static double difficultyRating()
    {return 3.0;}

    public static double hoursSpent()
    {return 8.0;}

}