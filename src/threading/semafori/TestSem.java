package threading.semafori;
import java.util.concurrent.Semaphore;

public class TestSem {
    public static void main(String[] args) {
        Semaphore s = new Semaphore(0);
        Thread t = new Thread(()->{
            System.out.println("Uno");
            s.release();
        });
        Thread x = new Thread(()->{
           try {
               s.acquire();
           }catch (InterruptedException  e){}
            System.out.println("PORCODIOPORCODIO");
           s.release();
        });
        x.start(); t.start();
        System.out.println(s.toString());
    }
}
