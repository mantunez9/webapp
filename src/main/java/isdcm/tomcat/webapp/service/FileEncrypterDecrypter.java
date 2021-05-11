package isdcm.tomcat.webapp.service;

import lombok.extern.slf4j.Slf4j;

import javax.crypto.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.NoSuchAlgorithmException;

@Slf4j
public class FileEncrypterDecrypter {

    private final SecretKey secretKey;
    private final Cipher cipher = Cipher.getInstance("AES");
    int read;

    public FileEncrypterDecrypter(SecretKey secretKey) throws NoSuchPaddingException, NoSuchAlgorithmException {
        this.secretKey = secretKey;
    }

    public void encrypt(String inputFile, String ouputFile) {

        try {

            cipher.init(Cipher.ENCRYPT_MODE, secretKey);

            File file = new File(inputFile);
            FileInputStream fileIn = new FileInputStream(file);
            FileOutputStream fileOut = new FileOutputStream(ouputFile);

            CipherInputStream cis = new CipherInputStream(fileIn, cipher);

            while ((read = cis.read()) != -1) {
                fileOut.write((char) read);
                fileOut.flush();
            }

            fileOut.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void decrypt(String inputFile, String ouputFile) {

        try {

            FileInputStream fileIn = new FileInputStream(inputFile);
            FileOutputStream fileOut = new FileOutputStream(ouputFile);

            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            CipherOutputStream cos = new CipherOutputStream(fileOut, cipher);

            while ((read = fileIn.read()) != -1) {
                cos.write(read);
                cos.flush();
            }

            cos.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
