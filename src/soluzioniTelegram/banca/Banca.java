package soluzioniTelegram.banca;

public abstract class Banca {
    /*protected */int numeroClienti;
    protected int[] denaroResiduo;
    /*protected*/ int numeroSportelli;
    protected final int sommaMax = 10_000;

    public Banca(int numeroClienti , int numeroSportelli){
        this.numeroClienti = numeroClienti;
        denaroResiduo = new int[numeroSportelli];
        this.numeroSportelli = numeroSportelli;
        for(int i=0; i<numeroSportelli;++i) denaroResiduo[i] = sommaMax;
    }//builder

    public abstract boolean prelievo(int sportello, int denaro);

    public abstract void  rifornisci();

    public int getNumeroSportelli(){
        return  numeroSportelli;
    }



    public void test(){
        Agente a = new Agente(this);
        a.setDaemon(true);
        a.start();
        for(int i=0; i<numeroClienti;++i) new Cliente(this).start();
    }

}
