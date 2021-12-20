<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="javax.naming.InitialContext" %>
<%@ page import="java.util.List, java.io.PrintWriter" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="Model.ItemDbUtil" %>
<%@ page import="Model.Item" %>

<!DOCTYPE html>
<html>
<head>
    <title>my to-do list</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
    <link rel="StyleSheet" href="style.css" type="text/css">
</head>

<body>
<%
    response.setHeader("Cache-Control", "no-cache");
    response.setHeader("Cache-Control", "no-store");
    response.setHeader("Pragma", "no-cache");
    response.setDateHeader("Expires", 0);
    String uname = (String) session.getAttribute("userEmail");
    request.setAttribute("userName", uname.charAt(0));
%>
<form style="float : right " action="controllers.logOut" method="post">
    <input style="background-color:#D3D3D3;
    border:2px solid black; border-radius: 50%;" type="submit" value="${userName}"
           title="${userEmail} &#013;&#010; click to log out"/>
</form>

<br>
<h2>
    <form action="homePage.jsp"><input class="btn btn-dark" type="submit" name="home" value="Мои задачи"></form>
</h2>
<h2>Списки друзей: </h2>
<hr>
<%
    String email = (String) session.getAttribute("userEmail");
    List<List<Item>> listItems = ItemDbUtil.getFriendsItems(email);
    request.setAttribute("listItems", listItems);
%>
<table id="table1" style="border:1px solid black;margin-left:auto;margin-right:auto;">
    <tr>
        <th style="padding:0 20px 0 20px;">задача</th>
        <th style="padding:0 20px 0 20px;">дата</th>
        <th style="padding:0 20px 0 20px;">время</th>
        <th style="padding:0 20px 0 20px;">удалить</th>
    </tr>
    <c:forEach var="tempItem" items="${listItems}">
        <tr>
            <td style="padding:0 20px 0 20px;">${tempItem.get(0).informFriend}: ${tempItem.get(0).label}</td>
            <td style="padding:0 20px 0 60px;">${tempItem.get(0).date}</td>
            <td style="padding:0 20px 0 60px;">${tempItem.get(0).time}</td>

                <%-- удалить --%>
            <form action="controllers.deleteFriendItems" method="post">
                <td style="padding:0 20px 0 40px;"><input type="hidden" name="id" value="${tempItem.get(0).id}"/> <input
                        class="btn btn-danger btn-sm"
                        type="submit" value="удалить"/></td>
            </form>
        </tr>
        <c:forEach var="tempsubItem" items="${tempItem}">
            <c:choose>
                <c:when test="${tempsubItem == tempItem.get(0)}">
                </c:when>
                <c:otherwise>
                    <tr>
                        <td style="padding:0 20px 0 80px;">${tempsubItem.label} [${tempsubItem.informFriend}]</td>
                        <td style="padding:0 20px 0 80px;">${tempsubItem.date}</td>
                        <td style="padding:0 20px 0 80px;">${tempsubItem.time}</td>
                    </tr>
                </c:otherwise>
            </c:choose>
        </c:forEach>
        <tr height=20px></tr>
    </c:forEach>
</table>
<script src="script.js"></script>
</body>
</html>