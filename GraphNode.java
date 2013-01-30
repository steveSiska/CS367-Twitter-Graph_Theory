///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Main Class File:  GraphAnalyser.java
// File:             GraphNode.java
// Semester:         Spring 2012
//
// Author:           Steve Siska ssiska@wisc.edu
// CS Login:         siska
// Lecturer's Name:  Hasti
// Lab Section:      NA
//
//                   PAIR PROGRAMMERS COMPLETE THIS SECTION
// Pair Partner:     (name of your pair programming partner)
// CS Login:         (partner's login name)
// Lecturer's Name:  (name of your partner's lecturer)
// Lab Section:      (your partner's lab section number)
//
//                   STUDENTS WHO GET HELP FROM ANYONE OTHER THAN THEIR PARTNER
// Credits:          (list anyone who helped you write your program)
//////////////////////////// 80 columns wide //////////////////////////////////
/**
  *The GraphNode class represents a node of a directed graph with a String as
  *it's data. The GraphNode also keeps track of it's successors in a TreeSet, as
  *well as fields for whether the node has been visited in a traversal, and if
  *a node is still on the call stack during a method that uses recursive calls
  *on the nodes.  This class implements the Comparable interface, and thus has
  *a compareTo method (to comapre GraphNodes)
  *
  * <p>Bugs: None known
  *
  * @author Steve Siska
  */
import java.util.*;

public class GraphNode implements Comparable<GraphNode> {
	
	private String data;
	private TreeSet<GraphNode> successors;
	private final int UNVISITED = 0;
	//the mark for recurisive method calls
	private int mark;
	//field for whether or not a node has been marked visited
	private boolean visited;
	/**
	  * Constructs a new GraphNode with the name of a user, and sets it's fields
	  * to the default new values (UNVISITED, and visisted = false
	  *
	  * @param name The name of the user this GraphNode represents
	  * 
	  */
	public GraphNode(String name){
		data = name;
		successors = new TreeSet<GraphNode>();
		mark = UNVISITED;
		visited = false;
	}
	/**
	  * Returns the name associated with this GraphNode
	  *
	  * @return the name of the GraphNode
	  */
	public String getName(){
		return data;
	}
	/**
	  * Sets (changes) the name associated with this GraphNode
	  *
	  * @param newName the new name for this GraphNode
	  */
	public void setName(String newName){
		data = newName;
	}
	/**
	  * Returns a list of Strings that are immediate successors of this
	  * GraphNode
	  *
	  * @return the list of successors to this GraphNode (in String form)
	  */
	public List<String> getEdges(){
		List<String> edges = new ArrayList<String>();
		for(GraphNode g : successors){
			edges.add(g.getName());
		}
		Collections.sort(edges);
		return edges;
	}
	/**
	  * Returns a list of GraphNodes that are immediate successors of this
	  * GraphNode
	  *
	  * @return the list of successors to this GraphNode (in GraphNode form)
	  */
	public List<GraphNode> getNodes(){
		List<GraphNode> edges = new ArrayList<GraphNode>();
		for(GraphNode g : successors){
			edges.add(g);
		}
		Collections.sort(edges);
		return edges;
	}
	/**
	  * Creates a new edge from this GraphNode to another
	  *
	  * @param next the GraphNode to connect this GraphNode to via an edge
	  */
	public void setEdge(GraphNode next){
		successors.add(next);
	}
	/**
	  * Returns this GraphNode's mark:
	  * 	0 if UNVISITED
	  * 	1 if TARGET
	  *
	  * @return mark the mark value of this GraphNode
	  */
	public int getMark(){
		return mark;
	}
	/**
	  * Sets this GraphNode's mark based on the values above
	  *
	  * @param mark the new mark for this GraphNode
	  */
	public void setMark(int mark){
		if(mark != 0 && mark != 1){
			throw new IllegalArgumentException();
		}
		this.mark = mark;
	}
	/**
	  * Returns this GraphNode's visit field (boolean)
	  *
	  * @return visited whether or not this GraphNode has been visited in a 
	  * traversal or search
	  */
	public boolean getVisited(){
		return visited;
	}
	/**
	  * Sets (changes) this GraphNode's visited field
	  *
	  * @param v the new visited state for this GraphNode
	  */
	public void setVisited(boolean v){
		visited = v;
	}
	/**
	  * Because this class implements the Comparable interface (to compare
	  * GraphNodes, we must implement compareTo, which compares one GraphNode
	  * to another using their String data values
	  * 
	  * @param other the other GraphNode that we are comparing to
	  * @return whether or not this GraphNode comes before, after, or is equal
	  * to other GraphNode
	  */
	public int compareTo(GraphNode other) {
		String word = other.getName();
		return data.compareTo(word);
	}
	
}
