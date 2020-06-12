package monitorNativi;

public class Stampante {
    public synchronized void stampa(String s) throws InterruptedException{
        System.out.println(s);
        Thread.sleep(1000);
    }
}
