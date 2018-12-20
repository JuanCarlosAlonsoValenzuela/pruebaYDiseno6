<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<p><spring:message code="educationRecord.handyWorker.title" /></p>

<security:authorize access="isAuthenticated()">

	<display:table pagesize="5" name="educationRecords" id="row" class="displaytag" 
					requestURI="/educationRecord/handyWorker/list.do">
					
					<!-- Autorización para que solo el handy worker pueda editar -->
					<security:authorize access="hasRole('HANDYWORKER')">
						<spring:url var="editUrl" value="educationRecord/handyWorker/edit.do?educationRecordId={recordId}">
							<spring:param name="recordId" value="${row.id}" />
						</spring:url>
						
						<a href="${editUrl}">
							<spring:message code="educationRecord.handyWorker.editEducationRecord" />	
						</a>
					</security:authorize>
					<!-- Fin de la Autorización -->
					
					<!-- Title -->
					<display:column property="title" titleKey="educationRecord.title" />	
					
					<!-- Start Date Study -->
					<display:column property="startDateStudy" titleKey="educationRecord.startDateStudy"	
						format="{0,date,dd/MM/yyyy HH:mm}" />				
						
					<!-- End Date Study -->
					<display:column property="endDateStudy" titleKey="educationRecord.endDateStudy"	
						format="{0,date,dd/MM/yyyy HH:mm}" />				
						
					<!-- Institution -->
					<display:column property="institution" titleKey="educationRecord.institution" />	
					
					<!-- URL -->
					<display:column property="url" titleKey="educationRecord.url" />	
						
					<!-- View Comments -->
					<display:column titleKey="educationRecord.comments">	
							<jstl:set var="commentsSize" value="${row.comments.size()}" />
							
							<spring:url var="commentsUrl" value="/comment/handyWorker/list.do?educationRecord={recordId}">
									<spring:param name="recordId" value="${row.id}"/>
							</spring:url>
							
							<a href="${commentsUrl}">
									<spring:message var ="viewComments1" code="educationRecord.viewComments" />	
									<jstl:out value="${viewComments1}(${commentsSize})" />		
							</a>
					</display:column>
	</display:table>
	
	<!-- Autorización para que solo el handy worker pueda crear -->
	<security:authorize access="hasRole('HANDYWORKER')">
			<spring:url var="create" value="educationRecord/handyWorker/create.do?recordId={recordId}">
					<spring:param name="recordId" value="${row.id}" />
			</spring:url>
						
			<a href="${editUrl}">
					<spring:message code="educationRecord.handyWorker.createEducationRecord" />	
			</a>
	</security:authorize>
	<!-- Fin de la Autorización -->	
	
	
</security:authorize>