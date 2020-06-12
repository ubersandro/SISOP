package piscina;

import java.util.*;

public class Persona extends Thread {
    Piscina p;
    private int corsia;
    Random r = new Random();

    public Persona(Piscina p){
        this.p = p;
    }

    public void setCorsia(int c){
        corsia = c;
    }

    public int getCorsia(){
        return corsia;
    }

    public void run(){
        try {
            p.entra();
            System.out.println("Sono entrato");
            p.esci();
        }catch(InterruptedException e){}
    }

}
