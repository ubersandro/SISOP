package soluzioniTelegram.tesoro;

import java.util.LinkedList;

/**
 * @author Marco Lackovic <mlackovic@dimes.unical.it>
 * @version 1.0, Jun 24, 2013
 */
public abstract class MappaDelTesoro {
    protected final Coordinate posizioneTesoro;
    private final LinkedList<Coordinate> elencoCoordinate = new LinkedList<>();
    protected boolean tesoroTrovato = false;

    public MappaDelTesoro(int n, int m, int x, int y) {
        posizioneTesoro = new Coordinate(x, y);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                elencoCoordinate.add(new Coordinate(i, j));
            }
        }
    }

    /**
     * Restituisce una copia superficiale (vengono copiati i riferimenti, non le
     * istanze) dell'elenco delle coordinate di tutte le caselle della matrice.
     * 
     * @return Una copia superficiale dell'elenco delle coordinate delle caselle
     *         della matrice.
     */
    @SuppressWarnings("unchecked")
    public LinkedList<Coordinate> getElencoCoordinate() {
        return (LinkedList<Coordinate>) elencoCoordinate.clone();
    }

    public abstract boolean iniziaRicerca(int x, int y)
            throws InterruptedException;

    public abstract boolean terminaRicerca(int x, int y)
            throws InterruptedException;

    protected void test(int numCeratori) throws InterruptedException {
        System.out.println("threading.esercizi.Test della classe " + getClass().getSimpleName());
        for (int i = 0; i < numCeratori; i++) {
            new Thread(new Cercatore(this)).start();
        }
    }
}
