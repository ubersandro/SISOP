package soluzioniTelegram.garaSci;

import java.util.LinkedList;

/**
 * @author Marco Lackovic <mlackovic@dimes.unical.it>
 * @version 1.0, Jul 18, 2014
 */
public abstract class Gara {

	protected final int numSciatori;
	protected int prossimoSciatore = -1;
	private LinkedList<Sciatore> sciatori = new LinkedList<>();

	public Gara(int numSciatori) {
		this.numSciatori = numSciatori;
	}

	public abstract void partenza(Sciatore sciatore)
			throws InterruptedException;

	public abstract int arrivo(Sciatore sciatore) throws InterruptedException;

	public abstract boolean prossimo() throws InterruptedException;

	protected int inserisci(Sciatore s) {
		int posizione = 0;
		int tempo = s.getTempo();
		for (Sciatore sciatore : sciatori) {
			if (sciatore.getTempo() >= tempo) {
				break;
			}
			posizione++;
		}
		sciatori.add(posizione, s);
		return posizione + 1;
	}

	protected boolean sciatoriArrivati() {
		return sciatori.size() == numSciatori;
	}

	protected void aggiorna() {
		int posizione = 1;
		for (Sciatore s : sciatori) {
			s.setArrivo(posizione++);
		}
	}

	protected void stampa() {
		System.out.println("\n --------- Classifica finale ---------\n");
		for (Sciatore s : sciatori) {
			System.out.println(s);
		}
	}

	protected void test() {
		for (int i = 0; i < numSciatori; i++) {
			new Thread(new Sciatore(this, i)).start();
		}
		new Thread(new Addetto(this)).start();
	}

}
