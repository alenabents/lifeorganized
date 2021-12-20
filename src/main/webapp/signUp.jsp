<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<link rel="StyleSheet" href="style.css" type="text/css">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Sign Up</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
</head>
<body style="text-align:center">

<c:choose>
    <c:when test="${not empty userEmail}">
        <br>
        <h4 style="color:#ffffff;">Пользователь уже авторизован.</h4>
        <h4 style="color:#ffffff;">
            Пожалуйста, перейдите на:
            <a href="homePage.jsp">Главную страницу</a>
        </h4>
    </c:when>
    <c:otherwise>
        <h1>Регистрация</h1>
        <br>
        <br>

        <p id="message">Пожалуйста заполните все поля</p>
        <p id="messagePass"></p>
        <form action="controllers.signUp" method="post" onsubmit="return (validMail())">

            <strong>Введите Email: </strong>
            <input id="email" type="text"  name="email" onkeyup="validMail(this.value)" placeholder="user@example.com" size=25/>
            <br>
            <br>
            <strong>Введите пароль: </strong>
            <input id="password" type="password" name="password" onkeyup="validPassword(this.value)"/>
            <br>
            <br>
            <strong>Повторите пароль: </strong>
            <input id="password2" type="password" onkeyup="validPassword(this.value)" name="confirmPassword"/>
            <br>
            <p style="color:#ffffff;">${emailError}</p>
            <p style="color:#ffffff;">${passwordError}</p>
            <c:if test="${not empty alreadyRegistered}">
                <p style="color:#ffffff;">
                    Пользователь уже зарегистрирован, пожалуйста
                    <a href="logIn.jsp">Войдите</a>
                </p>
            </c:if>
            <input name="button" class="btn btn-dark" type="submit" name="signedUp" value="Зарегистрироваться" />
        </form>
    </c:otherwise>
</c:choose>
<script src="validEmaill.js">
</script>
<script src="checkPassword.js">
</script>
</body>
</html>