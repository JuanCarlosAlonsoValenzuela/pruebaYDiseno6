<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<p><spring:message code="handyworker.endorsmentComments" /></p>

<security:authorize access="hasRole('HANDYWORKER')">
	
<display:table pagesize="5" name="${comments}" id="comment"
	requestURI="${requestURI}">
	<display:column titleKey="handyworker.endorsmentComments">
		<strong><jstl:out value="${comment}" /></strong>
	</display:column>
	
</display:table>
	
	<input type="button" name="cancel" value="<spring:message code="handyWorker.back" />"
		onClick="javascript:relativeRedir('endorsement/handyWorker/list.do');" />

</security:authorize>
