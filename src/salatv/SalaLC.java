package salatv;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SalaLC extends Sala {
    private Lock l = new ReentrantLock();
    private LinkedList<Thread> codaIngresso = new LinkedList<>();
    Condition possoEntrare = l.newCondition();
    Condition[] coda = new Condition[CANALI];
    private int postiOccupati = 0 ;


    public SalaLC(){
        for(int i=0; i< CANALI; ++i) coda[i] = l.newCondition();
    }


    @Override
    protected void entra(int canale) throws InterruptedException {
        l.lock();
        try{
            codaIngresso.addLast(Thread.currentThread());
            while(!(postiOccupati<CAPIENZA && codaIngresso.getFirst() == Thread.currentThread()))
                possoEntrare.await();
            codaIngresso.removeFirst(); //sono entrato
            if(canaleAttuale == canale){
                spettatoriAttuali++;
                return;
            }
            if(spettatoriAttuali == 0){
                canaleAttuale = canale;
                spettatoriAttuali++;
                return;
            }
            personeInAttesa[canale -1]++;
            while(!(canaleAttuale == canale)) coda[canale - 1].await();
            personeInAttesa[canale-1]--;
            spettatoriAttuali++;

        }finally{
            l.unlock();// posso mettere alcune operazioni qui
        }
    }

    @Override
    protected void esci(int canale) throws InterruptedException {
        l.lock();
        try{
            --spettatoriAttuali;
            if(spettatoriAttuali == 0){
                cambiaCanale() ;
            }
            postiOccupati--;
            possoEntrare.signalAll();
        }finally{
            l.unlock();
        }
    }

    public static  void main(String ... args){
        new SalaLC().test(200);
    }

    @Override
    protected void cambiaCanale() {
        l.lock();
        try{
            canaleAttuale = canalePiuRichiesto(); //tra 1 e 8
            coda[canaleAttuale - 1].signalAll();
        }finally{
            l.unlock();
        }
    }
}
