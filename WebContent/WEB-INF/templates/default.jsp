<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link href="<c:url value="/static/css/main.css"/>" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<c:url value="/static/script/jquery-1.11.3.min.js"/>"></script>

<title><tiles:insertAttribute name="title"></tiles:insertAttribute></title>
<tiles:insertAttribute name="includes"></tiles:insertAttribute>
</head>
<body>
<div class="header">
	<tiles:insertAttribute name="header"></tiles:insertAttribute>
</div>
<div class="content">
	<tiles:insertAttribute name="content"></tiles:insertAttribute>
</div>

<hr/>
<div class="footer">
	<tiles:insertAttribute name="footer"></tiles:insertAttribute>
</div>
</body>
</html>