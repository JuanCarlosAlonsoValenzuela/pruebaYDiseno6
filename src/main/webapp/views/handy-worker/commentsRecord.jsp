<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<p><spring:message code="comment.record.list" /></p>	

<security:authorize access="isAuthenticated()">

	<jstl:forEach var="comment" items="comments">
			<jstl:out value="${comment}" />
			<br />
	</jstl:forEach>
	
	<spring:url var="createCommentUrl" value="/comment/handyWorker/edit.do?recordId={recordId}">
		<spring:param name="recordId" value="${record.id}"/>
	</spring:url>
	
	
	<!-- Solo el Handy Worker puede añadir comentarios -->
	<security:authorize access="hasRole('HANDYWORKER')">
		<a href="${createCommentUrl}">
			<spring:message code="comments.create" />					
		</a>
	</security:authorize>


</security:authorize>


