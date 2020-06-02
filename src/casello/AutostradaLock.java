package casello;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.locks.*;

public class AutostradaLock extends Autostrada {
    Lock l;
    Condition [] possoPassare;
    LinkedList<Thread>[] codeCaselli;


    public AutostradaLock(int veicoli, int tariffa , int caselli){
        super(veicoli, tariffa, caselli);
        possoPassare = new Condition[caselli];
        codeCaselli = new LinkedList[caselli];
        for(int i=0; i<caselli; i++) codeCaselli[i] = new LinkedList<>();
        l = new ReentrantLock();
        for(int i=0; i< caselli;++i){
            possoPassare[i] = l.newCondition(); //un casello una condition

        }
    }//builder


    public void pagamento(double cifra, int casello){
        l.lock();
        try {
            while (!eIlMioTurno(casello)) {
                possoPassare[casello].await();
            }
            codeCaselli[casello].removeFirst();
            incasso += cifra;
            Thread.currentThread().sleep((long) ((new Random()).nextDouble()*3+3)*1000);
        }catch (InterruptedException e){

        }finally{
            l.unlock();
        }
    }

    public void accodati(int casello){
        l.lock();
        try{
            codeCaselli[casello].addLast(Thread.currentThread());
        }finally{
            l.unlock();
        }
    }//accodati


    private boolean eIlMioTurno(int casello){
        return Thread.currentThread().equals(codeCaselli[casello].getFirst());
    }

}
