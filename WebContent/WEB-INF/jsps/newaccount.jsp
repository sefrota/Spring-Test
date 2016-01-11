<%@ page language="java" contentType="text/html; charset=US-ASCII"
    pageEncoding="US-ASCII"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<link href="<c:url value="/static/css/main.css"/>" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<c:url value="/static/script/jquery-1.11.3.min.js"/>"></script>
<script type="text/javascript">

function onLoad(){
	$('#password').keyup(checkPasswordsValid);
	$('#confirmPass').keyup(checkPasswordsValid);

	$('#details').submit(canSubmit);
}

function canSubmit(){
	return checkPasswordsMatch();
}

function checkPasswordsMatch(){
	var password = $('#password').val();
	var confPassword = $('#confirmPass').val();
	if(password == confPassword)
		return true;
	else
		return false;
}

function checkPasswordsValid(){
	var password = $('#password').val();
	var confPassword = $('#confirmPass').val();
	var matchpass = $('#matchpass');
	
	if(password.length < 8 && confPassword.length < 8){
		matchpass.text("");
		return;
	}
	if(checkPasswordsMatch()){
		matchpass.text("<fmt:message key='MatchedPasswords.user.password'/>");
		matchpass.addClass("valid");
		matchpass.removeClass("error");
	} else{
		matchpass.text("<fmt:message key='UnmatchedPasswords.user.password'/>");
		matchpass.addClass("error");
		matchpass.removeClass("valid");
	}
}

$(document).ready(onLoad);

</script>
<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
<title>Create New Account</title>
</head>
<body>

<sf:form id="details" method="post" action="${pageContext.request.contextPath}/createaccount" commandName="user">

<table class="formtable">
<tr><td class="label">Username: </td><td><sf:input class="control" path="username" name="username" type="text" /><br/><div class="error"><sf:errors path="username"></sf:errors></div></td></tr>
<tr><td class="label">Email: </td><td><sf:input class="control"  path="email" name="email" type="text" /><br/><div class="error"><sf:errors path="email"></sf:errors></div></td></tr>
<tr><td class="label">Password: </td><td><sf:input class="control" id="password" path="password" name="password" type="password" /><br/><div class="error"><sf:errors path="password"></sf:errors></div></td></tr>
<tr><td class="label">Confirm Password: </td><td><input class="control" id="confirmPass" name="confirmpass" type="password" /><div id = "matchpass"></div></td></tr>
<tr><td class="label"> </td><td><input class="control"  value="Create account" type="submit" /></td></tr>
</table>

</sf:form>

</body>
</html>