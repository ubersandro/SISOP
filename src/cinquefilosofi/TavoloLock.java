package cinquefilosofi;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TavoloLock extends Tavolo{
	private Lock lock = new ReentrantLock();
	private Condition[] possoMangiare = new Condition[NUM_FILOSOFI];
	
	public TavoloLock() {
		for (int i=0; i<bacchette.length; i++) {
			possoMangiare[i] = lock.newCondition();
		}
	}

	public void prendiBacchette(int i) throws InterruptedException {
		lock.lock();
		try{
			while(bacchette[i] || bacchette[(i+1)%NUM_FILOSOFI]){
				if(bacchette[i])
					possoMangiare[i].await();
				else
					possoMangiare[(i+1)%NUM_FILOSOFI].await();
			}
			bacchette[i] = true;
			bacchette[ ( i+1 ) %NUM_FILOSOFI] = true;
		}finally{
			lock.unlock();
		}
		
	}
	
	public void rilasciaBacchette(int i) {
		lock.lock();
		try{
			bacchette[i] = false;
			bacchette[(i+1)%NUM_FILOSOFI] = false;
			possoMangiare[i].signal();
			possoMangiare[(i+1)%NUM_FILOSOFI].signal();
		}finally{
			lock.unlock();
		}
	}

	public static void main(String[] args) {
		TavoloLock tavolo = new TavoloLock();
		tavolo.test();
	}
}
