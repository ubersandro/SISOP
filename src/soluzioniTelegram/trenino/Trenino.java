package soluzioniTelegram.trenino;

public abstract class Trenino {
	protected final static int numCabine=8;
	protected int []cabine;
	protected int scatto,personeInCoda;
	public Trenino(){
		cabine= new int[numCabine];
		scatto=0;
		personeInCoda=0;
	}
	
	
	public abstract void viaSali ()throws InterruptedException;
	public abstract void viaScendi()throws InterruptedException;
	public abstract void impFaiSalire()throws InterruptedException;
	public abstract void impFaiScendere()throws InterruptedException;
	public abstract void impScatto()throws InterruptedException;
	
	public void test(int numViaggiatori){
		Impiegato I= new Impiegato(this);
		Viaggiatore[] V= new Viaggiatore[numViaggiatori];
		I.setDaemon(true);
		I.start();
		for(int i=0;i<numViaggiatori;i++){
			V[i]= new Viaggiatore(this);
			V[i].start();
		}	
	}
}
//grazie ad Antonio Marino per il contributo