package salatv;

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
            codaIngresso.addFirst(Thread.currentThread());
            while(!(postiOccupati<CAPIENZA && codaIngresso.getFirst()== Thread.currentThread()))
                possoEntrare.await();
            codaIngresso.removeFirst(); //sono entrato
            postiOccupati++; //ho occupato un posto in sala
            if(canaleAttuale == canale){
                spettatoriAttuali++;
                possoEntrare.signalAll();
                return;
            }
            if(spettatoriAttuali == 0){
                canaleAttuale = canale;
                spettatoriAttuali++;
                possoEntrare.signalAll();
                return;
            }
            while(!(canaleAttuale == canale)) coda[canale].await();
            spettatoriAttuali++;
            possoEntrare.signalAll();
        }finally{
            l.unlock();// posso mettere alcune operazioni qui
        }
    }

    @Override
    protected void esci(int canale) throws InterruptedException {
        l.lock();
        try{
            spettatoriAttuali--;
            postiOccupati--;
            if(spettatoriAttuali == 0){
                canaleAttuale = canalePiuRichiesto();
            }
            coda[canaleAttuale].signalAll();
            possoEntrare.signalAll();
        }finally{
            l.unlock();
        }
    }

    @Override
    protected void cambiaCanale() {

    }
}
