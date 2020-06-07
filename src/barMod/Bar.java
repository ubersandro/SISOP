package barMod;

public abstract class Bar {
    protected static final int BERE = 1, PAGARE = 0;
    protected int postiDisponibili = 4;
    protected boolean cassaLibera = true;
    protected int[] persone = new int[2];
    protected int clienti;

    public Bar(int numeroPersone){
        clienti = numeroPersone;
    }

    protected abstract int scegli() throws InterruptedException;
    protected abstract void inizia(int i) throws InterruptedException;
    protected abstract void finisci ( int i) throws InterruptedException;
    protected int scelta(){
        if (persone[PAGARE] == 0) {
            return 1;
        }
        if (persone[BERE] < 4) {

            return 0;
        }
        return (persone[BERE] > persone[PAGARE]) ? 0 : 1;

    }

    public void test(){
        for(int i=0; i< clienti; ++i) new Persona(this).start();
    }

}
