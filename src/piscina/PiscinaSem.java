package piscina;

import java.util.concurrent.*;

public class PiscinaSem extends Piscina{
    private Semaphore mutex = new Semaphore(1,true);
    private Semaphore posto;
    //private Semaphore[] corsia;



    public PiscinaSem(int vasche, int limite){
        super(vasche, limite);
        posto = new Semaphore(N*X,true);
//        corsia = new Semaphore[N];
//        for(int i=0; i<N; ++i) corsia[i] = new Semaphore(1);
    }

    @Override
    protected void entra() throws InterruptedException {
        posto.acquire();
        int i = scegliCorsia();

        mutex.acquire();
        vasca[i]++;
        mutex.release();

        cambio();
        ((Persona)Thread.currentThread()).setCorsia(i);
        System.out.println("Mi sono cambiato");
        nuota();

    }

    @Override
    protected void esci() throws InterruptedException {
        mutex.acquire();
        vasca[((Persona) Thread.currentThread()).getCorsia()]--;
        mutex.release();
        posto.release();
        System.out.println("Sono uscito dalla piscina");
        doccia();
    }

    public static void main(String[] args) {
        new PiscinaSem(10,4).test(80);
    }

}
