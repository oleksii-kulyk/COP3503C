// Sean Szumlanski
// COP 3503, Fall 2022

// ====================
// GenericBST: BST.java
// ====================
// Basic binary search tree (BST) implementation that supports insert() and
// delete() operations. This framework is provided for you to modify as part of
// Programming Assignment #2.


import java.io.*;
import java.util.*;

// Comparable restriction is passed down from the super class
class Node<T>
{
  T data;
  Node<T> left, right;

  Node(T data)
  {
    this.data = data;
  }
}

public class GenericBST<UserType extends Comparable<UserType>>
{
  private Node<UserType> root;
  
  // interfaces with outside packages for inserting a Node
  public void insert(UserType data)
  {
    root = insert(root, data);
  }

  // overloaded method that gets called by the `insert` method above
  // takes a link to the Node in the tree and the data to be added to the tree
  private Node<UserType> insert(Node<UserType> root, UserType data)
  {
    // descend into the tree recursively until the null pointer
    if (root == null)
    {
      return new Node<UserType> (data);
    }
    else if (data.compareTo(root.data) < 0)
    {
      root.left = insert(root.left, data);
    }
    else if (data.compareTo(root.data) > 0)
    {
      root.right = insert(root.right, data);
    }

    // if a value is already in the tree, return a pointer to the Node with the value
    return root;
  }

  // interfaces with outside packages for deleting a Node
  public void delete(UserType data)
  {
    root = delete(root, data);
  }

  // overloaded method that gets called by the `delete` method above
  // takes a link the Node in the tree and the data to be deleted from the tree
  private Node<UserType> delete(Node<UserType> root, UserType data)
  {
    // descend into the tree recursively until
    // the null pointer or finds the data to be deleted
    if (root == null)
    {
      return null;
    }
    else if (data.compareTo(root.data) < 0)
    {
      root.left = delete(root.left, data);
    }
    else if (data.compareTo(root.data) > 0)
    {
      root.right = delete(root.right, data);
    }
    else
    {
      // we found the data to be deleted
      // if both children are null, we are at a leaf Node
      if (root.left == null && root.right == null)
      {
        return null;
      }
      // if left child is null, relink the right child to the parent
      else if (root.left == null)
      {
        return root.right;
      }
      // if right child is null, relink the left child to the parent
      else if (root.right == null)
      {
        return root.left;
      }
      // if both children present
      else
      {
        // find the largest value in the left subtree and replace the deleted data with it
        root.data = findMax(root.left);
        // remove that Node with the largest value in the left subtree
        root.left = delete(root.left, root.data);
      }
    }
    // exit one level of recursion
    return root;
  }

  // This method assumes root is non-null, since this is only called by
  // delete() on the left subtree, and only when that subtree is not empty.
  private UserType findMax(Node<UserType> root)
  {
    // the max value in the BST must be on the very right
    while (root.right != null)
    {
      root = root.right;
    }

    return root.data;
  }
  
  // interfaces with outside packages for looking up a value in a BST
  public boolean contains(UserType data)
  {
    return contains(root, data);
  }

  // overloaded method that gets called by the `contains` method above
  private boolean contains(Node<UserType> root, UserType data)
  {
    // recursively searches the tree until hits null of find the value
    if (root == null)
    {
      return false;
    }
    else if (data.compareTo(root.data) < 0)
    {
      return contains(root.left, data);
    }
    else if (data.compareTo(root.data) > 0)
    {
      return contains(root.right, data);
    }
    else
    {
      return true;
    }
  }
  
  // interfaces with outside packages for printing the value of the tree in order
  public void inorder()
  {
    System.out.print("In-order Traversal:");
    inorder(root);
    System.out.println();
  }

  // overloaded method that gets called by the `inorder` method above
  // recurses to the left-most child and prints its value
  // then goes up a level and makes one step to the right child and repeats
  private void inorder(Node root)
  {
    if (root == null)
      return;

    inorder(root.left);
    System.out.print(" " + root.data);
    inorder(root.right);
  }

  // interfaces with outside packages for printing the value of the tree in preorder
  public void preorder()
  {
    System.out.print("Pre-order Traversal:");
    preorder(root);
    System.out.println();
  }

  // overloaded method that gets called by the `preorder` method above
  // prints the current node, then recursively descends to the left child
  // when there is no more left children, recursively makes one step to the right
  private void preorder(Node root)
  {
    if (root == null)
      return;

    System.out.print(" " + root.data);
    preorder(root.left);
    preorder(root.right);
  }

  // interfaces with outside packages for printing the value of the tree in postorder
  public void postorder()
  {
    System.out.print("Post-order Traversal:");
    postorder(root);
    System.out.println();
  }

  // overloaded method that gets called by the `postorder` method above
  // recursively prints the left-most child first, then the right-most child, and then the parent
  private void postorder(Node root)
  {
    if (root == null)
      return;

    postorder(root.left);
    postorder(root.right);
    System.out.print(" " + root.data);
  }
  
  public static double difficultyRating()
  {
    return 2;
  }
  
  public static double hoursSpent()
  {
    return 10;
  }

  public static void main(String [] args)
  {
    GenericBST<Integer> myTree = new GenericBST<>();

    for (int i = 0; i < 5; i++)
    {
      int r = (int)(Math.random() * 150) + 1;
      System.out.println("Inserting " + r + "...");
      myTree.insert(r);
    }

    myTree.inorder();
    myTree.preorder();
    myTree.postorder();
  }
}
