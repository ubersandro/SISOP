package soluzioniMie.garaSci;



public class Agente extends Thread{
    Gara g;


    public Agente(Gara g){
        this.g = g;
    }

    public void run(){
        int prossimoSciatore = 0;
        try{
            while(g.prossimo()){
                (g.getSciatore(prossimoSciatore)).start();
                prossimoSciatore++;
                Thread.sleep(1*1000);//20 secondi
            }
            for(int i=0; i < g.getNumeroSciatori(); i++)g.getSciatore(i).join();
            //sciatori arrivati
            g.fine();
        }catch(InterruptedException e){}
    }

}
