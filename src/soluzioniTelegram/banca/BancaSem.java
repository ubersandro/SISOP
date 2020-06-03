package soluzioniTelegram.banca;

import java.util.concurrent.Semaphore;

public abstract class BancaSem extends Banca {
    Semaphore[] sportelli;
    Semaphore mutex;

    public BancaSem(int numeroClienti, int numeroSportelli){
        super(numeroClienti, numeroSportelli);
    }

    @Override
    public boolean prelievo(int sportello, int denaro) {



        return true;
    }

    @Override
    public void rifornisci(int sportello) {

    }


    public void rifornisci() {

    }
}
