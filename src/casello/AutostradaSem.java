package casello;

import java.util.Random;
import java.util.concurrent.Semaphore;

public class CaselloSem extends Casello {
    private Semaphore pay;
    private Semaphore[] caselli;

    public CaselloSem(int tariffa, int veicoli, int numCaselli){
        super(veicoli,tariffa,numCaselli);
        pay = new Semaphore(1); //mutex
        caselli = new Semaphore[numCaselli];
        for(int i=0; i<numCaselli;i++) caselli[i] = new Semaphore(1,true); //1 per volta, FIFO
    }
    @Override
    public void pagamento(int cifra, int casello) {
        try {
            pay.acquire();
            incasso += cifra;
            Thread.currentThread().sleep((long) (new Random()).nextDouble()*3000+3000); //rivedi
            pay.release();
            caselli[casello].release();
        }catch(InterruptedException e){}
    }

    @Override
    public void accodati(int casello) {
        try{
            caselli[casello].acquire();
        }catch(InterruptedException e){}
    }
}
