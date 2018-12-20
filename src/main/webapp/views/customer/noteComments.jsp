
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<p><spring:message code="note.comments.list" /></p>

	<jstl:forEach var="comment" items="comments">
			<jstl:out value="${comment}" />
			<br /> 
	</jstl:forEach> 
	
			
	<spring:url var="createCommentUrl" value="report/note/comment/edit.do?noteId={notId}">
			<spring:param name="notId" value="${note.id}"/>
	</spring:url>
	
	<a href="${createCommentUrl}">
				<spring:message code="comments.create" />			
	</a>
	
	


