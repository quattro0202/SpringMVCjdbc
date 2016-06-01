<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title><tiles:insertAttribute name="title" ignore="true" /></title>
    <tiles:insertAttribute name="stylecss" />
</head>
<body>
  <tiles:insertAttribute name="menu" />
  <tiles:insertAttribute name="content" />
  <tiles:insertAttribute name="footer" />
  <tiles:insertAttribute name="scriptjs" />
</body>
</html>
