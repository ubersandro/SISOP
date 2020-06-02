package soluzioniTelegram.trenino;

import java.util.concurrent.TimeUnit;

public class Impiegato extends Thread{
	protected final static int Scatto=20;
	private int scatto;
	private Trenino t;
	
	public Impiegato(Trenino t){
		this.t=t;
		scatto=0;
	}
	
	public void run(){
		try {
			while(true){
				TimeUnit.SECONDS.sleep(5);
				if(scatto<8){
					t.impFaiScendere();
					t.impFaiSalire();
				}
				TimeUnit.SECONDS.sleep(2);
				t.impScatto();
				scatto=(scatto+1)%10;
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
//grazie ad Antonio Marino per il contributo