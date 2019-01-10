<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<p><spring:message code="customer.complaints" /></p>

<security:authorize access="hasRole('CUSTOMER')">

	<display:table pagesize="5" name="complaints" id="row"
	class="displaytag" requestURI="complaint/customer/list.do">
		
		<display:column property="moment" titleKey="complaint.moment"
					sortable="true" format="{0,date,dd/MM/yyyy HH:mm}"  />	
					
		<display:column property="description" titleKey="complaint.description" /> 
			
		<display:column titleKey="customer.attachments">
				<jstl:set var="attachmentsSize" value="${row.attachments.size()}" />
				<spring:url var="attachmentsUrl" value="/attachment/customer/list.do?complaintId={compId}">
							<spring:param name="compId" value="${row.id}"/>
				</spring:url>
				<jstl:if test="${fixUpTaskId>0}">
				<spring:url var="attachmentsUrl" value="/attachment/customer/listPerFixUpTask.do?complaintId={compId}">
							<spring:param name="compId" value="${row.id}"/>
							<spring:param name="fixUpTaskId" value="${fixUpTaskId}"/>
				</spring:url>
				</jstl:if>
				<a href="${attachmentsUrl}">
							<spring:message var ="viewAttachments1" code="customer.viewAttachments" />
							<jstl:out value="${viewAttachments1}(${attachmentsSize})" />		
				</a>
		</display:column>
		<display:column titleKey="complaint.reports">
				<spring:url var="reportsUrl" value="/report/customer/list.do?complaintId={compId}">
							<spring:param name="compId" value="${row.id}"/>
				</spring:url>
				<a href="${reportsUrl}">
							<spring:message var ="reports" code="complaint.reports" />
							<jstl:out value="${reports}" />		
				</a>
				
		</display:column>
		
		</display:table>
		
		<input type="button"
		name="cancel"
		value="<spring:message code="customer.cancel"/>" onclick="javascript:relativeRedir('fixUpTask/customer/list.do');" />
</security:authorize>