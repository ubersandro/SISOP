/* =======================================================================
 * Sistemi Operativi, Corso di Laurea in Ingegneria Informatica, DM 270/04
 * A.A. 2010/2011, Facoltà di Ingegneria, Università della Calabria
 * =======================================================================
 */

package soluzioneTelegram.banca;

/**
 * @author Marco Lackovic <mlackovic@deis.unical.it>
 * @version 1.0, Feb 3, 2012
 */
public abstract class Sportello {

	protected static final int MAX_DISPONIBILITA = 10000;
	protected static final int TEMPO_RIFORNIMENTO = 50;
	protected static final int TEMPO_PRELIEVO = 5;
	protected int denaro = MAX_DISPONIBILITA;
	protected Banca sedeCentrale;
	protected int id;

	public Sportello(Banca b, int id) {
		sedeCentrale = b;
		this.id = id;
	}

	public void rifornisci() throws InterruptedException {
		effettuaOperazione(MAX_DISPONIBILITA - denaro, TEMPO_RIFORNIMENTO);
	}

	public int preleva(int quantita) throws InterruptedException {
		return effettuaOperazione(-quantita, TEMPO_PRELIEVO);
	}

	protected int aggiornaDenaro(int quantita, int tempo) throws InterruptedException {
		if (quantita < 0 && denaro + quantita < 0) {
			// è un prelievo e non c'è denaro sufficiente nello sportello
			quantita = 0;
		} else {
			Thread.sleep(tempo);
			denaro += quantita;
			sedeCentrale.aggiornaDenaroSportello(id, denaro);
		}
		return quantita;
	}

	protected abstract int effettuaOperazione(int quantita, int tempo)
			throws InterruptedException;

}
