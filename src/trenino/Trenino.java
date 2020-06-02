package trenino;
/*
NON FUNZIONA
 */
public abstract class Trenino {
    protected final int numeroCabine = 10;
    protected final int capacita = 10;
    protected final int giroCompleto = 10;
    protected int[] cabine = new int[10]; //una cabina ha concluso un giro quando è di nuovo in posizione 0
    protected int turistiInAttesa;
    protected int cabinaAttuale;

    public abstract void turSali();
    public abstract void turScendi();
    public abstract void impFaiScendere();
    public abstract void impFaiSalire();
    public abstract void impMuovi();



    public void test() throws InterruptedException{

        Thread impiegato = new Impiegato(this);
        impiegato.setDaemon(true);
        impiegato.start();
        int i=0;
        while(i++<50){
            (new Turista(this)).start();
           // Thread.sleep(100);
        }
    }

}
