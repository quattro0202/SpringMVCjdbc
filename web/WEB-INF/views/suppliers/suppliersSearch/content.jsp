<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<div id="content">
  <c:if test="${!failMessage.equals(\"\")}">
    <span style="color: red; ">${failMessage}</span>
  </c:if>
  <div id="content_center">
    <form action="/suppliers/search" method="post">
      <p><strong>Id:</strong>
        <input name="id" value="${id}">${badId}</p>

      <p><strong>Ім'я:</strong>
        <input name="name" value="${name}"></p>

      <p><strong>Адреса:</strong>
        <input name="address" value="${address}"></p>

      <p><strong>Телефон:</strong>
        <input name="phone" value="${phone}"></p>

      <p><strong>E-mail:</strong>
        <input name="email" value="${email}"></p>
      <input type="submit" value="Search">
    </form>
  </div>

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

</div>
