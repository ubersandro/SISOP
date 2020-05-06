package threading.contocorrente;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ContoCorrenteLC extends ContoCorrente {

    private Lock l = new ReentrantLock();

    public ContoCorrenteLC(int depositoIniziale) {
        super(depositoIniziale);
    }

    public void deposita(int importo) {
        l.lock();
        try {
            deposito += importo;
        } finally {
            l.unlock();
        }
    }

    public void preleva(int importo) {
        l.lock();
        try {
            deposito -= importo;
        } finally {
            l.unlock();
        }
    }

}
