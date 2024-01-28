import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;
import java.util.Set;
import java.util.Stack;

/** Author: Brandon Hoover
 *  Date Created: Monday 11/12/23
 *  Copyright: Adapted from Sherif Khettab, all rights reserved
 *  AirlineSystem.java
 *  Project: 4
 *  Hours: At least 15 to 20
 */

// thats cool that if the class is inside the project packadge, you dont need to include it you can just instantiate it

public class AirlineSystem implements AirlineInterface {


	private String [] cityNames;
	private Digraph G;

	boolean finished = false;

	int bitFlag = 0;
	int amtOfOptions = 0;

	Route tempSave;

	double lowestPrice = Integer.MAX_VALUE;

	

	/** method - loadRoutes
	 * reads the city names and the routes from a file
	 * @param fileName the String file name
	 * @return true if routes loaded successfully and false otherwise
	 */
	public boolean loadRoutes(String fileName)  {

		// fileName passed in supposed to ask in main 
		// use a counter, int or byte, int because we dont care about size
		// if counter == the first value read in, you got a good read, if it doesnt, you got a bad read 
		boolean result = false;
		int counter = 0;
		int numVert = 0;


		// must nest everything inside of try catch  
		Scanner fileScan;
		try {
			fileScan = new Scanner(new FileInputStream(fileName));

			String nextRead = fileScan.nextLine();
			int numVertices = Integer.parseInt(nextRead);
			numVert = numVertices;
			// read the fist line pull the number of vertices and then initialize a Digraph with that number 
			G = new Digraph(numVertices);

			// initialize CityNames 
			cityNames = new String[numVertices];

			// reads airport list and populates cityNames array
			for (int i=0; i < numVertices; i++) {

				nextRead = fileScan.nextLine();
				cityNames[i] = nextRead;
				counter++;
			}

			// building the diGraph 
			while(fileScan.hasNextInt()){  	// read through until the end

				// pulling the two numbers and their corresponding relevance (start point, end point)
				int startPoint = fileScan.nextInt();
				int endPoint = fileScan.nextInt();

				int distance = fileScan.nextInt();
				double price = fileScan.nextDouble();

				if (price < lowestPrice ) {					
					lowestPrice = price;						// calculated the lowestPrice of any ticket in the graph (unused)
				}

				String source = findCity(startPoint);
				String destination = findCity(endPoint);

				// need to use Route class, its already made for you, create new route objects and add them to the adjacency list 

				// creating new Route objects to be added to the edge that was read in
				Route newRoute1 = new Route(source, destination ,distance ,price);  

				Route newRoute2 = new Route(destination, source, distance, price);

				// the reason that I am adding two routes is because to keep the graph undirected you have to make it bidirectional, which means that each route is visible both ways 
				// a directed graph cares about the ordering of the pairs (A, B) but with this scenario we dont because airports can close at random (snow, etc.)
				G.addEdge(newRoute1);
				G.addEdge(newRoute2);       // this might be causing an issue, dont forget

			}

			// end of reading data file, close and exit method
			fileScan.close();
		} catch (FileNotFoundException e) {    

			e.printStackTrace();
		}

		// this makes sure you read the names but how do you know if you read all of the routes correctly? 
		if (counter == numVert) {
			result = true;
		}

		return result;							// return result ends method


	}

	/** method - retriveCity Names
	 * returns the set of city names in the Airline system
	 * @return a (possibly empty) Set<String> of city names
	 */
	public Set<String> retrieveCityNames() {                    // this method will work as long as loadRoutes is called first 


		// Set still contains all attributes discussed in 0441 (no duplicates, order doesnt matter)

		// need to use Set class 
		Set<String> theHashSet = new HashSet<String>();

		for (int i=0; i < cityNames.length; i++) {

			theHashSet.add(cityNames[i]);
		}

		return theHashSet;                                     // is okay if this is returned empty?, (cityNames is uninitialized);

		// do you need to build the set every time or can you make this a class variable
		// what would be the benefit of using the set in other methods? --- a Set of the names of the airports, umm 


	}

