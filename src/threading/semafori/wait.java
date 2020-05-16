package threading.semafori;

public class wait {
    public static void main(String[] args) throws InterruptedException {
        Thread a[] = {new Thread(()->{
            while(true) {
                System.out.println("CIAO");
                try {
                    Thread.sleep(1000);
                }catch (InterruptedException e ){}
            }
        })};
        a[0].start();
        Thread.sleep(5000);
        a[0].wait();
        System.out.println("WAIT");
        Thread.sleep(5000);
        a[0].notify();
        a[0].interrupt();

    }
}
