package threading.semafori;
import java.util.concurrent.Semaphore;

public class Prova extends Thread {
    private String x;
    private Semaphore s= new Semaphore(1);

    public static void main(String[] args) {
        Prova A = new Prova("Porco");
        Prova B = new Prova("Dio");
        B.start();
        A.start();

    }

    public Prova(String s){
        x=s;
    }

    public void run(){
        try {
            s.acquire();
        }catch (InterruptedException e){}
        System.out.println(x);
        s.release();
    }
}
