<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<div id="content">
  <div id="content_center">
      <c:if test="${!failMessage.equals(\"\")}">
        <span style="color: red; ">${failMessage}</span>
      </c:if>
    <form:form commandName="employee" action="/employees/edit/${employee.id}" method="post">
      <fieldset>
        <legend>Співробітник</legend>
        <form:label path="id" >Id</form:label><br/>
        <form:input path="id" value="${employee.id}" disabled="true" /><form:errors path="id" cssStyle="color: #ff0000;"/>
        </p>
        <p>
          <form:label path="name" >Ім'я</form:label><br/>
          <form:input path="name" value="${employee.name}" /> <form:errors path="name" cssStyle="color: #ff0000;"/>
        </p>
        <p>
          <label path="manager" >Керуючий</label><br/>
          <input path="manager" value="${employee.manager.id}"/><span style="color: red; ">${managerError}</span>
        </p>

        <p>
          <input type="submit" />
        </p>
      </fieldset>
    </form:form>
  </div>


</div>
