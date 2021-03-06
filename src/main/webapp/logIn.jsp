<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<link rel="StyleSheet" href="style.css" type="text/css">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>log In</title>
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
        <h1>Авторизация</h1>
        <br>
        <br>
        <form action="controllers.logIn" method="post" onsubmit="return (validMail())">
            <p id="message">Пожалуйста заполните все поля</p>
            <p id="messagePass"></p>
            <strong>Введите Email : </strong>
            <input id="email" type="text" name="email" onkeyup="validMail(this.value)" placeholder="user@example.com" size=25/>
            <br>
            <br>
            <strong>Введите пароль : </strong>
            <input id="password" onkeyup="validPassword(this.value)" type="password" name="password"/>
            <br>
            <br>
            <c:if test="${not empty emailError}">
                <p style="color:#ffffff;">${emailError}</p>
            </c:if>
            <c:if test="${not empty passwordError}">
                <p style="color:#ffffff;">${passwordError}</p>
            </c:if>
            <c:if test="${not empty registerError}">
                <p style="color:#ffffff;">
                        ${registerError}
                    <br>
                    Пожалуйста <a href="signUp.jsp">Зарегистрируйтесь</a>
                </p>
            </c:if>
            <input class="btn btn-dark" type="submit" name="signedUp" value="Войти"/>
        </form>
    </c:otherwise>
</c:choose>
<script src="validEmaill.js">
</script>
<script src="checkPassword.js">
</script>
</body>
</html>