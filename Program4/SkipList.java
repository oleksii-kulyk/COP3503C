// Oleksii Kulyk, ol576586
// COP 3503, Fall 2022
// Using Gedit + Linux Bash Shell on Ubuntu 22.04.1 LTS

import java.util.*;
import java.lang.Math;

class Node<T>
{
  T data;
  int height;
  
  // the lowest index represents the bottom most level of nodes
  ArrayList<Node<T>> linkedNodes;
  
  
  Node(int height)
  {
    this.height = height;
    this.linkedNodes = new ArrayList<Node<T>>(2 * height + 5);
    for(int i = 0; i < 2 * height + 5; i++)
      linkedNodes.add(null);
  }
  
  Node(T data, int height)
  {
    this.data = data;
    this.height = height;
    this.linkedNodes = new ArrayList<Node<T>>(2 * height + 5);
    for(int i = 0; i < 2 * height + 5; i++)
      linkedNodes.add(null);
  }
  
  public T value()
  { return this.data; }
  
  public int height()
  { return this.height; }
  
  public Node<T> next(int level)
  {
    if(level < 0 || level > this.height - 1)
      return null;
    
    return this.linkedNodes.get(level);
  }

  public void setNext(int level, Node<T> node)
  { 
    if(this.linkedNodes.get(level) == null)
      this.linkedNodes.add(level, node);
    else
      this.linkedNodes.set(level, node);
  }
}


public class SkipList<UserType extends Comparable<UserType>> 
{
  private Node<UserType> HEAD;
  private int numNodes;
  
  private Node<UserType> CURNODE;
  private int CURLEV;
  
  private ArrayList<Node<UserType>> PREVNODES;

  SkipList()
  { 
    HEAD = new Node<UserType>(1);
    PREVNODES = new ArrayList<Node<UserType>>(7);
    for(int i = 0; i < 7; i++)
      PREVNODES.add(null);
  }

  SkipList(int height)
  {
    if(height < 1)
    {
      HEAD = new Node<UserType>(1);
      PREVNODES = new ArrayList<Node<UserType>>(7);
      for(int i = 0; i < 7; i++)
      PREVNODES.add(null);
    }
    else
    {
      HEAD = new Node<UserType>(height);
      PREVNODES = new ArrayList<Node<UserType>>(2 * height + 5);
      for(int i = 0; i < 2 * height + 5; i++)
      PREVNODES.add(null);
    }
  }

  public int size()
  { return numNodes; }
  
  // the HEAD height is used as the overall height variable for the whole SkipList
  public int height()
  { return HEAD.height(); }
  
  public Node<UserType> head()
  { return HEAD; }
  
  public void insert(UserType data)
  {
    // search for appropriate spot to insert
    search(data);
    // generate hight for the new node, grow SkipList if needed
    int newNodeHeight = generateHeight();
    if(newNodeHeight > height())
      growSkipList();
    // insert new node while linking to the elements behind
    Node<UserType> newNode = new Node<UserType>(data, newNodeHeight);
    for(int i = 0; i < newNodeHeight; i++)
    {
      newNode.setNext(i, PREVNODES.get(i).next(i));
      PREVNODES.get(i).setNext(i, newNode);
    }
    // grow SkipList of the size to height requirement is still not satisfied
    numNodes++;
    if( (int) Math.ceil( Math.log(size()) / Math.log(2) ) > height())
      growSkipList();
  }
  
  //differs from above method only in that is doesn't generate the height
  public void insert(UserType data, int height)
  {
    search(data);
    if(height > height())
      growSkipList();
    Node<UserType> newNode = new Node<UserType>(data, height);
    for(int i = 0; i < height; i++)
    {
      newNode.setNext(i, PREVNODES.get(i).next(i));
      PREVNODES.get(i).setNext(i, newNode);
    }
    numNodes++;
    if( (int) Math.ceil( Math.log(size()) / Math.log(2) ) > height())
      growSkipList();
  }
    
  public void delete(UserType data)
  { 
    //get the node to delete
    Node<UserType> delNode = get(data);
    if(delNode == null)
      return;
    // if the node exists, simply link nodes below to the nodes in front
    for(int i = 0; i < delNode.height; i++)
      PREVNODES.get(i).setNext(i, delNode.next(i));
    // decrease overall size and trim if needed
    numNodes--;
    trimSkipList();
    if(numNodes == 1)
      HEAD.height = 1;
    if( (int) Math.ceil( Math.log(size()) / Math.log(2) ) < height())
      HEAD.height = (int) Math.ceil( Math.log(size()) / Math.log(2));
  }
  
