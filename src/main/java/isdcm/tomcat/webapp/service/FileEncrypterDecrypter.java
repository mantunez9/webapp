package isdcm.tomcat.webapp.service;

import lombok.extern.slf4j.Slf4j;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import java.io.*;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Slf4j
public class FileEncrypterDecrypter {

    private final SecretKey secretKey;
    private final Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");

    public FileEncrypterDecrypter(SecretKey secretKey) throws NoSuchPaddingException, NoSuchAlgorithmException {
        this.secretKey = secretKey;
    }

    public void encrypt(String inputFile, String ouputFile) throws InvalidKeyException, FileNotFoundException {

        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] iv = cipher.getIV();

        File file = new File(inputFile);
        FileInputStream fileIn = new FileInputStream(file);

        try (FileOutputStream fileOut = new FileOutputStream(ouputFile);

             CipherOutputStream cipherOut = new CipherOutputStream(fileOut, cipher)) {
            fileOut.write(iv);

            byte[] fileContent = new byte[(int) file.length()];
            fileIn.read(fileContent);
            String s = new String(fileContent);

            cipherOut.write(s.getBytes());

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void decrypt(String inputFile, String ouputFile) {

        try (FileInputStream fileIn = new FileInputStream(inputFile);
             FileOutputStream fout = new FileOutputStream(ouputFile)) {

            byte[] fileIv = new byte[16];
            fileIn.read(fileIv);
            cipher.init(Cipher.DECRYPT_MODE, secretKey, new IvParameterSpec(fileIv));

            try (
                    CipherInputStream cipherIn = new CipherInputStream(fileIn, cipher);
                    InputStreamReader inputReader = new InputStreamReader(cipherIn);
                    BufferedReader reader = new BufferedReader(inputReader)
            ) {

                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }

                fout.write(sb.toString().getBytes());

            }

        } catch (IOException | InvalidAlgorithmParameterException | InvalidKeyException e) {
            e.printStackTrace();
        }

    }

}
