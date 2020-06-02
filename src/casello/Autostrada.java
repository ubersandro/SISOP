package casello;

public abstract class Autostrada {
    protected final int N, T, V;
    protected double incasso;

    public Autostrada(int veicoli, int tariffa, int caselli){
        N = caselli; V = veicoli; T = tariffa; incasso = 0;
    }

    public abstract void pagamento(double cifra, int casello);

    public abstract void accodati(int casello);
    public void test(){
        Thread[] veicoli = new Thread[V];
        for(Thread t : veicoli) {
            t = new Veicolo(this);
            t.start();
        }
    }

    public int getNumCaselli(){return N; }
    public int getTariffa(){return T; }
}
