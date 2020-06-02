package soluzioniTelegram.trenino;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TreninoLC extends Trenino{
	private Lock l = new ReentrantLock();
	private Condition coda,scesi,saliti;
	private Condition[] personeScendi;
	private boolean possosalire;
	public TreninoLC(){
		super();
		personeScendi= new Condition[numCabine];
		for(int i=0;i<numCabine;i++){
			personeScendi[i]=l.newCondition();
		}
		coda= l.newCondition();
		scesi= l.newCondition();
		saliti= l.newCondition();
		possosalire=false;
	}
	
	@Override
	public void viaSali() throws InterruptedException {
		l.lock();
		try{
			personeInCoda++;
			while(!possoSalire()){
				coda.await();
			}
			System.out.println(Thread.currentThread().getId()+" e salito nella cabina ="+scatto);
			cabine[scatto]+=1;
			personeInCoda--;
			if(cabine[scatto]==10){possosalire=false; saliti.signal();}
		}finally{
			l.unlock();
		}
	}

	private boolean possoSalire() {
		return possosalire;
	}
	
	@Override
	public void viaScendi() throws InterruptedException {
		l.lock();
		try{
			personeScendi[scatto].await();	
			cabine[scatto]-=1;
			System.out.println(Thread.currentThread().getId()+" e sceso nella cabina = "+scatto);
			if(cabine[scatto]==0) scesi.signal();
		}finally{
			l.unlock();
		}
		
	}



	@Override
	public void impFaiSalire() throws InterruptedException {
		l.lock();
		try{
			if(personeInCoda>=10){
				possosalire=true;
				coda.signalAll();
				while(cabine[scatto]!=10)
					saliti.await();
				System.out.println("sono salite 10 persone nella cabina ="+scatto);
			}
			else{
				System.out.println("non ci sono almeno 10 persone in fila per farle salire");
			}
		}finally{
			l.unlock();
		}
	}

	@Override
	public void impFaiScendere() throws InterruptedException {
		l.lock();
		try{
			if(cabine[scatto]!=0){
				personeScendi[scatto].signalAll();
				while(cabine[scatto]!=0){
				scesi.await();	
				}
				System.out.println("sono scese 10 persone nella cabina ="+scatto);
			}
			else{
				System.out.println("non ci sono pe"
						+ "rsone da fare scendere");
			}
		}finally{
			l.unlock();
		}
		
	}

	@Override
	public void impScatto() throws InterruptedException {
		l.lock();
		try{
			scatto=(scatto+1)%10;
			System.out.println("Scatto in avanti"+scatto);	
		}finally{
			l.unlock();
		}
	}
	
	public static void main(String[] args) {
		Trenino t= new TreninoLC();
		int numViaggiatori=100;
		t.test(numViaggiatori);
	}


}//grazie ad Antonio Marino per il contributo
