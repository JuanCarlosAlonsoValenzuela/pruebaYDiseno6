<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<p><spring:message code="comment.addComment" /></p>			

<security:authorize access="hasRole('HANDYWORKER')">

<form name="comment" id="comment" action="application/handyWorker/saveComment.do" method="post" >

	<label for="text"><spring:message code="comment.text" />: </label>
	<input type="text" name="comment" id="comment" required="required" placeholder="<spring:message code="comment.writeComment"/>" value="${comment}"/>
	<input type="hidden" name="applicationId" id="applicationId" value="<jstl:out value="${applicationId}"/>"/>
	
	<input type="submit" name="create" value="<spring:message code="comment.create"/>" />	
</form>

<jstl:if test="${comment=='' || comment!=null}">
	<p style="color:red"><spring:message code="operation.error"/></p>
</jstl:if>

	<spring:url var="applicationUrl" value="/application/handyWorker/listComments.do?applicationId={appId}">
	<spring:param name="appId" value="${param.applicationId}"/>
	</spring:url>
	
		<input type="button"
		name="cancel"
		value="<spring:message code="comment.cancel"/>" onclick="javascript:relativeRedir('${applicationUrl}');" />


</security:authorize>