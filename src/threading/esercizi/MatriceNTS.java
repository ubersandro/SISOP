package threading.esercizi;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;


public class MatriceNTS extends Matrice{
    int [][] m;
    public MatriceNTS(int a[][]){
        m = a;
                /*= new int [a.length][a[0].length]; //no check sulla dim
        for(int i=0; i<a.length;i++)
            for(int j=0; j< a[0].length; j++)
                m[i][j] = a[i][j];*/
    }//MatriceNTS


    @Override
    public void decrementa(RC opzione, int indice) {
        if(opzione==RC.RIGA)for(int i=0; i<m[0].length;i++) m[indice][i]--;
        else for(int i=0; i<m.length;i++) m[i][indice]--;
    }//decrementa
}
