<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<p><spring:message code="warranty.addTerm" /></p>			

<security:authorize access="hasRole('ADMIN')">

<form name="term" id="term" action="warranty/administrator/terms/create.do" method="post" >

	<label for="term"><spring:message code="term.text" />: </label>
	<input type="text" name="term" id="term" placeholder="<spring:message code="term.writeterm"/>" value="${term}"/>
	<input type="hidden" name="warrantyId" id="warrantyId" value="<jstl:out value="${warrantyId}"/>"/>
	
	<jstl:if test="${term=='' || term!=null}">
	<p style="color:red"><spring:message code="operation.error"/></p>
</jstl:if>
	
	
	<input type="submit" name="create" value="<spring:message code="term.create"/>" />	
</form>

	<spring:url var="applicationUrl" value="/warranty/administrator/terms/list.do?warrantyId={warrantyId}">
	<spring:param name="warrantyId" value="${warrantyId}"/>
	</spring:url>
	<a href="${applicationUrl}">
		<spring:message code="term.cancel" />			
	</a>


</security:authorize>