  public boolean contains(UserType data)
  { 
    // if get is able to find the node, then it is contained
    Node<UserType> LookUpNode = get(data);
    if( LookUpNode == null )
      return false;
    return data.compareTo( LookUpNode.data ) == 0;
  }

  public Node<UserType> get(UserType data)
  {
    // runs `search` func that looks for the node before the needed node
    search(data);
    if( CURNODE.next(CURLEV) == null )
      return null;

    if( data.compareTo( CURNODE.next(CURLEV).data ) == 0 )
      return CURNODE.next(CURLEV);
    else
      return null;
  }
  
  // finds appropriate values for CURNODE, CURLEV, PREVNODES so that they point to the correct spot for insertion or the get() method
  // main function that traverses the SkipList decending through the levels when needed
  private void search(UserType data)
  {
    // start at HEAD and at the highest level
    CURLEV = height() - 1;
    CURNODE = HEAD;
    // start a perpetual loop, we will break out of it as soon as we find the needed node
    while(true)
    {
      // check if we have decended to the bottom level yet
      if(CURLEV == 0)
      {
        // if yes, check if there are nodes in front and if the values are greater to the value we are inserting
        if( (CURNODE.next(CURLEV) == null) || (data.compareTo( CURNODE.next(CURLEV).data ) <= 0) )
        {
          // use this mess to grom ArrayList only when needed
          // update links to the nodes behind our current position
          if (PREVNODES.get(CURLEV) == null)
            PREVNODES.add(CURLEV, CURNODE);
          else
            PREVNODES.set(CURLEV, CURNODE);
          return;
        }

        // if data is larger then we node in front, skip to the next node
        if( data.compareTo( CURNODE.next(CURLEV).data ) > 0 )
        {
          CURNODE = CURNODE.next(CURLEV);
          continue;
        }
      }
      else
      {
        // we are not at the bottom level yet
        if( (CURNODE.next(CURLEV) == null)  || (data.compareTo( CURNODE.next(CURLEV).data ) <= 0) )
        {
          // use this mess to grom ArrayList only when needed
          // update links to the nodes behind our current position
          if (PREVNODES.get(CURLEV) == null)
            PREVNODES.add(CURLEV, CURNODE);
          else
            PREVNODES.set(CURLEV, CURNODE);

          // decend one level since the next node is either null or larger then the node we are inserting
          CURLEV--;
          // go back to the top of the loop to see if we are at the bottom level yet
          continue;
        }

        // we are not the the bottow level yet and the node in front is small, so skip that node
        if( data.compareTo( CURNODE.next(CURLEV).data ) > 0 )
        {
          CURNODE = CURNODE.next(CURLEV);
          // repeat the process
          continue;
        }
      }
    }
  }

  private int generateHeight()
  {
    int newHeight = 1;
    int maxHeight = Math.max((int) Math.ceil( Math.log(size()) / Math.log(2) ), height());
    while(true)
    {
      // keep flipping the coin until successful
      if( newHeight < maxHeight && Math.random() < 0.5 )
        newHeight++;
      else
        break;
    }
    return newHeight;
  }

  private void growSkipList()
  {
    CURLEV = height() - 1;
    CURNODE = HEAD;
    // increase HEAD height
    HEAD.height++;
    Node<UserType> PREVNODE = CURNODE;
    // until we haven't reached the end at this level
    while(CURNODE.next(CURLEV) != null)
    {
      // keep moving through the nodes and grow them at 50% chance
      CURNODE = CURNODE.next(CURLEV);
      if(Math.random() < 0.5)
      {
        CURNODE.height++;
        // link the previous grown top-most level for the node to current node at the top-most level
        PREVNODE.setNext(PREVNODE.height - 1, CURNODE);
        PREVNODE = CURNODE;
      }
    }
  }

  private void trimSkipList()
  {
    // if the length factor is above the height, we don't need to trim the SkipList
    if( (int) Math.ceil( Math.log(size()) / Math.log(2) ) >= height())
      return;
    else
    {
      // start at the height below the 
      CURLEV = height() - 1;
      CURNODE = HEAD;
      // reduce the height from the HEAD
      HEAD.height--;
      // until there are nodes at this high level
      while(CURNODE.next(CURLEV) != null)
      {
        // move through the nodes and decrease their height
        CURNODE = CURNODE.next(CURLEV);
        CURNODE.height--;
      }
    }
  }
  
  public static double difficultyRating() { return 5; }
  public static double hoursSpent() { return 12; }
}
