<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<p><spring:message code="report.attachments" /></p>

<security:authorize access="hasRole('REFEREE')">
<jstl:forEach var="attachment" items="attachments">
			<jstl:out value="${attachment}" />
			<br /> 
	</jstl:forEach> 	
	
	
		<spring:url var="createAttachmentUrl" value="report/attachment/edit.do?reportId={repId}">
					<spring:param name="repId" value="${repId}"/>
		</spring:url>
		
	
		<a href="${createAttachmentUrl}">
				<spring:message code="attachment.create" />			
		</a>
		
</security:authorize>