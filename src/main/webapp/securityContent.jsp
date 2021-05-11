<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="user" scope="session" type="isdcm.tomcat.webapp.model.User"/>
<%--
  Created by IntelliJ IDEA.
  User: gessi
  Date: 09/05/2021
  Time: 12:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="css/style.css">
    <link rel="stylesheet" type="text/css" href="css/dropdown.css">
    <meta http-equiv="Content-Type"
          content="text/html; charset=windows-1256">
    <title>XML Security</title>
</head>
<body>
<div class="dropdown">
    <button class="dropbtn">${user.nickName}</button>
    <div class="dropdown-content">
        <a href="${pageContext.request.contextPath}/logout">Logout</a>
    </div>
</div>
<button onclick="location.href='${pageContext.request.contextPath}/video'" type="button">
    Back to Video Page
</button>
</div>
<div class="video-page">
    <div class="video-form" style="text-align: left;">
        <h1 style="text-align: center;">Content Encryption/Decryption</h1>
        <form action="securityContent" method="post">
            <p>
                <label for="inputFile">Input file path: </label>
                <input id="inputFile" name="inputFile" type="text" required="required"/>
            </p>
            <p>
                <label for="outputFile">Output file path: </label>
                <input id="outputFile" name="outputFile" type="text"/>
            </p>
            <p>
                <label>Please select an option: </label>
                <br><br>
                <input type="radio" id="enc" name="option" value="enc" checked="checked"
                       style="text-align: left; width: 5%; margin-top:4px">
                <label for="enc">Content Encrypt</label>
                <br><br>
                <input type="radio" id="dec" name="option" value="dec"
                       style="text-align: left; width: 5%; margin-top:4px">
                <label for="dec">Content Decrypt</label>
            </p>
            <button type="submit">submit</button>
        </form>
    </div>
</div>

</body>
</html>
