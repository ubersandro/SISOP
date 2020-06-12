package monitorNativi;

public class Test {
    public static void main(String[] args) {
        Stampante s = new Stampante();
        for(int i=0; i<100; ++i){
            new Stampatore(s).start();
        }

    }
}

class Stampatore extends Thread{
    Stampante s;
    public Stampatore(Stampante s){
        this.s = s;
    }


    public void run(){

        try{
            while(true){
                s.stampa("CIAO" + getId());
            }
        }catch(InterruptedException e){}
    }
}