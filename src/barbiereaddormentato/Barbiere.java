package barbiereaddormentato;

import java.util.concurrent.TimeUnit;

public class Barbiere extends Thread {
	private Sala sala;
	
	public Barbiere(Sala s) {
		sala = s;
	}

	public void run() {
		while (true) {
			try {
				sala.tagliaCapelli();
				System.out.println("Taglio in corso...");
				taglio();
				
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private void taglio() throws InterruptedException {
		TimeUnit.SECONDS.sleep(3);		
	}
}
