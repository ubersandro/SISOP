package tesoro;

import java.util.concurrent.Semaphore;

public class MappaSemaphore extends Mappa {
    private Semaphore mutex = new Semaphore(1);
    private boolean trovato;
    private Semaphore[][] cella;

    public MappaSemaphore(int n, int m, int numeroCercatori) {
        super(n, m, numeroCercatori);
        System.out.println("X Y = " + x + " " + y);
        cella = new Semaphore[N][M];
        for (int i = 0; i < N; ++i)
            for (int j = 0; j < M; ++j) {
                cella[i][j] = new Semaphore(1, true);
            }
    }


    @Override
    protected boolean iniziaRicerca(int x, int y) throws InterruptedException {
        try {
            mutex.acquire();
            if (trovato || idCercatori[x][y].contains(Thread.currentThread().getId())) {
/*
                mutex.release();
*/
                return false;
            }
            idCercatori[x][y].add(Thread.currentThread().getId());
            /*mutex.release();*/
            cella[x][y].acquire();
        }finally{
                mutex.release();
            }
            return true;
    }

    @Override
    protected boolean terminaRicerca(int x, int y) throws InterruptedException{
        cella[x][y].release();
        try {
            mutex.acquire();
            if (x == this.x && y == this.y && !trovato) {
                trovato = true;
                interrompi(); //qui?
                System.out.println(x + " " + y);
                return true;
            }
        }
        finally{
            mutex.release();
        }
        return false;
    }

    public static void main(String[] args) {
        new MappaSemaphore(50, 70, 20).test();
    }
}
