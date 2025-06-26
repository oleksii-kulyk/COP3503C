// '#' - wall (not walkable)
// '@' - person
// 'e' - exit
// ' ' - space (walkable)

import java.io.*;
import java.util.*;

public class Pathogen
{
	// Used to toggle "animated" output on and off (for debugging purposes).
	private static boolean animationEnabled = false;

	// "Animation" frame rate (frames per second).
	private static double frameRate = 4.0;
	
	public static void enableAnimation() { Pathogen.animationEnabled = true; }
	public static void disableAnimation() { Pathogen.animationEnabled = false; }
	public static void setFrameRate(double fps) { Pathogen.frameRate = fps; }

	// Maze constants.
	private static final char WALL       = '#';
	private static final char CORONA     = '*';  // CORONA!!!
	private static final char PERSON     = '@';
	private static final char EXIT       = 'e';
	private static final char BREADCRUMB = '.';  // visited
	private static final char SPACE      = ' ';  // unvisited

	// Takes a 2D char maze and returns true if it can find a path from the
	// starting position to the exit. Assumes the maze is well-formed according
	// to the restrictions above.

	private static HashSet<String> paths = new HashSet<>();

	public static HashSet<String> findPaths(char [][] maze)
	{
		int height = maze.length;
		int width = maze[0].length;

		// The visited array keeps track of visited positions. It also keeps
		// track of the exit, since the exit will be overwritten when the '@'
		// symbol covers it up in the maze[][] variable. Each cell contains one
		// of three values:
		//
		//   '.' -- visited
		//   ' ' -- unvisited
		//   'e' -- exit
		char [][] visited = new char[height][width];
		for (int i = 0; i < height; i++)
			Arrays.fill(visited[i], SPACE);

		// Find starting position (location of the '@' character).
		int startRow = -1;
		int startCol = -1;

		for (int i = 0; i < height; i++)
		{
			for (int j = 0; j < width; j++)
			{
				if (maze[i][j] == PERSON)
				{
					startRow = i;
					startCol = j;
				}
			}
		}

		// To store the string about to be built
		StringBuilder trekPath = new StringBuilder();

		findPaths(maze, visited, trekPath, startRow, startCol, height, width);

		return paths;
	}

	private static void findPaths(char [][] maze, char [][] visited, StringBuilder trekPath,
	                                 int currentRow, int currentCol,
	                                 int height, int width)
	{
		// This conditional block prints the maze when a new move is made.
		if (Pathogen.animationEnabled)
		{
			printAndWait(maze, height, width, "Searching...", Pathogen.frameRate);
		}

		if (visited[currentRow][currentCol] == 'e')
		{
			if (Pathogen.animationEnabled)
			{
				char [] widgets = {'|', '/', '-', '\\', '|', '/', '-', '\\',
				                   '|', '/', '-', '\\', '|', '/', '-', '\\', '|'};

				for (int i = 0; i < widgets.length; i++)
				{
					maze[currentRow][currentCol] = widgets[i];
					printAndWait(maze, height, width, "Hooray!", 12.0);
				}

				maze[currentRow][currentCol] = PERSON;
				printAndWait(maze, height, width, "Hooray!", Pathogen.frameRate);
			}

			// add the current path to out HashSet while trimming the trailing whitespace
			paths.add(trekPath.toString().trim());
			return;
		}

		// Moves: left, right, up, down
		int [][] moves = new int[][] {{0, -1}, {0, 1}, {-1, 0}, {1, 0}};
		char [] directions = new char[] {'l', 'r', 'u', 'd'};

		for (int i = 0; i < moves.length; i++)
		{
			// find index for the next move
			int newRow = currentRow + moves[i][0];
			int newCol = currentCol + moves[i][1];
			
			// Check move is in bounds, not a wall, and not marked as visited.
			if (!isLegalMove(maze, visited, newRow, newCol, height, width))
				continue;
						
			// Change state. Before moving the person forward in the maze, we
			// need to check whether we're overwriting the exit. If so, save the
			// exit in the visited[][] array so we can actually detect that
			// we've gotten there.

			if (maze[newRow][newCol] == EXIT)
				visited[newRow][newCol] = EXIT;

			maze[currentRow][currentCol] = BREADCRUMB;
			visited[currentRow][currentCol] = BREADCRUMB;
			maze[newRow][newCol] = PERSON;

			// add a next step to the path
			trekPath.append(directions[i]);
			trekPath.append(' ');

			// Perform recursive descent.
			findPaths(maze, visited, trekPath, newRow, newCol, height, width);

			// Since the exit gets overwritten, we need to put it back for other paths to find
			if (visited[newRow][newCol] == EXIT)
				maze[newRow][newCol] = EXIT;
			else
				maze[newRow][newCol] = SPACE;

			// Also remove the BREADCRUMB since we won't step in the same direction
			// and we need to explore other paths
			visited[newRow][newCol] = SPACE;

			maze[currentRow][currentCol] = PERSON;

			// remove the trailing space and the direction
			trekPath.deleteCharAt(trekPath.length() - 1);
			trekPath.deleteCharAt(trekPath.length() - 1);

			// This conditional block prints the maze when a move gets undone
			// (which is effectively another kind of move).
			if (Pathogen.animationEnabled)
			{
				printAndWait(maze, height, width, "Backtracking...", frameRate);
			}
		}

		return;
	}

	// Returns true if moving to row and col is legal (i.e., we have not visited
	// that position before, and it's not a wall).
	private static boolean isLegalMove(char [][] maze, char [][] visited,
	                                   int row, int col, int height, int width)
	{
		if (row < 0 || row > height - 1 || col < 0 || col > width - 1)
			return false;

		if (maze[row][col] == WALL || visited[row][col] == BREADCRUMB || maze[row][col] == CORONA)
			return false;

		return true;
	}

	// This effectively pauses the program for waitTimeInSeconds seconds.
	private static void wait(double waitTimeInSeconds)
	{
		long startTime = System.nanoTime();
		long endTime = startTime + (long)(waitTimeInSeconds * 1e9);

		while (System.nanoTime() < endTime)
			;
	}

	// Prints maze and waits. frameRate is given in frames per second.
	private static void printAndWait(char [][] maze, int height, int width,
	                                 String header, double frameRate)
	{
		if (header != null && !header.equals(""))
			System.out.println(header);

		if (height < 1 || width < 1)
			return;

		for (int i = 0; i < height; i++)
		{
			for (int j = 0; j < width; j++)
			{
				System.out.print(maze[i][j]);
			}

			System.out.println();
		}

		System.out.println();
		wait(1.0 / frameRate);
	}

	// Read maze from file. This function dangerously assumes the input file
	// exists and is well formatted according to the specification above.
	private static char [][] readMaze(String filename) throws IOException
	{
		Scanner in = new Scanner(new File(filename));

		int height = in.nextInt();
		int width = in.nextInt();

		char [][] maze = new char[height][];

		// After reading the integers, there's still a new line character we
		// need to do away with before we can continue.

		in.nextLine();

		for (int i = 0; i < height; i++)
		{
			// Explode out each line from the input file into a char array.
			maze[i] = in.nextLine().toCharArray();
		}

		return maze;
	}

	public static double difficultyRating() { return 2.0;}

	public static double hoursSpent() { return 4.0; }

	public static void main(String [] args) throws IOException
	{
		// Load maze and turn on "animation."
		char [][] maze = readMaze("maze.txt");
		Pathogen.enableAnimation();

		// Go!!
		if (!Pathogen.findPaths(maze).isEmpty())
			System.out.println("Found path to exit!");
		else
			System.out.println("There doesn't appear to be a path to the exit.");
	}
}
