<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<p><spring:message code="warranty.addLaw" /></p>			

<security:authorize access="hasRole('ADMIN')">

<form name="law" id="law" action="warranty/administrator/laws/create.do" method="post" >

	<label for="law"><spring:message code="law.text" />: </label>
	<input type="text" name="law" id="law" placeholder="<spring:message code="law.writelaw"/>" value="${law}"/>
	<input type="hidden" name="warrantyId" id="warrantyId" value="<jstl:out value="${warrantyId}"/>"/>
	
	<jstl:if test="${law=='' || law!=null}">
	<p style="color:red"><spring:message code="operation.error"/></p>
</jstl:if>
	
	<input type="submit" name="create" value="<spring:message code="law.create"/>" />	
</form>

	<spring:url var="applicationUrl" value="/warranty/administrator/laws/list.do?warrantyId={warrantyId}">
	<spring:param name="warrantyId" value="${warrantyId}"/>
	</spring:url>
	<a href="${applicationUrl}">
		<spring:message code="law.cancel" />			
	</a>


</security:authorize>