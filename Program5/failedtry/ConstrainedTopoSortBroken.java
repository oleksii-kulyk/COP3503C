// Oleksii Kulyk
// COP 3503, Fall 2022
// Using Gedit + Linux Bash Shell on Ubuntu 22.04.1 LTS

import java.io.*;
import java.util.*;

class ConstrainedTopoSortBroken
{
    class Node
    {
        Integer ordering_num;
        int node_num;
        int incoming_num;

        public Node(int ordering_num, int node_num, int incoming_num)
        {
            this.ordering_num = ordering_num;
            this.node_num = node_num;
            this.incoming_num = incoming_num;
        }
    }

    ArrayList<LinkedList<Node>> graph;
    ArrayList<Node> incoming;
    int V;

    public ConstrainedTopoSortBroken (String filename) throws IOException
    {
        Scanner in = new Scanner(new File(filename));
        // number of vertices
        V = in.nextInt();
        // LinkedList implementation to store the graph
        graph = new ArrayList<>(V+2);
        // prepopulate incoming array
        incoming = new ArrayList<>(V+2);
        for (int i = 1; i <= V; i++)
            incoming.add(i, new Node(i, i, 0));

        for (int i = 1; i <= V; i++)
        {
            int len = in.nextInt();
            graph.add(i, new LinkedList<>());
            for (int j = 0; j < len; j++)
            {   
                // read in adjecent nodes
                int nextNode_num = in.nextInt();
                graph.get(i).add(new Node(nextNode_num, nextNode_num, 0));
                // increment incoming edges for adjecent nodes
                incoming.get(nextNode_num).incoming_num++;
            }
        }
    }

    Comparator<Node> Constraint = 
		(Node o1, Node o2)->o1.ordering_num.compareTo(o2.ordering_num);

    public boolean hasConstrainedTopoSort(int x, int y)
    {
        // check for graph with cycles
        int cnt = 0;
        
        // set order_num of x to -1
        // and set order_num of y to Integer.MAX_Value
        incoming.get(x).ordering_num = -1;
        incoming.get(y).ordering_num = Integer.MAX_VALUE;
        incoming.sort(Constraint);
        for (int i = 1; i <= V; i++)
        {
            for (Node node : graph.get(i))
            {
                if (node.node_num == x)
                    node.ordering_num = -1;
                if (node.node_num == y)
                    node.ordering_num = Integer.MAX_VALUE;
            }
            graph.get(i).sort(Constraint);
        }
        // now node x is guaranteed to get picked first
        // and y is guaranteed to get picked last

        Queue<Node> q = new ArrayDeque<Node>();

        for (Node cur_node : incoming)
            if (cur_node.incoming_num == 0)
                q.add(cur_node);

        while (!q.isEmpty())
        {
            Node cur_node = q.remove();
            if (cur_node.node_num == y)
                return false;
                
            if (cur_node.node_num == x)
                return true;

            ++cnt;

            for (Node node : graph.get(cur_node.node_num))
                if (--incoming.get(node.node_num).incoming_num == 0)
                    q.add(incoming.get(node.node_num));
        }

        if (cnt != V) return false;
        return false;
    }

    public static double difficultyRating()
    {return 3.0;}

    public static double hoursSpent()
    {return 8.0;}

}