<%--
  Created by IntelliJ IDEA.
  User: mantu
  Date: 25/02/2021
  Time: 18:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<link rel="stylesheet" type="text/css" href="css/style.css">
<html>
<head>
    <title>Register</title>
</head>
<body>
<div class="register-page">
    <div class="form">
        <h2>SIGN UP</h2>
        <form action="register" method="post">
            <span>
                <label for="name">Name:</label>
                <input id="name" name="name" required="required" type="text" placeholder="name"/>
            </span>
            <span>
                <label for="surnames">Surname:</label>
                <input id="surnames" name="surnames" required="required" type="text" placeholder="surname"/>
            </span>
            <span>
                <label for="nickName">Username:</label>
                <input id="nickName" name="nickName" required="required" type="text"
                       placeholder="username"/>
            </span>
            <span>
                <label for="email">Email:</label>
                <input id="email" name="email" required="required" type="email" placeholder="ejemplo@mail.com"/>
            </span>
            <span>
                <label for="password">Password:</label>
                <input id="password" name="password" required="required" type="password"
                       placeholder="eg. X8df!90EO" pattern="(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8,}"/>
            </span>
            <span>
                <label for="passwordsignup_confirm">Confirm password: </label>
                <input id="passwordsignup_confirm" name="passwordsignup_confirm" required="required" type="password"
                       placeholder="eg. X8df!90EO"/>
            </span>
            <p class="error-message"> ${message} </p>
            <button type="submit">register</button>
            <p class="message">Already registered? <a href="${pageContext.request.contextPath}/login">Log In</a></p>
        </form>
    </div>
</div>
</body>
</html>
<script type="text/javascript">
    window.onload = function () {
        let txtPassword = document.getElementById("password");
        let txtConfirmPassword = document.getElementById("passwordsignup_confirm");
        txtPassword.onchange = ConfirmPassword;
        txtConfirmPassword.onkeyup = ConfirmPassword;

        function ConfirmPassword() {
            txtConfirmPassword.setCustomValidity("");
            if (txtPassword.value !== txtConfirmPassword.value) {
                txtConfirmPassword.setCustomValidity("Please enter the same Password as above.");
            }
        }
    }
</script>