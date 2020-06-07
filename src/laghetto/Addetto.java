package laghetto;

public class Addetto extends Thread {
    private final static int minAttesa = 300, maxAttesa = 600;
    Laghetto l;

    public Addetto(Laghetto l){
        this.l = l;
    }

    public void run(){
        while(true){
            l.inizia(1); //ripopola
            if(l.operazione(+10, minAttesa, maxAttesa)) System.out.println("Ho rifornito il laghetto "+getId());
            else System.out.println("Non sono riuscito a rifornire il laghetto "+ getId());
            allontanati();
            l.finisci(1);
        }
    }


    private void allontanati(){
        try{
            Thread.sleep(3000);
        }catch(InterruptedException e){}
    }
}
