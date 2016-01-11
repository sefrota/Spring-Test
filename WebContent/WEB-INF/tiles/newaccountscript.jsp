<%@ page language="java" contentType="text/html; charset=US-ASCII"
    pageEncoding="US-ASCII"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

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