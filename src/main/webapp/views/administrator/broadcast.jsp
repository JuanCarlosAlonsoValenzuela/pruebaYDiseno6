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

<form:form modelAttribute="messageSend" action="broadcast/administrator/send.do">

	<form:hidden path="id"/>
	<form:hidden path="version"/>
	<form:hidden path="moment"/>
	<form:hidden path="sender"/>
	<form:hidden path="receiver"/>
	<form:hidden path="tags"/>
	<form:hidden path="priority"/>


	<form:label path="subject">
		<spring:message code="broadcast.subject"/>:
	</form:label>
	<form:input path="subject" />
	<form:errors cssClass="error" path="subject"/>
	<br />


	<form:label path="body">
		<spring:message code="mail.message"/>:
	</form:label>
	<form:textarea path="body" />
	<form:errors cssClass="error" path="body"/>
	<br />
	
	<input type="submit" name="send" value="<spring:message code="broadcast.send" />" />
	
</form:form>

</security:authorize>
