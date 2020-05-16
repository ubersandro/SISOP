package threading.semafori;

import java.util.concurrent.Semaphore;

public class AAB {
    private static Semaphore a = new Semaphore(2);
    private static Semaphore b = new Semaphore(0);

    private static class A extends Thread{

        @Override
        public void run() {

                try{
                        a.acquire();
                        System.out.print("A");
                        b.release();

                }catch(InterruptedException e){}

            }

    }

    private static class B extends Thread{
        @Override
        public void run() {

                try {
                    b.acquire(2);
                    System.out.print("B \n");
                    a.release(2);
                } catch (InterruptedException e) {
                }
            }

    }


    public static void main(String[] args) throws InterruptedException {
        while(true) {
            new A().start(); new B().start();
            Thread.sleep(500);
        }
    }

}
