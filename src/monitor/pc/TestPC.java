package monitor.pc;

public class TestPC {
    public static void main(String[] args) {
        int NProd = 2, NCons = 2;
        Buffer b = new Buffer(8, 4);
        Thread[] produttori = new Thread[NProd];
        Thread[] consumatori = new Thread[NCons];

        for(int i=0; i<NCons; ++i){
            (consumatori[i] = new Thread(new Consumatore(b)){
                public boolean equals(Thread x){
                    return Thread.currentThread().getId()==x.getId();
                }
            }).start(); //NAIVE
        }//for

        for(int i=0; i<NProd; ++i){
            (produttori[i] = new Thread(new Produttore(b)){
                public boolean equals(Thread x){
                    return Thread.currentThread().getId()==x.getId();
                }
            }).start();
        }//for
    }//main
}//TestPC
