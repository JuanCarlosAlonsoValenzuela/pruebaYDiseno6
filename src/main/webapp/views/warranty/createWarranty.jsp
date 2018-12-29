<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<p><spring:message code="warranty.create" /></p>

<security:authorize access="hasRole('ADMIN')"> 

<form:form modelAttribute="warranty" action="warranty/administrator/create.do">
    <!--Hidden Attributes -->
	<form:hidden path ="id"/>
	<form:hidden path ="version"/>

	<form:hidden path ="terms"/>
	<form:hidden path ="laws"/>
	<form:hidden path ="isDraftMode"/>

	<!-- Warranty Attributes -->
	<form:label path="title">
		<spring:message code="warranty.title" />
	</form:label>
	<form:input path="title" />
	<form:errors cssClass="error" path="title"/>
	<br />

	
	<!-- BOTONES -->	
	<input type="submit" name="save" value="<spring:message code="warranty.save" />" /> 
	
	
	<spring:url var="cancelURL"	value="/warranty/administrator/list.do" />
	<a href="${cancelURL}" style="text-decoration: none;">
    	<input type="button" value="<spring:message code="warranty.cancel" />" />
	</a>
		
	</form:form>
	
</security:authorize>