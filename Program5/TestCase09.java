// Sean Szumlanski
// COP 3503, Fall 2022

// ====================================
// ConstrainedTopoSort: TestCase06.java
// ====================================
// A small test case for ConstrainedTopoSort.


import java.io.*;
import java.util.*;

public class TestCase09
{
	private static void failwhale(String params)
	{
		System.out.println("Test Case #9: hasConstrainedTopoSort(" + params + "): fail whale :(");
		System.exit(0);
	}

	public static void main(String [] args) throws IOException
	{
		ConstrainedTopoSort t = new ConstrainedTopoSort("input_files/g4.txt");

		if (t.hasConstrainedTopoSort(1, 2) != false) failwhale("1, 2");
		if (t.hasConstrainedTopoSort(1, 3) != false) failwhale("1, 3");
		if (t.hasConstrainedTopoSort(1, 5) != false) failwhale("1, 5");
		if (t.hasConstrainedTopoSort(4, 5) != false) failwhale("4, 5");

		System.out.println("Test Case #9: PASS (Hooray!)");
	}
}
