package soluzioniMie.garaSci;


import java.util.concurrent.Semaphore;

public class GaraSem extends Gara {
    private Semaphore mutex = new Semaphore(1); //mutex
    private Semaphore[] puoPartire;


    public GaraSem(int numeroSciatori) {
        super(numeroSciatori);
        puoPartire = new Semaphore[numeroSciatori];
        for (int i = 0; i < numeroSciatori; ++i) {
            puoPartire[i] = new Semaphore(0);
        }

    }

    @Override
    protected void fine() {
        try {
            mutex.acquire();
            stampaClassifica();
            mutex.release();
        } catch (InterruptedException e) {
        }
    }


    @Override
    protected void partenza(Sciatore s) {
        try {
            puoPartire[s.getIdSciatore()].acquire();
        } catch (InterruptedException e) {
        }
    }

    @Override
    protected int arrivo(Sciatore s) {
        int tempoPercorrenza = 0, mioId = 0;
        int posizione = 0;
        try {
            mutex.acquire();
            tempoPercorrenza = s.getTempoPercorrenza();
            mioId = s.getIdSciatore();
            posizione = stampaPosizione(tempoPercorrenza, mioId);
            aggiornamento(mioId, tempoPercorrenza);
            mutex.release();
        } catch (InterruptedException e) {
        }
        return posizione;
    }

    @Override
    protected void aggiornaClassifica(int mioId, int tempo){
        try{
            mutex.acquire();

            mutex.release();
        }catch (InterruptedException e){}
    }
    @Override
    protected boolean prossimo() {
        try {
            mutex.acquire();
            prossimoSciatore++;
            if(prossimoSciatore<numeroSciatori){
                puoPartire[prossimoSciatore].release();
                mutex.release();
                return true;
            }
        } catch (InterruptedException e) {}
        mutex.release();
        return false;
    }
}

