import org.bouncycastle.cms.CMSException;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.security.cert.CertificateEncodingException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class EncryptorTest {

    @BeforeClass
    public static void setUp() {
        Encryptor.generateKeyAndCertificate();
    }

    @Test
    public void testEncryption()
            throws CertificateEncodingException, IOException, CMSException {
        String secretMessage = "My password is 123456Seven";
        //System.out.println("Original Message : " + secretMessage);
        byte[] stringToEncrypt = secretMessage.getBytes();
        byte[] encryptedData = Encryptor.encryptData(stringToEncrypt, Encryptor.getCertificate());
        //System.out.println("Encrypted Message : " + new String(encryptedData));
        byte[] rawData = Encryptor.decryptData(encryptedData, Encryptor.getPrivateKey());
        String decryptedMessage = new String(rawData);
        //System.out.println("Decrypted Message : " + decryptedMessage);
        assertEquals(secretMessage, decryptedMessage);
    }

    @Test
    public void testEDSEncryption() throws Exception {
        String secretMessage = "Try to encrypt it by digital signature";
        byte[] rawData = secretMessage.getBytes();
        byte[] signedData = Encryptor.signData(rawData, Encryptor.getCertificate(),
                Encryptor.getPrivateKey());
        //Boolean check = Encryptor.verifySignedData(signedData);
        assertTrue(Encryptor.verifySignedData(signedData));
    }
}
