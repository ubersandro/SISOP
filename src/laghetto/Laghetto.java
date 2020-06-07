package laghetto;

public abstract class Laghetto {
    protected final int minPesci;
    protected final int maxPesci;
    protected int numeroPescatori;
    protected int numeroAddetti;
    protected int numeroPesci;


    public Laghetto(int minPesci, int maxPesci, int numeroPescatori, int numeroAddetti){
        numeroPesci = maxPesci;
        this.minPesci = minPesci;
        this.maxPesci = maxPesci;
        this.numeroAddetti = numeroAddetti;
        this.numeroPescatori = numeroPescatori;
    }

    protected abstract void inizia(int t);
    protected abstract void finisci(int t);
    protected boolean operazione(int p, int minAttesa, int maxAttesa){
        try{
            if(numeroPesci+p < minPesci){
                System.out.println("Numero insufficiente di pesci.");
                return false;
            }
            if(numeroPesci + p> maxPesci) {
                System.out.println("Soglia massima raggiunta. ");
                return false;
            }

            else{
                numeroPesci+=p;
                Thread.sleep( (int) (Math.random()* maxAttesa-minAttesa + 1 + minAttesa));
            }
        }catch(InterruptedException e ){}
        return true;
    }

    public void test(){
        for(int i=0; i<numeroPescatori; ++i)
            new Pescatore(this).start();
        for(int i=0; i<numeroAddetti; ++i)
            new Addetto(this).start() ;
    }



}
