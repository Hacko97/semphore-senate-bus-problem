import java.util.Random;

/**
 * Class to spawn the threads for the bus arrival
 */
public class BusGenerator implements Runnable {

    private float arrival_mean_time;
    private WaitingArea waiting_area;
    private static Random random;

    public BusGenerator(float arrival_mean_time, WaitingArea waiting_area) {
        this.arrival_mean_time = arrival_mean_time;
        this.waiting_area = waiting_area;
        random = new Random();
    }

    @Override
    public void run() {

        int bus_index = 1;

       // Generation of bus threads for the value specified by the user
        while (!Thread.currentThread().isInterrupted()) {

            try {
                // Initialized and started the bus threads
                Bus bus = new Bus(waiting_area.get_rider_boarding_area_entrance_sem(), waiting_area.get_bus_departure_sem(), waiting_area.get_mutex(), bus_index, waiting_area);
                (new Thread(bus)).start();

                bus_index++;
                // Sleep the thread to obtain the inter-arrival time between the threads of the bus
                Thread.sleep(get_exponentially_distributed_bus_inter_arrival_time());

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("All buses have finished arriving");
    }

    // Method to get the exponentially distributed bus inter arrival time
    public long get_exponentially_distributed_bus_inter_arrival_time() {
        float lambda = 1 / arrival_mean_time;
        return Math.round(-Math.log(1 - random.nextFloat()) / lambda);
    }
}