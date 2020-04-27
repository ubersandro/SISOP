package threading.crypto;


import com.sun.xml.internal.ws.api.model.wsdl.WSDLOutput;

import java.io.*;
import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.security.*;
import java.util.Scanner;

/**
 * Tutte le verifiche si effettuano su sequenza di byte senza scrivere su file utilizzando oggetti Stream ed inficiando l'efficienza.
 */
public class Decripta {
    public static void main(String[] args) throws IOException {
        //input := byte[] a
        File f = new File(args[0]);//nome fornito da riga di comando
        BufferedInputStream bin = new BufferedInputStream(new FileInputStream(f));
        byte[] input = new byte[(int) f.length()];
        bin.read(input);
        //input byte[] initialized

        int n = 8;
        Thread[] t = new Decrypter[n];
        int max = Integer.MAX_VALUE;
        int width = max / n;
        System.out.println("WIDTH :" + width);
        for (int i = 0; i < n; i++) {
            //ogni oggetto thread dispone di una copia locale del file convertito in byte[]
            //l'ho fatto perchè non volevo che il file(convertito in byte[]) fosse una risorsa condivisa tra i thread
            byte[] a = new byte[input.length];
            System.arraycopy(input, 0, a, 0, input.length);
            t[i] = new Decrypter(a, i * width, (i + 1) * width, "SISOP".getBytes());
            t[i].start();
        }//for
    }//main
}//Decripta


class Decrypter extends Thread {
    byte[] fileInput;
    int inf, sup;
    byte[] pattern, result;
    String key;
    Cipher c;

    /**
     * Ogni thread recupera un suo oggetto Cipher locale e lo rende disponibile nel while loop del run per fare le varie prove. In questo
     * modo siamo sollevati dalla necessità di ottenere un nuovo oggetto Cipher ad ogni iterazione del ciclo while interno al run().
     * Per semplicità (siccome la nostra casistica e' basata sullo standard AES) ho omesso la creazione di un campo dedicato alla stringa che identifica
     * lo standard, il che rende la soluzione valida solo per l'AES in questo contesto.
     *
     * @param input
     * @param inf
     * @param sup
     * @param pattern
     */
    public Decrypter(byte[] input, int inf, int sup, byte[] pattern) {
        this.inf = inf;
        this.sup = sup;
        this.pattern = pattern;
        fileInput = input;
        try {
            c = Cipher.getInstance("AES");
        } catch (NoSuchPaddingException | NoSuchAlgorithmException ex) {
            ex.printStackTrace();
        }
    }//Decrypter

    /**
     * Stampa anche il tempo di esecuzione
     */
    public void run() {
        long x = System.currentTimeMillis();
        ext:
        while (inf < sup) {
            try {
                key = String.format("%016d", inf);
                //decryption
                result = Util.decrypt(c, key, fileInput);
            } catch (CryptoException e) {
                //if (result == null) continue;
                // Si è volutamente scelto di non gestire l'eccezione perche' generata in 95 casi su cento quando la chiave non e' quella esatta.
                //E' un po' meno robusto ma in questo caso semplice funziona, in altri non si puo' garantire.
            }//catch

            if (result != null && Util.present(result, pattern)) { //non e' strettamente necessario verificare se result==null
                //in caso di successo stampiamo l'identificatore del thread, la chiave e il tempo impiegato.
                System.out.printf("Match found on thread %d. That's the key %s \n", getId(), key);
                long y = System.currentTimeMillis();
                System.out.println("Time elapsed: " + ((y - x) / 1000) + "seconds");
                System.exit(0);//Fermiamo l'applicazione con una System.exit(0) in modo tale da terminare tutti i thread che nel frattempo stanno eseguendo.
                //non e' una soluzione furba nel caso in cui si voglia continuare ad eseguire il main o qualche altro thread.
                //Ma fondamentalmente questo programma ha finito appena trova la chiave. Per semplicità la decrittazione effettiva avviene altrove.
            } else inf++;
        }//while

        System.out.println("No match on Thread ID " + getId());
    }//run


}