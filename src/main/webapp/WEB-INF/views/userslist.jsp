<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="joda" uri="http://www.joda.org/joda/time/tags" %> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>List of Users</title>
</head>
<body>
			List of Users 
			<table>
	    		<thead>
		      		<tr>
		      			<th>Id</th>
				        <th>Name</th>
				        <th>SsoId</th>
				        <th>Email</th>
				        <th>Joining Date</th>
				        <th>Gender</th>
				        <th>Password</th>
				        <th>Status</th>
				        
					</tr>
		    	</thead>
	    		<tbody>
				<c:forEach items="${users}" var="user">
					<tr>
						<td>${user.id}</td>
						<td>${user.name}</td>
						<td>${user.ssoId}</td>
						<td>${user.email}</td>
						<td><joda:format pattern="dd-MMM-yyyy" value="${user.joiningDate}"/></td>
						<td>${user.gender}</td>
						<td>${user.password}</td>
						<td>${user.status}</td>
						<td><a href="<c:url value='/user-${user.ssoId}' />">Select</a></td>
						<td><a href="<c:url value='/edit-user-${user.ssoId}' />">Edit</a></td>
						<td><a href="<c:url value='/delete-user-${user.ssoId}' />">Delete</a></td>
					</tr>
				</c:forEach>
	    		</tbody>
	    	</table>

<a href="<c:url value='/adduser' />">Add user </a>

</body>
</html>