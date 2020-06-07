package barMod;

import java.util.Random;

public class Persona extends Thread{
    Bar b;
    private final static int MIN_BERE = 20, MAX_BERE = 40, MIN_PAGARE = 5, MAX_PAGARE = 10;

    public Persona(Bar b){
        this.b = b;
    }


    public void run(){
        try {
            int i = b.scegli();
            b.inizia(i);
            Thread.sleep((long) new Random().nextInt(i*(MAX_BERE-MIN_BERE - MAX_PAGARE) +MAX_PAGARE ) + i*(MIN_BERE-MIN_PAGARE) + MIN_PAGARE );
            b.finisci(i);
            System.out.format("Ho %s %d\n", (i==0)?"PAGATO": "BEVUTO", getId());
            i  = 1-i;
            b.inizia(i);
            Thread.sleep((long) new Random().nextInt(i*(MAX_BERE-MIN_BERE - (MAX_PAGARE - MIN_PAGARE)) +MAX_PAGARE - MIN_PAGARE ) + i*(MIN_BERE-MIN_PAGARE) + MIN_PAGARE );
            b.finisci(i);
            System.out.format("Ho %s %d\n", (i==0)?"PAGATO": "BEVUTO", getId());

        }catch(InterruptedException e){}
    }
}
