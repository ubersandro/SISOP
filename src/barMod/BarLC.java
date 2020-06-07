package barMod;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BarLC extends Bar{
    Lock l = new ReentrantLock(true);
    Condition[] operazione = {l.newCondition(), l.newCondition()};
    ArrayList<LinkedList<Thread>> coda = new ArrayList<> (2);

    public BarLC(int numeroPersone) {
        super(numeroPersone);
        coda.add(new LinkedList<>());
        coda.add(new LinkedList<>());
    }


    @Override
    protected int scegli() throws InterruptedException {
        l.lock();
        try{
            return scelta();
        }finally {
            l.unlock();
        }
    }

    @Override
    protected void inizia(int i) throws InterruptedException {
        l.lock();
        try{
            coda.get(i).addLast(Thread.currentThread());
            while(!(coda.get(i).getFirst() == Thread.currentThread())
                    && (i!=1 || persone[BERE]<4 )
                        ) operazione[i].await();
            coda.get(i).removeFirst();
            persone[i]++ ;
            operazione[i].signalAll();
        }finally {
            l.unlock();
        }
    }

    @Override
    protected void finisci(int i) throws InterruptedException {
        l.lock();
        try{
            persone[i]--;
            operazione[i].signalAll();
        }finally{
            l.unlock();
        }
    }

    public static void main(String[] args) {
        new BarLC(40).test();
    }
}
