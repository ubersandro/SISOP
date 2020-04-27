package threading.crypto;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import java.io.*;
import java.security.NoSuchAlgorithmException;

public class EsercizioCrittografia {
    public static void main(String[] args) throws IOException, NoSuchPaddingException, NoSuchAlgorithmException {
        File f = new File("C:\\Users\\aless\\Desktop\\document2020.encryptedOK");
        BufferedInputStream b = new BufferedInputStream(new FileInputStream(f));
        byte[] a = new byte[(int)f.length()];
        b.read(a);
        b.close();
        byte[] result=null;
        try {
            Cipher c = Cipher.getInstance("AES");
            result = Util.decrypt(c, "0000001618033988", a);
        }catch (CryptoException e) {
            e.printStackTrace();
        }
        BufferedOutputStream bout = new BufferedOutputStream(new FileOutputStream("C:\\Users\\aless\\Desktop\\Decriptato.txt"));
        bout.write(result);
        bout.close();
        //f.delete();
    }
}
