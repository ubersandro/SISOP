package tesoro;

import java.util.HashSet;
import java.util.Random;
import java.util.concurrent.Semaphore;

public abstract class Mappa {
    protected int N, M;
    Thread[] cercatore;
    protected int x , y;
    protected HashSet<Long>[][] idCercatori;

    public int getN() {
        return N;
    }
    public int getM(){
        return M;
    }

    protected static final int MIN_CERCA = 2, MAX_CERCA = 5;

    protected Mappa(int n, int m, int cercatori){
        N = n;
        M = m;
        cercatore = new Thread[cercatori];
        Random r = new Random();
        x = r.nextInt( N );
        y = r.nextInt( M );
        idCercatori = new HashSet[N][M];
        for (int i = 0; i < N; ++i)
            for (int j = 0; j < M; ++j) {
                idCercatori[i][j] = new HashSet<>();
            }
    }

    protected abstract boolean iniziaRicerca(int x , int y) throws InterruptedException;
    protected abstract boolean terminaRicerca(int x, int y) throws InterruptedException;

    protected void interrompi(){
        System.out.println("PROVA");
        for(Thread t : cercatore)
            ///if(t!=Thread.currentThread())
                t.interrupt();
    }

    protected void test(){
        for(int i=0; i< cercatore.length; ++i) (cercatore[i] = new Cercatore(this)).start();
    }
}
