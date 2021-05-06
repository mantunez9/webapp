<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<link rel="stylesheet" type="text/css" href="css/style.css">
<!DOCTYPE html>
<html>
<head>
    <title>Login</title>
</head>
<body>
<div class="login-page">
    <div class="form">
        <h2>SIGN IN</h2>
        <form action="login" method="post">
            <input name="nickName" type="text" placeholder="username"/>
            <input name="password" type="password" placeholder="password"/>
            <p class="error-message"> ${message} </p>
            <button type="submit">login</button>
            <p class="message">Not registered? <a href="${pageContext.request.contextPath}/register">Create an
                account</a></p>
        </form>
    </div>
</div>
</body>
</html>



