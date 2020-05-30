package monitor.pc;

import java.util.Random;

public class Consumatore implements Runnable{
    private Buffer b;

    public Consumatore(Buffer b){
        this.b = b;
    }

    public void run(){
        while(true){
            try{
                int x = b.get();
                Thread.sleep(1000);
                System.out.printf("Consumato elemento %d\n", x);
            }catch (InterruptedException e ){
                e.printStackTrace();
            }
        }
    }
}
