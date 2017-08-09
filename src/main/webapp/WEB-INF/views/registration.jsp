<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>User Registration Form</title>
</head>
<body>

	<h1>User Registration Form</h1>
	<form:form method="POST" modelAttribute="user" name="userform">
		<form:input type="hidden" path="id" id="id"/>
			Name <form:input type="text" path="name" id="name"/> <form:errors path="name" /> <br>
			<c:choose>
				<c:when test="${edit}">
					SsoId <form:input type="text" path="ssoId" id="ssoId" disabled="true"/>
				</c:when>
				<c:otherwise>
					SsoId <form:input type="text" path="ssoId" id="ssoId"/>
					<form:errors path="ssoId" />
				</c:otherwise>
			</c:choose> <br>
			Email <form:input type="text" path="email" id="email"/> <form:errors path="email" /> <br>
			Gender <form:radiobutton path="gender" value="MALE" />Male <form:radiobutton path="gender" value="FEMALE" />Female
				<form:errors path="gender" /> <br>
			Status <form:input type="text" path="status" id="status"/> <br>
			Password <form:input type="text" path="password" id="password"/> <form:errors path="password" /> <br>
			<form:input type="hidden" path="joiningDate" id="joiningDate"/>
			<input type="submit" value="Register" />
	</form:form>
	
</body>
</html>