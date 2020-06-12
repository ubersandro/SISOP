package tesoro;

import java.util.Random;

public class Cercatore extends Thread {
    private static final int MIN = 2, MAX = 5;
    private int daControllare;
    private Mappa m;

    Random r = new Random();
    private int righe, colonne;

    public Cercatore(Mappa m) {
        this.m = m;
        righe = m.getN();
        colonne = m.getM();
        daControllare = righe * colonne;
    }


    public void run() {
        while (/*daControllare > 0*/!this.isInterrupted()) {
            try {
                int x = r.nextInt(righe), y = r.nextInt(colonne);
                if (m.iniziaRicerca(x, y)) {

                    Thread.sleep(r.nextInt(MAX - MIN + 1) + MIN); //ricerca

                    daControllare--;
                    if (m.terminaRicerca(x, y)) {
                        System.out.println("HO VINTO " + getId());//si dovrebbe interrompere
                        //break;
                    }
                }
            } catch (InterruptedException e) {
                //System.out.println("Interrotto" + getId());
               // break;
                interrupt();
            }
            /*System.out.println(getId());
            System.out.println(getState());*/
        }

    }
}
