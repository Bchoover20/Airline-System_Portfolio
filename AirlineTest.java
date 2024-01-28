import java.util.ArrayList;
import java.util.Set;
import java.util.Scanner;
import java.io.IOException;

final public class AirlineTest {

  private AirlineInterface airline;
  private Scanner scan;
  /**
  * Test client.
  */
  public static void main(String[] args) throws IOException {
    new AirlineTest();
  }

  public AirlineTest(){
    airline = new AirlineSystem();
    scan = new Scanner(System.in);
    while(true){
      try{
        switch(menu()){
          case 1:
            readGraph();
            break;
          case 2:
            printGraph();
            break;
          case 3:
            shortestHops();
            break;
          case 4:
            shortestDistance();
            break;
          case 5:
            cheapest();
            break;
          case 6:
            cheapestWithTransit();
            break;
          case 7:
            tripsUnderFrom();
            break;
          case 8:
            tripsUnder();
            break;
          case 9:
            mst();
            break;
          case 10:
            System.exit(0);
            break;
          default:
            System.out.println("Incorrect option.");
        }
      } catch (CityNotFoundException e){
        System.out.println("City not found. Please choose from the list " +
        "and check spelling: " + e.getMessage());
      }
      catch (NullPointerException e){
        System.out.println("Null pointer exception " + e.getMessage());
      }
      System.out.print("Please press ENTER to continue ...");
      scan.nextLine();
    }
  }

  private int menu(){
    int choice = -1;
    System.out.println("*********************************");
    System.out.println("Welcome to Fifteen O'One Airlines!");
    System.out.println("1. Read routes from a file.");
    System.out.println("2. Display all routes.");
    System.out.println("3. Compute shortest path(s) based on number of stops.");
    System.out.println("4. Compute shortest path(s) based on distance.");
    System.out.println("5. Compute cheapest path.");
    System.out.println("6. Compute cheapest path with a specified transit.");
    System.out.println("7. Find all paths out of a given city and within a given budget.");
    System.out.println("8. Find all paths within a given budget.");
    System.out.println("9. Find an MST for each component.");
    System.out.println("10. Exit.");
    System.out.println("*********************************");
    while(true){
      System.out.print("Please choose a menu option (1-9): ");
      try{
        choice = Integer.parseInt(scan.nextLine());
        break;
      } catch(Exception e){
        System.out.println("Invalid input: " + e.getMessage());
      }
    }
    return choice;
  }

  private void readGraph() {
    System.out.println("Please enter filename:");
    String fileName = scan.nextLine();
    if(airline.loadRoutes(fileName)){
      System.out.println("Data imported successfully.");
    } else {
      System.out.println("Error importing data from " + fileName);
    }
  }

  private void printGraph() throws CityNotFoundException {
    for(String city: airline.retrieveCityNames()){
      System.out.print(city + ": ");
      for (Route r : airline.retrieveDirectRoutesFrom(city)) {
        System.out.print(r.destination + "(" + r.distance + " miles, $"
        + r.price + ") ");
      }
      System.out.println();
    }
  }

  private void shortestHops() throws CityNotFoundException {
    for(String city : airline.retrieveCityNames()){
      System.out.println(city);
    }
    System.out.print("Please enter source city: ");
    String source = scan.nextLine();
    System.out.print("Please enter destination city: ");
    String destination = scan.nextLine();

    Set<ArrayList<String>> shortestSet =
    airline.fewestStopsItinerary(source, destination);
    System.out.println("Found " + shortestSet.size() + " path(s):");
    for(ArrayList<String> shortest : shortestSet){
      System.out.print("The shortest path from " + source +
      " to " + destination + " has " +
      (shortest.size()-2) + " stop(s): ");
      for(String r : shortest){
        System.out.print(r + " ");
      }
      System.out.println();
    }
  }

  private void shortestDistance() throws CityNotFoundException {
    for(String city : airline.retrieveCityNames()){
      System.out.println(city);
    }
    System.out.print("Please enter source city: ");
    String source = scan.nextLine();
    System.out.print("Please enter destination city: ");
    String destination = scan.nextLine();

    Set<ArrayList<Route>> shortestSet =
    airline.shortestDistanceItinerary(source, destination);
    System.out.println("Found " + shortestSet.size() + " path(s):");
    for(ArrayList<Route> shortest : shortestSet){
      int totalDistance = 0;
      for(Route r : shortest){
        totalDistance += r.distance;
      }
      System.out.print("The shortest path from " + source +
      " to " + destination + " has " +
      totalDistance + " miles: ");
      System.out.print(source);
      for(Route r : shortest){
        System.out.print(" " + r.distance + " " + r.destination);
      }
      System.out.println();
    }
  }

