<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<p><spring:message code="complaint.referee.assign" /></p>	

<security:authorize access="hasRole('REFEREE')">

<form:form action="complaint/referee/assign.do" modelAttribute="complaint">

		<!-- Hidden Attributes -->
		
		<form:hidden path="ticker" />
		<form:hidden path="moment" />
		<form:hidden path="description" />
		<form:hidden path="attachments" />
		<form:hidden path="reports" />
		<form:hidden path="referee" />
		
		<input type="hidden" name="comp" value="${comp}" />
		
		<input type="submit" name="save" value="<spring:message code="complaint.assign.button"/>" />	
			
		
		<input type="submit" name="cancel" value="<spring:message code="complaint.cancel.button"/>" />
		


</form:form>

</security:authorize>