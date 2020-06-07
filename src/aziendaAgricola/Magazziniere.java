package aziendaAgricola;

public class Magazziniere extends Thread{
    Azienda a;

    public Magazziniere(Azienda a){
        this.a = a;
    }

    public void run(){
        try {
            while (true) {
                a.ripristina();
                System.out.println("Ripristinati.");
            }

        }catch(InterruptedException e){}
    }
}
