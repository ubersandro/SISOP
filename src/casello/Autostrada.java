package casello;

public abstract class Casello {
    protected final int N, T, V;
    protected int incasso;

    public Casello(int veicoli, int tariffa, int caselli){
        N = caselli; V = veicoli; T = tariffa; incasso = 0;
    }

    public abstract void pagamento(int cifra, int casello);

    public abstract void accodati(int casello);
    public void test(){

    }
}
