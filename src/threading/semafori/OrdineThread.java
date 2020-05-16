package threading.semafori;

import java.util.concurrent.Semaphore;

public class OrdineThread {
    private static final int N = 200;
    private static Semaphore turn = new Semaphore(1,true);

    public static void main(String[] args) {
        Thread atleti[] = new Thread[N];

        for(int i=0; i<N; ++i ){
            (atleti[i] = new Atleta(i)).start();
        }



    }
    private static class Atleta extends Thread{
        private int indice;
        public Atleta(int i) {
            indice = i;
        }

        @Override
        public void run() {
            try{
                while(true) {
                    if(turn.availablePermits()==indice+1){
                        turn.acquire(indice + 1);
                        System.out.format("Ciao sono il thread %d\n", indice);
                        turn.release(indice + 1 + 1);
                        this.interrupt();
                        break;
                    }
                }
            }catch (InterruptedException e ){e.printStackTrace();}
        }
    }

}
