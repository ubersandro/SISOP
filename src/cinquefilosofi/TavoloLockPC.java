package cinquefilosofi;

import java.util.HashSet;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TavoloLockPC extends Tavolo {
	private Lock lock = new ReentrantLock();
	private Condition[] possoMangiare = new Condition[NUM_FILOSOFI];

	private boolean[] filosofiATavola = new boolean[NUM_FILOSOFI];
	private HashSet<String> possibiliCombinazioni = new HashSet<String>();

	public TavoloLockPC() {
		for (int i = 0; i < bacchette.length; i++) {
			possoMangiare[i] = lock.newCondition();
		}
		aggiungiCombinazione();
	}

	public void prendiBacchette(int i) throws InterruptedException {
		lock.lock();
		try {
			while (bacchette[i] || bacchette[(i + 1) % NUM_FILOSOFI]) {
				if (bacchette[i])
					possoMangiare[i].await();
				else
					possoMangiare[(i + 1) % NUM_FILOSOFI].await();
			}
			bacchette[i] = true;
			bacchette[(i + 1) % NUM_FILOSOFI] = true;
			filosofiATavola[i] = true;
			aggiungiCombinazione();
			stampaCombinazioni();
		} finally {
			lock.unlock();
		}

	}

	private void stampaCombinazioni() {
		System.out.print("{");
		for (String s : possibiliCombinazioni) {
			System.out.print(s);
		}
		System.out.println("}");

	}

	private void aggiungiCombinazione() {
		String ret = "[";
		for (int i = 0; i < NUM_FILOSOFI; i++) {
			if (filosofiATavola[i])
				ret += i;
			else
				ret += "-";
		}
		ret += "]";
		possibiliCombinazioni.add(ret);
	}

	public void rilasciaBacchette(int i) {
		lock.lock();
		try {
			bacchette[i] = false;
			bacchette[(i + 1) % NUM_FILOSOFI] = false;
			possoMangiare[i].signal();
			possoMangiare[(i + 1) % NUM_FILOSOFI].signal();
			filosofiATavola[i] = false;
			aggiungiCombinazione();
			stampaCombinazioni();
		} finally {
			lock.unlock();
		}
	}

	public static void main(String[] args) {
		TavoloLockPC tavolo = new TavoloLockPC();
		tavolo.test();
	}
}
