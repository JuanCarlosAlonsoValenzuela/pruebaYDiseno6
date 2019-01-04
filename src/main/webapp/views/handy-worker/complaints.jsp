<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<p><spring:message code="customer.complaints" /></p>	


	


	<display:table pagesize="5" name="complaints" id="row"
	class="displaytag" requestURI="/complaint/handyWorker/list.do">
		
		<display:column property="moment" titleKey="complaint.moment"
					sortable="true" format="{0,date,dd/MM/yyyy HH:mm}"  />	
					
		<display:column property="description" titleKey="complaint.description" /> 
			
				<display:column titleKey="complaint.attachments">
				<jstl:set var="attachmentsSize" value="${row.attachments.size()}" />
				<spring:url var="attachmetsUrl" value="/complaint/handyWorker/attachmentList.do">
							<spring:param name="complaintId" value="${row.id}"/>
							<spring:param name="fixUpTaskId" value="${param.fixUpTaskId}"/>
				</spring:url>
				<a href="${attachmetsUrl}">
							<spring:message var ="viewAttachments1" code="complaint.viewAttachments" />
							<jstl:out value="${viewAttachments1}(${attachmentsSize})" />		
				</a>
		</display:column>
		
		<display:column titleKey="complaint.reports">
		<jstl:set var="reportsSize" value="${row.reports.size()}" />
		<spring:url var="reportsUrl" value="/report/handyWorker/list.do">
							<spring:param name="complaintId" value="${row.id}"/>
							<spring:param name="fixUpTaskId" value="${param.fixUpTaskId}"/>
		</spring:url>
		<a href="${reportsUrl}">
							<spring:message var ="viewReports1" code="complaint.viewReports" />
							<jstl:out value="${viewReports1} (${reportsSize})" />		
				</a>
		</display:column>
		</display:table>
		
	
	<input type="button"
		name="cancel"
		value="<spring:message code="handyWorker.back"/>" onclick="javascript:relativeRedir('fixUpTask/handyWorker/list.do');" />
		
		
	
		 
			