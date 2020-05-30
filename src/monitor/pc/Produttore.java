package monitor.pc;

import java.util.*;

public class Produttore implements Runnable{
    private Buffer b;

    public Produttore(Buffer b){
        this.b = b;
    }

    public void run(){
        while(true){
            try{
                int x = (new Random()).nextInt();
                Thread.sleep(1000);
                b.put(x);
                System.out.printf("Inserito elemento %d\n", x);

            }catch (InterruptedException e ){
                e.printStackTrace();
            }
        }
    }
}
