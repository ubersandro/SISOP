package threading.semafori;

import java.util.LinkedList;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.Semaphore;

public class Semaforo {
    private final int MAX_PERMITS;
    private int counter;
    private LinkedList<Thread> queue;
    private boolean fair;
    private Semaphore queueSemaphore = new Semaphore(1);


    public Semaforo(int permits) {
        MAX_PERMITS = counter = permits;
        queue = new LinkedList<>();
    }

    public Semaforo(int permits, boolean fairness) {
        this(permits);
        fair = fairness;
    }

    public void P() throws InterruptedException {
        if (--counter < 0) {
            //try {
            queueSemaphore.acquire();
            Thread c = Thread.currentThread();
            c.wait();
            queue.addLast(c);
            //}catch (InterruptedException e){}
            queueSemaphore.release();
        }//if
    }//P


    public void V() {
        if (++counter > 0) {
            if(queue.size()>0){
                try {
                queueSemaphore.acquire();
                Thread c;

                if (fair) c = queue.removeFirst();
                else c = queue.remove(new Random().nextInt(MAX_PERMITS));//RANDOM, si poteva usare size di queue

                c.notify();
            } catch (InterruptedException e) {}
                queueSemaphore.release();
            }

        }//if
    }//V

    private class Test extends Thread {
        String stampa;
        Semaforo s = new Semaforo(1);

        public Test(String s) {
            stampa = s;
        }

        public void run() {
            while (true) {
                try {
                    s.P();
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                }
                System.out.println(stampa);

                s.V();
            }
        }

    }

    public static void main(String[] args) throws InterruptedException {
        /*Thread one = new Thread("ONE");
        Thread two = new Thread("TWO");
        Thread three = new Thread("ONE");
        one.start(); two.start(); three.start();
        LinkedList<Thread> a = new LinkedList<>();
        a.add(one);
        a.add(two);
        a.add(three);

        a.forEach((t) -> {
            try {
                t.join();
            } catch (InterruptedException e) {
            }
        });
        Scanner sc;
        int x;
        do {
            sc = new Scanner(System.in);
            x = sc.nextInt();
        } while (x != 0);
        a.forEach((t) -> {
            t.interrupt();
        });
        System.out.println("BYE");*/
        Semaforo s = new Semaforo(1, true);
        Thread t = new Thread(()->{
            try{
                s.P();
            } catch(InterruptedException e ){ e.printStackTrace();}
            System.out.println("ORCODIO");
            s.V();
        });
        Thread x = new Thread(()->{
            try{
                s.P();
            } catch(InterruptedException e ){ e.printStackTrace();}
            System.out.println("E la madonna");
            s.V();
        });
        t.start(); x.start();
        t.join(); x.join();
    }

}
