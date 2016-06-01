<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<div id="content">
  <div id="content_center">
    <c:if test="${!failMessage.equals(\"\")}">
      <span style="color: red; ">${failMessage}</span>
    </c:if>
    <form action="/employees/search" method="post">
      <p><strong>Id:</strong>
        <input name="id" value="${id}">${badId}</p>

      <p><strong>Ім'я:</strong>
        <input name="name" value="${name}"></p>

      <p><strong>Керуючий:</strong>
        <input name="manager" value="${manager}">${badManager}</p>

      <input type="submit" value="Search">
    </form>
  </div>

  <table id="table">
    <thead>
    <tr>
      <th>Номер</th>
      <th>Ім'я</th>
      <th>Керуючий</th>
      <th></th>
      <th></th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${employees}" var="empl">
      <tr>
        <td>${empl.id}</td>
        <td>${empl.name}</td>
        <td>${empl.manager}</td>
        <td><a href="/employees/delete/${empl.id}">delete</a></td>
        <td><a href="/employees/edit/${empl.id}">edit</a>
      </tr>
    </c:forEach>
    </tbody>
  </table>

</div>
