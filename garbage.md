
```Java 

     
                  // arrays start a 0, so the first entry Pittsburgh needs to be decremented, same goes for subsequent entries
          
                 // DirectedEdge pathForward = new DirectedEdge(startPoint, endPoint);		// create a new Directed Edge that has been read

                  // making it undirected with the Directed Edge implementation

                 // DirectedEdge pathBackward = new DirectedEdge(endPoint, startPoint);

                 // G.addEdge(new WeightedDirectedEdge(from-1, to-1, weight));

                  // you also didnt add any weight dummy, how are you going to add the two weights, distance, price
                 


                //  G.adj[startPoint-1].add(pathForward);						// add path to LL inside of airport in adj going forwards
  
               //   G.adj[endPoint-1].add(pathBackward);                      // adding the same path but in reverse from the other airport


               
		/**
		 * Add the edge e to this digraph.
		 *
		 * @param edge A directed edge between two existing vertices in the Digraph.
		 */

        /*
        *  fingers crossed I did this right
		public void addEdge (Route edge) {
            int from = edge.from();
			adj[from].add(edge);
			e++;
		}
        */
        

        private boolean[] marked;  // marked[v] = is there an s-v path
    private int[] edgeTo;      // edgeTo[v] = previous edge on shortest s-v path
    private int[] distTo;      // distTo[v] = number of edges shortest s-v path

    // need to get price for smallestPrice method (both use Dijkstras) // convert from double to int? 
                double price = w.price;
                int dijTo = findIndex(w.destination);

                if (distTo[current]+ price  < distTo[dijTo]) {
                    edgeTo[dijTo] = current;
                //  distTo[dijTo] = distTo[current]+ price;
                }


                 // Prims is very similar to dijkstras but dijkstras doesnt give you an MST? 
        // you can start by copying and pasting Dijkstras into method called prims, instead of using distance
        // distance[array] use bestEdge[array] conditition is not based on if the distance to current vertex, then you update the neighbors

        // if the edge wight from current vertex to neighbor is < then the bestEdge for that neighbor you update the best edge for that neighbor and the parent of that neighbor
        // Two algs Prims or Kosco's that return MST

        // return a set of MST's 
        // need to call Prims multiple times, collect spanning tree edges

        // wrapper loop that can, when it finds a vertice that is not marked(bitflag) call prims on that 
        // multiple unconnected graphs 

        // colelct MST edges from the edgeTo array - how? how can I only get the vertices that were marked by the most recent call
        // first I ran the connectedComponents alg, so you know which vertex belongs to which component
        // only collect vertices that belong to same component 
        // can simply be done by adding class or local ADT that contains previously marked vertices

        // add them to a set
        // add set into the result(return) set 

        // MST - spanning tree with the minimum sum of weights

        

        // 

        
						// if the edgeweight from current vertex to the neighbor (current distance) is < than bestEdge for that neighbor (current value stored in distTo[neighbor])
						// then update the bestEdge for that neighbor and update the parent of that neighbor


                        	// so what really needs modified here 
						//edgeWeight < distTo[neighbor]
						// culmanating distance to the current vertex + the edgeweight is less than the neighbor, update neighbors info
						/**
						if (distTo[current]+ distance < distTo[dijTo]) {            // one possible weight comparrison for finding best edge
							edgeTo[dijTo] = current;
							distTo[dijTo] = distTo[current]+ distance;

						}
						*/

     // wrapper loop is local so the ADT can be local as well 

		// use G.marked
		// how do I get source and destination I would like to use 0 and 9 but that may not be a possibility 

        //destination++;			    // resetting destination to start going through neighbors again
				// 


                // needs an if source move besides that desintation plus 1
			// if there is a source move then you just need to do source++




		//if (goodFind) {					// the reason you use goodFind is that when it starts repeatedly popping off the stack it doesnt overwrite 	

			return true;
		}
		else {
			// update changes hey this should be in solve you dummy 

			// need to check the previous route, Decision--, and remove AlistRoutes

			aListRoutes.remove(aListRoutes.size());	// removing the last Route

			// need to subract and go back until you get a goodFind again 

		
			
		}

		return goodFind;


        // does it have to initially loop, no because the recursion will take care of everything
		// starting at city, check neighbors (is that solve), vertice is the city 
		// a trip is path, once money is < budget add the route and have it fail, kind of like a DFS
		// markstart, // current decision is gonna be the next neighbor, what you increment on solve(+1, currentSol)

		/**
		 // So take me through an iteration, starts at Pittsburgh wants to make a decision on  if it can fly to Erie,
		 *  it can flies right back, mark both, checks the next decision, if it can fly to Altoona, it can flies to altoona, checks Johnstown it can adds that route 
		 *  so the decision should be a String or an index 
		 */

		 /**
		  *  So take me through an iteration, starts at Pittsburgh to Erie, is viable, adds route to ALR, flies it back, adds that to ALR,
		  	flies to altoona, is viable, adds ALR, flies to Johnstown, not viable, adds Path to Set, remove last from ALR, money will auto go back keep trying
			// when you get back to the front that is the base case that you should stop at finished = true
		  */
		// need to find the city in the diGraph (contains price) --- see if it checkCity does that for you 


        // mark the current route, but your marking the current vertice 

			

			// I dont think I need a goodFind, its not that deep 

			// when it backtracks it saves the data in ArrayList right? 


		// what if you reach the end of the neighbor search, your chillin in Reading airport,
			// I think your okay because Reading will look at the neighbors and find a route and keep going, some will go backwards 

			//Decision = findRoute(source, destination);													// that finds the current route I need the next one
			
	

		// with recursion the less complicated the better
			if (neighbors >= G.adj[destination].size()) {

				
				//finished = true;
				System.out.println("Neighbors is: " + neighbors + "	adj SOURCE is " + G.adj[source].size());
				
				
				if (neighbors < G.adj[source].size()) {				// path has failed need to check a different route at that source, which doesnt work because you should only be changing Pittsburgh source

					Decision = G.adj[source].get(neighbors);
																	// resetting neighbors to check the first route of the new source
				}
				else {
					finished = true; 				// cant even check new sources neighbors anymore

				}
				
			}
			else {
			

				
			}

				// You care if it is true or false because once it continually false checks then you know you reached the end of the recursion (base case)		
		
		// BASE case is when money < 0, 

		// else call solve with the next neighbor 
		// when money is < 0  add the Arraylist of Routes to the set of ArrayList of Routes and return false? 
		// remember recursion pops off the stack so it will automatically go back // use tail recursion! 

			else if (amtOfOptions == 0) {			// bug with the first pull for altoona - Johnsotown
					neighbors = 0;
				}


				
		// Im coming in if its a viable purchase, I make it, thereby adding route to ALR, adding the new Set to SLR, and making sure I didnt break the bank
		// if I didnt break the bank then I need to check its neighbor, I checked its neighbor purchased, checked its neihbor, AND THAT BROKE THE BANK, 
		// now what, finished is now gonna be true and its gonna repeated return true, no really backtracking but your checking only to the right 


// okay so you figured it out, you already made the purchase of Altoona to Johnstown, but thats the second route in the Linked List so 
				// you need to get the first one n = 1, could try a neighbors--, or you 
			
			

	public boolean solve (Route Decision, double budget, double money, Set<ArrayList<Route>> SetAListRoutes, ArrayList<Route> thePath) {
		

	if (G.adj[source].get(neighbors).price == tempSave.price && neighbors - 1 >= 0) {

						// Harrisburg found, go further down
						neighbors--;

					}

					if (upMarked && downMarked) {			// if both are not available then it runs an infiintie loop 

					skipSolve = true;
			}
			else {



					boolean actuallyFinished = false;
		boolean reset = false;
		boolean skipSolve = false;
	
		// this is the main comparrison, if you can afford the current route then addit to the route, and proceed from there
		if (money >= Decision.price) {

			System.out.println("making a purchase... ");
			aListRoutes.add(Decision);			// made the decision to purchase the route
			money = money - Decision.price;		// subtracting from purse

			// what if I add this as a current trip you could, would make backtracking easier
			System.out.println("you just flew from " + Decision.source + " - " + Decision.destination);
			
			
	
			SetAListRoutes.add(aListRoutes);

		}
		else {									
									
			reset = true;
			int source = findIndex(Decision.source);
			neighbors =  findIndexWithin(Decision, source);

			//G.adj[dest].get(i).distance == Decision.distance

			if (neighbors < G.adj[source].size()-1) {				// if the amount of routes in the destination airport is bigger than one, try another route!

					neighbors++;				// going up 	
					// find index of parent if the new

					
					
			}
			else if (neighbors-1 >= 0 ) {
					neighbors--;				// going down		

					if (G.adj[source].get(neighbors).price == tempSave.price && neighbors - 1 >= 0) {

						// Harrisburg found, go further down
						neighbors--;

					}
			}

			Decision = G.adj[source].get(neighbors);		// moving along the normal path 
		
		
		}

		// seeing if you broke tha BANK
		if (money <= lowestPrice && amtOfOptions == G.adj[checkCity].size()-1) {		// if its the last option at the airport backtracking is complete

			System.out.println("	BASE CASE REACHED MONEY IS LESS THAN LOWEST PRICE");
			actuallyFinished = true;
		}
		else if (money <= lowestPrice) {	// BROKE THE BANK

			reset = true; 					// exausted that option
			amtOfOptions++;					// increment the INITIAL neighbor (Pittsburgh)

			System.out.println("	Exausted this path, money <= lowestPrice");
			// reset with the neighbor of the source 
			money = budget;

			Decision.marked = true;

			// maxes out that path, go back to Pittsburgh!
			Decision = tempSave;

			
			int source = findIndex(Decision.source);					// finding index of initial city to start with a different route
			initialNeighborCount++;										// moving to the next option in the inital airport
			Decision = G.adj[source].get(initialNeighborCount);			// getting the next route
			
			// reinitializing arrayList remove everything in it 
			aListRoutes = new ArrayList<Route>();		
			
		}

		// CHECKING FOR BASE CASE 
		if (actuallyFinished) {						
			return true;
		}
		else {									// else there are still more flights to be purchased!! 
			
			int destination = findIndex(Decision.destination);			

			if (!reset) {

				// how to set Decision to be the next neighbor 
				// need to find index of where that route is stored within the destination (erie) 
				neighbors =  findIndexWithin(Decision, destination);
				
					if (neighbors < G.adj[destination].size()-1) {				// if the amount of routes in the destination airport is bigger than one, try another route!
	
						neighbors++;				// going up 
								
					
					}
					else if (neighbors-1 >= 0) {
	
						neighbors--;				// going down
					
					}

				// you dont wanna pull the route where you came from, will it always be the first 
				// assume that the route where you just came from/used is going to be the first node in that linked list 

				Decision = G.adj[destination].get(neighbors);		// moving along the normal path 
				
			}
			
			System.out.println("		Neighbors is: " + neighbors + " desintaion is: " +  G.adj[destination].size() + " Going from - " + Decision.source + " to - " + Decision.destination + "   gonna cost you: " + Decision.price);
		
			if (Decision.marked == false) {

				finished = solve (Decision, budget, money, SetAListRoutes, aListRoutes, checkCity, neighbors);				// did I only put in one recursive, gj 
			}
			// else you already did this, it is marked,  pop it off stack

			// reset money, remove ALR, 
			System.out.println("					popping off the stack" + Decision.source);
	
		}

		return finished; // once it pops off stack its going to execute this statement right here

			boolean upMarked = false;
	boolean downMarked = false;

	/*
		Route Decision = G.adj[checkCity].get(0);	// old go about it your own way your smarter code
		tempSave = Decision;
		int theNeigh = G.adj[checkCity].size();
		*/

			/*
		if (succesfullRun) {

			System.out.println("Successfull Run!");
		}
		else {
			System.out.println("Something went wrong");

		}
		*/