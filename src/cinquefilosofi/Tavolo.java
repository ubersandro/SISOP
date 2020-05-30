package cinquefilosofi;

public abstract class Tavolo {
	public final static int NUM_FILOSOFI = 5;
	protected boolean[] bacchette = new boolean[NUM_FILOSOFI];//libera false, occupata true
	
	public abstract void prendiBacchette(int i) throws InterruptedException;
	
	public abstract void rilasciaBacchette(int i);
	
	protected void test() {
		for (int i=0; i< NUM_FILOSOFI; i++) {
			new Filosofo(this, i).start();;
		}
	}
}
