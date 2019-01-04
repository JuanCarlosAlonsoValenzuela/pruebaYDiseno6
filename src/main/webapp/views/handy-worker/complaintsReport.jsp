<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<p><spring:message code="complaints.report" /></p>		


	<display:table pagesize="5" name="reports" id="row"
	class="displaytag" requestURI="handyWorker/report/list.do">
	
	
	<display:column property="moment" titleKey="report.moment" /> 
	
	<display:column property="description" titleKey="report.description" /> 
	
	<display:column titleKey="report.attachments">
				<jstl:set var="attachmentsSize" value="${row.attachments.size()}" />
				<spring:url var="attachmentsUrl" value="/report/handyWorker/attachmentList.do">
							<spring:param name="reportId" value="${row.id}"/>
							<spring:param name="complaintId" value="${param.complaintId}"/>
							<spring:param name="fixUpTaskId" value="${param.fixUpTaskId}"/>
				</spring:url>
				<a href="${attachmentsUrl}">
							<spring:message var ="viewAttachments1" code="report.viewAttachments" />
							<jstl:out value="${viewAttachments1} (${attachmentsSize})" />		
				</a>
		</display:column>
		
	
	<display:column titleKey="report.notes">
				<jstl:set var="reportsSize" value="${row.notes.size()}" />
				<spring:url var="notesUrl" value="/note/handyWorker/list.do">
							<spring:param name="reportId" value="${row.id}"/>
							<spring:param name="complaintId" value="${param.complaintId}"/>
							<spring:param name="fixUpTaskId" value="${param.fixUpTaskId}"/>
				</spring:url>
				<a href="${notesUrl}">
							<spring:message var ="viewNotes1" code="report.viewNotes" />
							<jstl:out value="${viewNotes1} (${reportsSize})" />		
				</a>
	</display:column>
	
	</display:table>
	
	<spring:url var="complaintUrl" value="complaint/handyWorker/list.do">
	<spring:param name="fixUpTaskId" value="${param.fixUpTaskId}"/>
	</spring:url>
	
	<input type="button"
		name="cancel"
		value="<spring:message code="handyWorker.back"/>" onclick="javascript:relativeRedir('${complaintUrl}');" />

