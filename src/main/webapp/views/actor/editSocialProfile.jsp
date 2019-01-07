<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<p><spring:message code="socialProfile.createEditSocialProfile" /></p>

<security:authorize access="isAuthenticated()">

<form:form modelAttribute="socialProfile" action="authenticated/socialProfile/create.do">
		
	<!--Hidden Attributes -->
	<form:hidden path ="id"/>
	<form:hidden path ="version"/>
	
	<!-- nick -->
	<form:label path="nick">
		<spring:message code="socialProfile.nick" />
	</form:label>
	<form:input path="nick" />
	<form:errors cssClass="error" path="nick"/>
	<br />
	
	<!-- name -->
	<form:label path="name">
		<spring:message code="socialProfile.name" />
	</form:label>
	<form:input path="name" />
	<form:errors cssClass="error" path="name"/>
	<br />
	
	<!-- profileLink -->
	<form:label path="profileLink">
		<spring:message code="socialProfile.profileLink" />
	</form:label>
	<form:input path="profileLink" />
	<form:errors cssClass="error" path="profileLink"/>
	<br />
	
	
	<!-- BOTONES -->	
	<input type="submit" name="save" value="<spring:message code="socialProfile.save" />" /> 
	
	<input type="submit" <jstl:if test="${socialProfile.id == 0}">
		<jstl:out value="disabled='disabled'"/></jstl:if>
		name="delete" value="<spring:message code="socialProfile.delete" />"
		onclick="return confirm('<spring:message code="socialProfile.verificationDelete" />')" />
	
	<jstl:if test="${isHandyWorker}">		
		<spring:url var="cancelURL"	value="/handyWorker/handyWorker/showProfile.do" />
			<a href="${cancelURL}" style="text-decoration: none;">
    			<input type="button" value="<spring:message code="socialProfile.cancel" />" />
			</a>
	</jstl:if>
	<jstl:if test="${!isHandyWorker}">
		<spring:url var="cancelURL"	value="/authenticated/showProfile.do" />
			<a href="${cancelURL}" style="text-decoration: none;">
    			<input type="button" value="<spring:message code="socialProfile.cancel" />" />
			</a>
	</jstl:if>
	
</form:form>
</security:authorize>