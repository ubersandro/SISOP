package threading.contocorrente;
public class ContoCorrenteNTS extends ContoCorrente {

    public ContoCorrenteNTS(int depositoIniziale) {
        super(depositoIniziale);
    }

    public void deposita(int importo) {
        this.deposito += importo;
    }

    public void preleva(int importo) {
        this.deposito -= importo;
    }
}
