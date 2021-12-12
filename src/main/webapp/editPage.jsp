<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="java.util.List, java.io.PrintWriter" %>
<%@ page import="Model.ItemDbUtil" %>
<%@ page import="Model.Item" %>
        <!DOCTYPE html>
<html>
<link rel="StyleSheet" href="style.css" type="text/css">
<head>

    <title>my to-do list</title>

</head>

<body >
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
    border:2px solid black; border-radius: 50%;" type="submit" value="${userName}" title="${userEmail} &#013;&#010; click to log out"/>
</form>
<h1 style="text-align:center" > edit task </h1>

<form  action="controllers.editItems" method="post">
    <table style="margin-left:auto;margin-right:auto;">
        <tr>
            <td>item-label : </td>
            <td>
                <input type="text" name="ItemLabel" autofocus="autofocus" value="${itemLabel}" />
            </td>
        </tr>
        <tr>
            <td>item-date : </td>
            <td>
                <input type="date" name="ItemDate" value="${itemDate}" />
            </td>
        </tr>
        <tr>
            <td>item-time : </td>
            <td>
                <input type="time" name="ItemTime" value="${itemTime}" />
            </td>
        </tr>
        <tr>
            <td></td>
            <td>
                <input type="hidden" name="id" value="${id}" />
                <input type="submit" name="editTheItem" value="save item">
            </td>
        </tr>
    </table>
</form>

<br>

<h2>your Tasks : </h2>
<hr>
<%

    ItemDbUtil dbUtil = new ItemDbUtil();

    String email = (String)session.getAttribute("userEmail");

    List<List<Item>> listItems = dbUtil.getItems(email);

    request.setAttribute("listItems", listItems);


%>
<table style="border:1px solid black;margin-left:auto;margin-right:auto;">
    <tr>
        <th style="padding:0 20px 0 20px;" >label</th>
        <th style="padding:0 20px 0 20px;" >date</th>
        <th style="padding:0 20px 0 20px;" >time</th>
        <th style="padding:0 20px 0 20px;" >edit</th>
        <th style="padding:0 20px 0 20px;" >delete</th>
        <th style="padding:0 20px 0 20px;" >add</th>
        <th style="padding:0 20px 0 20px;" >share</th>
    </tr>

    <c:forEach var="tempItem" items="${listItems}">
        <tr>
                <%-- чекбокс --%>
            <input type="hidden" name="tmp" value="${tempItem.get(0)}">
            <form  action="controllers.checkItems" method="post">
                <td style="padding:0 20px 0 60px;" ><label>
                    <input type="checkbox" name = "check" checked="unchecked" value="${tempItem.get(0).id}">
                </label>${tempItem.get(0).label}</td>
            </form>


            <td style="padding:0 20px 0 60px;" >${tempItem.get(0).date}</td>
            <td style="padding:0 20px 0 60px;" >${tempItem.get(0).time}</td>
                <%-- редактирование --%>
            <td style="padding:0 20px 0 40px;" >&nbsp;&nbsp;<a href="controllers.editItems?id=${tempItem.get(0).id}">edit</a></td>

                <%-- удалить --%>
            <form  action="controllers.deleteItems" method="post">
                <td style="padding:0 20px 0 40px;" ><input type="hidden" name="id" value="${tempItem.get(0).id}" /> <input
                        type="submit" value="Delete" /></td>
            </form>

                <%-- добавить --%>
            <td style="padding:0 20px 0 20px;" ><a href="controllers.addSubItems?id=${tempItem.get(0).id}">add</a></td>
        </tr>

        <c:forEach var="tempsubItem" items="${tempItem}">
            <c:choose>
                <c:when test="${tempsubItem == tempItem.get(0)}">
                </c:when>
                <c:otherwise>
                    <tr>
                            <%-- чекбокс --%>
                        <form  action="controllers.checkSubItems" method="post">
                            <td style="padding:0 20px 0 80px;" ><label>
                                <input type="checkbox" name = "check" checked="unchecked" value="${tempsubItem.id} ${tempItem.get(0).id}">
                            </label>${tempsubItem.label}</td>
                        </form>


                        <td style="padding:0 20px 0 80px;" >${tempsubItem.date}</td>
                        <td style="padding:0 20px 0 80px;" >${tempsubItem.time}</td>
                            <%-- реадакт --%>
                        <td style="padding:0 20px 0 55px;" >&nbsp;&nbsp;<a href="controllers.editSubItems?id=${tempsubItem.id} ${tempItem.get(0).id}">edit</a></td>

                            <%-- удалить --%>
                        <form  action="controllers.deleteSubItems" method="post">
                            <td style="padding:0 20px 0 55px;" ><input type="hidden" name="id" value="${tempsubItem.id} ${tempItem.get(0).id}" /> <input
                                    type="submit" value="Delete" /></td>
                        </form>
                    </tr>
                </c:otherwise>
            </c:choose>
        </c:forEach>
        <tr height = 20px></tr>
    </c:forEach>
</table>
</body>

</html>