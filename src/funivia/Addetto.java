package funivia;

public class Addetto extends Thread{
    Funivia f;
    private static final int TEMPO_SALITA = 5;
    private static final int TEMPO_DISCESA = 2;

    public Addetto(Funivia f){
        this.f = f;
    }

    public void run(){
        while(true){
            try{
                f.pilotaStart();
                Thread.sleep(TEMPO_SALITA*100); //5 secondi
                f.pilotaEnd();
                Thread.sleep(TEMPO_DISCESA*100);//2 secondi
            }catch(InterruptedException e){}
        }
    }
}
