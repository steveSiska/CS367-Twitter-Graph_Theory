///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Title:            GraphAnalyser
// Files:            GraphAnalyser.java
// Semester:         Spring 2012
//
// Author:           Steve Siska	ssiska@wisc.edu
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
  *The GraphAnalyser class takes one command line argument (a file name) with 
  *the file in the appropriate format and recreates an overview of Twitter. Edges
  *between nodes in the graph are the direction in which tweets go. The graph
  *analyser also calculates statisitcs based on the graph.
  *
  * <p>Bugs: None known
  *
  * @author Steve Siska
  */
import java.io.*;
import java.util.*;

public class GraphAnalyser {
	
	public static void main(String[] args){
		//if there are the incorrect # of command line args, quit
		if(args.length != 1){
			System.err.println("Usage: java GraphAnalyser fileName");
			System.exit(-1);
		}
		//now check if the argument given is a readable file
		Scanner in = null, sec = null;
		try{
			File inFile = new File(args[0]);
			if (!inFile.exists() || !inFile.canRead()) {
				System.err.println("Error: Cannot access input file");
				System.exit(1);
			}
			in = new Scanner(inFile);
			sec = new Scanner(inFile);
		}
		catch(FileNotFoundException e){
			System.err.println("Error: Cannot access input file");
			System.exit(1);
		}
		//construct a new BasicGraph
		BasicGraph twitter = new BasicGraph();
		String[] tokens = null;
		//here we create the users who can be followed
		while(in.hasNextLine()){
			String nextLine = in.nextLine();
			//take out the : and , marks in the input file
			tokens = nextLine.split(":|\\,");
			String user = tokens[0];
			twitter.addNode(user);
		}
		//now add their followers to the graph
		while(sec.hasNextLine()){
			String nextLine = sec.nextLine();
			tokens = nextLine.split(":|\\,");
			String user = tokens[0];
			for(int i = 1; i < tokens.length; i++){
				twitter.addEdge(user, tokens[i]);
			}
		}
		//close the files for safety
		in.close();
		sec.close();
		//display the needed statistics
		displayStats(twitter);

	}
	/**
	  * Displays the statistics of the basic graph to standard output
	  *
	  * @param twitter The basic graph
	  * 
	  */
	public static void displayStats(BasicGraph twitter){
		//first, find the first user (alphabetically) in the list of users
		Iterator<String> itr = twitter.iterator();
		String first = null;
		if(itr.hasNext()){
			first = itr.next();
		}
		//now print out the statistics
		System.out.println("Number of users: " + twitter.size());
		System.out.println("Number of follow links: " + twitter.getNumLinks());
		System.out.println("DFS visit order: " + twitter.dfs(first));
		System.out.println("BFS visit order: " + twitter.bfs(first));
		System.out.println("Most followers: " + mostFollowers(twitter));
		System.out.println("No followers: " + noFollowers(twitter));
		System.out.println("Don't follow anyone: " + dontFollowAnyone(twitter, first));
		System.out.println("Receive most tweets: " + receiveMostTweets(twitter));
		System.out.println("Tweet reaches self: " + reachesSelf(twitter));
		System.out.println("Most users reached: " + mostUsersReached(twitter));
		System.out.println("Minutes to get tweet: " + minutesToGet(twitter));
	}
	/**
	  * Calculates which user has the most followers
	  *
	  * @param twitter The basic graph
	  * @return the List of the users with the most followers (if more than one)
	  */
	public static List<String> mostFollowers(BasicGraph twitter){
		List<String> most = new ArrayList<String>();
		int max = 0; 
		Iterator<String> itr = twitter.iterator();
		//first figure out how many followers is the most
		while(itr.hasNext()){
			String curr = itr.next();
			if(twitter.successors(curr).size() >= max){
				max = twitter.successors(curr).size();
			}
		}
		//now go through and add the users with that ammount of followers to the
		//list to be returned.
		Iterator<String> itr2 = twitter.iterator();
		while(itr2.hasNext()){
			String curr = itr2.next();
			if(twitter.successors(curr).size() == max){
				most.add(curr);
			}
		}
		return most;
	}
	/**
	  * Calculates which user(s), if any, have no followers
	  *
	  * @param twitter The basic graph
	  * @return the number of users with no followers
	  */
	public static int noFollowers(BasicGraph twitter){
		int noFollowers = 0;
		Iterator<String> itr = twitter.iterator();
		while(itr.hasNext()){
			String curr = itr.next();
			//if the user has no successors, then they have no followers
			if(twitter.successors(curr).isEmpty()){
				noFollowers++;
			}
		}
		return noFollowers;
	}
	/**
	  * Calculates which user(s), don't follow anyone
	  *
	  * @param twitter The basic graph
	  * @return the number of users who don't follow anyone
	  */
	public static int dontFollowAnyone(BasicGraph twitter, String first){
		List<String> elems = new ArrayList<String>();
		List<String> doFollow = new ArrayList<String>();
		Iterator<String> itr = twitter.iterator();
		//first create a list with all the users who get followed in it
		while(itr.hasNext()){
			String curr = itr.next();
			elems.add(curr);
		}
		//now create a list with all of those users followers
		for(int i = 0; i <elems.size(); i++){
			doFollow.addAll(twitter.successors(elems.get(i)));
		}
		//if the lists have a common user, remove that user
		for(int i = 0; i <doFollow.size(); i++){
			if(elems.contains(doFollow.get(i))){
				elems.remove(doFollow.get(i));
			}
		}
		//now we have the size of the list of users who don't follow anyone
		return elems.size();
	}
	/**
	  * Calculates which user(s), recieve the most tweets
	  *
	  * @param twitter The basic graph
	  * @return the list of the users who recieve the most tweets
	  */
	public static List<String> receiveMostTweets(BasicGraph twitter){
		int largest = 0;
		List<String> mostTweets = new ArrayList<String>();
		List<String> elems = new ArrayList<String>();
		List<String> doFollow = new ArrayList<String>();
		Iterator<String> itr = twitter.iterator();
		//do a similar thing here as don'tFollowAnyone
		while(itr.hasNext()){
			String curr = itr.next();
			elems.add(curr);
		}
		for(int i = 0; i <elems.size(); i++){
			doFollow.addAll(twitter.successors(elems.get(i)));
		}
		//but now for every word in doFollow, figure out the largest amount 
		//of times that word appears
		for(String word : doFollow){
			if(Collections.frequency(doFollow, word) > largest){
				largest = Collections.frequency(doFollow, word);
			}
		}
		//add the users which have the largest ammount of tweets
		for(String word : doFollow){
			if(Collections.frequency(doFollow, word) == largest){
				if(!mostTweets.contains(word)){
					mostTweets.add(word);
				}
			}
		}
		//sort the list alphabetically
		Collections.sort(mostTweets);
		return mostTweets;
	}
	/**
	  * Calculates which user(s), can reach the most people with a single tweet
	  *
	  * @param twitter The basic graph
	  * @return the number of users that can be reached plus the list of the
	  * users who can reach that number of other users
	  */
	public static String mostUsersReached(BasicGraph twitter){
		Integer mostUsers = 0;
		Iterator<String> itr = twitter.iterator();
		List<String> mostUsersNames = new ArrayList<String>();
		while(itr.hasNext()){
			String curr = itr.next();
			//calculate the size of the bfs for a user
			if(twitter.bfs(curr).size() > mostUsers){
				mostUsers = twitter.bfs(curr).size(); 
			}
		}
		//if that user has a bfs with the max size, it can reach the most users
		Iterator<String> itr2 = twitter.iterator();
		while(itr2.hasNext()){
			String curr = itr2.next();
			if(twitter.bfs(curr).size() == mostUsers){
				mostUsersNames.add(curr);
			}
		}
		return Integer.toString(mostUsers) + " " + mostUsersNames;
	}
	/**
	  * Calculates which user(s) can recieve their own tweet
	  *
	  * @param twitter The basic graph
	  * @return the number of users who can tweet to themselves plus those
	  * users' names
	  */
	public static String reachesSelf(BasicGraph twitter){
		Iterator<String> itr = twitter.iterator();
		List<String> self = new ArrayList<String>();
		//if the user has a cyclic path to itself, it reaches itself
		while(itr.hasNext()){
			String curr = itr.next();
			if(twitter.cyclicPath(curr) != null){
				self.add(curr);
			}
		}
		return self.size() + " " + self.toString();
	}
	/**
	  * If each follower were to retweet a message from the user with the most
	  * followers, this method calculates long it would take for the tweet to 
	  * be recieved by all followers of the user with the most followers
	  *
	  * @param twitter The basic graph
	  * @return the number of minutes it takes for everyone to recieve the tweet
	  */
	public static int minutesToGet(BasicGraph twitter){
		List<String> most = mostFollowers(twitter);
		String firstMost = most.get(0);
		String last = twitter.bfs(firstMost).get(twitter.bfs(firstMost).size()-1);
		//return the shortest path between the most followed user and the last
		//one in the bfs-this is the ammount of levels(minutes) the graph goes
		return twitter.shortestPath(firstMost, last).size()-1 ;
	}
}
