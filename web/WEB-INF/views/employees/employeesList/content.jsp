<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div id="content">
  <c:if test="${!failMessage.equals(\"\")}">
    <span style="color: red; ">${failMessage}</span>
  </c:if>
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
        <td>${empl.manager.name}</td>
        <td><a href="/employees/delete/${empl.id}">delete</a></td>
        <td><a href="/employees/edit/${empl.id}">edit</a>
      </tr>
    </c:forEach>
    </tbody>
  </table>
  <div id="table_pages">
    <c:forEach items="${employeesPagesList}" var="i">
      <c:if test="${i == employeesCurrentPage}">
        <a href="/employees/list/${i}" id="current_page">${i}</a>
      </c:if>
      <c:if test="${i != employeesCurrentPage}">
        <a href="/employees/list/${i}">${i}</a>
      </c:if>
    </c:forEach>
  </div>
</div>