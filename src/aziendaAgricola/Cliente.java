package aziendaAgricola;

import java.util.Random;

public class Cliente extends Thread{
    Azienda a;
    private static int MIN = 1, MAX = 10;

    public Cliente(Azienda a){
        this.a = a;
    }

    public void run(){
        try {
            int numero =5; //scegli();
            a.compraSacchi(numero); //lo fa mettere in coda alla cassa;
            System.out.println("Ho comprato "+ numero + " sacchi.  " + getId() );
            a.ritiraSacchi(numero);
            System.out.println("Ho ritirato "+ numero + " sacchi. " + getId());
        }catch (InterruptedException e ){}

    }

    private int scegli(){
        return new Random().nextInt(MAX) + MIN;
    }
}