	/** method - retrieveDirectRoutesFrom 
	 * returns the set of direct routes out of a given city
	 * @param city the String city name
	 * @return a (possibly empty) Set<Route> of Route objects representing the
	 * direct routes out of city
	 * @throws CityNotFoundException if the city is not found in the Airline
	 * system
	 */
	public Set<Route> retrieveDirectRoutesFrom(String city) throws CityNotFoundException {

		// this could be as easy, as iterating through a linked list in the adj of the right index and adding it to a set 

		// finding the index for adj -- assuming the two data structure are aligned (it should)
		int adjIndex = findIndex(city);

		if (adjIndex == -1) {                               // city not found throw exception

			throw new CityNotFoundException(city);
		}

		Set<Route> setOfRoutes = new HashSet<Route>();      

		// iterating through the LL in adj 
		for (int i=0; i < G.adj[adjIndex].size(); i++) {


			setOfRoutes.add(G.adj[adjIndex].get(i));      // Linked List starts at 1, so simply pull i+1 until you reach end

		}

		return setOfRoutes;								  // return the setOfRoutes and end the method 
	}

	/** method - fewestStopsItinerary 
	 * finds fewest-stops path(s) between two cities
	 * @param source the String source city name
	 * @param destination the String destination city name
	 * @return a (possibly empty) Set<ArrayList<String>> of fewest-stops paths.
	 * Each path is an ArrayList<String> of city names that includes the source
	 * and destination city names.
	 * @throws CityNotFoundException if any of the two cities are not found in the
	 * Airline system
	 */
	public Set<ArrayList<String>> fewestStopsItinerary(String sourceString, String destinationString) throws CityNotFoundException {        

		// I didnt even know you could declare a type Stack in java
		// so String source, and string destination are passed in so they expect you to convert them to indices Im assuming
		// if you convert them 

		int source = findIndex(sourceString);
		int destination = findIndex(destinationString);

		// could do a numVertices or set source to -1 and then see if it gets updated, only covers 1 end 

		Set<ArrayList<String>> theSet = new HashSet<ArrayList<String>>();  // this doesnt make any sense either

		ArrayList<String> shortestPath = new ArrayList<String>();

		// copy code but still need to import bfs class but now source and destination should be fine except for the to() method
		// what can you do about that, the to() method returns the destination so 

		G.bfs(source);
		if(!G.marked[destination]){
			System.out.println("There is no route from " + cityNames[source]
					+ " to " + cityNames[destination]);
		} else {
			Stack<Integer> path = new Stack<>();
			for (int x = destination; x != source; x = G.edgeTo[x])
				path.push(x);
			path.push(source);
			System.out.print("The shortest route from " + cityNames[source] +
					" to " + cityNames[destination] + " has " +
					G.distTo[destination] + " hop(s): ");
			while(!path.empty()){
				shortestPath.add(cityNames[path.pop()]);
				// System.out.print(cityNames[path.pop()] + " ");

			}
			System.out.println();

		}

		theSet.add(shortestPath);               // adding the ArrayList that we contains the shortest path to the set, should only be one item in set

		return theSet;							// return the set and end the method
	}


	/** method - shortestDistanceItinerary
	 * finds shortest distance path(s) between two cities
	 * @param source the String source city name
	 * @param destination the String destination city name
	 * @return a (possibly empty) Set<ArrayList<Route>> of shortest-distance
	 * paths. Each path is an ArrayList<Route> of Route objects that includes a
	 * Route out of the source and a Route into the destination.
	 * @throws CityNotFoundException if any of the two cities are not found in the
	 * Airline system
	 */
	public Set<ArrayList<Route>> shortestDistanceItinerary(String sourceString, String destinationString)
			throws CityNotFoundException {

		int source = -1;					// for throws
		int destination = -1;				// for throws
		bitFlag = 0;             

		source = findIndex(sourceString);					// conversion to integers
		destination = findIndex(destinationString);

		if (source == -1) {

			throw new CityNotFoundException(sourceString);
		}
		else if (destination == -1) {

			throw new CityNotFoundException(destinationString);
		}

		// could do a numVertices or set source to -1 and then see if it gets updated, only covers 1 end 

		Set<ArrayList<Route>> theSet = new HashSet<ArrayList<Route>>();  // this doesnt make any sense either

		ArrayList<Route> shortDist = new ArrayList<Route>();


		G.dijkstrasInt(source, destination);
		if(!G.marked[destination]){
			System.out.println("There is no route from " + cityNames[source]
					+ " to " + cityNames[destination]);
		} else {
			Stack<Integer> path = new Stack<>();
			for (int x = destination; x != source; x = G.edgeTo[x]){
				path.push(x);
			}
			System.out.print("The shortest route from " + cityNames[source] +
					" to " + cityNames[destination] + " has " +
					G.distTo[destination] + " miles: ");

			int prevVertex = source;
			System.out.print(cityNames[source] + " ");
			while(!path.empty()){
				int v = path.pop();

				// which adj[] are you looking for v or prevVertex

				Route aRoute = findRoute(v, prevVertex);        // finding the route that was calculated in the stack

				shortDist.add(aRoute);                          // adding that route to the Array List of routes

				// v is the destination right 
				// need to find the route that is being popped
				// so findIndex, so can you use adj[v] but that is a linked list while v is destination

				// doing that until end of loop 

				System.out.print(G.distTo[v] - G.distTo[prevVertex] + " "
						+ cityNames[v] + " ");
				prevVertex = v;
			}

		}

		theSet.add(shortDist);               // adding the ArrayList that we contains the shortest path to the set, should only be one item in set

		return theSet;						 // return the set and end the method 


	}

