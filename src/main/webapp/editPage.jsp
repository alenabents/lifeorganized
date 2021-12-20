<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List, java.io.PrintWriter" %>
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
<h1 style="text-align:center"> Редактировать задачу </h1>

<form action="controllers.editItems" method="post">
    <table style="margin-left:auto;margin-right:auto;">
        <tr>
            <td>задача:</td>
            <td>
                <input type="text" name="ItemLabel" autofocus="autofocus" value="${itemLabel}"/>
            </td>
        </tr>
        <tr>
            <td>дата:</td>
            <td>
                <input type="date" name="ItemDate" value="${itemDate}"/>
            </td>
        </tr>
        <tr>
            <td>время:</td>
            <td>
                <input type="time" name="ItemTime" value="${itemTime}"/>
            </td>
        </tr>
        <tr>
            <td></td>
            <td>
                <input type="hidden" name="id" value="${id}"/>
                <input class="btn btn-dark" type="submit" name="editTheItem" value="Сохранить">
            </td>
        </tr>
    </table>
</form>

<br>

<h2>
    <form action="controllers.FriendsItems" method="post"><input class="btn btn-dark" type="submit" name="friends"
                                                                 value="Списки друзей">
    </form>
</h2>
<h2>
    <form action="homePage.jsp"><input class="btn btn-dark" type="submit" name="home" value="Добавить задачу"></form>
</h2>
<h2>Мои задачи: </h2>
<hr>
<%
    String email = (String) session.getAttribute("userEmail");
    List<List<Item>> listItems = ItemDbUtil.getItems(email);
    request.setAttribute("listItems", listItems);
%>
<table id="table1" style="border:1px solid black;margin-left:auto;margin-right:auto;">
    <tr>
        <th style="padding:3px 20px 3px 20px;">задача</th>
        <th style="padding:3px 20px 3px 20px;">дата</th>
        <th style="padding:3px 20px 3px 20px;">время</th>
        <th style="padding:3px 20px 3px 20px;">редактировать</th>
        <th style="padding:3px 20px 3px 20px;">удалить</th>
        <th style="padding:3px 20px 3px 20px;">добавить</th>
        <th style="padding:3px 20px 3px 20px;">поделиться</th>
    </tr>
    <c:forEach var="tempItem" items="${listItems}">
        <tr>
                <%-- чекбокс --%>
            <form action="controllers.checkItems">
                <td style="padding:3px 20px 3px 40px;">
                    <input type="hidden" name="id" value="${tempItem.get(0).id}">
                    <input type="hidden" name="pageName" value="editPage.jsp">
                    <c:choose>
                        <c:when test="${tempItem.get(0).check==1}">
                            <input type="checkbox" id="chk" name="chkSms"
                                   value="${tempItem.get(0).check}" checked="checked" onclick="form.submit();"/>
                        </c:when>
                        <c:otherwise>
                            <input type="checkbox" id="chk" name="chkSms"
                                   value="${tempItem.get(0).check}" onclick="form.submit();"/>
                        </c:otherwise>
                    </c:choose>
                    <label>
                    </label>${tempItem.get(0).label}
                </td>
            </form>
            <td style="padding:3px 20px 3px 40px;">${tempItem.get(0).date}</td>
            <td style="padding:3px 20px 3px 40px;">${tempItem.get(0).time}</td>

                <%-- редактирование --%>
            <td style="padding:3px 20px 3px 20px;">&nbsp;&nbsp;<a class="btn btn-dark btn-sm"
                                                              href="controllers.editItems?id=${tempItem.get(0).id}">редактировать</a>
            </td>

                <%-- удалить --%>
            <form action="controllers.deleteItems" method="post">
                <td style="padding:3px 20px 3px 20px;"><input type="hidden" name="id" value="${tempItem.get(0).id}"/> <input
                        class="btn btn-danger btn-sm"
                        type="submit" value="удалить"/></td>
            </form>

                <%-- добавить --%>
            <td style="padding:3px 20px 3px 20px;"><a class="btn btn-dark btn-sm"
                                                  href="controllers.addSubItems?id=${tempItem.get(0).id}">добавить</a>
            </td>

                <%-- поделиться --%>
            <td style="padding:3px 20px 3px 20px;"><a class="btn btn-dark btn-sm"
                                                  href="controllers.ShareItems?id=${tempItem.get(0).id}">поделиться</a>
            </td>
        </tr>
        <c:forEach var="tempsubItem" items="${tempItem}">
            <c:choose>
                <c:when test="${tempsubItem == tempItem.get(0)}">
                </c:when>
                <c:otherwise>
                    <tr>
                            <%-- чекбокс --%>
                        <form action="controllers.checkSubItems">
                            <td style="padding:3px 20px 3px 60px;">
                                <input type="hidden" name="id" value="${tempsubItem.id}">
                                <input type="hidden" name="pageName" value="editPage.jsp">
                                <c:choose>
                                    <c:when test="${tempsubItem.check==1}">
                                        <input type="checkbox" id="chk" name="chkSms"
                                               value="${tempsubItem.check}" checked="checked" onclick="form.submit();"/>
                                    </c:when>
                                    <c:otherwise>
                                        <input type="checkbox" id="chk" name="chkSms"
                                               value="${tempsubItem.check}" onclick="form.submit();"/>
                                    </c:otherwise>
                                </c:choose>
                                <label>
                                </label>${tempsubItem.label}
                            </td>
                        </form>
                        <td style="padding:3px 20px 3px 60px;">${tempsubItem.date}</td>
                        <td style="padding:3px 20px 3px 60px;">${tempsubItem.time}</td>

                            <%-- редакт --%>
                        <td style="padding:3px 20px 3px 45px;">&nbsp;&nbsp;<a class="btn btn-dark btn-sm"
                                                                          href="controllers.editSubItems?id=${tempsubItem.id}">редактировать</a>
                        </td>

                            <%-- удалить --%>
                        <form action="controllers.deleteSubItems" method="post">
                            <td style="padding:3px 20px 3px 45px;"><input type="hidden" name="id"
                                                                      value="${tempsubItem.id}"/>
                                <input class="btn btn-danger btn-sm" type="submit" value="удалить"/></td>
                        </form>
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