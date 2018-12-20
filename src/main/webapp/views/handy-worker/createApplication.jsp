<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<p><spring:message code="createApplication.title" /></p>		

<security:authorize access="hasRole('HANDYWORKER')">

	<form:form action="application/handyWorker/edit.do" modelAttribute="application">
		
		<!-- Hidden Attributes -->	
		<form:hidden path="id" />
		<form:hidden path="version" />
		<form:hidden path="moment" />
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
	
		<input type="submit" name="cancel" value="<spring:message code="application.cancel"/>" />	
	
	</form:form>

</security:authorize>