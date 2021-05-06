<%--
  Created by IntelliJ IDEA.
  User: mantu
  Date: 15/03/2021
  Time: 19:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page isErrorPage="true" %>
<link rel="stylesheet" type="text/css" href="css/style.css">
<html>
<head>
    <title>Error</title>
</head>
<body>
<div class="login-page">
    <div style="background: #FFFFFF; padding: 15px; text-align: center;">
        <h2>Sorry an exception occured!</h2>
        <p class="error-message"> ${error} </p>
        <%= exception %>
        <br/>
        <br/>
        <p>Go to <a href="${pageContext.request.contextPath}/login">Log In</a></p>
    </div>
</div>
</body>
</html>
