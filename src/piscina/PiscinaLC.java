package piscina;

import java.util.concurrent.locks.*;
import java.util.LinkedList;



public class PiscinaLC extends Piscina{
    Lock l = new ReentrantLock();
    Condition possoEntrare = l.newCondition();
    LinkedList<Thread> coda = new LinkedList<>();

    public PiscinaLC (int vasche, int limite){
        super(vasche, limite);
    }



    @Override
    protected void entra() throws InterruptedException {
        l.lock();
        try{
            coda.add(Thread.currentThread());
            while(coda.getFirst()!=Thread.currentThread() || persone == N*X) possoEntrare.await();
            coda.removeFirst();
            System.out.println("Sono entrato in piscina");
            persone++;
            int i = scegliCorsia();
            ((Persona)Thread.currentThread()).setCorsia(i);
            vasca[i]++;
            cambio();
            nuota();
        }finally {
            l.unlock();
        }
    }

    @Override
    protected void esci() throws InterruptedException {
        l.lock();
        try{
            System.out.println("Sono uscito dall'acqua");
            int i = ((Persona)Thread.currentThread()).getCorsia();
            vasca[i]--;
            persone--;
            possoEntrare.signalAll();
        }finally{
            l.unlock();
        }
    }

    public static void main(String[] args) {
        new PiscinaLC(10, 4).test(80);
    }
}
