package soluzioniMie.garaSci;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.TreeMap;

public abstract class Gara {
    protected int[] tempoArrivo;
    protected Sciatore[] sciatore;
    protected TreeMap<Integer, LinkedList<Integer>> classifica; //ammette duplicati
    protected int numeroSciatori;
    protected int posizioneClassificaSingolo = 1;
    protected int prossimoSciatore = -1;

    public Gara(int numeroSciatori) {
        tempoArrivo = new int[numeroSciatori];
        classifica = new TreeMap<>();

        sciatore = new Sciatore[numeroSciatori];
        for (int i = 0; i < numeroSciatori; ++i) sciatore[i] = new Sciatore(i, this); //ognuno con il suo id

        this.numeroSciatori = numeroSciatori;
    }

    protected Sciatore getSciatore(int indice) {
        return sciatore[indice];
    }

    protected abstract void fine();

    protected int getNumeroSciatori() {
        return numeroSciatori;
    }

    protected abstract void partenza(Sciatore s);

    protected abstract int arrivo(Sciatore s);

    protected abstract boolean prossimo();

    protected abstract void aggiornaClassifica(int id, int tempo);

    protected void stampaClassifica() {
        classifica.forEach((tempo, lista) -> {
                    Iterator<Integer> it = lista.listIterator();
                    while (it.hasNext())
                        System.out.printf("Sciatore %d | Tempo = %d (milli) secondi\n", it.next(), tempo); //stampa anche il tempo
                }
        );
    }

    protected void aggiornamento(int mioId, int tempoPercorrenza) {
        LinkedList<Integer> arrivi;

        if (!classifica.containsKey(tempoPercorrenza)) {
            arrivi = new LinkedList<>();
            arrivi.addFirst(mioId);
            classifica.put(tempoPercorrenza, arrivi);
        } else {
            arrivi = classifica.get(tempoPercorrenza);
            arrivi.addLast(mioId);
        }

    }

    protected int stampaPosizione(int tempo, int id) {
        posizioneClassificaSingolo = 1;
        classifica.forEach((t, l) -> {
            if (tempo < t) posizioneClassificaSingolo++;
            else if (tempo == t) return;
        });
        System.out.println(posizioneClassificaSingolo + "°" + "THREAD " + id);
        return posizioneClassificaSingolo;
    }


}
