// Sean Szumlanski
// COP 3503, Fall 2022

// ====================================
// ConstrainedTopoSort: TestCase06.java
// ====================================
// A small test case for ConstrainedTopoSort.


import java.io.*;
import java.util.*;

public class TestCase07
{
	private static void failwhale(String params)
	{
		System.out.println("Test Case #6: hasConstrainedTopoSort(" + params + "): fail whale :(");
		System.exit(0);
	}

	public static void main(String [] args) throws IOException
	{
		ConstrainedTopoSort t = new ConstrainedTopoSort("input_files/g2.txt");

		if (t.hasConstrainedTopoSort(4, 1) != false) failwhale("4, 1");
		if (t.hasConstrainedTopoSort(4, 2) != false) failwhale("4, 2");
		if (t.hasConstrainedTopoSort(4, 3) != false) failwhale("4, 3");

		System.out.println("Test Case #7: PASS (Hooray!)");
	}
}
