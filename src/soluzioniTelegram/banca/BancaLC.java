package soluzioniTelegram.banca;


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BancaLC extends Banca {
    Lock l;
    Condition[] prelievoSportello;
    ArrayList<LinkedList<Thread>> codeSportello;

    public BancaLC(int numeroClienti, int numeroSportelli) {
        super(numeroClienti, numeroSportelli);
        l = new ReentrantLock(true);
        prelievoSportello = new Condition[numeroSportelli];
        for (int i = 0; i < numeroSportelli; ++i) prelievoSportello[i] = l.newCondition();
        codeSportello = new ArrayList<>(numeroSportelli);
        for (int i = 0; i < numeroSportelli; ++i) codeSportello.add(new LinkedList<Thread>());
    }


    @Override
    public boolean prelievo(int sportello, int somma) {
        l.lock();
        try {
            codeSportello.get(sportello).addLast(Thread.currentThread());//enqueue
            while (!eIlMioTurno(sportello))
                prelievoSportello[sportello].await();
            System.out.println("Provo a prelevare... " + Thread.currentThread().getId());
            if (denaroResiduo[sportello] < somma) {
                prelievoSportello[sportello].signalAll(); //tanto c'è il while
                codeSportello.get(sportello).removeFirst();
                return false; //eseguirà sempre e comunque l.unlock() nel blocco finally
            } else denaroResiduo[sportello] -= somma; //prelievo
            Thread.sleep(5);//tempo operazione
            prelievoSportello[sportello].signalAll();
        } catch (InterruptedException e) {
        } finally {
            l.unlock();
        }
        return true;
    }//

    private boolean eIlMioTurno(int sportello) {
        return codeSportello.get(sportello).getFirst().equals(Thread.currentThread());
    }//eilMioTurno


    public void rifornisci(int sportello) {
        l.lock();
        try {
            denaroResiduo[sportello] = sommaMax;
            System.out.println("Rifornimento sportello " + sportello);
            Thread.sleep(50);
        } catch (InterruptedException e) {
        } finally {
            l.unlock();
        }
    }

    @Override
    public int sportelloDaRifornire() {
        l.lock();
        int x = 0;
        try {
            for (int i = 0; i < numeroSportelli; i++)
                if (denaroResiduo[i] < denaroResiduo[x]) x = i;
        } finally {
            l.unlock();
            System.out.println("Sportello da RIFORNIRE " + x);
        }
        return x;
    }

    public static void main(String[] args) {
        new BancaLC(100, 30).test();
    }
}
