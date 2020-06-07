package aziendaAgricola;


import java.util.LinkedList;
import java.util.concurrent.locks.*;


public class AziendaLC extends Azienda {
    Lock l = new ReentrantLock();
    Condition cassa = l.newCondition(), possoPrelevare = l.newCondition() , rifornimento = l.newCondition();
    LinkedList<Thread> codaCassa = new LinkedList<>(), codaMagazzino = new LinkedList<>();

    public AziendaLC(int numeroClienti){
        super(numeroClienti);
    }

    @Override
    protected void compraSacchi(int numeroSacchi) throws InterruptedException {
        l.lock();
        try{
            codaCassa.addLast(Thread.currentThread());
            while(!((sacchiDisponibili >= numeroSacchi) && codaCassa.getFirst() == Thread.currentThread()))cassa.await();
            codaCassa.removeFirst();
            sacchiDisponibili -= numeroSacchi;
            incasso+= 3* numeroSacchi;
            cassa.signalAll();
        }finally{
            l.unlock();
        }
    }

    @Override
    protected void ritiraSacchi(int numero) throws InterruptedException {
        l.lock();
        try{
            codaMagazzino.addLast(Thread.currentThread());
            while(! ( Thread.currentThread() == codaMagazzino.getFirst())){
                possoPrelevare.await();
            }
            codaMagazzino.removeFirst();
            Thread.sleep(1*numero);
            possoPrelevare.signalAll();
            rifornimento.signal();
        }finally{
            l.unlock();
        }
    }

    @Override
    protected void ripristina() throws InterruptedException {
        l.lock();
        try{
            while(sacchiDisponibili>0) rifornimento.await();
            Thread.currentThread().sleep(TEMPO_MAGAZZINIERE * 100);
            sacchiDisponibili = sacchiMax;
            cassa.signalAll();
        }finally{
            l.unlock();
        }
    }

    public static void main(String[] args) throws InterruptedException{
        new AziendaLC(2000).test();
    }
}
