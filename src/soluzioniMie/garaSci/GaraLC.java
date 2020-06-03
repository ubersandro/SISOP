package soluzioniMie.garaSci;

import java.util.LinkedList;
import java.util.concurrent.locks.*;

public class GaraLC extends Gara {
    Lock l;
    Condition[] puoPartire;

    protected int prossimoSciatore = -1;

    public GaraLC(int numeroSciatori) {
        super(numeroSciatori);
        l = new ReentrantLock(); //no fairness
        puoPartire = new Condition[numeroSciatori];
        for(int i=0; i< numeroSciatori; ++i) puoPartire[i] = l.newCondition();
    }

    @Override
    protected void fine() {
        l.lock();
        try{
            stampaClassifica();
        }finally {
            l.unlock();
        }
    }

    @Override
    protected void partenza(Sciatore s) {
        l.lock();
        try{
            while(!eIlMioTurno(s.getIdSciatore() ) ) puoPartire[ (int) s.getId()].await();
        }catch(InterruptedException e){
        }finally{
            l.unlock();
        }
    }

    private boolean eIlMioTurno(int sciatoreAttuale){
        return sciatoreAttuale == prossimoSciatore;
    }
    @Override
    protected int arrivo(Sciatore s){
        l.lock();
        try{
            int mioId = s.getIdSciatore();
            int tempoPercorrenza = s.getTempoPercorrenza();
            aggiornaClassifica(mioId, tempoPercorrenza);
            return stampaPosizione(tempoPercorrenza, mioId);
        }finally{

            l.unlock();
        }
      //  return tempoArrivo[s.getIdSciatore()];
    }

    protected void aggiornaClassifica(int mioId, int tempoPercorrenza){
        l.lock();//rientrante
        try{
            aggiornamento(mioId, tempoPercorrenza);
        }finally{
            l.unlock();
        }
    }
    protected boolean prossimo(){
        l.lock();
        try{
            prossimoSciatore++;
            if(prossimoSciatore >= numeroSciatori){
                return false;
            }
            puoPartire[prossimoSciatore].signal();
            return true;
        }finally{
            l.unlock();
        }
    }



}
