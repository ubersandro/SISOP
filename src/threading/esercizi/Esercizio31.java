package threading.esercizi;

import java.util.Arrays;

public class Esercizio31 {
    public static void main(String[] args)  throws InterruptedException{
        int m[][]={ {0,0,0,0,0,0},
                    {0,0,0,0,0,0},
                    {0,0,0,0,0,0},
                    {0,0,0,0,0,0}};
       // Matrice matrice = new MatriceTS(m);
        Matrice matrice = new MatriceNTS(m);
        int righe = m.length, colonne = m[0].length;
        Thread riga[] = new Thread[righe];
        Thread colonna[] = new Thread[colonne];
        for(int i=0; i<righe; i++) (riga[i] = new Worker(matrice, RC.RIGA, i)).start();
        for(int j=0; j<colonne; j++) (colonna[j] = new Worker(matrice, RC.COLONNA, j)).start();
        for(int i=0; i<righe; i++) riga[i].join();
        for(int j=0; j<colonne; j++) colonna[j].join();

        for(int[]a : m)
        {
            System.out.println(Arrays.toString(a));
        }

    }
}
