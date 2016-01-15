<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<table class="offers">

	<c:forEach var="offer" items="${offers}">
		<tr>

			<td class="name"><c:out value="${offer.user.name}"></c:out></td>

			<td class="contact"><a class="href-btn"
				href="<c:url value='/message?uid=${offer.user.username}'/>"><button>Contact
						Me!</button></a></td>

			<td class="text"><c:out value="${offer.text}"></c:out></td>

		</tr>
	</c:forEach>
</table>

<script type="text/javascript">
	function updateMessageCounter(data) {
		$("#numberMessages").text(data.number);
	}

	function onLoad() {
		updatePage();
		window.setInterval(updatePage, 5000);
	}

	function updatePage(){
		$.getJSON("<c:url value='/getmessages'/>").done(updateMessageCounter)
		.fail(function(json) {
			console.log(json);
		});
	}

	$(document).ready(onLoad);
</script>
