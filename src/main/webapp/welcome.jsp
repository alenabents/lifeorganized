<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<link rel="StyleSheet" href="style.css" type="text/css">
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>welcome</title>
</head>
<body style="text-align:center">


<!-- this is first page. -->
<span style="color: black; ">
<br>
<h1> Welcome </h1>
<h1> To </h1>
<h1> LIFEORGANIZED</h1>
<h4> go to the best version of yourself </h4>
<h6> please login or register :</h6>
<div>
    <!-- a login button to log in into the system -->
    <form action="logIn.jsp" method="post">
        <input style="border:2px solid black;" type="submit" value="log in" />
    </form>

    <br>
    <!-- a sign up button to register into the system -->
    <form action="signUp.jsp" method="post">
        <input style="border:2px solid black;" type="submit" value="sign up"/>
    </form>
</div>
<br>
<br>
    </span>
</body>
</html>