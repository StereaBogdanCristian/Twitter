<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Add message</title>
</head>
<body> 
	<c:choose>
		<c:when test="${update}">
			<h1>Update message</h1> <br>
		</c:when>
		<c:otherwise>
			<h1>Add message</h1> <br>
		</c:otherwise>
	</c:choose> <br>
			
	<form:form method="POST" modelAttribute="message" name="messageform" enctype="application/x-www-form-urlencoded"> <%-- action="/addmessage"  --%>
			
			Message <br>
			<form:textarea path="message" id="message" rows="5" cols="30"/> <form:errors path="message" /> <br>
			
			<%-- <form:hidden path="id" value="${message.id}"/>
			<form:hidden path="date" value="${message.date}"/>
			<form:hidden path="authorSsoId" value="${message.authorSsoId}"/>
			<form:hidden path="authorName" value="${message.authorName}"/>
			<form:hidden path="messType" value="${message.messType}"/> --%>
			
			<c:choose>
				<c:when test="${update}">
					<input type="submit" value="Update" />
					<input type="hidden" name="update" value="${true}"/>
				</c:when>
				<c:otherwise>
					<input type="submit" value="Post" />
					<input type="hidden" name="update" value="${false}">
				</c:otherwise>
			</c:choose> <br>
				
	</form:form>
</body>
</html>