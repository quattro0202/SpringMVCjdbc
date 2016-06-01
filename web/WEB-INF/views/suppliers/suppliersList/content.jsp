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
      <th>Адреса</th>
      <th>Телефон</th>
      <th>Пошта</th>
      <th></th>
      <th></th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${suppliers}" var="supp">
      <tr>
        <td>${supp.id}</td>
        <td>${supp.name}</td>
        <td>${supp.address}</td>
        <td>${supp.phone}</td>
        <td>${supp.email}</td>
        <td><a href="/suppliers/delete/${supp.id}">delete</a></td>
        <td><a href="/suppliers/edit/${supp.id}">edit</a>
      </tr>
    </c:forEach>
    </tbody>
  </table>
  <div id="table_pages">
    <c:forEach items="${suppliersPagesList}" var="i">
      <c:if test="${i == suppliersCurrentPage}">
        <a href="/suppliers/list/${i}" id="current_page">${i}</a>
      </c:if>
      <c:if test="${i != suppliersCurrentPage}">
        <a href="/suppliers/list/${i}">${i}</a>
      </c:if>
    </c:forEach>
  </div>
</div>