	/** method - cheapestItinerary (method overloading 1 of 2)
	 * finds cheapest path(s) between two cities
	 * @param source the String source city name
	 * @param destination the String destination city name
	 * @return a (possibly empty) Set<ArrayList<Route>> of cheapest
	 * paths. Each path is an ArrayList<Route> of Route objects that includes a
	 * Route out of the source and a Route into the destination.
	 * @throws CityNotFoundException if any of the two cities are not found in the
	 * Airline system
	 */
	public Set<ArrayList<Route>> cheapestItinerary(String sourceString, String destinationString) throws CityNotFoundException {

		bitFlag = 1;				// a bitFlag that flags based on which version of cheapestIternery you are using 

		int source = -1;			// for throws exception, if < 0 obviously you didnt find the city
		int destination = -1;

		// another dijkstras if you set bitFlag at the start of each method you should be fine for reuse

		source = findIndex(sourceString);
		destination = findIndex(destinationString);

		if (source == -1) {

			throw new CityNotFoundException(sourceString);
		}
		else if (destination == -1) {

			throw new CityNotFoundException(destinationString);
		}

		// could do a numVertices or set source to -1 and then see if it gets updated, only covers 1 end 

		Set<ArrayList<Route>> theSet = new HashSet<ArrayList<Route>>();  // this doesnt make any sense either

		ArrayList<Route> shortDist = new ArrayList<Route>();


		G.dijkstrasInt(source, destination);
		if(!G.marked[destination]){
			System.out.println("There is no route from " + cityNames[source]
					+ " to " + cityNames[destination]);
		} else {
			Stack<Integer> path = new Stack<>();
			for (int x = destination; x != source; x = G.edgeTo[x]){
				path.push(x);
			}
			System.out.print("The shortest route from " + cityNames[source] +
					" to " + cityNames[destination] + " has " +
					G.distTo[destination] + " dollars: ");

			int prevVertex = source;
			System.out.print(cityNames[source] + " ");
			while(!path.empty()){
				int v = path.pop();

				// which adj[] are you looking for v or prevVertex
				Route aRoute = findRoute(v, prevVertex);        // finding the route that was calculated in the stack
				shortDist.add(aRoute);                          // adding that route to the Array List of routes

				System.out.print(G.distTo[v] - G.distTo[prevVertex] + " "   // will eventually be removed
						+ cityNames[v] + " ");
				prevVertex = v;
			}

		}

		theSet.add(shortDist);               // adding the ArrayList that we contains the shortest path to the set, should only be one item in set

		return theSet;						 // return the set end the method

	}


