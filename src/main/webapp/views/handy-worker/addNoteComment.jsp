<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<p><spring:message code="comment.addComment" /></p>			

<security:authorize access="hasRole('HANDYWORKER')">

<form name="comment" id="comment" action="note/handyWorker/saveComment.do" method="post" >

	<label for="text"><spring:message code="comment.text" />: </label>
	<input type="text" name="comment" id="comment"  required="required" placeholder="<spring:message code="comment.writeComment"/>"/>
	<input type="hidden" name="noteId" id="noteId" value="<jstl:out value="${noteId}"/>"/>
	<input type="hidden" name="reportId" id="reportId" value="<jstl:out value="${reportId}"/>"/>
	<input type="hidden" name="complaintId" id="complaintId" value="<jstl:out value="${param.complaintId}"/>"/>
	<input type="hidden" name="fixUpTaskId" id="fixUpTaskId" value="<jstl:out value="${param.fixUpTaskId}"/>"/>
	
	
	
	
	<input type="submit" name="create" value="<spring:message code="comment.create"/>" />	
</form>

	<spring:url var="noteUrl" value="/note/handyWorker/listComments.do?noteId={appId}">
	<spring:param name="appId" value="${noteId}"/>
	<spring:param name="reportId" value="${reportId}"/>
	</spring:url>
	<a href="${noteUrl}">
		<spring:message code="comment.cancel" />			
	</a>


</security:authorize>