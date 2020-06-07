package soluzioniTelegram.laghetto;

import java.util.concurrent.Semaphore;

/**
 * @author Marco Lackovic <mlackovic@dimes.unical.it>
 * @version 1.3, Jul 2, 2014
 */
public class LaghettoSem extends Laghetto {
	private int numPersoneInAttesa = 0;
	private Semaphore mutex = new Semaphore(1);
	private Semaphore ingresso = new Semaphore(0);
	private Semaphore[] pesci;

	public LaghettoSem(int min, int max) {
		super(min, max);
		//pesci = new Semaphore[2];
		pesci[PESCATORE] = new Semaphore(numPesci - minPesci);
		pesci[ADDETTO] = new Semaphore(maxPesci - numPesci);
	}

	public void entra(int tipo) throws InterruptedException {
		pesci[tipo].acquire(tipo * (PESCI_ALLA_VOLTA - 1) + 1);
		mutex.acquire();
		if (numPersoneDentro[1 - tipo] > 0) {
			numPersoneInAttesa++;
			mutex.release();
			ingresso.acquire();
		} else {
			numPersoneDentro[tipo]++;
			mutex.release();
		}
	}

	public void esce(int tipo) throws InterruptedException {
		mutex.acquire();
		numPersoneDentro[tipo]--;
		aggiornaPesci(tipo);
		if (numPersoneDentro[tipo] == 0 && numPersoneInAttesa > 0) {
			numPersoneDentro[1 - tipo] = numPersoneInAttesa;
			numPersoneInAttesa = 0;
			ingresso.release(numPersoneDentro[1 - tipo]);
		} else {
			mutex.release();
		}
		pesci[1 - tipo].release(tipo * (PESCI_ALLA_VOLTA - 1) + 1);
	}

	public static void main(String[] args) {
		new LaghettoLC(50, 200).test(new int[] { 40, 5 });
	}
}
