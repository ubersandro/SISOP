package soluzioniTelegram.banca;

public class Test {
    public static void main(String[] args) {
        Banca b = new BancaSem();
        Agente a = new Agente(b);
        a.setDaemon(true);
        a.start();
        for(int i=0; i<10; ++i) new Cliente(b).start();

    }
}
