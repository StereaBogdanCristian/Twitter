<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="joda" uri="http://www.joda.org/joda/time/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Tweet - user ${user.name}</title>
</head>
<body>

	<h1>User ${user.name}</h1>
	
	Id: ${user.id}<br>
	Name: ${user.name}<br>
	SsoId: ${user.ssoId}<br>
	Email: ${user.email}<br>
	Joining Date: ${user.joiningDate}<br>			
	Gender: ${user.gender}<br>
	Password: ${user.password}<br>
	Status: ${user.status}<br>
	Friends: <c:forEach items="${user.listOfFriends}" var="friend">
					<a>${friend.ssoId}  </a>
			</c:forEach>
	<br>
	Who's friend: <c:forEach items="${user.whosFriend}" var="friend">
					<a>${friend.ssoId}  </a>
			</c:forEach>
	<br>
	<br>
	<a href="<c:url value='/addmessage' />">Add message </a> <br>
	<a href="<c:url value='/friendsmessages' />">Friends' messages </a> <br>
	<a href="<c:url value='/' />">Home </a> <br>
	
	<form action="/Twitter/search">
		<label>Search user</label><input type="text" name="nameSearch"/> <input type="submit" value="Search" />
	</form>
	<br>
	
	<h3>Messages</h3>
	
	<c:forEach items="${messages}" var="message">
			Id  ${message.id} <br>
			Message  ${message.message} <br>
			Date  <joda:format pattern="dd-MMM-yyyy HH:mm:ss" value="${message.date}"/> <br>
			Author SsoId  ${message.authorSsoId} <br>
			Author Name  ${message.authorName} <br>
			Message Type  ${message.messType} <br>
			<c:set var="lastmessid" value="${message.id}"> </c:set>
			Like: <c:forEach items="${message.listOfUserLike}" var="userlike">
							<a>${userlike.userAuxEntSsoId} </a>
				  </c:forEach>
			<br>
			Share: <c:forEach items="${message.listOfUserShare}" var="usershare">
							<a>${usershare.userAuxEntSsoId} </a>
				  </c:forEach>
			<br>
			<a href="<c:url value='/message-edit-${message.id}' />">Edit </a> <a href="<c:url value='/message-delete-${message.id}?userssoId=${user.ssoId}' />"> Delete</a>
			<br> <br>
	</c:forEach>
	
	<c:if test="${currentPage >= 3}">
        <td><a href="<c:url value='/user-${user.ssoId}?currentPage=1' />">First</a></td>
    </c:if>
	<c:if test="${currentPage != 1}">
        <td><a href="<c:url value='/user-${user.ssoId}?currentPage=${currentPage - 1}' />">Previous</a></td>
    </c:if>
	${currentPage}
	<c:if test="${currentPage < maxNumberOfPages}">
        <td><a href="<c:url value='/user-${user.ssoId}?currentPage=${currentPage + 1}' />">Next</a></td>
    </c:if>
    <c:if test="${((currentPage + 1) < maxNumberOfPages)}">
        <td><a href="<c:url value='/user-${user.ssoId}?currentPage=${maxNumberOfPages}' />">Last</a></td>
    </c:if>
</body>
</html>