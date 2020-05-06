package threading.esercizi;

import java.util.concurrent.atomic.AtomicInteger;

public class MatriceTS extends Matrice {
    AtomicInteger m[][];
    int [][] a ;

    public MatriceTS(int a[][]) {
        this.a= a;
        m = new AtomicInteger[a.length][a[0].length];
        for (int i = 0; i < a.length; ++i)
            for (int j = 0; j < a[0].length; ++j)
                m[i][j] = new AtomicInteger(a[i][j]);
    }

    public void decrementa(RC opzione, int indice) {
        if (opzione == RC.RIGA) for (int i = 0; i < m[0].length; i++) a[indice][i] = m[indice][i].addAndGet(-1);
        else for (int i = 0; i < m.length; i++) a[i][indice] = m[i][indice].addAndGet(-1);
    }//decrementa
}
