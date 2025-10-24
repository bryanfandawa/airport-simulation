package airportSim;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.LinkedList;
import java.util.Queue;

public class airportSimBetter {
	private static int takeoff_duration;
	private static int landing_duration;
	private static double departure_rate;
	private static double arrival_rate;
	private static int reserve_fuel_time;
	private static int simulation_time;
	private static String description;
	
    static int current_time = 0;
    static boolean runway_occupied = false;
    static int planes_departed = 0;
    static int planes_landed = 0;
    static int planes_crashed = 0;
    static Plane planeOnRunway = null;
	static Plane planeOnRunwayOne = null;
	static Plane planeOnRunwayTwo = null;
    static int totalDepartWait = 0;
    static int totalArriveWait = 0;
	static Queue<Plane> landingQueue = new LinkedList<Plane>();
	static Queue<Plane> takeoffQueue = new LinkedList<Plane>();
	
    public static void addPlaneToQueue(Queue<Plane> landingQueue, Queue<Plane> takeoffQueue) {
        if (Math.random() < arrival_rate) {
            landingQueue.add(new Plane(current_time, false, reserve_fuel_time));
        }
        if (Math.random() < departure_rate) {
            takeoffQueue.add(new Plane(current_time, true));
        }
    }
    
    public static void checkPlaneDeparting() {
    	if (runway_occupied && planeOnRunway.isDeparting()) {
    		if (current_time - planeOnRunway.out >= takeoff_duration) {
    			runway_occupied = false;
    		}
    	}
    	if (runway_occupied && !planeOnRunway.isDeparting()) {
    		if (current_time - planeOnRunway.out >= landing_duration) {
    			runway_occupied = false;
    		}
    	}
    }
    
    public static void doesPlaneCrash(Queue<Plane> landingQueue) {
    	while(!landingQueue.isEmpty() && landingQueue.peek().fuel < current_time - landingQueue.peek().in) {
    		planes_crashed++;
    		landingQueue.remove();
    	}
    }
    
    public static void landPeople(Queue<Plane> landingQueue) {
		if ((!landingQueue.isEmpty()) && (!runway_occupied)) {
			planeOnRunway = landingQueue.remove();
			planes_landed++;
			runway_occupied = true;
			planeOnRunway.out = current_time;
			totalArriveWait += (planeOnRunway.out - planeOnRunway.in);
		}
    }
    
    public static void departPeople(Queue<Plane> takeoffQueue) {
		if ((!takeoffQueue.isEmpty()) && (!runway_occupied)) {
			planeOnRunway = takeoffQueue.remove();
			planes_departed++;
			runway_occupied = true;
			planeOnRunway.out = current_time;
			totalDepartWait += (planeOnRunway.out - planeOnRunway.in);
		}
    }
    
    public static void addMinute() {
    	current_time++;
    }
    //checks the runway whether the plane has completed the takeoff duration that it could be removed from the runway
    // so that the runway can be used for other planes.
    //when runway is true, it means there is atleast one runway that is available.
    public static void checkDepartingDoneTwo() {
		if(runway_occupied && (planeOnRunwayOne.isDeparting() || planeOnRunwayTwo.isDeparting())) {
			if(planeOnRunwayOne.isDeparting() && current_time - planeOnRunwayOne.out >= takeoff_duration) {
				runway_occupied = false;
				planeOnRunwayOne = null;
			}
			if(planeOnRunwayTwo.isDeparting() && current_time - planeOnRunwayTwo.out >= takeoff_duration) {
				runway_occupied = false;
				planeOnRunwayTwo = null;
			}
		}
    }
    
    
    //checks the runway whether the plane has completed the landing duration that it could be removed from the runway
    // so that the runway can be used for other planes.
    public static void checkTakeoffDoneTwo() {
		if(runway_occupied && ((!planeOnRunwayOne.isDeparting()) || !(planeOnRunwayTwo.isDeparting()))) {
			if((!planeOnRunwayOne.isDeparting()) && current_time - planeOnRunwayOne.out >= landing_duration) {
				runway_occupied = false;
				planeOnRunwayOne = null;
			}
			if((!planeOnRunwayTwo.isDeparting()) && current_time - planeOnRunwayTwo.out >= landing_duration) {
				runway_occupied = false;
				planeOnRunwayTwo = null;
			}
		}
    }
    
    //this code see if there is a runway available so a plane can be placed in either or both runways to land
    public static void checkLandingTwo(Queue<Plane> landingQueue) {
		if((!runway_occupied) && (!landingQueue.isEmpty())) {
			if(planeOnRunwayOne == null) {
				planeOnRunwayOne = landingQueue.remove();
				planeOnRunwayOne.out = current_time;
				planes_landed++;
				totalArriveWait += (planeOnRunwayOne.out - planeOnRunwayOne.in);
			}
			if(planeOnRunwayTwo == null && (!landingQueue.isEmpty())) {
				planeOnRunwayTwo = landingQueue.remove();
				planeOnRunwayTwo.out = current_time;
				planes_landed++;
				totalArriveWait += (planeOnRunwayTwo.out - planeOnRunwayTwo.in);
			}
			if(planeOnRunwayOne != null && planeOnRunwayTwo != null) {
				runway_occupied = true;
			}
		}
    }
    
