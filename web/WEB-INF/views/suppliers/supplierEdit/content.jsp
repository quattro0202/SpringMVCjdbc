<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<div id="content">
  <div id="content_center">
    <c:if test="${!failMessage.equals(\"\")}">
      <span style="color: red; ">${failMessage}</span>
    </c:if>
    <form:form commandName="supplier" action="/suppliers/edit/${supplier.id}" method="post">
      <fieldset>
        <legend>Постачальник</legend>
        <p>
        <form:label path="id" >Id</form:label><br/>
        <form:input path="id" readonly="true" value="${supplier.id}" /><form:errors path="id" cssStyle="color: #ff0000;"/>
        </p>
        <p>
          <form:label path="name" >Ім'я</form:label><br/>
          <form:input path="name" value="${supplier.name}" /> <form:errors path="name" cssStyle="color: #ff0000;"/>
        </p>
        <p>
          <form:label path="address" >Адреса</form:label><br/>
          <form:input path="address" value="${supplier.address}"/> <form:errors path="address" cssStyle="color: #ff0000;" />
        </p>
        <p>
          <form:label path="phone" >Телефон</form:label><br/>
          <form:input path="phone" value="${supplier.phone}"/> <form:errors path="phone" cssStyle="color: #ff0000;"/>
        </p>
        <p>
          <form:label path="email" >E-mail</form:label><br/>
          <form:input path="email" value="${supplier.email}"/> <form:errors path="email" cssStyle="color: #ff0000;"/>
        </p>
        <p>
          <input type="submit" />
        </p>
      </fieldset>
    </form:form>
  </div>


</div>
