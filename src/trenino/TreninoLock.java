package trenino;

import java.util.concurrent.locks.*;
/*
NON FUNZIONA
 */
public class TreninoLock extends Trenino {
    Lock l;
    Condition[] postiCabine;
    Condition possoSalire;
    Condition sonoScesi;
    Condition saliti;
    boolean permesso;

    public TreninoLock(){
        l = new ReentrantLock();
        postiCabine = new Condition[capacita];
        possoSalire = l.newCondition();
        for(int i=0; i< capacita ; ++i) postiCabine[i] = l.newCondition();
        sonoScesi = l.newCondition();
        saliti = l.newCondition();
    }

    @Override
    public void turSali() {
        l.lock();
        try{
            turistiInAttesa++;
            while(!permesso){
                possoSalire.await();
            }
            //((Turista)Thread.currentThread()).setCabina(cabinaAttuale);
            turistiInAttesa--;
            cabine[cabinaAttuale]++;
            if(cabine[cabinaAttuale]==10){
                saliti.signal(); permesso = false;
            }
        }catch(InterruptedException e){
        }finally{
            l.unlock();
        }
    }

    @Override
    public void turScendi() {
        l.lock();
        try {
            /*while(cabinaAttuale!=((Turista)Thread.currentThread()).getCabina())
                postiCabine[((Turista)Thread.currentThread()).getCabina()].await(); //ogni turista aspetta la sua cabina*/
            postiCabine[cabinaAttuale].await();
            cabine[cabinaAttuale]--;
            if(cabine[cabinaAttuale]==0)sonoScesi.signal();
        }catch(InterruptedException e){}
        finally {
            l.unlock();
        }
    }

    @Override
    public void impFaiScendere() {
        l.lock();
        try {
            if(cabine[cabinaAttuale]!=0) {
                postiCabine[cabinaAttuale].signalAll();
                while (cabine[cabinaAttuale] != 0) sonoScesi.await();
                System.out.println("Scesi tutti cabina "+ cabinaAttuale);
            }
            }catch(InterruptedException e){}
        finally {
            l.unlock();
        }
    }

    @Override
    public void impFaiSalire() {
        l.lock();
        try {
            if (turistiInAttesa >= 10) {
                permesso = true;
                possoSalire.signalAll();
                while (cabine[cabinaAttuale] != 10) saliti.await();
                System.out.println("Saliti 10 turisti in cabina "+ cabinaAttuale);
            }
        }catch (InterruptedException r){}

        finally{
            l.unlock();
        }
    }

    @Override
    public void impMuovi() {
        l.lock();
        cabinaAttuale = (cabinaAttuale+1)%capacita ;
        try {
            Thread.sleep(3 * 100);
        }catch (InterruptedException e ){}
        finally {
            l.unlock();
        }
    }


    public static void main(String[] args) throws InterruptedException{
        new TreninoLock().test();
    }
}
