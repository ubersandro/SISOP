package monitorNativi.blocchi;

import java.util.LinkedList;

public class Conto {
    private int bilancio = 10;
    private LinkedList<Thread> coda = new LinkedList<>();

    public synchronized void modifica(int i) throws InterruptedException{
        coda.add(Thread.currentThread());
        while(coda.getFirst()!=Thread.currentThread()) wait();
        coda.removeFirst();
        bilancio +=i;
        notifyAll();
    }

    public String toString(){
        return String.valueOf(bilancio);
    }
}
