
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<p><spring:message code="report.notes.list" /></p>


 	
	<display:table pagesize="5" name="notes" id="row" class="displaytag"
			requestURI="note/handyWorker/list.do"> 
			
	<display:column property="moment" titleKey="note.moment"/>
	
	<display:column property="mandatoryComment" titleKey="note.mandatoryComment"/>
	<display:column titleKey="note.comments">
				<jstl:set var="optionalCommentsSize" value="${row.optionalComments.size()}" />
				<spring:url var="commentsUrl" value="note/handyWorker/listComments.do?noteId={noteId}&reportId={reportId}">
							<spring:param name="noteId" value="${row.id}"/>
							<spring:param name="reportId" value="${reportId}"/>
							<spring:param name="complaintId" value="${param.complaintId}"/>	
							<spring:param name="fixUpTaskId" value="${param.fixUpTaskId}"/>
				</spring:url>
				<a href="${commentsUrl}">
							<spring:message var ="viewComments1" code="note.viewComments" />
							<jstl:out value="${viewComments1}(${optionalCommentsSize})" />		
				</a>
	</display:column>
		
	</display:table>
	
	<spring:url var="createNoteUrl" value="note/handyWorker/edit.do">
							<spring:param name="reportId" value="${reportId}"/>
							<spring:param name="complaintId" value="${param.complaintId}"/>	
							<spring:param name="fixUpTaskId" value="${param.fixUpTaskId}"/>
		</spring:url>
		
	
		
		<input type="button"
		name="cancel"
		value="<spring:message code="note.create"/>" onclick="javascript:relativeRedir('${createNoteUrl}');" />

			<spring:url var="complaintUrl" value="report/handyWorker/list.do">
		<spring:param name="complaintId" value="${param.complaintId}"/>	
		<spring:param name="fixUpTaskId" value="${param.fixUpTaskId}"/>
		
	</spring:url>
	
		<input type="button"
		name="cancel"
		value="<spring:message code="handyWorker.back"/>" onclick="javascript:relativeRedir('${complaintUrl}');" />
	