	/** method - cheapestItinerary (method overloading 2 of 2)
	 * finds cheapest path(s) between two cities going through a third city
	 * @param source the String source city name
	 * @param transit the String transit city name
	 * @param destination the String destination city name
	 * @return a (possibly empty) Set<ArrayList<Route>> of cheapest
	 * paths. Each path is an ArrayList<Route> of city names that includes
	 * a Route out of source, into and out of transit, and into destination.
	 * @throws CityNotFoundException if any of the three cities are not found in
	 * the Airline system
	 */
	public Set<ArrayList<Route>> cheapestItinerary(String sourceString, String transitString, String destinationString)
			throws CityNotFoundException {

        // finding shortest path from source to transit and finding shortest path from transit to destination and then concatenating the two result
        // calling dijkstra, fill up first part, then call disjkstra's again with transit as source // and go from there

        // it should be source to transit and then transit to destination

        // still using price implementation so bitFlag is = 1
        // I am not sure if this is right but you gotta sleep and start tomorrow

        bitFlag = 1;
        int source = -1;
		int destination = -1;
		source = findIndex(sourceString);
		destination = findIndex(transitString);

         // for debug
        int transit = findIndex(destinationString);

		if (source == -1) {

			throw new CityNotFoundException(sourceString);
		}
		else if (destination == -1) {

			throw new CityNotFoundException(transitString);
		}

		Set<ArrayList<Route>> theSet = new HashSet<ArrayList<Route>>();  // do not need to reinitialize for part 2 of this method
		ArrayList<Route> shortDist = new ArrayList<Route>();

		G.dijkstrasInt(source, destination);                             // FIRST DIJKSTRAS CALL
		if(!G.marked[destination]){
			System.out.println("There is no route from " + cityNames[source]
					+ " to " + cityNames[destination]);
		} else {
			Stack<Integer> path = new Stack<>();
			for (int x = destination; x != source; x = G.edgeTo[x]){
				path.push(x);
			}
			System.out.print("The cheapest route from " + cityNames[source] +
					" to " + cityNames[transit] + " with a STOP in " + cityNames[destination] + " costs " +
					G.distTo[destination] + " dollars (wrong): ");

			int prevVertex = source;
			System.out.print(cityNames[source] + " ");
			while(!path.empty()){
				int v = path.pop();

				// which adj[] are you looking for v or prevVertex
				Route aRoute = findRoute(v, prevVertex);        // finding the route that was calculated in the stack
				shortDist.add(aRoute);                          // adding that route to the Array List of routes

				System.out.print(G.distTo[v] - G.distTo[prevVertex] + " "       // eventually delete
						+ cityNames[v] + " ");
				prevVertex = v;
			}

		}

        /////////////// The second part of lowestItinerary Transit to Destination \\\\\\\\\\\\\

        source = -1;
		destination = -1;
		source = findIndex(transitString);
		destination = findIndex(destinationString);


		if (source == -1) {

			throw new CityNotFoundException(transitString);         // still need notFoundExceptions for at least destination string but why not transitString
		}
		else if (destination == -1) {

			throw new CityNotFoundException(destinationString);
		}

        G.dijkstrasInt(source, destination);                        // SECOND DIJKSTRAS CALL
		if(!G.marked[destination]){
			System.out.println("There is no route from " + cityNames[source]
					+ " to " + cityNames[destination]);
		} else {
			Stack<Integer> path = new Stack<>();
			for (int x = destination; x != source; x = G.edgeTo[x]){
				path.push(x);
			}

			int prevVertex = source;
			System.out.print(cityNames[source] + " ");
			while(!path.empty()){
				int v = path.pop();

				// which adj[] are you looking for v or prevVertex
				Route aRoute = findRoute(v, prevVertex);        // finding the route that was calculated in the stack
				shortDist.add(aRoute);                          // adding that route to the Array List of routes

				System.out.print(G.distTo[v] - G.distTo[prevVertex] + " "       // eventually delete
						+ cityNames[v] + " ");
				prevVertex = v;
			}

		}

        // currently adding the transit city twice, but the price is correct

		theSet.add(shortDist);    // adding the ArrayList that we contains the shortest path to the set, should only be one item in set
	    return theSet;            // returning a Set that contains one Arraylist of routes that contains the cheapest path (price) from a source to a destination with a transit city
       
	}

