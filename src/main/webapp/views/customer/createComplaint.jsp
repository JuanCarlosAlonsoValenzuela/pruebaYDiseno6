<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<p><spring:message code="complaint.customer.create" /></p>	

<security:authorize access="hasRole('CUSTOMER')">

<form:form action="/complaint/customer/edit.do" modelAttribute="complaint">

		<!-- Hidden Attributes -->
		<form:hidden path="id"/>
		<form:hidden path="ticker" />
		<form:hidden path="moment" />
		
			
		<!-- Description -->
		<form:label path="description">	<!-- Tiles -->
			<spring:message code="fixUpTask.description" />	
		</form:label>
		<form:textarea path="description"/>
		<form:errors cssClass="error" path="description" />
		
			
		<!-- Attachments -->
		<form:label path="attachments">	<!-- Tiles Revisar -->
			<spring:message code="complaint.attachments" />	
		</form:label>
		<form:textarea path="attachments"/>
		<form:errors cssClass="error" path="attachmets" />
		
		<input type="submit" name="create" value="<spring:message code="fixUpTask.create.button"/>" />	
			
		
		<input type="submit" name="cancel" value="<spring:message code="fixUpTask.cancel.button"/>" />
		


</form:form>

</security:authorize>

