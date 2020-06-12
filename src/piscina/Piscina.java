package piscina;

import java.util.Random;

public abstract class Piscina {
    protected int persone;
    protected int N, X;
    protected int[] vasca;
    protected static final int MIN_CAMBIO = 3, MAX_CAMBIO = 5, PERMANENZA = 45, MIN_DOCCIA=10, MAX_DOCCIA = 15;


    public Piscina(int corsie, int limite){
        N = corsie;
        X = limite;
        vasca = new int [N];
    }

    protected abstract void entra() throws InterruptedException;
    protected abstract void esci() throws InterruptedException;

    protected void cambio() throws InterruptedException{
        Thread.sleep(new Random().nextInt(MAX_CAMBIO-MIN_CAMBIO +1)+MIN_CAMBIO);
    }

    protected void doccia() throws InterruptedException{
        Thread.sleep(new Random().nextInt(MAX_CAMBIO-MIN_CAMBIO +1)+MIN_CAMBIO);
    }

    protected void nuota() throws InterruptedException{
        Thread.sleep(PERMANENZA);
    }

    protected int scegliCorsia(){
        int x=0;
        for(int i=0; i<N ; ++i ){
            if(vasca[i]<vasca[x]) x = i;
        }
        return x;
    }

    public void test(int numeroClienti){
        for(int i=0; i<numeroClienti; ++i) new Persona(this).start();
    }
}
