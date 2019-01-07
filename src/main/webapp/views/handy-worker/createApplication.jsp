<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<p><spring:message code="createApplication.title" /></p>		

<security:authorize access="hasRole('HANDYWORKER')">

		<spring:url var="createApplicationUrl" value="application/handyWorker/edit.do?fixUpTaskId={fixId}">
						<spring:param name="fixId" value="${param.fixUpTaskId}" />
					</spring:url>

	<form:form action="${createApplicationUrl}" modelAttribute="application">
	
		
		<!-- Hidden Attributes -->	
		<form:hidden path="id" />
		<form:hidden path="version" />
		<form:hidden path="moment" />
		<form:hidden path="comments" />
		<form:hidden path="status" />
		<form:hidden path="fixUpTask" />
		<form:hidden path="handyWorker" />
		
		<!-- Offered Prices -->
		<form:label path="offeredPrice">
		<spring:message code="createApplication.offeredPrice" />	
		</form:label>
		<form:input path="offeredPrice" />
		<form:errors cssClass="error" path="offeredPrice" />
		<br />
		
		<input type="submit" name="create" value="<spring:message code="application.create"/>" />	
	
	
	</form:form>
	
		
	<input type="button"
		name="cancel"
		value="<spring:message code="handyWorker.cancel"/>" onclick="javascript:relativeRedir('fixUpTask/handyWorker/list.do');" />


</security:authorize>