    //this code see if there is a runway available so a plane can be placed in either or both runways to takeoff.
    //if both runways are occupied then mark runway_occupied to inform that there is no runway available at all. runway_occupied
    // false when not even one runway is available. 
    public static void checkTakeoffTwo(Queue<Plane> takeoffQueue) {
		if((!runway_occupied) && (!takeoffQueue.isEmpty())) {
			if(planeOnRunwayOne == null) {
				planeOnRunwayOne = takeoffQueue.remove();
				planeOnRunwayOne.out = current_time;
				planes_departed++;
				totalDepartWait += (planeOnRunwayOne.out - planeOnRunwayOne.in);
			}
			if(planeOnRunwayTwo == null && (!takeoffQueue.isEmpty())) {
				planeOnRunwayTwo = takeoffQueue.remove();
				planeOnRunwayTwo.out = current_time;
				planes_departed++;
				totalDepartWait += (planeOnRunwayTwo.out - planeOnRunwayTwo.in);
			}
			if(planeOnRunwayOne != null && planeOnRunwayTwo != null) {
				runway_occupied = true;
			}
		}
    }
    
	
	public static void main(String[] args) throws FileNotFoundException {
		File file = new File("busier.data");
		Scanner sc = new Scanner(file);
	    sc.useLocale(java.util.Locale.US);
		takeoff_duration = sc.nextInt();
		landing_duration = sc.nextInt();
		departure_rate = sc.nextDouble();
		arrival_rate = sc.nextDouble();
		reserve_fuel_time = sc.nextInt();
		simulation_time = sc.nextInt();
		sc.nextLine();
		description = sc.nextLine();
		
		while(current_time <= simulation_time) {
			addPlaneToQueue(landingQueue, takeoffQueue);
			checkPlaneDeparting();
			doesPlaneCrash(landingQueue);
			landPeople(landingQueue);
			departPeople(takeoffQueue);
			addMinute();	
		}
		System.out.println("Description: " + description);
		System.out.println();
		System.out.println("Number of planes departed: " + planes_departed);
		System.out.println("Average of wait time for departing planes: " + (double) (totalDepartWait) / (planes_departed) + "minutes");
		System.out.println("Number of planes landed: " + planes_landed);
		System.out.println("Average of wait time to landing planes: " + (double) (totalArriveWait) / (planes_landed) + "minutes");
		System.out.println("Number of planes crashed: " + planes_crashed);
		System.out.println();
		
		runway_occupied = false; //global checker if both runway is in use
		planes_departed = 0;
		planes_landed = 0;
		planes_crashed = 0;
		totalDepartWait = 0;
		totalArriveWait = 0;
		landingQueue.clear();
		takeoffQueue.clear();
		current_time = 0;
		
		while(current_time <= simulation_time) {
			addPlaneToQueue(landingQueue, takeoffQueue);
			checkDepartingDoneTwo();
			checkTakeoffDoneTwo();
			doesPlaneCrash(landingQueue);
			checkLandingTwo(landingQueue);
			checkTakeoffTwo(takeoffQueue);
			addMinute();	
		}
		
		System.out.println("Results of two runways: ");
		System.out.println("Number of planes departed: " + planes_departed);
		System.out.println("Average of wait time for departing planes: " + (double) (totalDepartWait) / (planes_departed) + "minutes");
		System.out.println("Number of planes landed: " + planes_landed);
		System.out.println("Average of wait time to landing planes: " + (double) (totalArriveWait) / (planes_landed) + "minutes");
		System.out.println("Number of planes crashed: " + planes_crashed);
		System.out.println();
		
		
		
		runway_occupied = false; //global checker
		planes_departed = 0;
		planes_landed = 0;
		planes_crashed = 0;
		totalDepartWait = 0;
		totalArriveWait = 0;
		landingQueue.clear();
		takeoffQueue.clear();
		current_time = 0;
		
		while(current_time <= simulation_time) {
			addPlaneToQueue(landingQueue, takeoffQueue);
			checkPlaneDeparting();
			doesPlaneCrash(landingQueue);
			double ran = Math.random();
			if (ran < 0.6666667) {
				landPeople(landingQueue);
			}
			departPeople(takeoffQueue);
			landPeople(landingQueue);
			addMinute();
		}

		
		System.out.println("Results of probability 0.6666667 on arriving planes: ");
		System.out.println("Number of planes departed: " + planes_departed);
		System.out.println("Average of wait time for departing planes: " + (double) (totalDepartWait) / (planes_departed) + "minutes");
		System.out.println("Number of planes landed: " + planes_landed);
		System.out.println("Average of wait time to landing planes: " + (double) (totalArriveWait) / (planes_landed) + "minutes");
		System.out.println("Number of planes crashed: " + planes_crashed);
		System.out.println();
		
		runway_occupied = false;
		planes_departed = 0;
		planes_landed = 0;
		planes_crashed = 0;
		totalDepartWait = 0;
		totalArriveWait = 0;
		landingQueue.clear();
		takeoffQueue.clear();
		current_time = 0;
		planeOnRunwayOne = null;
		planeOnRunwayTwo = null;
		
		while (current_time <= simulation_time) {
			addPlaneToQueue(landingQueue, takeoffQueue);
			checkDepartingDoneTwo();
			checkTakeoffDoneTwo();
			doesPlaneCrash(landingQueue);
			double rand = Math.random();
			if (rand < 0.6666667) {
				checkLandingTwo(landingQueue);
			}
			checkTakeoffTwo(takeoffQueue);
			checkLandingTwo(landingQueue);
			addMinute();
		}
		System.out.println("Results of two runways with p = 0.6666667 for landing: ");
		System.out.println("Number of planes departed: " + planes_departed);
		System.out.println("Average of wait time for departing planes: " + (double) (totalDepartWait) / (planes_departed) + "minutes");
		System.out.println("Number of planes landed: " + planes_landed);
		System.out.println("Average of wait time to landing planes: " + (double) (totalArriveWait) / (planes_landed) + "minutes");
		System.out.println("Number of planes crashed: " + planes_crashed);
		System.out.println();
	}
}
