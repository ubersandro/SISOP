package cinquefilosofi;

import java.util.concurrent.TimeUnit;

public class Filosofo extends Thread{
	private Tavolo tavolo;
	private int posizione;
	
	public Filosofo(Tavolo t, int pos){
		tavolo = t;
		posizione = pos;
	}

	public void run() {
		try {
			while(true){
				tavolo.prendiBacchette(posizione);
				System.out.format("Il filosofo %d ha iniziato a mangiare%n", posizione);
				mangia();
				System.out.format("Il filosofo %d ha finito di mangiare%n", posizione);
				tavolo.rilasciaBacchette(posizione);
				pensa();
			}
		} catch (InterruptedException e) {e.printStackTrace();}
	}

	private void mangia() {
		try {TimeUnit.SECONDS.sleep(10);
		} catch (InterruptedException e) {e.printStackTrace();}		
	}
	
	private void pensa() {
		try {TimeUnit.SECONDS.sleep(5);
		} catch (InterruptedException e) {e.printStackTrace();}		
	}
}
