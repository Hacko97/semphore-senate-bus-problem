import java.util.concurrent.Semaphore;

/**
 * class for the bus threads
 */
public class Bus implements Runnable {

    private final Semaphore rider_boarding_sem;
    private final Semaphore bus_departure_sem;
    private final Semaphore mutex;
    private final int index;
    private WaitingArea waiting_area;


    public Bus(Semaphore rider_boarding_sem, Semaphore bus_departure_sem, Semaphore mutex, int index, WaitingArea waiting_area) {
        this.rider_boarding_sem = rider_boarding_sem;
        this.bus_departure_sem = bus_departure_sem;
        this.mutex = mutex;
        this.index = index;
        this.waiting_area = waiting_area;
    }

    @Override
    public void run() {

        try {
            mutex.acquire();
                
                arrived(); // Bus is arrived

                // Check for waiting riders 
                if (waiting_area.get_riders_count() > 0) {

                    
                    rider_boarding_sem.release();// Release the rider boarding semaphore allowing a rider to board the bus
                    
                    bus_departure_sem.acquire();// Acquire the bus departure semaphore to wait for the bus until the riders board
                }
            mutex.release();

           
            depart(); //depart the bus
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void depart() {
        System.out.println("==== Bus : " + index + " successfully departed!");
    }

    public void arrived() {
        System.out.println("==== Bus : " + index + " successfully arrived!");
        System.out.println("==== Waiting rider count : " + waiting_area.get_riders_count());
    }
}