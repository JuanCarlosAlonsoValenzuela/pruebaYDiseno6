<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<p><spring:message code="endorsement.deleteConfirmation" /></p>	

<security:authorize access="hasRole('CUSTOMER')">

<form:form modelAttribute="endorsement" action="endorsement/customer/delete.do">

	<form:hidden path ="id"/>
	<form:hidden path ="version"/>
	
	<form:hidden path ="moment"/>
	<form:hidden path ="writtenBy"/>
	<form:hidden path ="comments"/>
	<form:hidden path ="writtenTo"/>
	
	<!-- DELETE BUTTON -->
	<input type="submit" name="delete" value="<spring:message code="endorsment.delete" />" onClick="return confirm('<spring:message code="endorsement.deleteConfirmation" />')">
		
	
	<!-- CANCEL BUTTON -->
	<input type="button" name="cancel" value="<spring:message code="customer.cancel" />"	
		onClick="javascript:relativeRedir('endorsement/customer/list.do');" /> 
</form:form>	

</security:authorize>