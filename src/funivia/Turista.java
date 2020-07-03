package funivia;

public class Turista extends Thread{
    Funivia f;
    private int tipo;
    protected static final int A_PIEDI = 0, IN_BICI = 1;


    public Turista(int tipo, Funivia f){
        this.tipo = tipo;
        this.f = f;
    }

    public void run(){
        try {
            f.turistaSali(tipo);
            f.turistaScendi(tipo);
        }catch (InterruptedException e){

        }
    }

    public String toString(){
        return "Turista " + ((tipo==A_PIEDI) ? "a piedi ": "in bicicletta ") + "con ID: " + getId();
    }
}
