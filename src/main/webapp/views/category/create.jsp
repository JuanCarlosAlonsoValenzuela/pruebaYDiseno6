<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<p>
	<spring:message code="category.action.4" />
</p>

<security:authorize access="hasRole('ADMIN')">

	<form:form modelAttribute="name">
		<form:label path="n"></form:label>
		<form:input path="n" />
		<input type="submit" name="save"
			value="<spring:message code="category.save" />" />
	</form:form>

</security:authorize>