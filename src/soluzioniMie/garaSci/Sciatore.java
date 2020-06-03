package soluzioniMie.garaSci;

public class Sciatore extends Thread{
    private final int idSciatore;
    private static final int MAX_TEMPO = 70, MIN_TEMPO = 50;
    Gara g;
    private int tempoPercorrenza;

    public Sciatore(int id, Gara g){
        idSciatore = id;
        this.g = g;
    }

    public void run(){
        g.partenza(this);
        percorriTragitto();
        g.arrivo(this);
    }
    public int getIdSciatore(){
        return idSciatore;
    }

    public int getTempoPercorrenza(){
        return tempoPercorrenza;
    }
    private void percorriTragitto(){
        tempoPercorrenza =  (int) (Math.random()* (MAX_TEMPO - MIN_TEMPO)  + MIN_TEMPO);
        try{
            Thread.sleep(tempoPercorrenza);
        }catch (InterruptedException e){}
    }
}
