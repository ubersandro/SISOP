package threading.contocorrente;

import java.util.concurrent.atomic.AtomicInteger;

public class ContoCorrenteAI extends ContoCorrente {

    protected AtomicInteger deposito;

    public ContoCorrenteAI(int depositoIniziale) {
        super(depositoIniziale);
        deposito = new AtomicInteger(depositoIniziale);
    }

    public void deposita(int importo) {
        deposito.addAndGet(importo);
    }

    public void preleva(int importo) {
        deposito.addAndGet(-importo);
    }

    public int getDeposito() {
        return deposito.get();
    }

}
