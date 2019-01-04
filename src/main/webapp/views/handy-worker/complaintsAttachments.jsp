<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<p><spring:message code="referee.attachments" /></p>
	
<security:authorize access="hasRole('HANDYWORKER')">

<display:table pagesize="5" name="attachments" id="row" class="displaytag" 
					requestURI="/complaint/handyWorker/attachmentList.do">
	<display:column titleKey="complaint.attachments">
		<jstl:out value="${row}"/>
	</display:column>
</display:table>

	<spring:url var="complaintUrl" value="complaint/handyWorker/list.do">
	<spring:param name="fixUpTaskId" value="${param.fixUpTaskId}"/>
	</spring:url>	
		
	<input type="button"
		name="cancel"
		value="<spring:message code="handyWorker.back"/>" onclick="javascript:relativeRedir('${complaintUrl}');" />

</security:authorize>