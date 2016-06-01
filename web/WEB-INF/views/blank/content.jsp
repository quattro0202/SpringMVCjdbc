<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div id="content">
  <div id="content_center" >
    <c:if test="${!successMessage.equals(\"\")}">
      <span style="color: green; ">${successMessage}</span>
    </c:if>
    <c:if test="${!failMessage.equals(\"\")}">
      <span style="color: red; ">${failMessage}</span>
    </c:if>
  </div>
</div>