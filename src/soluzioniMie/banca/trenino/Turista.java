package soluzioniMie.banca.trenino;

public class Turista extends Thread{
    Trenino t;
    private int cabina;

    public Turista(Trenino t){
        this.t = t ;
    }
    public void setCabina(int x){cabina = x; }
    public int getCabina(){return cabina;  }

    public void run(){
        t.turSali();
        System.out.println("Sto salendo sul treno "+ getId());
        t.turScendi();
        System.out.println("Scendo "+getId());
    }
}
