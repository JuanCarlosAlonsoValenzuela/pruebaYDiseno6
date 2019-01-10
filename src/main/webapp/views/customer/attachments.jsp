<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<p>
	<spring:message code="complaint.attachments" />
</p>

<security:authorize access="hasRole('CUSTOMER')">
	
	<display:table pagesize="5" name="attachments" id="row"
		requestURI="${requestURI}">
			<display:column titleKey="attachment.name">
				<jstl:out value="${row}"/>
			</display:column>
	</display:table>
	
	<jstl:set var="javascript" value="javascript:relativeRedir('complaint/customer/list.do');"/>
	<jstl:if test="${fixUpTaskId>0}">
		<jstl:set var="javascript" value="javascript:relativeRedir('complaint/customer/listPerTask.do?fixUpTaskId=${fixUpTaskId}');"/>
	</jstl:if>
	
	<input type="button" name="cancel" onclick="${javascript}"  value="<spring:message code="phase.back.button"/>" />	
	
</security:authorize>