package aziendaAgricola;

public abstract class Azienda {
    protected int sacchiMax = 100;
    protected int sacchiDisponibili = sacchiMax;
    protected final static int TEMPO_MAGAZZINIERE = 10; //minuti
    protected int incasso = 0;
    protected int numeroClienti; //non so se mi serve

    protected abstract void compraSacchi(int numeroSacchi) throws InterruptedException;
    protected abstract void ritiraSacchi(int numero) throws InterruptedException;
    protected abstract void ripristina() throws InterruptedException;

    public Azienda(int numeroClienti){
        this.numeroClienti = numeroClienti;
    }



    protected void test() throws InterruptedException{
        Cliente[]clients = new Cliente[numeroClienti];
        for(int i=0; i<numeroClienti; ++i) (clients[i] = new Cliente(this)).start();
        Magazziniere m  = new Magazziniere(this);
        m.setDaemon(true);
        m.start();
        for(int i=0; i<numeroClienti; ++i) clients[i].join();
        System.out.println(this.incasso +" INCASSO.");
    }
}