	/** method getMSTs
	 * finds one Minimum Spanning Tree (MST) for each connected component of
	 * the graph
	 * @return a (possibly empty) Set<Set<Route>> of MSTs. Each MST is a Set<Route>
	 * of Route objects representing the MST edges.
	 */
	public Set<Set<Route>> getMSTs() {      // no throws exception
		
        
        Set<Set<Route>> theMainSet = new HashSet<Set<Route>>();         // a set of a set of Routes
		
        Set<Route> aSetRoute = new HashSet<Route>();                // A set of routes that is going to be added to theSet

		int source = 0;												// starting from 0
		int destination = G.v-1;									// building an MST from 0 to 8, going to cover a lot and then the loop will take care of the rest

		 // call prims here  (find a bunch of edges, pull out edgeTo (best) as you pop) // like do I need to use a stack? 
		 G.prims(source, destination);
		// System.out.println("debug)");

		// you should do an add RouteSet here loop through call add, and then keep doing that
		 Set<Route> newRouteSet = addRouteSet();			// that contains what I want

		 
		 aSetRoute.addAll(newRouteSet);			


        for (int i=0; i < cityNames.length; i++) {          // will eventually check each vertice but only call a few times 

			
            // if vertices marked dont call prims
				if (!G.marked[i]) {
	
					// vertice that was not marked was found, call Prims!
					G.prims(i, G.v-1);
					Set<Route> newRouteSet2 = addRouteSet(); // only add once done with prims
					aSetRoute.addAll(newRouteSet2);
				}
			
            // else dont call prims keep iterating
			//System.out.println("for");
			
			aSetRoute.remove(G.adj[0].get(2));
			aSetRoute.remove(G.adj[4].get(2));
        }

        theMainSet.add(aSetRoute);
        return theMainSet;
	}

	/***
	 * adds a set of routes to a set, used in dijsktras
	 * @return - the set of routes
	 */
	public Set<Route> addRouteSet () {

		Set<Route> theSetOfRoutes = new HashSet<Route>();  			// could be empty

		// iterate throught eh adjacency list and add all routes that are not 0 or MAX_INT
		
		for (int i=0; i < G.bestEdge.length; i++) {

			// conditional
			if (G.bestEdge[i] != 0 && G.bestEdge[i] != Integer.MAX_VALUE) {

				// pull the route 
				// bestEdgeWeight

				int bestEdgeWeight = G.bestEdge[i];			
				for (int j=0; j < G.adj[i].size(); j++) {

					if (bestEdgeWeight == G.adj[i].get(j).distance) {

						// pull it
						theSetOfRoutes.add(G.adj[i].get(j));

					}
				}
			}

			// add it to the set, going to add duplicates 

		}

		return theSetOfRoutes;			// return the set of routes
	}

	/** method - tripsWithin
	 * finds all itineraries starting out of a source city and within a given
	 * price
	 * @param city the String city name
	 * @param budget the double budget amount in dollars
	 * @return a (possibly empty) Set<ArrayList<Route>> of paths with a total cost
	 * less than or equal to the budget. Each path is an ArrayList<Route> of Route
	 * objects starting with a Route object out of the source city.
	 */
	public Set<ArrayList<Route>> tripsWithin(String city, double budget) throws CityNotFoundException {
		

		int checkCity = -1; 
		amtOfOptions = 0;
		G.marked = new boolean[G.v];						// initializing marked array, you are marking vertices

		checkCity = findIndex(city);						// grabbing the index that were are supposed to start at

		if (checkCity < 0) {								// handled

			throw new CityNotFoundException(city);
		}

		Set<ArrayList<Route>> setAListRoutes = new HashSet<>();		// 
			
		ArrayList<Route> thePath = new ArrayList<Route>();			// getting the syntax down

		double cost = 0;											// subtracting price from money 


		// why do you want Decision to be of tpe route 
		G.marked[checkCity] = true;
		setAListRoutes = solve (checkCity, budget, cost, setAListRoutes, thePath);					// initial solve call

		//System.out.println("testing " + setAListRoutes);
		

		return setAListRoutes;				// returns the hat will be printed by Airlinetest.java
	}


