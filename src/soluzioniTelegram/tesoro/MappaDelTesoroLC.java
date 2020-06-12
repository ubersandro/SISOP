package soluzioniTelegram.tesoro;

import java.util.Arrays;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Marco Lackovic <mlackovic@dimes.unical.it>
 * @version 1.0, Jun 24, 2013
 */
public class MappaDelTesoroLC extends MappaDelTesoro {
    private Lock[][] mappa;

    public MappaDelTesoroLC(int n, int m, int x, int y) {
        super(n, m, x, y);
        mappa = new ReentrantLock[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                mappa[i][j] = new ReentrantLock(true);
            }
        }
    }

    @Override
    public boolean iniziaRicerca(int x, int y) throws InterruptedException {
        mappa[x][y].lock();
        if (tesoroTrovato) {
            mappa[x][y].unlock();
            return false;
        }
        return true;
    }

    @Override
    public boolean terminaRicerca(int x, int y) throws InterruptedException {
        if (posizioneTesoro.x == x && posizioneTesoro.y == y) {
            tesoroTrovato = true;
            mappa[x][y].unlock();
            return true;
        }
        mappa[x][y].unlock();
        return false;
    }

    public static void main(String[] args) throws InterruptedException {
        int numCercatori = 20;

        int numRighe = 50;
        int numColonne = 70;

        int xTesoro = 7;
        int yTesoro = 32;

        new MappaDelTesoroLC(numRighe, numColonne, xTesoro, yTesoro)
                .test(numCercatori);
    }
}
