package threading.crypto;

import javax.crypto.*;
import javax.crypto.spec.*;
import java.io.*;
import java.security.*;


public class Util {
    public static void main(String[] args) throws IOException {
       /* File fin = new File("C:\\SISOP\\test.txt");
        String keyTemp = String.format("%016d", 4999999);
        InputStream inStr = new FileInputStream(fin);
        byte[] infile = new byte[(int)fin.length()];
        inStr.read(infile);
        byte[] crypt=null;
        try{
            crypt = encrypt(Cipher.getInstance("AES"), keyTemp, infile );
        }catch (Exception exc){
            System.out.println("Fallimmu");
        }
        BufferedOutputStream bous = new BufferedOutputStream(new FileOutputStream(new File("Criptato")));
        bous.write(crypt);
        bous.close();
*/


        File f = new File("C:\\SISOP\\Criptato");
        InputStream a = new FileInputStream(f);
        byte[] input = new byte[(int)f.length()];
        a.read(input);

       int n = 8;
        Thread [] t = new Decrypter[n];
        int max  = 5000000;
        int width = max/n;
        System.out.println("WIDTH :"+width);
        for(int i=0; i<n; i++){
                byte [] x = new byte[input.length];
                System.arraycopy(input,0,x,0,input.length);
                t[i] = new Decrypter(x , i*width, (i+1)*width, "SISOP".getBytes());

            t[i].start();
        }//for*/
        /*byte[] result = null;
        try {
            Cipher c = Cipher.getInstance("AES");
            result = Util.decrypt(c,String.format("%016d",1999999), input);
        }catch (CryptoException e ) {
            e.printStackTrace();}
        catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        if(present(result, "ciao".getBytes())) System.out.println("PRESENTE");
        else System.out.println("Mannaia a dio");
        System.out.println(Arrays.toString(result));
        System.out.println(Arrays.toString("ciao".getBytes()));*/

       /* try {
            //Cipher c = Cipher.getInstance("AES");
            result = Util.decrypt(c,String.format("%016d",1999999), input);
        }catch (CryptoException e ) {
            e.printStackTrace();}
       /*catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }*/

       /* if(present(result, "ciao".getBytes())) System.out.println("PRESENTE");
        else System.out.println("Mannaia a dio");
        System.out.println(Arrays.toString(result));
        System.out.println(Arrays.toString("ciao".getBytes()));*/
    }

    /**
     * Il metodo legge la sequenza di byte prodotta in output dal processo di decifrazione della chiave usata
     * e verifica se la sequenza cercata è presente
     * con una serie di confronti. Se si ha un match che però porta fuori dall'array
     * di byte del file si restituisce false. Altrimenti si verifica ogni sottosequenza possibile.
     * @param file
     * @param pattern
     * @return
     */
    public static boolean present(byte[] file, byte[] pattern){
        ext: for(int x=0; x<file.length; x++){
            if(file[x]==pattern[0]){
                int filePos = x+1;
                for(int j=1; j<pattern.length; j++) {
                    if (filePos >= file.length) return false;
                    if (file[filePos++] != pattern[j]) continue ext;
                }
                return true;
            }
        }
        return false;
    }//presente

    private static final String ALGORITHM = "AES";



    public static byte[] encrypt(Cipher c, String key, byte[] inputFile)
            throws CryptoException {
        return doCrypto(c,Cipher.ENCRYPT_MODE, key, inputFile);
    }

    public static byte[] decrypt(Cipher c,String key, byte[] inputFile)
            throws CryptoException {
        return doCrypto(c, Cipher.DECRYPT_MODE, key, inputFile);
    }

    /**
     * Notiamo che il metodo solleva un'eccezione anche nel caso in cui la chiave usata è "semplicemente" sbagliata (non nel formato, bensì non e' la chiave esatta).
     * Nel chiamante la gestione dell'eccezione e' quindi poco utile nel caso del brute force. E' il metodo fornito leggermente "snellito" per questioni di efficienza.
     * @param c un oggetto di tipo Cipher è passato a priori nel chiamante per evitare l'aggravio derivante dalla chiamata del metodo Cipher.getInstance()
     *          che altrimenti verrebbe chiamato ad ogni iterazione(ho notato che un po' rallenta l'esecuzione).
     * @param cipherMode
     * @param key
     * @param inputFile
     * @return
     * @throws CryptoException
     */
    private static final byte[] doCrypto(Cipher c,  int cipherMode, String key, byte[] inputFile) throws CryptoException {
        try {
            Key secretKey = new SecretKeySpec(key.getBytes(), ALGORITHM);
            c.init(cipherMode, secretKey);
            final byte[] end = c.doFinal(inputFile);
            return end;
        } catch (InvalidKeyException | BadPaddingException | IllegalBlockSizeException ex) {
            throw new CryptoException("Error encrypting/decrypting file", ex);
        }//catch
    }//doCrypto
}//Util

class CryptoException extends Exception {

    public CryptoException() {
    }

    public CryptoException(String message, Throwable throwable) {
        super(message, throwable);
    }
}

