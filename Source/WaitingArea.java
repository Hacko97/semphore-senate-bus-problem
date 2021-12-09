import java.util.concurrent.Semaphore;
public class WaitingArea {

    private static int riders_count = 0;
    private static int maximum_bus_capacity = 50;

 
    private static final Semaphore rider_waiting_area_entrance_sem = new Semaphore(maximum_bus_capacity);

    // Semaphore for riders to get into the boarding area
    private static final Semaphore rider_boarding_area_entrance_sem = new Semaphore(0);

    // Semaphore for bus to depart
    private static final Semaphore bus_departure_sem = new Semaphore(0);

    // Semaphore for riders count variable
    private static final Semaphore mutex = new Semaphore(1);

    //get the riders count
    public int get_riders_count() {
        return riders_count;
    }

    //increment the riders count
    public void increment_riders_count() {
        riders_count++;
    }

    //decrement the riders count
    public void decrement_riders_count() {
        riders_count--;
    }

    //access semaphore that is used for riders to enter the waiting area
    public static Semaphore get_rider_waiting_area_entrance_sem() {
        return rider_waiting_area_entrance_sem;
    }

    //access semaphore that is used for riders to board the bus
    public static Semaphore get_rider_boarding_area_entrance_sem() {
        return rider_boarding_area_entrance_sem;
    }

    //access semaphore that is used for the bus to depart
    public static Semaphore get_bus_departure_sem() {
        return bus_departure_sem;
    }

    //access semaphore that is used for handle access to the riders count
    public static Semaphore get_mutex() {
        return mutex;
    }
}
