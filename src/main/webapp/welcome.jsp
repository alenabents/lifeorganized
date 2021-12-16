<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<link rel="StyleSheet" href="style.css" type="text/css">
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>welcome</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
</head>
<body style="text-align:center">

<span style="color: black; ">
<br>
<h1> Добро пожаловать </h1>
<h1> в </h1>
<h1> LIFEORGANIZED</h1>
<h4> Стань лучшей версией себя </h4>
<h6> Пожалуйста, войдите или зарегистрируйтесь:</h6>
<div>
    <form action="logIn.jsp" method="post">
        <input style="border:2px solid black;" class="btn btn-dark" type="submit" value="Войти"/>
    </form>

    <br>
    <form action="signUp.jsp" method="post">
        <input style="border:2px solid black;" class="btn btn-dark" type="submit" value="Зарегистрироваться"/>
    </form>
</div>
<br>
<br>
    </span>
</body>
</html>