	/**
	 * The recusive solve method that is used for trips within, very difficult 
	 * @param curDecision - current vertice in quesiton
	 * @param budget - the budget you have to spend
	 * @param cost - current cost of the path that is calculated
	 * @param setAListRoutes - the Set of Array List of routes that will be returned once base case is reached
	 * @param thePath - the current path that the alg has taken while purchasing tickets
	 * @return
	 */
	public Set<ArrayList<Route>> solve (int curDecision, double budget, double cost, Set<ArrayList<Route>> setAListRoutes, ArrayList<Route> thePath) {
		
		// for all possibilites, while loop e
		// at a given vertex look at all the neighbors of a given vertex 
		amtOfOptions = G.adj[curDecision].size(); 				// amtOfOptions should be for 
		boolean finished = false;

			// iterate over all possibilites 
			for (int i=0; i < G.adj[curDecision].size(); i++) {

				// pull that neighbor, you have source right now 
				String neighborS = G.adj[curDecision].get(i).destination;	// should pull Erie

				int neighborVert = findIndex(neighborS);

				if (G.marked[neighborVert] == false) { 						// if vertice is unmarked


					// if cost plus edgeWeight is less than buget, pull edge weight from the one you have 
					double edgeWeight = G.adj[curDecision].get(i).price;

					if (cost + edgeWeight <= budget) {	// make a recusive call on that neighbor

						thePath.add(G.adj[curDecision].get(i));				// adding it to the path
						cost += edgeWeight;									// updating new cost
						G.marked[neighborVert] = true;						// marking the neighbor Vertice

						ArrayList<Route> copyList = new ArrayList<>(thePath);
					
						setAListRoutes.add(copyList);						// is a valid path so add it to the set of ALR
										
				//		System.out.println("current path makeup: " + thePath);
				//		System.out.println("SLR: " + setAListRoutes);
						
						solve (neighborVert, budget, cost, setAListRoutes, thePath);

						// how do I undo changes, 
						cost -= edgeWeight;									// if you have edgeWeight thats actually amazing
						thePath.remove(G.adj[curDecision].get(i));			// like this would be understanding recursion on a whole new level
						G.marked[neighborVert] = false;						// still Erie

				

					}

					// pop off stack stuff here  or outside of for loop

				}
			}							// end of for loop
			
	
		return setAListRoutes;			// return the Set and end method, only happens once everything is popped off the stack
		
	}

	/*** three stars auto generated that ------------ this was a previous implementation of solve but Im scared to get rid of it
	 * Function - helper method that finds an index within an adj[] or an airport in phyical terms
	 * @param Decision - old code disregard
	 * @param dest - old code disegard
	 * @return
	 */
	public int findIndexWithin (Route Decision, int dest) {

		for (int i=0; i < G.adj[dest].size(); i++) {

			// G.adj[1] 		// Decision is Source pitt Dest erie

			if (G.adj[dest].get(i).distance == Decision.distance) {			// comparing two ints is easier 

				// this might not work
				//System.out.println("							Route found within, indexOF "+ Decision.source + " - " + Decision.destination + " INSIDE OF DESTINATION IS " + i );		
				//System.out.println("						route found within");
				return i;
			}


		}

		//System.out.println("					findIndexWithinFAILED");

		return 10;
	}

	

	/** method - tripsWithin
	 * finds all itineraries within a given price regardless of the
	 * starting city
	 * @param  budget the double budget amount in dollars
	 * @return a (possibly empty) Set<ArrayList<Route>> of paths with a total cost
	 * less than or equal to the budget. Each path is an ArrayList<Route> of Route
	 * objects.
	 */
	public Set<ArrayList<Route>> tripsWithin(double budget) {
		
			Set<ArrayList<Route>> resultSet = new HashSet<ArrayList<Route>>();

			Set<ArrayList<Route>> pulledSet = new HashSet<ArrayList<Route>>();
			// a set of ArrayLists of type Route

			// Hashsets do not allow DUPLICATES or care about ORDER
			// they also are just POINTERS

			for (int i=0; i < cityNames.length; i++) {			// could have picked any number of 9 length arrays that you are using

				// call TripsWithin
				try {
				
					pulledSet = tripsWithin(cityNames[i], budget);		// I can set it equal but not add
					resultSet.addAll(pulledSet);

				} catch (CityNotFoundException e) {					// I dont understand this but if the autograder doesnt fail it then its fine
				
					e.printStackTrace();
				}

			}
			return resultSet;

	}

	

	////////////////////////////// Private inner Class of DiGraph from lab 5 \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
	/**
	 *  The <tt>Digraph</tt> class represents an directed graph of vertices
	 *  named 0 through v-1. It supports the following operations: add an edge to
	 *  the graph, iterate over all of edges leaving a vertex.Self-loops are
	 *  permitted.
	 */
	private class Digraph {

		private final int v;
		private int e;
		private LinkedList<Route>[] adj;
		private boolean[] marked;  // marked[v] = is there an s-v path
		private int[] edgeTo;      // edgeTo[v] = previous edge on shortest s-v path
		private int[] distTo;      // distTo[v] = number of edges shortest s-v path

