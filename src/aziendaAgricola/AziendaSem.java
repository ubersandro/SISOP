package aziendaAgricola;

import java.util.concurrent.Semaphore;

public class  AziendaSem  extends Azienda{
    private Semaphore mutex;//mutex con fairness
    private Semaphore magazzino = new Semaphore(1, true) ;
    private Semaphore disponibili = new Semaphore (sacchiDisponibili, true);
    private Semaphore rifornimento = new Semaphore(0,true);

    public AziendaSem(int numeroClienti){
        super(numeroClienti);
        mutex = new Semaphore(1, true);
    }


    @Override
    protected void compraSacchi(int numeroSacchi) throws InterruptedException {
        disponibili.acquire(numeroSacchi);
        mutex.acquire();
        incasso += numeroSacchi*3 ;
        mutex.release();
    }

    @Override
    protected void ritiraSacchi(int numero) throws InterruptedException {
        while(--numero>0) {
            magazzino.acquire();
            Thread.currentThread().sleep(10); //dovrebbe essere un minuto
            magazzino.release();
            rifornimento.release();
        }
    }


    protected void ripristina() throws InterruptedException{
        rifornimento.acquire(sacchiDisponibili);
        Thread.currentThread().sleep(TEMPO_MAGAZZINIERE * 100);
        disponibili.release(sacchiDisponibili);
    }


    public static void main(String[] args)throws InterruptedException
    {
        new AziendaSem(3000).test();
    }
}
