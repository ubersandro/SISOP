/* =======================================================================
 * Sistemi Operativi, Corso di Laurea in Ingegneria Informatica, DM 270/04
 * A.A. 2011/2012, Facoltà di Ingegneria, Università della Calabria
 * =======================================================================
 */

package soluzioniTelegram.salaTv;

import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Marco Lackovic <mlackovic@deis.unical.it>
 * @version 1.0, Jul 16, 2012
 */
public class SalaLC extends Sala {

	private LinkedList<Thread> coda = new LinkedList<Thread>();
	private int numPersone = 0;

	private Lock lock = new ReentrantLock();
	private Condition ingresso = lock.newCondition();

	@Override
	public void entra() throws InterruptedException {
		lock.lock();
		try {
			coda.add(Thread.currentThread());
			while (!mioTurno()) {
				ingresso.await();
			}
			coda.removeFirst();
			numPersone++;
		} finally {
			lock.unlock();
		}
	}

	private boolean mioTurno() {
		return numPersone < MAX_POSTI
				&& coda.getFirst() == Thread.currentThread();
	}

	@Override
	public void esci() throws InterruptedException {
		lock.lock();
		try {
			numPersone--;
			ingresso.signalAll();
		} finally {
			lock.unlock();
		}
	}
}
