<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="Model.ItemDbUtil" %>
<%@ page import="Model.Item" %>
<%@ page import="java.util.List" %>


<!DOCTYPE html>
<html>
<link rel="StyleSheet" href="style.css" type="text/css">
<link rel="StyleSh" href="board.css" type="text/css">
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
<h1 style="text-align:center" > Share item </h1>

<form  action="controllers.ShareItems" method="post">
    <%
        ItemDbUtil dbUtil = new ItemDbUtil();
        List<List<Item>> listItems = dbUtil.getItems(uname);
        request.setAttribute("listItems", listItems);
        String id = (String) request.getAttribute("id");

        Item mainItem = dbUtil.getItemWithId(id,uname);
        List<Item> shareItems = dbUtil.getSubItems(Integer.parseInt(id),uname,mainItem);
        request.setAttribute("shareItems", shareItems);
    %>
    <table style="border:1px solid black;margin-left:auto;margin-right:auto;">
        <tr>
            <th style="padding:0 20px 0 20px;" >label</th>
            <th style="padding:0 20px 0 20px;" >date</th>
            <th style="padding:0 20px 0 20px;" >time</th>
            <th style="padding:0 20px 0 20px;" >responsible</th>
        </tr>

        <tr>
            <td style="padding:0 20px 0 30px;" >${shareItems.get(0).label}</td>
            <td style="padding:0 20px 0 30px;" >${shareItems.get(0).date}</td>
            <td style="padding:0 20px 0 30px;" >${shareItems.get(0).time}</td>
        </tr>
        <%
            shareItems.remove(0);
            int count = 0;
            request.setAttribute("count", Integer.toString(count));
        %>

        <c:forEach var="tempsubShare" items="${shareItems}">
            <tr>
                <td style="padding:0 20px 0 50px;" >${tempsubShare.label}</td>
                <td style="padding:0 20px 0 50px;" >${tempsubShare.date}</td>
                <td style="padding:0 20px 0 50px;" >${tempsubShare.time}</td>
                <td style="padding:0 20px 0 50px;" >
                    <input type="text" name="${count}" placeholder="enter responsible person" size="20" /> </td>
            </tr>
            <% count = count + 1;
                request.setAttribute("count", Integer.toString(count));
            %>

        </c:forEach>
    </table>
    <table style="margin-left:auto;margin-right:auto;">
        <tr>
            <td>
                <% String coun = Integer.toString(count);
                    request.setAttribute("coun", coun);
                %>
                <input type="hidden" name="id" value="${id}" />
                <input type="hidden" name="count" value="${coun}" />
                <input type="text" name="users" placeholder="user1@mail.ru,user2@mail.ru" size="30" />
                <input type="submit" value="Share" />
            </td>
        </tr>
        <tr>
            <td>
                <% String message = (String) request.getAttribute("message");
                %>
                ${message}
            </td>
        </tr>
    </table>


</form>

<br>

<h2><input type="submit" name="friends" value="friends lists"> </h2>
<h2><form action="homePage.jsp"><input type="submit" name="friends" value="My lists"> </form></h2>
<h2>your Tasks : </h2>
<hr>

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


                <%-- поделиться --%>
            <td style="padding:0 20px 0 20px;" ><a href="controllers.ShareItems?id=${tempItem.get(0).id}">share</a></td>
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