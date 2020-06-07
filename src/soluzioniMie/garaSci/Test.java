package soluzioniMie.garaSci;

public class Test {
    public static void main(String[] args) {
        Gara g = new GaraSem(10);
        Agente a = new Agente(g);
        a.start();
    }
}