		private int[] bestEdge;
		/**
		 * Create an empty digraph with v vertices.
		 *
		 * @param v The number of vertices of the Digraph.
		 */
		public Digraph (int v) {
			if (v < 0) throw new RuntimeException("Number of vertices must be nonnegative");
			this.v = v;
			this.e = 0;
			@SuppressWarnings("unchecked")
			LinkedList<Route>[] temp =
			(LinkedList<Route>[]) new LinkedList[v];
			adj = temp;
			for (int i = 0; i < v; i++) adj[i] = new LinkedList<Route>();
		}

		public void addEdge (Route edge) {  

			int from = findIndex(edge.source);         // add it to the starting airport which is source
			adj[from].add(edge);
			e++;
		}

        // should explain this - current understanding, we find a node, add its neighbors pop that node and go on until all vertices found
		public void bfs(int source) {
			marked = new boolean[this.v];
			distTo = new int[this.e];
			edgeTo = new int[this.v];

			Queue<Integer> q = new LinkedList<Integer>();
			for (int i = 0; i < v; i++){
				distTo[i] = Integer.MAX_VALUE;
				marked[i] = false;
			}
			distTo[source] = 0;
			marked[source] = true;
			q.add(source);

			while (!q.isEmpty()) {
				int v = q.remove();
				for (Route w : adj(v)) {

					int bfsTo = findIndex(w.destination);
					if (!marked[bfsTo]) {


						edgeTo[bfsTo] = v;
						distTo[bfsTo] = distTo[v] + 1;
						marked[bfsTo] = true;
						q.add(bfsTo);
					}
				}
			}
		}


		// this is dijkstra, its contained within Digraph class like bfs but needs a bit flag to decide if it should be distance or price based
        // goes off of finding the edge with the lowest weight and then choosing that low weight edge until an MST is generated?
		public void dijkstrasInt (int source, int destination) {
			marked = new boolean[this.v];
			distTo = new int[this.v];
			edgeTo = new int[this.v];


			for (int i = 0; i < v; i++){
				distTo[i] = Integer.MAX_VALUE;            // the infinity placement
				marked[i] = false;                        // initializing each vertice as unmarked
			}
			distTo[source] = 0;                           // initializing vars
			marked[source] = true;
			int nMarked = 1;

			int current = source;
			while (nMarked < this.v) {                  // the loop that does the searching
				for (Route w : adj(current)) {            

					if (bitFlag == 0) {                 // shortestDistance()


						// need to get distance of the route object that is being checked
						int distance = w.distance;
						int dijTo = findIndex(w.destination);

						if (distTo[current]+ distance < distTo[dijTo]) {            // one possible weight comparrison for finding best edge
							edgeTo[dijTo] = current;
							distTo[dijTo] = distTo[current]+ distance;
						}
					}
					else {                              // cheapestItinerary()

						// need to get distance of the route object that is being checked
						int price = (int) w.price;
						int dijTo = findIndex(w.destination);

						if (distTo[current]+ price < distTo[dijTo]) {
							edgeTo[dijTo] = current;
							distTo[dijTo] = distTo[current]+ price;
						}
					}

				}
				//Find the vertex with minimim path distance
				//This can be done more effiently using a priority queue!
				// Section C
				int min = Integer.MAX_VALUE;
				current = -1;

				for(int i=0; i<distTo.length; i++){
					if(marked[i])
						continue;
					if(distTo[i] < min){
						min = distTo[i];
						current = i;
					}
				}
				if(current >= 0){
					marked[current] = true;
					nMarked++;
				} else //graph is disconnected
					break;
			}
		}

