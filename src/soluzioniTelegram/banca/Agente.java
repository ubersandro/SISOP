package soluzioniTelegram.banca;

public class Agente extends Thread{
    Banca b;

    public Agente(Banca b){
        this.b = b;
    }

    public void run(){
        int x=0;
        try {
            while(true) {
                x = b.sportelloDaRifornire();
                Thread.sleep(100);
                b.rifornisci(x);
            }
        }catch(InterruptedException e){}
    }

}