 private void cheapest() throws CityNotFoundException {
      for(String city : airline.retrieveCityNames()){
        System.out.println(city);
      }
      System.out.print("Please enter source city: ");
      String source = scan.nextLine();
      System.out.print("Please enter destination city: ");
      String destination = scan.nextLine();

      Set<ArrayList<Route>> shortestSet = airline.cheapestItinerary(source, destination);
      System.out.println("Found " + shortestSet.size() + " path(s):");
      for(ArrayList<Route> shortest : shortestSet){
        double totalPrice = 0;
        for(Route r : shortest){
          totalPrice += r.price;
        }
        System.out.print("The cheapest itinerary from " + source +
        " to " + destination + " costs " +
        totalPrice + " dollars: ");
        System.out.print(source);
        for(Route r : shortest){
          System.out.print(" " + r.price + " " + r.destination);
        }
        System.out.println();
      }
  }

  private void cheapestWithTransit() throws CityNotFoundException {
      for(String city : airline.retrieveCityNames()){
        System.out.println(city);
      }
      System.out.print("Please enter source city: ");
      String source = scan.nextLine();
      System.out.print("Please enter destination city: ");
      String destination = scan.nextLine();
      System.out.print("Please enter transit city: ");
      String transit = scan.nextLine();

      Set<ArrayList<Route>> shortestSet =
        airline.cheapestItinerary(source, transit, destination);
      System.out.println("Found " + shortestSet.size() + " path(s):");
      for(ArrayList<Route> shortest : shortestSet){
        double totalPrice = 0;
        for(Route r : shortest){
          totalPrice += r.price;
        }
        System.out.print("The cheapest itinerary from " + source +
        " to " + destination + " costs " +
        totalPrice + " dollars: ");
        System.out.print(source);
        for(Route r : shortest){
          System.out.print(" " + r.price + " " + r.destination);
        }
        System.out.println();
      }
  }

  private void tripsUnderFrom() throws CityNotFoundException {
      System.out.print("Please enter source city: ");
      String source = scan.nextLine();
      double targetPrice = 0.0;
      while(true){
        try{
          System.out.print("Please enter your target budget in dollars: ");
          targetPrice = Double.parseDouble(scan.nextLine());
          break;
        } catch(Exception e){
          System.out.println("Invalid input: " + e.getMessage());
        }
      }
      Set<ArrayList<Route>> result = airline.tripsWithin(source, targetPrice);
      System.out.println("Found " + result.size() + " path(s):");

      for(ArrayList<Route> trip : result){
        double totalPrice = 0;
        for(Route r : trip){
          totalPrice += r.price;
        }
        System.out.print("Cost " + totalPrice + " dollars: ");
        System.out.print(trip.get(0).source + " ");
        for(Route r : trip){
          System.out.print(r.price + " " + r.destination);
        }
        System.out.println();
      }
  }

  private void tripsUnder() {
      double targetPrice = 0.0;
      while(true){
        try{
          System.out.print("Please enter your target budget in dollars: ");
          targetPrice = Double.parseDouble(scan.nextLine());
          break;
        } catch(Exception e){
          System.out.println("Invalid input: " + e.getMessage());
        }
      }
      Set<ArrayList<Route>> result = airline.tripsWithin(targetPrice);
      System.out.println("Found " + result.size() + " path(s):");
      for(ArrayList<Route> trip : result){
        double totalPrice = 0;
        for(Route r : trip){
          totalPrice += r.price;
        }
        System.out.print("Cost " +
        totalPrice + " dollars: ");
        System.out.print(trip.get(0).source + " ");
        for(Route r : trip){
          System.out.print(r.price + " " + r.destination);
        }
        System.out.println();
      }
  }

  private void mst() {
      Set<Set<Route>> msts = airline.getMSTs();
      int i = 1;
      System.out.println("Found " + msts.size() + " tree(s):");
      for(Set<Route> mst : msts){
        int totalDistance = 0;
        for(Route r : mst){
          totalDistance += r.distance;
        }

        System.out.println("MST #" + i + " (" + totalDistance + " miles):");
        for(Route r : mst){
          System.out.println(r.source + ", " + r.destination + ": " + r.distance);
        }
        i++;
      }
  }

}
