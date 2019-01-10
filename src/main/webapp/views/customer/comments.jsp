
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<security:authorize access="hasRole('CUSTOMER')">

<display:table pagesize="5" name="comments" id="row" class="displaytag" 
					requestURI="/application/customer/listComments.do">
	<display:column titleKey="application.comments">
		<jstl:out value="${row}"/>
	</display:column>
</display:table>

<spring:url var="newComment" value="/application/customer/newComment.do?fixUpTaskId={fixId}&applicationId={appId}">
	<spring:param name="appId" value="${applicationId}"/>
	<spring:param name="fixId" value="${fixUpTaskId}"/>
</spring:url>
<a href="${newComment}">
	<spring:message var ="newComment1" code="application.comment.new" />
	<jstl:out value="${newComment1}"/>
</a>

<br/>

<spring:url var="urlApplications" value="/application/customer/list.do?fixUpTaskId={fixId}">
	<spring:param name="fixId" value="${fixUpTaskId}"/>
</spring:url>
<a href="${urlApplications}">
	<spring:message var ="back" code="application.back" />
	<jstl:out value="${back}"/>
</a>
</security:authorize>