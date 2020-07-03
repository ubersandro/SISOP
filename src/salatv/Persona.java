package salatv;

import java.util.Random;

public class Persona implements Runnable{
    Sala s;
    Random r = new Random();
    private static final int MAX = 800, MIN = 80;
    private int id;

    public Persona(Sala s, int id){
        this.s = s;
        this.id = id;
    }


    private int scegliCanale(){
        return r.nextInt(s.getCanali()) + 1;//un numero casuale tra 1 e 8
    }

    private void guardaTV() throws InterruptedException {
        Thread.sleep(r.nextInt(MAX-MIN ) + MIN);
    }

    public void run(){
        try{
            //while(true) {
                int canale = scegliCanale();
                s.entra(canale);
                System.out.println("Sono entrato per guardare il canale "+canale + " THREAD: "+ id);
                guardaTV();
                System.out.println("Ho finito di guardare il canale " + canale + " e me ne vado... "+ "THREAD: "+ id);
                s.esci(canale);
                System.out.println("Sono uscito "+ "THREAD: "+ id);
            //}
        }catch(InterruptedException e){}
    }
}
