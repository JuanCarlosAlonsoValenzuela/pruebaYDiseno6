<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<p><spring:message code="warranty.action.2" /></p>

<security:authorize access="hasRole('ADMIN')">

<form:form modelAttribute="warranty" action="warranty/administrator/edit.do">

	<!--Hidden Attributes -->
	<form:hidden path ="id"/>
	<form:hidden path ="version"/>
	<form:hidden path ="terms"/>
	<form:hidden path ="laws"/>
	
	
	<!-- title -->
	<form:label path="title">
		<spring:message code="warranty.title" />
	</form:label>
	<form:input path="title" />
	<form:errors cssClass="error" path="title"/>
	<br />
	
	<form:label path="isDraftMode"> 
		<spring:message code="warranty.isDraftMode" />:
	</form:label>
	<form:radiobutton path="isDraftMode" value="True"/> <spring:message code="warranty.draftMode" />
	<form:radiobutton path="isDraftMode" value="False"/> <spring:message code="warranty.finalMode" />
	<form:errors cssClass="error" path="isDraftMode"/>
	<br />
	
	<!-- BOTONES -->	
	<input type="submit" name="save" value="<spring:message code="warranty.save" />" /> 
	
	<input type="submit" <jstl:if test="${row.id == 0}">
		<jstl:out value="disabled='disabled'"/></jstl:if>
		name="delete" value="<spring:message code="warranty.delete" />"
		onclick="return confirm('<spring:message code="warranty.verificationDelete" />')" />
	
	<spring:url var="cancelURL"	value="/warranty/administrator/list.do" />
	<a href="${cancelURL}" style="text-decoration: none;">
    	<input type="button" value="<spring:message code="warranty.cancel" />" />
	</a>
</form:form>
</security:authorize>