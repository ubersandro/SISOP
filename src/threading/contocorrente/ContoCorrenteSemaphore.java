package it.unical.sisop.contocorrente;

import java.util.concurrent.Semaphore;

public class ContoCorrenteSemaphore extends ContoCorrente {
	
	private Semaphore mutex = new Semaphore(1);

	public ContoCorrenteSemaphore(int depositoIniziale) {
		super(depositoIniziale);
	}

	public void deposita(int importo){
		try {
			mutex.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		this.deposito += importo;
		mutex.release();
	}

	public void preleva(int importo){
		try {
			mutex.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		this.deposito -= importo;
		mutex.release();
	}	
}
