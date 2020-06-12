package salatv;

import java.util.Random;

public class Persona implements Runnable{
    Sala s;
    Random r = new Random();
    private static final int MAX = 300, MIN = 3;

    public Persona(Sala s){
        this.s = s;
    }


    private int scegliCanale(){
        return r.nextInt(s.getCanali()) + 1;
    }

    private void guardaTV() throws InterruptedException {
        Thread.sleep(r.nextInt(MAX-MIN +1) + MIN );
    }

    public void run(){
        int canale = scegliCanale();
        try{
            while(true) {
                s.entra(canale);
                System.out.println("Sono entrato per guardare il canale "+canale);
                guardaTV();
                System.out.println("Ho finito di guardare il canale " + canale + " e me ne vado...");
                s.esci(canale);
                System.out.println("Sono uscito");
            }
        }catch(InterruptedException e){}
    }
}
