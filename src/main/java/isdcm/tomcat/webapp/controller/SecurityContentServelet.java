package isdcm.tomcat.webapp.controller;

import isdcm.tomcat.webapp.service.FileEncrypterDecrypter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.NoSuchAlgorithmException;

@Controller
@RequestMapping("/securityContent")
public class SecurityContentServelet {

    SecretKey secretKey;
    FileEncrypterDecrypter fileEncrypterDecrypter;

    {
        try {
            secretKey = KeyGenerator.getInstance("AES").generateKey();
            fileEncrypterDecrypter = new FileEncrypterDecrypter(secretKey);
        } catch (NoSuchPaddingException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    @GetMapping
    public String doGet(HttpServletRequest req) {
        try {
            HttpSession session = req.getSession(false);
            if (session != null && session.getAttribute("user") != null)
                return "securityContent";
            return "login";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "error";
    }

    @PostMapping
    public String doPost(HttpServletRequest req) {
        try {

            String inputFile = req.getParameter("inputFile");
            String option = req.getParameter("option");
            String outputFile = req.getParameter("outputFile");

            switch (option) {
                case "enc":
                    fileEncrypterDecrypter.encrypt(inputFile, outputFile);
                    break;
                case "dec":
                    fileEncrypterDecrypter.decrypt(inputFile, outputFile);
                    break;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "securityContent";
    }

}