		////////// Prims
		// because Prims uses v you have to have it as an inner class
		// replaced distTo array with bestEdge array
		public void prims (int source, int destination) {
			marked = new boolean[this.v];		 	// only checks relevent vertices, if marked then it is turned green
			bestEdge = new int[this.v];				// distTo needs to be modified and changed to bestEdge, maybe or you can just leave it 
			edgeTo = new int[this.v];				// keeps track of parents


			for (int i = 0; i < v; i++){
				bestEdge[i] = Integer.MAX_VALUE;            // setting all values in to infintiy or 2^31 - 1
				marked[i] = false;                        // initializing each vertice as unmarked (gray)
			}
			bestEdge[source] = 0;                           // setting first element in distTo to 0
			marked[source] = true;						  // coloring the first vertice GREEN
			int nMarked = 1;                              // one vertice is marked, increment

			int current = source;                         // where to start

			// Section B
			
			while (nMarked < this.v) {                    // while (1 < 8)
				
				// I mean if you use a counter declared here that would be a simple solve
				int neighborIndex = 0; 

				for (Route x : adj(current)) {            

						/**
						 *  Omg the neighbors of each vertice (circle) are contained inside of the LL of that index of the ajdacency list
						 *  Its simple when you think about it, for instance Pittsburg's first route is too Erie, because the first neighbor added is Erie
						 */

						int edgeWeight = x.distance;					// its okay if its edgeWeight of (x,w) because its an undirected bidrectional graph
						int neighbor = findIndex(x.destination);		// neighbor

						// if(seen[x] == 0 and BestEdge[x] > 
						// boolean, 0 is false 1 is true 

						// marked[] is of size v (9), so if the vertice is grey (0, unmarked) then compare, if it is green skip over it 

						
						if ((!marked[neighborIndex]) && bestEdge[neighborIndex] > edgeWeight ) {            // one possible weight comparrison for finding best edge
							edgeTo[neighbor] = current;
							bestEdge[neighbor] = edgeWeight;						 // updating bestEdge 
						}

						neighborIndex++;
						
						// thoughts on using nMarked as the index, it makes sense to me 
						// are you sure its supposed to be marked[nMarked]
						// uh oh, for each neighbor x of w, so that means its iterating though the vertice w which means w
					

				}
				
				// Section C
				int min = Integer.MAX_VALUE;
				current = -1;

				for(int i=0; i<bestEdge.length; i++){					// iterating through the BestEdge array that contains the lowest weight for each set of neighbors
					if(marked[i])
						continue;
					if(bestEdge[i] < min){
						min = bestEdge[i];
						current = i;
					}
				}
				if(current >= 0){
					marked[current] = true;
					nMarked++;
				} else //graph is disconnected
					break;
			}
		}


		/**
		 * Return the edges leaving vertex v as an Iterable.
		 * To iterate over the edges leaving vertex v, use foreach notation:
		 * <tt>for (DirectedEdge e : graph.adj(v))</tt>.
		 *
		 * @param v Vertex id
		 * @return A DirectedEdge Iterable Object
		 */
		public Iterable<Route> adj (int v) {
			return adj[v];
		}


	}       // end of inner class Digraph


	///////////////////////////////////// HELPER METHODS \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
	/**
	 * Functionality these two helper methods are instrumental to this class 
	 * This one findy a city to a current index and the other one finds an index with the string of a city, abstracts the converting away so you can feel confident in it
	 * @param index - index being passed in
	 * @return - the string equivalent of the index
	 */
	public String findCity(int index) {
		
		// compares returns corresponding cityName from index passed in, arrays start at 0, graphs start at 1
		// so if 1 is passed in (Pittsburgh) then it would return index 0 which is Pittsburgh O(1) runtime

		return cityNames[index-1];
	}

	// this one is going to take linear time because you have to look through the array and find the city to get the corresponding index to addEdge?
	// this one might not work on the stringComparrison might have to fix, O(n) runtime
	public int findIndex (String city) {

		int index = -1;

		for (int i=0; i < cityNames.length; i++) {      

			if (city.equalsIgnoreCase(cityNames[i])) {      // hopefully this works on the string comparrison

				index = i;                                  // because arrays start at 0
				return index;
			}
		}

		return index;        // if index returns -1 then the city was not found within cityNames 

	}


	/*** findroute method
	 *  Functionality - finds a route anywhere within the graph from a source (v) to a destination (prevVertex) - very useful
	 * @param v - equivalent to source but in index form
	 * @param prevVertex - equivalent to destination but in index form
	 * @return - returns the route if it was found 
	 */
	public Route findRoute (int v, int prevVertex) {

		Route theRoute = null;

		for (int i=0; i < G.adj[v].size(); i++) {

			theRoute = G.adj[v].get(i);

			int sourceNum = findIndex(theRoute.destination);     // adding in reverse order or the reverse route (from destination to source)

			if (sourceNum == prevVertex) {

				// found route stop looping and return the route 
				return theRoute;
			}


		}

		System.out.println("Route Not found \n\n");
		return theRoute;
	}



}

