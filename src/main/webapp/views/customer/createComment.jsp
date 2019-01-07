<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<p><spring:message code="comment.addComment" /></p>			

<security:authorize access="hasRole('CUSTOMER')">

<form name="comment" id="comment" action="endorsement/customer/comment/create.do" method="post" >

	<label for="comment"><spring:message code="comment.text" />: </label>
	<input type="text" name="comment" id="comment" placeholder="<spring:message code="comment.addComment"/>" value="${comment}"/>
	<input type="hidden" name="endorsementId" id="endorsementId" value="<jstl:out value="${endorsementId}"/>"/>
	
	<input type="submit" name="create" value="<spring:message code="customer.addComment"/>" />	
</form>

	<spring:url var="endorsementURL" value="/endorsement/customer/listComments.do?endorsementId={endorsementId}">
	<spring:param name="endorsementId" value="${endorsementId}"/>
	</spring:url>
	<a href="${endorsementURL}">
		<spring:message code="comment.cancel" />			
	</a>


</security:authorize>