package ru.geekbrains.homework5;

import java.util.concurrent.Semaphore;

public class Tunnel extends Stage {
    Semaphore tunnelSemaphore;

    public Tunnel(Semaphore tunnelSemaphore) {
        this.length = 80;
        this.description = "Тоннель " + length + " метров";
        this.tunnelSemaphore = tunnelSemaphore;
    }

    @Override
    public void go(Car c) {
        try {
            try {
                System.out.println(c.getName() + " готовится к этапу(ждет): " + description);
                // Взять семафор
                tunnelSemaphore.acquire();
                System.out.println(c.getName() + " начал этап: " + description);
                Thread.sleep(length / c.getSpeed() * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                System.out.println(c.getName() + " закончил этап: " + description);
                // Отпустить семафор
                tunnelSemaphore.release();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
