import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;

public class Philosopher implements Runnable{

    private static final long EATING_TIME = 500;
    private static final long THINKING_TIME = 500;
    private static final int TIME_DEVIATION = 300;
    private static final int EATING_NUMBER = 3;
    private int countOfEating;
    private String name;
    private AtomicBoolean leftHand;
    private AtomicBoolean rightHand;
    private CountDownLatch ctl;

    public Philosopher(String name, AtomicBoolean leftHand, AtomicBoolean rightHand, CountDownLatch ctl) {
        countOfEating = EATING_NUMBER;
        this.name = name;
        this.leftHand = leftHand;
        this.rightHand = rightHand;
        this.ctl = ctl;
    }

    @Override
    public void run() {
        while (countOfEating > 0) {
            try {
                if (rightHand.get() && leftHand.get()) {
                    eat();
                } else {
                    think();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println(name + " наелся");
        ctl.countDown();
    }

    private void eat() throws InterruptedException {
        rightHand.set(false);
        leftHand.set(false);
        System.out.printf("%s ест спагетти в %d раз\n\r", name, EATING_NUMBER - countOfEating + 1);
        Thread.sleep(EATING_TIME + new Random().nextInt(TIME_DEVIATION));
        countOfEating--;
        rightHand.set(true);
        leftHand.set(true);
    }

    private void think() throws InterruptedException {
        System.out.println(name + " думает");
        Thread.sleep(THINKING_TIME + new Random().nextInt(TIME_DEVIATION));
    }
}
