package ru.geekbrains.homework5;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.atomic.AtomicBoolean;

public class Car implements Runnable {
    private static int CARS_COUNT;

    static {
        CARS_COUNT = 0;
    }

    private Race race;
    private int speed;
    private CyclicBarrier startFlag;
    private CountDownLatch checkeredFlag;
    private AtomicBoolean isWinner;

    private String name;

    public String getName() {
        return name;
    }

    public int getSpeed() {
        return speed;
    }

    public Car(Race race, int speed, CyclicBarrier startFlag, CountDownLatch checkeredFlag, AtomicBoolean isWinner) {
        this.race = race;
        this.speed = speed;
        this.startFlag = startFlag;
        this.checkeredFlag = checkeredFlag;
        this.isWinner = isWinner;
        CARS_COUNT++;
        this.name = "Участник #" + CARS_COUNT;
    }

    @Override
    public void run() {
        try {
            System.out.println(this.name + " готовится");
            Thread.sleep(500 + (int)(Math.random() * 800));
            System.out.println(this.name + " готов");
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            startFlag.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < race.getStages().size(); i++) {
            race.getStages().get(i).go(this);
        }
        // Проверить, есть ли победитель, если нет, то стать победителем.
        if(isWinner.compareAndSet(false, true))
            System.out.println(this.name + " - WIN");
        checkeredFlag.countDown();
    }
}

