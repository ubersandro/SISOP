package soluzioniMie.banca.trenino;

import java.util.concurrent.TimeUnit;

public class Impiegato extends Thread {
    Trenino t;
    public Impiegato(Trenino t){
        this.t = t;
    }


    public void run(){
        while(true){
            t.impFaiScendere();
            t.impFaiSalire();
            try {
                TimeUnit.SECONDS.sleep(1);
            }catch(InterruptedException e){}
            System.out.println("Scatto");
            t.impMuovi();

        }
    }
}
