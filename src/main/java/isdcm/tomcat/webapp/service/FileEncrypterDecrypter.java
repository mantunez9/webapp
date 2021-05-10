package isdcm.tomcat.webapp.service;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import java.io.*;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class FileEncrypterDecrypter {

    private final SecretKey secretKey;
    private final Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");

    FileEncrypterDecrypter(SecretKey secretKey) throws NoSuchPaddingException, NoSuchAlgorithmException {
        this.secretKey = secretKey;
    }

    void encrypt(String content, String fileName) throws InvalidKeyException {
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] iv = cipher.getIV();

        try (FileOutputStream fileOut = new FileOutputStream(fileName);

             CipherOutputStream cipherOut = new CipherOutputStream(fileOut, cipher)) {
            fileOut.write(iv);
            cipherOut.write(content.getBytes());

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    String decrypt(String fileName) {

        String content = "";

        try (FileInputStream fileIn = new FileInputStream(fileName)) {

            byte[] fileIv = new byte[16];
            fileIn.read(fileIv);
            cipher.init(Cipher.DECRYPT_MODE, secretKey, new IvParameterSpec(fileIv));

            try {

                CipherInputStream cipherIn = new CipherInputStream(fileIn, cipher);
                InputStreamReader inputReader = new InputStreamReader(cipherIn);
                BufferedReader reader = new BufferedReader(inputReader);

                StringBuilder sb = new StringBuilder();
                String line;

                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }

                content = sb.toString();

            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (IOException | InvalidAlgorithmParameterException | InvalidKeyException e) {
            e.printStackTrace();
        }

        return content;

    }

}
