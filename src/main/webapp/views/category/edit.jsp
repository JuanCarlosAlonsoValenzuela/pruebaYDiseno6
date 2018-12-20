<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<p><spring:message code="category.action.2" /></p>

<security:authorize access="hasRole('ADMIN')">

<form:form modelAttribute="category">
	<form:label path="name"><spring:message code="category.name" />:</form:label>
	<form:input path="name" />
	</br>
	
	<input type="submit" name="save" value="<spring:message code="category.save" />" />

	<input type="submit" <jstl:if test="${row.id == 0}">
		<jstl:out value="disabled='disabled'"/></jstl:if>
		name="delete" value="<spring:message code="category.delete" />"
		onclick="return confirm('<spring:message code="category.verificationDelete" />')" />

	<input type="submit" name="cancel" value="<spring:message code="category.cancel" />"
		onClick="javascript: relativeRedir('category/list.do');" />
</form:form>
</security:authorize>