package tesoro;

import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MappaLC extends Mappa {
    private Lock l = new ReentrantLock();
    Condition[][] cella;
    LinkedList[][] codaCella;
    private boolean trovato = false;

    public MappaLC(int n, int m, int cercatori){
        super(n,m,cercatori);
        cella = new Condition[n][m];
        codaCella = new LinkedList[n][m];
        for(int i=0; i<n; ++i)
            for(int j=0 ; j< m ;++j) {
                cella[i][j] = l.newCondition();
                codaCella[i][j] = new LinkedList();
            }
    }

    @Override
    protected boolean iniziaRicerca(int x, int y) throws InterruptedException{
        l.lock();
        try {
            if(trovato)return false;
            codaCella[x][y].addLast(Thread.currentThread());
            while (codaCella[x][y].getFirst() != Thread.currentThread()) cella[x][y].await();
            codaCella[x][y].removeFirst();
            cella[x][y].signalAll();
        }finally{
            l.unlock();
        }
        return true;
    }

    @Override
    protected boolean terminaRicerca(int x, int y) throws InterruptedException{
        l.lock();
        try{
            if (x == this.x && y == this.y && !trovato) {
                trovato = true;
                interrompi(); //qui?
                System.out.println(x + " " + y);
                return true;
            }
        }finally{
            l.unlock();
        }
        return false;
    }

    public static void main(String[] args) {
        new MappaLC(50,70, 20).test();
    }
}
