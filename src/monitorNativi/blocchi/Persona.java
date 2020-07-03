package monitorNativi.blocchi;

import java.security.PrivilegedAction;

public class Persona  implements Runnable{
    Conto c;
    public Persona(Conto c){
        this.c = c;
    }

    public void run(){
        try {
            c.modifica(1);
            c.modifica(-1);
        }catch (InterruptedException e){}
    }

    public static void main(String[] args) {
        Conto c = new Conto();
        int N = 2000;
        Thread[]threads = new Thread[N];
        for(int i=0; i<N; ++i){
            threads[i] = new Thread(new Persona(c));
            threads[i].start();
        }
        try {
            for(int i=0; i<N; ++i){
                threads[i].join();
            }
        }catch (InterruptedException e){}

        System.out.println(c);

    }
}

