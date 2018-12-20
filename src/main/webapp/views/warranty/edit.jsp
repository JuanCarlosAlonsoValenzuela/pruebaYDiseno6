<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<p><spring:message code="warranty.action.2" /></p>

<security:authorize access="hasRole('ADMIN')">

<form:form modelAttribute="warranty">
	<form:label path="title"><spring:message code="warranty.title" />:</form:label>
	<form:input path="title" />
	</br>
	
	<form:label path="draftMode"><spring:message code="warranty.isDraftMode" />:</form:label>
	<form:radiobutton path = "draftMode" value = <spring:message code="warranty.draftMode" /> />
    <form:radiobutton path = "draftMode" value = <spring:message code="warranty.finalMode" /> />
	</br>
	
	<input type="submit" name="save" value="<spring:message code="warranty.save" />" />

	<input type="submit" <jstl:if test="${row.id == 0}">
		<jstl:out value="disabled='disabled'"/></jstl:if>
		name="delete" value="<spring:message code="warranty.delete" />"
		onclick="return confirm('<spring:message code="warranty.verificationDelete" />')" />

	<input type="submit" name="cancel" value="<spring:message code="warranty.cancel" />"
		onClick="javascript: relativeRedir('warranty/list.do');" />
</form:form>
</security:authorize>