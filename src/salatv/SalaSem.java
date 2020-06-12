package salatv;

import java.util.concurrent.Semaphore;

public class SalaSem extends Sala {
    private Semaphore mutex = new Semaphore(1);
    private Semaphore postoInSala = new Semaphore(CAPIENZA, true);
    private Semaphore[] coda = new Semaphore[CANALI];


    public SalaSem() {

        for (int i = 0; i < CANALI; ++i) coda[i] = new Semaphore(0);
    }


    @Override
    protected void entra(int canale) throws InterruptedException {
        postoInSala.acquire();//avere il posto in sala non vuol dire guardare il canale
        mutex.acquire();
        if (canaleAttuale == canale) { //all'inzio è 0 quindi non può essere un valore tra 1 e 8
            spettatoriAttuali++;
            mutex.release();
            return;
        }
        if (spettatoriAttuali == 0) {
            spettatoriAttuali++;
            canaleAttuale = canale;
            mutex.release();
            return;
        }
        if (canaleAttuale != canale && spettatoriAttuali > 0) {
            personeInAttesa[canale]++; //segnalo che sono in attesa
            mutex.release();//per evitare deadlock
            coda[canale].acquire(); //mi metto in coda sul canale desiderato
            mutex.acquire();
            spettatoriAttuali++; //quando il canale viene cambiato io posso andare a guardarlo
            mutex.release();
        }
    }

    @Override
    protected void esci(int canale) throws InterruptedException {
        mutex.acquire();
        spettatoriAttuali--; //me ne sto andando
        if(spettatoriAttuali==0) cambiaCanale();
        mutex.release();
        postoInSala.release();
    }

    /*
    Non ho bisogno di riacquisire il mutex, con il lock potrei acquisirlo lo stesso (rientrante)
     */
    protected void cambiaCanale(){
        canaleAttuale = canalePiuRichiesto();
        coda[canaleAttuale].release(personeInAttesa[canaleAttuale]);
        personeInAttesa[canaleAttuale] = 0;
    }

}
