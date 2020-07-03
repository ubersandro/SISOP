package funivia;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;

public abstract class Funivia {
    protected HashSet<Thread> cabina = new HashSet<>();
    protected static final int MAX_BICI = 3 , MAX_A_PIEDI = 6;
    protected static final int CAPIENZA_MAX = 6;
    protected int tipoAttuale;

    protected abstract void pilotaStart() throws InterruptedException;
    protected abstract void pilotaEnd();
    protected abstract void turistaSali(int t) throws InterruptedException;
    protected abstract void turistaScendi(int t) throws InterruptedException;

    protected void stampa(){
        Iterator<Thread > it = cabina.iterator();
        while(it.hasNext()){
            System.out.println(it.next());
            it.remove();
//            System.out.println("rimosso");
        }
    }

    public void test(int numero){
        Random r = new Random();
        Addetto a = new Addetto(this);
        a.setDaemon(true);
        a.start();
        for(int i=0; i<numero; ++i) (new Turista(r.nextInt(2), this)).start();
    }
}
