<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<p>
	<spring:message code="administrator.action.3" />
</p>

<security:authorize access="hasRole('ADMIN')">

<form:form modelAttribute="broadcast">
	<form:label path="message"><spring:message code="broadcast.message" />:</form:label>
	<form:input path="message" />
	</br>
	
	<input type="submit" name="send" value="<spring:message code="broadcast.send" />" />
	
	<input type="submit" name="cancel" value="<spring:message code="broadcast.cancel" />"
		onClick="javascript: relativeRedir('administrator/action-7.do');" />
</form:form>

</security:authorize>
