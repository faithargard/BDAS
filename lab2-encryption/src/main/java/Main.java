import org.bouncycastle.cms.CMSException;

import java.io.IOException;
import java.security.cert.CertificateEncodingException;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        Encryptor.generateKeyAndCertificate();
        byte[] testStr = "string to be tested for encryption".getBytes();
        System.out.println("Current string: " + new String(testStr));
        try {
            byte[] encrStr = Encryptor.encryptData(testStr,
                    Encryptor.getCertificate());
            System.out.println("Encrypted string: " + new String(encrStr));
            byte[] decrStr = Encryptor.decryptData(encrStr,
                    Encryptor.getPrivateKey());
            System.out.println("Decrypted string: " + new String(decrStr));
            System.out.println("Current string equals decrypted ? " + Arrays.equals(testStr, decrStr));
        } catch (CertificateEncodingException | IOException
                | CMSException e) {
            e.printStackTrace();
        }
    }
}
