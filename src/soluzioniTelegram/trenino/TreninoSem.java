package soluzioniTelegram.trenino;

import java.util.concurrent.Semaphore;

public class TreninoSem extends Trenino{
	private Semaphore coda,scesi,saliti;
	private Semaphore [] personeScendi;
	private Semaphore mutex= new Semaphore(1);
	
	public TreninoSem(){
		super();
		personeScendi= new Semaphore[numCabine];
		for(int i=0;i<numCabine;i++){
			personeScendi[i]=new Semaphore(0);
		}
		coda = new Semaphore(0);
		scesi = new Semaphore(0);
		saliti = new Semaphore(0);
	}
	
	@Override
	public void viaSali() throws InterruptedException {
		mutex.acquire();
		personeInCoda++;
		mutex.release();
		
		coda.acquire();
		System.out.println(Thread.currentThread().getId()+" e salito nella cabina ="+scatto);
		mutex.acquire();
		cabine[scatto]+=1;
		personeInCoda--;
		if(cabine[scatto]==10) saliti.release();
		mutex.release();
		
	}

	@Override
	public void viaScendi() throws InterruptedException {
		personeScendi[scatto].acquire();
		mutex.acquire();
		cabine[scatto]-=1;
		System.out.println(Thread.currentThread().getId()+" e sceso nella cabina ="+scatto);
		if(cabine[scatto]==0) scesi.release();
		mutex.release();
		
	}

	@Override
	public void impFaiSalire() throws InterruptedException {
		mutex.acquire();
		if(personeInCoda>=10){
			coda.release(10);
			mutex.release();
			saliti.acquire();
			System.out.println("sono salite 10 persone nella cabina ="+scatto);
		}
		else{
			System.out.println("non ci sono almeno 10 persone in fila per farle salire");
			mutex.release();
		}
	}

	@Override
	public void impFaiScendere() throws InterruptedException {
		mutex.acquire();
		if(cabine[scatto]!=0){
			personeScendi[scatto].release(10);
			mutex.release();
			scesi.acquire();	
			System.out.println("sono scese 10 persone nella cabina ="+scatto);
		}
		else{
			System.out.println("non ci sono pe"
					+ "rsone da fare scendere");
			mutex.release();
		}	
	}

	@Override
	public void impScatto() throws InterruptedException {
		mutex.acquire();
		scatto=(scatto+1)%10;
		System.out.println("Scatto in avanti"+scatto);
		mutex.release();
		
	}

	public static void main(String[] args) {
		Trenino t= new TreninoSem();
		int numViaggiatori=100;
		t.test(numViaggiatori);
	}

}//grazie ad Antonio Marino per il contributo
