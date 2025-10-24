package airportSim;

public class Plane {
	int in; //time in the queue
	int out; //time out of the queue (landing/takeoff)
	int fuel;
	boolean departing;
	
	public Plane() {
		in = 0;
		out = 0;
	}
	
	public Plane (int time, boolean depart) {
		in = time;
		departing = depart;
	}
	
	public Plane (int time, boolean depart, int f) {
		in = time;
		fuel = f;
		departing = depart;
	}
	
	public boolean isDeparting() {
		return departing;
	}
	
}
