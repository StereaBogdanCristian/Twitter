<%@page import="com.twitter.model.Message"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="joda" uri="http://www.joda.org/joda/time/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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
	<br>
	<a href="<c:url value='/addmessage' />">Add message </a> <br>
	<a href="<c:url value='/friendsmessages' />">Friends' messages </a> <br>
	<a href="<c:url value='/' />">Home </a> <br>
	
	<form action="/Twitter/search">
		<label>Search user</label><input type="text" name="nameSearch"/> <input type="submit" value="Search" />
	</form>
	<br>
	
	<h3>Messages</h3>
	
	<c:forEach items="${messages}" var="message" varStatus="index">
	
			<c:if test="${ index.index == 0 }"> <c:set var="indexfirstmess" value="${message.id}" /></c:if>
			Id  ${message.id} <br>
			Message  ${message.message} <br>
			Date  <joda:format pattern="dd-MMM-yyyy HH:mm:ss" value="${message.date}"/> <br>
			Author SsoId  ${message.authorSsoId} <br>
			Author Name  ${message.authorName} <br>
			Message Type  ${message.messType} <br>
			<c:set var="indexlastmess" value="${message.id}"> </c:set>
			<c:set var="likes" value="${message.listOfUserLike}"> </c:set>
			Like: ( ${fn:length(likes)} )
			<c:set var="like" value="false"/>
				<c:forEach items="${message.listOfUserLike}" var="userlike">
							<a>${userlike.userAuxEntSsoId} </a>
						<c:if test="${userlike.userAuxEntSsoId == user.ssoId}">
								<c:set var="like" value="true"/>
						</c:if>
				</c:forEach>
			<br>
			<c:set var="shares" value="${message.listOfUserShare}"> </c:set>
			Share: ( ${fn:length(shares)} )
			<c:set var="share" value="false"/>
				<c:forEach items="${message.listOfUserShare}" var="usershare">
							<a>${usershare.userAuxEntSsoId} </a>
						<c:if test="${usershare.userAuxEntSsoId == user.ssoId}">
								<c:set var="share" value="true"/>
						</c:if>
				</c:forEach>
			<br>
			<c:set var="comments" value="${message.comments}"> </c:set>
			Comments: ( ${fn:length(comments)} )
			<c:forEach items="${message.comments}" var="comment" end="1">
				<a>${comment.message} </a>
			</c:forEach>
			
				
			<br>
				<c:choose>
					<c:when test="${like}">
							<a href="<c:url value='/message-unlike-${message.id}?userssoid=${user.ssoId}&username=${user.name}&currentPage=${currentPage}&messageid=${indexfirstmess}&maxNumberOfPages=${maxNumberOfPages}' />">Unlike </a>
					</c:when>
					<c:otherwise>
							<a href="<c:url value='/message-like-${message.id}?userssoid=${user.ssoId}&username=${user.name}&currentPage=${currentPage}&messageid=${indexfirstmess}&maxNumberOfPages=${maxNumberOfPages}' />">Like </a>
					</c:otherwise>
				</c:choose>
				
				<c:choose>
					<c:when test="${share}">
							<a href="<c:url value='/message-unshare-${message.id}?userssoid=${user.ssoId}&username=${user.name}&currentPage=${currentPage}&messageid=${indexfirstmess}&maxNumberOfPages=${maxNumberOfPages}' />">Unshare </a>
					</c:when>
					<c:otherwise>
							<a href="<c:url value='/message-share-${message.id}?userssoid=${user.ssoId}&username=${user.name}&currentPage=${currentPage}&messageid=${indexfirstmess}&maxNumberOfPages=${maxNumberOfPages}' />">Share </a>
					</c:otherwise>
				</c:choose>
				
				<br>
				
				<c:choose>
					<c:when test="${messageCommentId == message.id}">
								<h4>Comments</h4> 
								<c:forEach items="${message.comments}" var="comment">
									Id  ${comment.id} <br>
									Message  ${comment.message} <br>
									Date  <joda:format pattern="dd-MMM-yyyy HH:mm:ss" value="${comment.date}"/> <br>
									Author SsoId  ${comment.authorSsoId} <br>
									Author Name  ${comment.authorName} <br>
									Message Type  ${comment.messType} <br>
									<br>
								</c:forEach>
								
								<c:choose>
									<c:when test="${update}">
										<h4>Update comment</h4> 
									</c:when>
									<c:otherwise>
										<h4>Add comment</h4> 
									</c:otherwise>
								</c:choose> 
										
								<spring:url value="/message-comments" var="addCommentActionUrl" />
										
								<form:form modelAttribute="comment" method="POST" action="${addCommentActionUrl}" enctype="application/x-www-form-urlencoded">
										<input type="hidden" name="idmess" value="${message.id}">
										<input type="hidden" name="userssoid" value="${user.ssoId}">
										<input type="hidden" name="username" value="${user.name}">
										<input type="hidden" name="currentPage" value="${currentPage}">
										<input type="hidden" name="messageid" value="${indexfirstmess}">
										<input type="hidden" name="maxNumberOfPages" value="${maxNumberOfPages}">
										<input type="hidden" name="comments" value="${true}">
										
										<form:textarea path="message" id="message" rows="5" cols="30"/> <form:errors path="message" /><br>
										<c:choose>
											<c:when test="${update}">
												<input type="submit" value="Update" />
											</c:when>
											<c:otherwise>
												<input type="submit" value="Post" />
											</c:otherwise>
										</c:choose> <br>
											
								</form:form>
													
				</c:when>
							<c:otherwise>
									<a href="<c:url value='/message-comments?id=${message.id}&userssoid=${user.ssoId}&username=${user.name}&currentPage=${currentPage}&messageid=${indexfirstmess}&maxNumberOfPages=${maxNumberOfPages}&comments=${true}' />">Comments </a>
							</c:otherwise>
				</c:choose>
				
			<br>
			<br>
	</c:forEach>
	<br>

	<br>
	<c:if test="${currentPage >= 3}">
        <td><a href="<c:url value='/friendsmessages?currentPage=1' />">First</a></td>
    </c:if>
	<c:if test="${currentPage != 1}">
        <td><a href="<c:url value='/friendsmessages?way=backward&messageid=${indexfirstmess}&currentPage=${currentPage - 1}&maxNumberOfPages=${maxNumberOfPages}' />">Previous</a></td>
    </c:if>
	${currentPage}
	<c:if test="${currentPage < maxNumberOfPages}">
        <td><a href="<c:url value='/friendsmessages?way=forward&messageid=${indexlastmess}&currentPage=${currentPage + 1}&maxNumberOfPages=${maxNumberOfPages}' />">Next</a></td>
    </c:if>
    
</body>
</html>