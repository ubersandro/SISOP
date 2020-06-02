package casello;

import java.util.Random;
import java.util.concurrent.Semaphore;

public class AutostradaSem extends Autostrada {
    private Semaphore pay;
    private Semaphore[] caselli;

    public AutostradaSem(int tariffa, int veicoli, int numCaselli){
        super(veicoli,tariffa,numCaselli);
        pay = new Semaphore(1); //mutex
        caselli = new Semaphore[numCaselli];
        for(int i=0; i<numCaselli;i++) caselli[i] = new Semaphore(1,true); //1 per volta, FIFO
    }
    @Override
    public void pagamento(double cifra, int casello) {
        try {
            pay.acquire();
            incasso += cifra;
            Thread.currentThread().sleep((long) (new Random()).nextDouble()*3000+3000); //RIVEDI -> MI POSSO ADDORMENTARE CON IL SEMAFORO IN MANO?
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
