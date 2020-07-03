package funivia;

import java.util.concurrent.Semaphore;

public class FuniviaSem extends Funivia{
    private Semaphore [] operazione = {new Semaphore(0), new Semaphore(0)};
    private Semaphore sonoSaliti = new Semaphore(0);//farà acquire di tre o sei
    private Semaphore possoScendere = new Semaphore(0);



    @Override
    protected void pilotaStart()  throws InterruptedException{
        operazione[tipoAttuale].release(CAPIENZA_MAX); //o 3 o 6 permessi
        sonoSaliti.acquire(CAPIENZA_MAX); //quando sono saliti può andare
        tipoAttuale = (tipoAttuale+1) % 2;//round-robin
        System.out.println("NUOVO TIPO "+ tipoAttuale);
    }

    @Override
    protected void pilotaEnd() {
        possoScendere.release(CAPIENZA_MAX);
        stampa();
    }

    @Override
    protected void turistaSali(int t) throws InterruptedException{
        operazione[t].acquire(1+1*t);//dipende dal tipo
        cabina.add(Thread.currentThread());
        sonoSaliti.release(1+1*t); //rilascia un permesso per dire che è salito
    }

    @Override
    protected void turistaScendi(int t) throws InterruptedException{
        possoScendere.acquire(1+1*t);
//        System.out.println("Arrivederci");
    }

    public static void main(String[] args) {
        new FuniviaSem().test(200);
    }
}
