package soluzioniMie.banca;

import java.util.Random;

public class Cliente extends Thread{
    Banca b;
    Random r;

    public Cliente(Banca banca){
        b = banca;
        r = new Random();
    }//builder

    public void run(){
        while (true){
            int sportello = r.nextInt(b.numeroSportelli);
            int somma = r.nextInt(200) + 50;
            try{
                Thread.sleep(10);//tempo raggiungere
                if(b.prelievo(sportello, somma)) {
                    System.out.printf("THREAD %d -> Ho prelevato %d euro dallo sportello %d\n", Thread.currentThread().getId(),  somma, sportello);
                    Thread.sleep(somma);
                }
                else System.out.printf("THREAD %d ->  Non sono riuscito a prelevare %d euro\n.", Thread.currentThread().getId(),  somma);//per semplicità i soldi sono interi, ma è poco realistico => meglio double...
            }catch(InterruptedException e ){}
        }
    }
}
