///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Main Class File:  GraphAnalyser.java
// File:             BasicGraph.java
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
 *The BasicGraph class represents a directed graph with string labels for nodes. 
 *Node labels are assumed to be unique and it is assumed that nodes do not have
 *edges to themselves (i.e., no self edges).
 *
 * <p>Bugs: None known
 *
 * @author Steve Siska
 */
import java.util.*;

public class BasicGraph {

	private int numNodes; //number of nodes in the graph
	private int numLinks; //number of Edges in the graph
	private TreeSet<GraphNode> nodes; //the TreeSet of nodes (ordered)
	/**
	 * Constructs a new empty graph.
	 */
	public BasicGraph(){
		nodes = new TreeSet<GraphNode>();
		numNodes = 0;
	}
	/**
	 *Add to the graph the specified directed edge from the node with the label 
	 *sourceLabel to the node with the label targetLabel. If the edge is 
	 *already in the graph, the graph is unchanged and false is returned. 
	 *Otherwise (i.e., if edge is not already in the graph), true is returned.
	 * @param: sourceLabel - label of source node of the edge
			   targetLabel - label of target node of the edge
	 * @returns: false if the graph already contains the edge, true otherwise
	 * @throws: java.lang.IllegalArgumentException - if either label is null or 
	 *			if there are no nodes in the graph with the given labels
	 */
	public boolean addEdge(String sourceLabel, String targetLabel){
		if(sourceLabel == null || targetLabel == null){
			throw new IllegalArgumentException();
		}
		for(GraphNode g: nodes){
			if(g.getName().equals(sourceLabel)){
				for(GraphNode n : nodes){
					if(n.getName().equals(targetLabel)){
						g.setEdge(n);
						numLinks++;
						return true;
					}
				}
				GraphNode newNode = new GraphNode(targetLabel);
				nodes.add(newNode);
				numNodes++;
				g.setEdge(newNode);
				numLinks++;
				return true;
			}
		}
		throw new IllegalArgumentException();
	}
	/**
	 * Adds a node with the given label to the graph. If a node with this label 
	 * is already in the graph, the graph is unchanged and false is returned. 
	 * Otherwise (i.e., if there previously had not been a node with this label 
	 * and a new node with this label is added to the graph), true is returned.
	 * @param: label - label of newly added node
	 * @returns: false if a node with the given label is already in the graph, 
	 * true otherwise
	 * @throws: java.lang.IllegalArgumentException - if label is null
	 */
	public boolean addNode(String label){
		if(label == null){
			throw new IllegalArgumentException();
		}
		for(GraphNode g : nodes){
			if(g.getName().equals(label)){
				return false;
			}
		}
		nodes.add(new GraphNode(label));
		numNodes++;
		return true;
	}
	/**
	 * Return true if and only if the graph contains an edge from the node with 
	 * the label sourceLabel to the node with the label targetLabel.
	 * @param: sourceLabel - label of source node of the edge
				targetLabel - label of target node of the edge
	 * @returns: true if and only if there is an edge from the node labeled 
	 *sourceLabel to the node labeled targetLabel
	 * @throws: java.lang.IllegalArgumentException - if either label is null or 
	 *if there are no nodes in the graph with the given labels
	 */
	public boolean hasEdge(String sourceLabel, String targetLabel){
		if(sourceLabel == null || targetLabel == null){
			throw new IllegalArgumentException();
		}
		for(GraphNode g : nodes){
			if(g.getName().equals(sourceLabel)){
				return g.getEdges().contains(targetLabel);
			}
		}
		throw new IllegalArgumentException();
	}
	/**
	 * public boolean hasNode(java.lang.String label)
	 * Return true if and only if a node with the given label is in the graph.
	 *	@param: label - label of node to check
	 *	@returns: true if and only if there is a node with the given label in the graph
	 *	@throws: java.lang.IllegalArgumentException - if label is null
	 */
	public boolean hasNode(String label){
		if(label == null){
			throw new IllegalArgumentException();
		}
		for(GraphNode g : nodes){
			if(g.getName().equals(label)){
				return true;
			}
		}
		return false;
	}
	/**
	 * Return labels of immediate successors of the given node in alphabetical 
	 * order.
	 * @param: label - label of the start node
	 * @returns: labels of immediate neighbors of the start node in alphabetical 
	 * order
	 * @throws: java.lang.IllegalArgumentException - if label is null or if 
	 *  there is no node in the graph with the given label
	 */
	public List<String> successors(String label){
		if(label == null){
			throw new IllegalArgumentException();
		}
		for(GraphNode g : nodes){
			if(g.getName().equals(label)){
				return g.getEdges();
			}
		}
		throw new IllegalArgumentException();
	}
	/**
	 * Returns this GraphNode's visit field (boolean)
	 *
	 * @return visited whether or not this GraphNode has been visited in a 
	 * traversal or search
	 */
	public int size(){
		return numNodes;
	}
	/**
	 * Returns this GraphNode's visit field (boolean)
	 *
	 * @return visited whether or not this GraphNode has been visited in a 
	 * traversal or search
	 */
	public boolean isEmpty(){
		return numNodes == 0;
	}
	/**
	 * Returns this GraphNode's visit field (boolean)
	 *
	 * @return visited whether or not this GraphNode has been visited in a 
	 * traversal or search
	 */
	public int getNumLinks(){
		return numLinks;
	}
	/**
	 * Returns this GraphNode's visit field (boolean)
	 *
	 * @return visited whether or not this GraphNode has been visited in a 
	 * traversal or search
	 */
	public Iterator<String> iterator(){
		List<String> names = new ArrayList<String>();
		for(GraphNode g : nodes){
			names.add(g.getName());
		}
		return names.iterator();
	}
	/**
	 * Return a list of node labels in the order they are visited using a 
	 * depth-first search starting at the node with the given label. Whenever a
	 * node has multiple successors, the successors are visited in alphabetical 
	 * order.
	 * @param: startLabel - label of the start node
	 * @returns: list of the node labels in DFS order
	 * @throws: java.lang.IllegalArgumentException - if startLabel is null or 
	 * there is no node with this label in the graph
	 */
	public List<String> dfs(String startLabel){
		if(startLabel == null){
			throw new IllegalArgumentException();
		}
		List<String> dfsList = new ArrayList<String>();
		return (dfs(startLabel, dfsList)) ;
	}
	/**
	 * The private auxilary method for dfs which allows for recursion using
	 * the String label of the node as well as the list of nodes found in the
	 * traversal
	 * @param label - label of the node
	 * 		dfsList - the list of nodes found in the dfs so far
	 * @returns the list of nodes found in dfs order
	 * 
	 */
	private List<String> dfs(String label, List<String> dfsList){
		GraphNode n = null;
		for(GraphNode g : nodes){
			if (g.getName().equals(label)){
				n = g;
			}
		}
		if(n == null){
			throw new IllegalArgumentException();
		}
		dfsList.add(n.getName());
		n.setVisited(true);
		for(GraphNode g : n.getNodes()){
			if(!g.getVisited()){
				dfs(g.getName(), dfsList);
			}
		}

		return dfsList;
	}
	/**
	 * Return a list of node labels in the order they are visited using a 
	 * breadth-first search starting at the node with the given label. Whenever 
	 * a node has multiple successors, the successors are visited in 
	 * alphabetical order.
	 * @param:: startLabel - label of the start node
	 * @returns: list of the node labels in BFS order
	 * @throws: java.lang.IllegalArgumentException - if startLabel is null or 
	 * there is no node with this label in the graph
	 */
	public List<String> bfs(String startLabel){
		if(startLabel == null){
			throw new IllegalArgumentException();
		}
		for(GraphNode v : nodes){
			v.setVisited(false);
		}
		List<String> bfsList = new ArrayList<String>();
		LinkedList<GraphNode> queue = new LinkedList<GraphNode>();
		GraphNode n = null;
		for(GraphNode g : nodes){
			if (g.getName().equals(startLabel)){
				n = g;
			}
		}
		if(n == null){
			throw new IllegalArgumentException();
		}
		n.setVisited(true);
		bfsList.add(n.getName());
		queue.offer(n);
		while(!queue.isEmpty()){
			GraphNode curr = queue.remove();
			for(GraphNode g : curr.getNodes()){
				if(!g.getVisited()){
					bfsList.add(g.getName());
					g.setVisited(true);
					queue.offer(g);
				}
			}
		}
		return bfsList;
	}
	/**
	 * Find a cyclic path that starts and ends at the given node. Whenever a 
	 * node has multiple successors, the successors are visited in alphabetical 
	 * order. Returns the complete list of node labels along the path, with the 
	 * start node label appearing first and last. The path, if it is found, is 
	 * non-trivial, i.e., it contains at least one edge. If there is not a 
	 * cyclic path that starts and ends at the given node, null is returned.
	 * @param: startLabel - label of the start node
	 * @returns: sequence of nodes along the cyclic path, starting and ending 
	 * at the start node, or null if there is no such path
	 * @throws: java.lang.IllegalArgumentException - if startLabel is null or 
	 * there is no node with this label in the graph
	 */
	public List<String> cyclicPath(String startLabel){
		if(startLabel == null){
			throw new IllegalArgumentException();
		}
		//set all nodes visit marks to false
		for(GraphNode v : nodes){
			v.setVisited(false);
		}
		//set all nodes mark fields to false
		for(GraphNode v : nodes){
			v.setMark(0);
		}
		GraphNode start = null;
		for(GraphNode n : nodes){
			if(n.getName().equals(startLabel)){
				start = n;
			}
		}
		if(start == null){
			throw new IllegalArgumentException();
		}
		List<String> cyclic = new ArrayList<String>();
		start.setMark(1);
		boolean isCyclic = false;
		isCyclic = hasCycle(start, cyclic);
		if(!isCyclic){
			return null;
		}
		Collections.reverse(cyclic);
		return cyclic;
	}
	/**
	 * The private auxilary method for hasCycle that allows for recursion. This
	 * method returns a boolean value to indicate if a cyclic path has been
	 * found or not
	 * @param label - the label of the GraphNode
	 * @param cyclic - the list of users in the cyclic path
	 * @returns true iff there is a cyclic path from label
	 */
	private boolean hasCycle(GraphNode label, List<String> cyclic){
		final int TARGET = 1;
		label.setVisited(true);
		for(GraphNode m : label.getNodes()){
			if(m.getMark() == TARGET){
				cyclic.add(m.getName());
				return true;
			}
			if(!m.getVisited()){
				if(hasCycle(m, cyclic)){
					cyclic.add(m.getName());
					return true;
				}
			}
		}
		return false;
	}
	/**
	 * Find the shortest path from a start node to a finish node. Whenever a 
	 * node has multiple successors, the successors are visited in alphabetical 
	 * order. Returns the complete list of node labels along the path, with the 
	 * start node label appearing first and the finish node label appearing 
	 * last.
	 * @param: startLabel - label of the start node
	 * @param:	finishLabel - label of the finish node
	 * @returns: sequence of nodes along the path, or null if there is no such 
	 * path
	 * @throws: java.lang.IllegalArgumentException - if either label is null or
	 *  if there are no nodes in the graph with the given labels
	 */
	public List<String> shortestPath(String startLabel, String finishLabel){
		if(startLabel == null || finishLabel == null){
			throw new IllegalArgumentException();
		}
		GraphNode start = null;
		GraphNode finish = null;
		for(GraphNode n : nodes){
			if(n.getName().equals(startLabel)){
				start = n;
			}
			else if(n.getName().equals(finishLabel)){
				finish = n;
			}
		}
		if(start == null || finish == null){
			throw new IllegalArgumentException();
		}

		for(GraphNode v : nodes){
			v.setVisited(false);
			v.setMark(0);
		}
		ArrayList<String> labels = new ArrayList<String>();
		finish.setMark(1);

		if(!shortestPath(start, finish, labels)){
			return null;
		}
		labels.add(start.getName());
		Collections.reverse(labels);
		return labels;
	}
	/**
	 * The private auxilary method which allows for recursion. This method is
	 * similar to the private method for cyclicPath.
	 *  @param start - the GraphNode to start the path from
	 *  @param finish - the last GraphNode in the path
	 *  @param labels - the list of node names in the shortest path
	 *  @returns true iff there is a path from start to finish
	 */
	private boolean shortestPath(GraphNode start, GraphNode finish, List<String> labels){
		final int TARGET = 1;
		start.setVisited(true);
		for(GraphNode m : start.getNodes()){
			if(m.getMark() == TARGET){
				labels.add(m.getName());
				return true;
			}
			if(!m.getVisited()){
				if(hasCycle(m, labels)){
					labels.add(m.getName());
					return true;
				}
			}
		}
		return false;
	}
}




