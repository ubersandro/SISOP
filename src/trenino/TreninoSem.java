package trenino;

import java.util.LinkedList;
import java.util.concurrent.Semaphore;

/*
Per salire sulla cabina i 10 thread dovranno eseguire 10 acquire del semaforo relativo alla cabina corrente (sempre
ammesso che 10 thread ci siano.
 */
public class TreninoSem extends Trenino {
    //private Semaphore codaSem;
    private Semaphore[] postoCabina;//un semaforo per cabina che blocca i thread
    private Semaphore possoSalire;
    private Semaphore mutex = new Semaphore(1);



    public TreninoSem(){
       // codaSem = new Semaphore(1); //MUTEX
        turistiInAttesa = 0;
        possoSalire = new Semaphore(0); //NO FAIRNESS
        postoCabina = new Semaphore[10]; //10 cabine
        for(int i=0; i<10; ++i) postoCabina[i] = new Semaphore(10); //così da bloccare tutti i thread turista che salgono
    }

    @Override
    public void turSali(){
        try{
            mutex.acquire();
            //codaSem.acquire();
            turistiInAttesa++;
            //codaSem.release();

            possoSalire.acquire(); //per capire se posso salire
            mutex.release();
        }catch(InterruptedException e){}
    }

    @Override
    public void turScendi(){

        try {
            mutex.acquire();
            postoCabina[cabinaAttuale].acquire();
            mutex.release();
        }catch (InterruptedException e){}
    }
    @Override
    public void impFaiScendere(){
        /*
        se i turisti non ci sono non possono scendere...
         */
        try {
            mutex.acquire();
            if (cabine[cabinaAttuale] != 0) {
                cabine[cabinaAttuale] -= 10;
                postoCabina[cabinaAttuale].release(10); //scendono i dieci turisti
            }
            mutex.release();
        }catch (InterruptedException e){}

    }
    @Override
    public void impFaiSalire(){
        try {
            mutex.acquire();
            //codaSem.acquire();
            if (turistiInAttesa >= 10){
                possoSalire.release(10);//fai salire 10 thread
                cabine[cabinaAttuale] +=10;
                turistiInAttesa-=10;
                System.out.println("Saliti 10 turisti");
            };
            //codaSem.release();
            mutex.release();
        }catch(InterruptedException e){}
    }
    @Override
    public void impMuovi(){
        try{
            mutex.acquire();
            cabinaAttuale = (cabinaAttuale+1)%numeroCabine; //compreso tra 0 e 9
            Thread.currentThread().sleep(3 * 100);
            mutex.release();
        }catch (InterruptedException e){}
    }//impMuovi

    public static void main(String[] args) throws InterruptedException{
        new TreninoSem().test();
    }

}//TreninoSem
