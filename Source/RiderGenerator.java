import java.util.Random;

public class RiderGenerator implements Runnable {

    private float arrival_mean_time;
    private WaitingArea waiting_area;
    private static Random random;

    public RiderGenerator(float arrival_mean_time, WaitingArea waiting_area) {
        this.arrival_mean_time = arrival_mean_time;
        this.waiting_area = waiting_area;
        random = new Random();
    }

    @Override
    public void run() {

        int rider_index = 1;
        
        while (!Thread.currentThread().isInterrupted()) {

            try {
                // start the rider threads
                Rider rider = new Rider(waiting_area.get_rider_waiting_area_entrance_sem(), waiting_area.get_rider_boarding_area_entrance_sem(), waiting_area.get_bus_departure_sem(), waiting_area.get_mutex(), rider_index, waiting_area);
                (new Thread(rider)).start();

                rider_index++;
                // make Sleep thread to obtain the inter arrival time between the threads
                Thread.sleep(get_exponentially_distributed_rider_inter_arrival_time());

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public long get_exponentially_distributed_rider_inter_arrival_time() {
        float lambda = 1 / arrival_mean_time;
        return Math.round(-Math.log(1 - random.nextFloat()) / lambda);
    }
}
