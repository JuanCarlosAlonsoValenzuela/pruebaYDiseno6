<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<p><spring:message code="comment.addComment" /></p>			

<security:authorize access="hasRole('CUSTOMER')">

<form name="comment" id="comment" action="application/customer/saveComment.do" method="post">
	<label for="male"><spring:message code="comment.text" />: </label>
	<input type="text" name="comment" id="comment" placeholder="<spring:message code="comment.writeComment"/>" value="${comment}"/>
	<input type="hidden" name="applicationId" id="applicationId" value="<jstl:out value="${applicationId}"/>"/>
	<input type="hidden" name="fixUpTaskId" id="fixUpTaskId" value="<jstl:out value="${fixUpTaskId}"/>"/>
	
	<input type="submit" name="create" value="<spring:message code="comment.create"/>" />	
</form>

<jstl:if test="${comment=='' || comment!=null}">
	<p style="color:red"><spring:message code="operation.error"/></p>
</jstl:if>

</security:authorize>