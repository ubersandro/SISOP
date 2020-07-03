package funivia;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class FuniviaLC extends Funivia {
    private Lock l = new ReentrantLock();
    private Condition[] operazione = {l.newCondition(), l.newCondition()};
    private Condition sonoSaliti = l.newCondition();
    private Condition possoScendere = l.newCondition();
    private boolean inCima;
    private int postiOccupati;
    //private boolean[] turno = {false, false}; //permette di capire a chi tocca


    @Override
    protected void pilotaStart() throws InterruptedException {
        l.lock();
        try{
            postiOccupati = 0;
            operazione[tipoAttuale].signalAll();//segnala ai thread che possono salire
            while((postiOccupati<CAPIENZA_MAX))sonoSaliti.await();
            System.out.println("Si parte...");
             //sarebbe stato più facile usare un array di booleani
        }finally{
            l.unlock();
        }
    }

    @Override
    protected void pilotaEnd() {
        l.lock();
        try {
            inCima = true;
            stampa();
            possoScendere.signalAll();
            inCima = false;
            tipoAttuale = (tipoAttuale+1)%2;
        }finally{
            l.unlock();
        }
    }

    @Override
    protected void turistaSali(int t) throws InterruptedException {
        l.lock();
        try{
            while(!(tipoAttuale == t && postiOccupati<CAPIENZA_MAX)) operazione[t].await();
            cabina.add(Thread.currentThread());
            postiOccupati += (1+t*1); //1 0 2 posti ciascuno
            sonoSaliti.signal();//solo un thread è in attesa su questa condition
        }finally{
            l.unlock();
        }
    }

    @Override
    protected void turistaScendi(int t) throws InterruptedException {
        l.lock();
        try{
            while(!inCima)possoScendere.await();
        }finally {
            l.unlock();
        }
    }

    public static void main(String[] args) {
        new FuniviaLC().test(200);
    }
}
