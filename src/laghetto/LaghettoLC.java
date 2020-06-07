package laghetto;

import java.util.concurrent.locks.*;

public class LaghettoLC extends Laghetto {
    private Lock l;
    Condition possoPescare;
    Condition possoRipopolare ;
    private int a, p;

    public LaghettoLC(int minPesci, int maxPesci, int numeroPescatori, int numeroAddetti){
        super(minPesci, maxPesci, numeroPescatori, numeroAddetti);
        l = new ReentrantLock(true);
        possoPescare = l.newCondition();
        possoRipopolare = l.newCondition();
    }


    @Override
    protected void inizia(int t) {
        try {
            l.lock();
            if (t == 0) {
                while(a!=0) possoPescare.await();
                p++;
            }
            if(t == 1){
                while(p!=0) possoRipopolare.await();
                a++;
            }
        } catch (InterruptedException e) {}
        finally{
        l.unlock();
        }
    }

    @Override
    protected void finisci(int t) {
        l.lock();
        try{
            if(t == 0){
                if(--p == 0) possoRipopolare.signalAll();
            }
            if(t==1){
                if(--a == 0) possoPescare.signalAll();
                System.out.println("A === " + a);
            }
        }finally{
            l.unlock();
        }
    }
}
