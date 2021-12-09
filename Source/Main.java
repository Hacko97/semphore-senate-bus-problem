import java.util.Scanner;

/**
 * Main Class for the Senate Bus Problem
 *
 * Running the program
 *      javac Main.java
 *      java Main
 *
 *Stopping the simulation
 *      Press any key
 */
public class Main {

    public static void main(String[] args) throws InterruptedException {

        float rider_arrival_mean_time = 30f * 1000;
        float bus_arrival_mean_time = 20 * 60f * 1000 ;
        
        Scanner scanner = new Scanner(System.in);
        String user_input;
        WaitingArea waiting_area = new WaitingArea();

        System.out.println("\n*******  Press any key to exit.  *******\n" );

        RiderGenerator rider_generator = new RiderGenerator(rider_arrival_mean_time, waiting_area);
        (new Thread(rider_generator)).start();

        BusGenerator bus_generator = new BusGenerator( bus_arrival_mean_time ,waiting_area);
        (new Thread(bus_generator)).start();

        // Program Termination if a user inputs
        while(true){
            user_input = scanner.nextLine();
            if(user_input != null)
                System.exit(0);
        }
    }
}
