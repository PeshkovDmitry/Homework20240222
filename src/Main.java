import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;

public class Main {

    private static final int NUMBER_OF_PHILOSOPHERS = 5;

    public static void main(String[] args) throws InterruptedException {

        CountDownLatch ctl = new CountDownLatch(NUMBER_OF_PHILOSOPHERS);

        AtomicBoolean[] forks = new AtomicBoolean[NUMBER_OF_PHILOSOPHERS];
        for (int i = 0; i < forks.length; i++) {
            forks[i] = new AtomicBoolean(true);
        }

        Philosopher[] philosophers = new Philosopher[NUMBER_OF_PHILOSOPHERS];
        for (int i = 0; i < philosophers.length; i++) {
            philosophers[i] = new Philosopher(
                    "Философ " + i,
                    forks[i],
                    forks[(i != philosophers.length - 1) ? i + 1 : 0],
                    ctl);
        }

        for (int i = 0; i < philosophers.length; i++) {
            new Thread(philosophers[i]).start();
        }

        ctl.await();
        System.out.println("Все наелись");




    }
}
