package barMod;

import java.util.concurrent.Semaphore;

public class BarSem extends Bar {
    private Semaphore mutex = new Semaphore(1, true); //fifoness
    private Semaphore[] code = {new Semaphore(1, true), new Semaphore(4, true)};//CASSA = 0 BANCONE = 1

    public BarSem(int numeroPersone) {
        super(numeroPersone);
    }

    @Override
    protected int scegli() throws InterruptedException {
            mutex.acquire();
            int x = scelta();
            mutex.release();
            return x;
    }

    @Override
    protected void inizia(int i) throws InterruptedException {
        code[i].acquire();
        mutex.acquire();
        persone[i]++;
        mutex.release();
    }

    @Override
    protected void finisci(int i) throws InterruptedException {
        code[i].release();
        mutex.acquire();
        persone[i]--;
        mutex.release();
    }

    public static void main(String[] args) {
        new BarSem(30).test();
    }
}
