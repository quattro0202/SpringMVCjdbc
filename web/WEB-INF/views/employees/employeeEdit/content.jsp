<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<div id="content">
  <div id="content_center">
      <c:if test="${!failMessage.equals(\"\")}">
        <span style="color: red; ">${failMessage}</span>
      </c:if>
    <form action="/employees/edit/${id}" method="post">
      <p><strong>Id:</strong>
        <input name="id" disabled="true" value="${id}"></p>

      <p><strong>Ім'я:</strong>
        <input name="name" value="${name}"><span style="color: red; ">${nameFieldError}</span></p>

      <p><strong>Керуючий:</strong>
        <input name="manager" value="${manager}"><span style="color: red; ">${managerFieldError}</span></p>

      <input type="submit" value="edit">
    </form>
  </div>


</div>
