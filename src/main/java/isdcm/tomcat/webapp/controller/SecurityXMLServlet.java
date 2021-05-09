package isdcm.tomcat.webapp.controller;

import org.apache.xml.security.encryption.XMLEncryptionException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.File;
import java.io.FileOutputStream;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Element;

import javax.crypto.SecretKey;
import javax.crypto.KeyGenerator;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.apache.xml.security.encryption.XMLCipher;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;

@Controller
@RequestMapping("/securityXML")
public class SecurityXMLServlet {

    static {
        org.apache.xml.security.Init.init();
    }

    private static SecretKey symmetricKey = GenerateDataEncryptionKey();

    private static SecretKey GenerateDataEncryptionKey() {
        try {
            String jceAlgorithmName = "AES";
            KeyGenerator keyGenerator = KeyGenerator.getInstance(jceAlgorithmName);
            keyGenerator.init(128);
            return keyGenerator.generateKey();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @GetMapping
    public String doGet(HttpServletRequest req, HttpServletResponse resp) {
        try {
            HttpSession session = req.getSession(false);
            if (session != null && session.getAttribute("user") != null)
                return "securityXML";
            return "login";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "error";
    }

    @PostMapping
    public String doPost(HttpServletRequest req, HttpServletResponse resp) {
        try {
            String inputFile = req.getParameter("inputFile");
            Document xmlDocument = inputFileToDoc(inputFile);
            String option = req.getParameter("option");
            String outputFile = req.getParameter("outputFile");
            boolean encryptContentsOnly = false;
            String onlyContent = req.getParameter("onlyContent");
            if (onlyContent!= null && onlyContent.equals("1"))
                encryptContentsOnly = true;
            String nodes = req.getParameter("nodes");
            switch (option) {
                case "enc":
                    encrypt(xmlDocument, nodes, outputFile, encryptContentsOnly);
                    break;
                case "dec":
                    decrypt(xmlDocument, outputFile, encryptContentsOnly);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "securityXML";
    }

    public static void encrypt(Document doc, String nodes, String outputFile, boolean encryptContentsOnly) throws XMLEncryptionException, Exception {
        XMLCipher xmlCipher = XMLCipher.getInstance(XMLCipher.AES_128);
        xmlCipher.init(XMLCipher.ENCRYPT_MODE, symmetricKey);
        if (!nodes.isEmpty())
        {
            NodeList list = doc.getElementsByTagName(nodes);
            System.out.println(list);
            for (int i = 0; i < list.getLength(); ++i)
            {
                doc = xmlCipher.doFinal(list.item(i).getOwnerDocument(), (Element)list.item(i), encryptContentsOnly);
            }
        }
        else
        {
            doc = xmlCipher.doFinal(doc, doc.getDocumentElement(), encryptContentsOnly);
        }

        if (outputFile.isEmpty())
            outputDocToFile(doc, "..\\webapp\\src\\main\\webapp\\xml\\encryptedInfo.xml");
        else
            outputDocToFile(doc, outputFile);
    }

    public static void decrypt(Document doc, String outputFile, boolean encryptContentsOnly) throws XMLEncryptionException, Exception {
        XMLCipher xmlCipher = XMLCipher.getInstance(XMLCipher.AES_128);
        xmlCipher.init(XMLCipher.DECRYPT_MODE, symmetricKey);
        //Document document = xmlCipher.doFinal(doc, doc.getDocumentElement(), encryptContentsOnly);

        NodeList list = findXMLNodes(doc, "*[local-name()='EncryptedData']");
        System.out.println("Items found: " + list.getLength());
        System.out.println(list);

        for (int i = 0; i < list.getLength(); ++i)
        {
            Element elem = (Element)list.item(i);
            if (encryptContentsOnly && elem.getParentNode() != null) elem = (Element)elem.getParentNode();
            doc = xmlCipher.doFinal(elem.getOwnerDocument(), elem, encryptContentsOnly);
        }

        if (outputFile.isEmpty())
            outputDocToFile(doc, "..\\webapp\\src\\main\\webapp\\xml\\decryptedInfo.xml");
        else
            outputDocToFile(doc, outputFile);
    }

    private static Document inputFileToDoc(String fileName) throws Exception {
        File xmlFile = new File(fileName);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        dbFactory.setNamespaceAware(true);
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(xmlFile);
        System.out.println(
                "Document loaded from " + xmlFile.toURI().toURL().toString()
        );
        return doc;
    }

    private static void outputDocToFile(Document doc, String fileName) throws Exception {
        File encryptionFile = new File(fileName);
        FileOutputStream f = new FileOutputStream(encryptionFile);
        TransformerFactory factory = TransformerFactory.newInstance();
        Transformer transformer = factory.newTransformer();
        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(f);
        transformer.transform(source, result);
        f.close();
        System.out.println(
                "Wrote document containing encrypted data to " + encryptionFile.toURI().toURL().toString()
        );
    }

    private static NodeList findXMLNodes(Document doc, String nodeName) throws Exception {
        try
        {
            XPathFactory xPathfactory = XPathFactory.newInstance();
            XPath xpath = xPathfactory.newXPath();
            XPathExpression expr = xpath.compile("//" + nodeName);
            return (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
        }
        catch (XPathExpressionException ex) {
            throw ex;
        }
    }

}
