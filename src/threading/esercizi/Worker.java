package threading.esercizi;

public class Worker extends Thread{
    Matrice m; RC op; int index;

    public Worker(Matrice m, RC opzione, int indice){
        this.m = m; this.index=indice; this.op=opzione;
    }

    @Override
    public void run(){
        m.decrementa(op, index);
    }//run
}
