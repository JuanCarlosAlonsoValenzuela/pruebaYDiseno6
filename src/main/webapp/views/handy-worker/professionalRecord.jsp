<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<p><spring:message code="professionalRecord.handyWorker.title" /></p>	 <!-- Tiles -->

<security:authorize access="isAuthenticated()">

	<display:table pagesize="5" name="professionalRecord" id="row" class="displaytag" 
					requestURI="/professionalRecord/handyWorker/list.do">
					
					<!-- Autorización para que solo el handy worker pueda editar -->
					<security:authorize access="hasRole('HANDYWORKER')">
						<spring:url var="editUrl" value="professionalRecord/handyWorker/edit.do?professionalRecordId={recordId}">
							<spring:param name="recordId" value="${row.id}" />
						</spring:url>
						
						<a href="${editUrl}">
							<spring:message code="professionalRecord.handyWorker.editProfessionalRecord" />
						</a>
					</security:authorize>
					<!-- Fin de la Autorización -->
					
					
					<!-- Name Company -->
					<display:column property="nameCompany" titleKey="professionalRecord.title" />	
					
					<!-- Start Date Study -->
					<display:column property="startDate" titleKey="professionalRecord.startDate"	
						format="{0,date,dd/MM/yyyy HH:mm}" />				
						
					<!-- End Date Study -->
					<display:column property="endDate" titleKey="professionalRecord.endDate"	
						format="{0,date,dd/MM/yyyy HH:mm}" />				
					
					<!-- Role -->
					<display:column property="role" titleKey="professionalRecord.role" />	
					
					<!-- Link Attachements -->
					<display:column property="linkAttachment" titleKey="professionalRecord.linkAttachment" />
					
					<!-- View Comments -->
					<display:column titleKey="professionalRecord.comments">	<!-- Añadir -->
							<jstl:set var="commentsSize" value="${row.comments.size()}" />
							
							<spring:url var="commentsUrl" value="/comment/handyWorker/list.do?professionalRecordId={recordId}">
									<spring:param name="recordId" value="${row.id}"/>
							</spring:url>
							
							<a href="${commentsUrl}">
									<spring:message var ="viewComments1" code="professionalRecord.viewComments" />	
									<jstl:out value="${viewComments1}(${commentsSize})" />		
							</a>
					</display:column>
					
	</display:table>
	
	
	<!-- Autorización para que solo el handy worker pueda crear -->
	<security:authorize access="hasRole('HANDYWORKER')">
			<spring:url var="create" value="professionalRecord/handyWorker/create.do?recordId={recordId}">
					<spring:param name="recordId" value="${row.id}" />
			</spring:url>
						
			<a href="${editUrl}">
					<spring:message code="professionalRecord.handyWorker.createProfessionalRecord" />
			</a>
	</security:authorize>
	<!-- Fin de la Autorización -->
		
</security:authorize>