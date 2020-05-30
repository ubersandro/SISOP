package threading.semafori.barbiere;


    /*
        inizializzo un semaforo barbiere con una sua sala d'attesa(buffeer limitato) regolata da un semaforo che mi dice se ci sono ancora posti disponibili
        e poi inizializzo di continuo nuovi thread cliente che tentano di accedere
         */


import java.util.concurrent.Semaphore;

public class BarbiereAddormentato {
    private Semaphore mutex; //per accedere alle variabili del buffer
    private final int postiTotali;
    private int postiDisponibili;
    private Semaphore barbiere;

    public BarbiereAddormentato(int posti){
        postiTotali = posti;
        postiDisponibili=postiTotali;
        barbiere = new Semaphore(1,true);
        mutex = new Semaphore(1);
    }


    public boolean ciSonoPosti() throws InterruptedException{
         mutex.acquire();
         if(postiDisponibili>0){
             mutex.release();
             return true;
         }
         mutex.release();
         return false;
    }//ciSonoPosti





    public static void main(String[] args) {

    }
}
