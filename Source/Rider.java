import java.util.concurrent.Semaphore;

class Rider implements Runnable {

    private final Semaphore rider_waiting_area_entrance_sem;
    private final Semaphore rider_boarding_sem;
    private final Semaphore bus_departure_sem;
    private final Semaphore mutex;
    private final int index;
    private WaitingArea waiting_area;

    public Rider(Semaphore rider_waiting_area_entrance_sem, Semaphore rider_boarding_sem, Semaphore bus_departure_sem, Semaphore mutex, int index, WaitingArea waiting_area) {
        this.rider_waiting_area_entrance_sem = rider_waiting_area_entrance_sem;
        this.rider_boarding_sem = rider_boarding_sem;
        this.bus_departure_sem = bus_departure_sem;
        this.index = index;
        this.mutex = mutex;
        this.waiting_area = waiting_area;
    }

    @Override
    public void run() {

        try {
            // allow only 50 rider at a given time
            rider_waiting_area_entrance_sem.acquire();

                // whenever rider enters increase the riders count
                mutex.acquire();
                    enter_boarding_area();
                    waiting_area.increment_riders_count();
                mutex.release();

                //acuqure semaphore
                rider_boarding_sem.acquire();
                board_bus();

            // release semaphore
            rider_waiting_area_entrance_sem.release();

            // whenever rider leave reduce the riders count
            waiting_area.decrement_riders_count();

            // when riders full allow bus to board
            if (waiting_area.get_riders_count() == 0)
                bus_departure_sem.release();
            // if riders available let them into bus
            else
                rider_boarding_sem.release();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void board_bus() {
        System.out.println("Rider :" + index + " successfully boarded!");
    }

    public void enter_boarding_area() {
        System.out.println("Rider :" + index + " successfully entered boarding area!");
    }
}