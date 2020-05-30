package monitor.pc;

import java.util.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Buffer {
    private List<Thread> consumatori = new LinkedList<>();
    private final int MAX_LISTA;
    private List<Thread> produttori = new LinkedList<>();
    private int[] buffer;
    private Lock l;
    int numeroElementi;
    Condition bufferPieno;
    Condition bufferVuoto;
    private int out, in;

    public Buffer(int dimensione, int numeroMassimoPC){
        MAX_LISTA = numeroMassimoPC;
        buffer = new int[dimensione];
        l = new ReentrantLock();
        bufferPieno = l.newCondition();
        bufferVuoto = l.newCondition();
        in = out = 0;
    }//Buffer

    public int get() throws InterruptedException{
        int x = Integer.MAX_VALUE;
        l.lock();
        try {
            if (consumatori.size() < MAX_LISTA) {
                consumatori.add(Thread.currentThread()); //Con runnable poi...
                while (!possoConsumare()) bufferVuoto.await();
                x = buffer[out];
                out = (out+1) % buffer.length;
                numeroElementi--;
                Thread.sleep(200);
                bufferPieno.signalAll();
            }//if
            else System.out.printf("Non posso consumare. THREAD ID %d \n", Thread.currentThread().getId()) ;
        }finally{
            l.unlock();
        }
        return x;
    }//get

    private boolean possoConsumare(){
        return numeroElementi>0 && Thread.currentThread().equals(consumatori.get(0));
    }

    public void put(int x) throws InterruptedException{
        l.lock();
        try{
            if(produttori.size() < MAX_LISTA) {
                produttori.add(Thread.currentThread());
                while (!possoInserire()) bufferPieno.await();
                buffer[in] = x;
                in = (in + 1) % buffer.length;
                numeroElementi++;
                bufferVuoto.signalAll();
            }//if
            else System.out.printf("Non posso produrre. THREAD ID %d\n", Thread.currentThread().getId());
        }finally{
            l.unlock();
        }
    }//put

    private boolean possoInserire(){
        return Thread.currentThread().equals(produttori.get(0)) && numeroElementi<buffer.length;
    }

}
