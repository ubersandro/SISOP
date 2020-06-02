package casello;

import java.util.Random;


public class Veicolo extends Thread{
    int casello;
    Autostrada a;

    public Veicolo(Autostrada a){
        this.a = a;
        casello = a.getNumCaselli();
    }//Veicolo

    //Una sola volta
    public void run(){
        int casello = (new Random()).nextInt(a.getNumCaselli()); //genera il casello
        double distanza = (new Random()).nextDouble()*50+50;
        viaggia(distanza);
        a.accodati(casello);
        System.out.printf("Il veicolo %d si è accodato al casello %d dopo aver percorso %3.2f km.\n", getId(), casello, distanza);
        double cifra = a.getTariffa()*distanza;
        a.pagamento(cifra, casello);
        System.out.printf("Il veicolo %d ha pagato %3.2f euro al casello %d\n", this.getId(), cifra, casello);
    }


    private void viaggia(double distanza){
        try {
            int d = (int) distanza;
            int tempo = /*40 * */d;
            Thread.sleep(tempo /** 1000*/);//TimeUnit rivedi
        }catch (InterruptedException e){}
    }
}
