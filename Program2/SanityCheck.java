// Sean Szumlanski
// COP 3503, Fall 2022

// ============================
// GenericBST: SanityCheck.java
// ============================
//
// When you submit your GenericBST.java source file, you want to be sure that
// it's NOT generating Xlint:unchecked warnings. In order to do that, you need
// to be sure that your compiler is capable of generating such warnings in the
// first place. (Annoyingly, not all compilers/IDEs generate those warnings by
// default.)
//
// This source file has some problems with unchecked or unsafe operations. If
// you're able to compile it without any warnings, then your compiler is failing
// to generate Xlint:unchecked warnings. Yikes! In that case, you need to be
// careful to test in an environment that DOES generate Xlint warnings, such as
// Eustis.
//
// Again: This file should generate Xlint warnings, and can be used to check
// whether your system does generate such warnings. However, the GenericBST.java
// source file you submit should NOT generate any warnings at all.


import java.util.*;

public class SanityCheck<QQ> {
	LinkedList L;

	SanityCheck() {
		L = new LinkedList();
		L.add(null);
	}
}
