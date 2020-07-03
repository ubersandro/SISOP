package threading.esercizi;

public class Test {
    public static void main(String[] args) {
        final Thread[] ts =new Thread[2];
        ts[0] = new Thread(()->{

            try{
                Thread.sleep(1000);
            }catch (InterruptedException e){};
            System.out.println("STATO DI T "+ ts[1].getState() );

        });
         ts[1] = new Thread(()->{

            try{
                ts[0].join();
            }catch (InterruptedException e){};

            System.out.println("CIA");
        });
         ts[0].start(); ts[1].start();


    }
}
