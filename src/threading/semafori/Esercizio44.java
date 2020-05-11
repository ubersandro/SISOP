package threading.semafori;

import java.util.concurrent.Semaphore;

public class Esercizio44 {

    public static void main(String[] args) {
        Semaphore f = new Semaphore(1);
        Runnable r = new Runnable() {
            @Override
            public void run() {

                    try {
                        f.acquire();
                    } catch (InterruptedException e) {
                    }
                    System.out.print("B ");
                    f.release();
                }

        };
        Runnable s = new Runnable(){
            public void run(){

                    try {
                        f.acquire();
                    } catch (InterruptedException e) {
                    }
                    System.out.print("A ");
                    f.release();

            }//run
        };
        Thread a,b;
        while(true) {
            a = new Thread(s);
            b = new Thread(r);
            a.start();
            b.start();
        }
    }//main

}
