package soluzioniTelegram.banca;

public class Agente extends Thread{
    Banca b;

    public Agente(Banca b){
        this.b = b;
    }

    public void run(){
        try {
            while(true) {
                Thread.sleep(10);
                b.rifornisci();
            }
        }catch(InterruptedException e){}
    }

}
