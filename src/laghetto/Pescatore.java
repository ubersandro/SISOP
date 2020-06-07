package laghetto;

public class Pescatore extends Thread {
    private static final int minAttesa  = 200, maxAttesa = 800;
    Laghetto l ;

    public Pescatore(Laghetto l){
        this.l = l;
    }



    public void run(){
        l.inizia(0);
        if (l.operazione(-1, minAttesa, maxAttesa)) System.out.println("Ho pescato "+ getId());
        else System.out.println("Non sono riuscito a pescare. ");
        allontanati();
        l.finisci(0);
    }




    private void allontanati(){
        try{
            Thread.sleep(1000);
        }catch(InterruptedException e){}
    }
}
