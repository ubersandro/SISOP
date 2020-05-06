package threading.contocorrente;

public class ContoCorrenteTest {
    public static void main(String args[]) throws InterruptedException {
        int depositoIniziale = 100_000;
        //ContoCorrente cc = new ContoCorrenteNTS(depositoIniziale);
        ContoCorrente cc = new ContoCorrenteAI(depositoIniziale);
        //ContoCorrente cc = new ContoCorrenteSemaphore(depositoIniziale);
        //ContoCorrente cc = new ContoCorrenteLC(depositoIniziale);
        int numCorrentisti = /*10;*/ 200;
        int importo = 100;
        int numOperazioni = /*2;*/ 5000;
        testContoCorrente(cc, numCorrentisti, importo, numOperazioni);
        if (cc.getDeposito() == depositoIniziale) {
            System.out.format("Corretto! Il deposito finale è di %s€", cc.getDeposito());
        } else {
            System.out.format("Errore! Il deposito iniziale era di %s €, il deposito finale è di %s€", depositoIniziale, cc.getDeposito());
        }
    }

    private static void testContoCorrente(ContoCorrente cc, int numCorrentisti, int importo, int numOperazioni) throws InterruptedException {
        Correntista correntisti[] = new Correntista[numCorrentisti];
        for (int i = 0; i < numCorrentisti; i++) {
            correntisti[i] = new Correntista(cc, importo, numOperazioni);
        }
        Thread threadCorrentisti[] = new Thread[numCorrentisti];
        for (int i = 0; i < numCorrentisti; i++) {
            threadCorrentisti[i] = new Thread(correntisti[i]);
            threadCorrentisti[i].start();
        }
        for (int i = 0; i < numCorrentisti; i++) {
            threadCorrentisti[i].join();
        }
    }
}
