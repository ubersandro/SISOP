package soluzioniTelegram.trenino;

public class Viaggiatore extends Thread{
	private Trenino t;


	public Viaggiatore(Trenino t){
		this.t=t;
	}

	public void run(){
		try {
			t.viaSali();
			t.viaScendi();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
//grazie ad Antonio Marino per il contributo