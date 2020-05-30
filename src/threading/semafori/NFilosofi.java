package threading.semafori;


import java.util.concurrent.Semaphore;
enum State {HUNGRY, EATING, THINKING }

public class NFilosofi {

    public static void main(String[] args) {
        int N = 3;
        Tavolo t = new Tavolo(N);
        Thread[] a = new ThreadFilosofo[N];
        for(int i=0; i<N; ++i ){
            (a[i] = new ThreadFilosofo(i,t)).start();
        }//for
    }//main

}
class ThreadFilosofo extends Thread{
    private int id;
    Tavolo t;
    public ThreadFilosofo(int id, Tavolo t){ this.id = id; this.t=t;}

    public void run(){
        while(true){
            try {
                t.prendiBacchetta(id);
                //System.out.printf("Il filosofo %d sta mangiando\n", id);
                Thread.sleep(2000);
                t.rilasciaBacchetta(id);
                //System.out.printf("Il filosofo %d sta pensando...\n", id );
                Thread.sleep(2000);
            }catch(InterruptedException e ){}
        }//while
    }//run
}//ThreadFilosofo

class Tavolo{
    private Semaphore mutex;
    private Semaphore[] bacchetta;
    private State[] stato;
    private int n;
    public Tavolo(int n){
        bacchetta = new Semaphore[n];
        for(int i=0; i<n; ++i) bacchetta[i]= new Semaphore(0);
        stato = new State[n];
        for(int i=0; i<n; ++i) stato[i] = State.THINKING;
        this.n = n;
        mutex = new Semaphore(1, true);
    }

    public void prendiBacchetta(int id) throws InterruptedException{
        mutex.acquire();//per verificare stati cazzi e mazzi
        stato[id] = State.HUNGRY;//vuole mangiare
        test(id);
        mutex.release();
        bacchetta[id].acquire();//se non ha mangiato aspetta una signal dagli altri thread
    }

    public void rilasciaBacchetta(int id) throws InterruptedException{
        System.out.printf("Il filosofo %d sta pensando...\n", id );
        mutex.acquire();
        stato[id] = State.THINKING;//ha finito di mangiare
        test((id-1+bacchetta.length)%bacchetta.length); test((id+1)%bacchetta.length);//signal su quelli a fianco
        mutex.release();
    }

    private void test(int id) throws InterruptedException{
        if(stato[(id+1)%n]!=State.EATING && stato[(id+5)%n]!=State.EATING && stato[id]==State.HUNGRY){
            stato[id] = State.EATING;
            //System.out.printf("Filosofo %d mangia\n", id);
            bacchetta[id].release();//per segnalare che ha mangiato
            System.out.printf("Il filosofo %d sta mangiando...\n", id );
        }//if
    }
}