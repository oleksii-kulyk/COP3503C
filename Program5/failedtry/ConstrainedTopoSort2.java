// Oleksii Kulyk
// COP 3503, Fall 2022
// Using Gedit + Linux Bash Shell on Ubuntu 22.04.1 LTS

import java.io.*;
import java.util.*;

class ConstrainedTopoSort2
{
    ArrayList<LinkedList<Integer>> graph;
    int[] incoming;
    int V;
    // visited X flag is at idx 0, visited Y is at idx 1
    boolean visitedX;

    public ConstrainedTopoSort2 (String filename) throws IOException
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
        Queue<Integer> q = new ArrayDeque<>();

        for (int i = 1; i <= V; i++)
            if (incoming[i] == 0)
                q.add(i);

        return backTrackTopoSort(q, x, y);
    }

    private boolean backTrackTopoSort(Queue<Integer> q, int x, int y)
    {
        while (!q.isEmpty())
        {
            int curNode = q.remove();
            if (curNode == y)
                return visitedX;

            if (curNode == x)
                visitedX = true;

                Queue<Integer> qu = new ArrayDeque<>();

            for (int node : graph.get(curNode))
                if (--incoming[node] == 0)
                    qu.add(node);
            
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