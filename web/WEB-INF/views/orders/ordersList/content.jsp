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
      <th>Постачальник</th>
      <th>Співробітник</th>
      <th>Дата</th>
      <th></th>
      <th></th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${orders}" var="o">
      <tr>
        <td>${o.id}</td>
        <td>${o.supplier.name}</td>
        <td>${o.employee.name}</td>
        <td>${o.date}</td>
        <td><a href="/orders/delete/${o.id}">delete</a></td>
        <td><a href="/orders/edit/${o.id}">edit</a>
      </tr>
    </c:forEach>
    </tbody>
  </table>
  <div id="table_pages">
    <c:forEach items="${ordersPagesList}" var="i">
      <c:if test="${i == ordersCurrentPage}">
        <a href="/orders/list/${i}" id="current_page">${i}</a>
      </c:if>
      <c:if test="${i != ordersCurrentPage}">
        <a href="/orders/list/${i}">${i}</a>
      </c:if>
    </c:forEach>
  </div>
</div>