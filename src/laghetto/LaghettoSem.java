package laghetto;

import java.util.concurrent.Semaphore;

public class LaghettoSem extends Laghetto {
    private Semaphore mutex;
    private Semaphore possoPescare;
    private Semaphore possoRipopolare;
    private Semaphore alt = new Semaphore(1);
    private int p, a;


    public LaghettoSem(int minPesci, int maxPesci, int numeroPescatori, int numeroAddetti) {
        super(minPesci, maxPesci, numeroPescatori, numeroAddetti);
        mutex = new Semaphore(1);
        possoPescare = new Semaphore(numeroPescatori);
        possoRipopolare = new Semaphore(numeroAddetti);
    }


    @Override
    public void finisci(int t) {
        try {
            if (t == 0) {
               // possoPescare.release();
                mutex.acquire();
                p--;
                if(p==0) possoRipopolare.release(numeroAddetti);
                mutex.release();
            }
            if (t == 1) {
                possoRipopolare.release();
                /*mutex.acquire();
                a--;
                mutex.release();*/
            }

        } catch (InterruptedException e) {
        }
    }

    @Override
    protected void inizia(int t) {
        try {
            if (t == 0) {//pesca
                mutex.acquire();
                if (p == 0) {
                    //mutex.release();
                    possoRipopolare.acquire(numeroAddetti);
                    //mutex.acquire();
                }
                p++;
                mutex.release();
            }

            if (t == 1) {
                possoRipopolare.acquire();
                /*mutex.acquire();
                a++;
                mutex.release();*/
            }
        } catch (InterruptedException e) {
        }
    }
}
