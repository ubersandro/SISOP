package threading.esercizi;


enum RC {RIGA, COLONNA}

public abstract class Matrice{


    public abstract void decrementa(RC opzione, int indice);

   /* public String toString(){
        StringBuilder sb = new StringBuilder(200);
        for(int i=0; i<m.length; i++) {
            sb.append('|');
            for (int j = 0; j < m[0].length; j++) {
                 sb.append(m[i][j]);
            }//for
            sb.delete(sb.length()-1, sb.length());
            sb.append('|');
        }//for
        return sb.toString();
    }//toString
*/
}
