package salatv;

import java.util.*;

public abstract class Sala {
    protected final static int CAPIENZA = 30, CANALI = 8;
    protected int spettatoriAttuali, canaleAttuale;
    protected int [] personeInAttesa = new int[CANALI];



    public int getCanali(){
        return CANALI;
    }

    protected int canalePiuRichiesto(){
        int maxPersone = 0, canale= 0;
        for(int i=0; i<CANALI; ++i)
            if(personeInAttesa[i]>maxPersone){
                maxPersone = personeInAttesa[i];
                canale = i;
            }
        return canale + 1; //un numero tra 1 e 8
    }
    protected abstract void entra(int canale) throws InterruptedException;
    protected abstract void esci(int canale) throws InterruptedException;
    protected abstract void cambiaCanale();

    public void test(int persone){
        int c = 0;
        while(persone-->0) (new Thread(new Persona(this, ++c))).start();
    }
}
