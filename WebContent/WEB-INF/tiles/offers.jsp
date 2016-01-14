<%@ page language="java" contentType="text/html; charset=US-ASCII"
	pageEncoding="US-ASCII"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<table class="offers">
	<tr>
		<td>Name</td>
		<td>Email</td>
		<td>Offer</td>
	</tr>

	<c:forEach var="offer" items="${offers}">
		<tr>

			<td><c:out value="${offer.user.name}"></c:out></td>

			<td><a class="href-btn"
				href="<c:url value='/message?uid=${offer.user.username}'/>"><button>Contact
						Me!</button></a></td>

			<td><c:out value="${offer.text}"></c:out></td>

		</tr>
	</c:forEach>
</table>