<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<p><spring:message code="miscellaneousRecord.handyWorker.title" /></p>	

<security:authorize access="isAuthenticated()">
	
	<display:table pagesize="5" name="miscellaneousRecords" id="row" class="displaytag" 
					requestURI="/miscellaneousRecord/handyWorker/list.do">
					
					<!-- Autorización para que solo el handy worker pueda editar -->
					<security:authorize access="hasRole('HANDYWORKER')">
						<spring:url var="editUrl" value="miscellaneousRecord/handyWorker/edit.do?miscellaneousRecordId={recordId}">
							<spring:param name="recordId" value="${row.id}" />
						</spring:url>
						
						<a href="${editUrl}">
							<spring:message code="endorserRecord.handyWorker.editMiscellaneousRecord" />	
						</a>
					</security:authorize>
					<!-- Fin de la Autorización -->
					
					<!-- Title -->
					<display:column property="title" titleKey="miscellaneousRecord.title" />	
					
					<!-- Link Attachements -->
					<display:column property="linkAttachment" titleKey="miscellaneousRecord.linkAttachment" />
					
					
					<!-- View Comments -->
					<display:column titleKey="miscellaneousRecord.comments">	<!-- Añadir -->
							<jstl:set var="commentsSize" value="${row.comments.size()}" />
							
							<spring:url var="commentsUrl" value="/comment/handyWorker/list.do?miscellaneousRecord={recordId}">
									<spring:param name="recordId" value="${row.id}"/>
							</spring:url>
							
							<a href="${commentsUrl}">
									<spring:message var ="viewComments1" code="miscellaneousRecord.viewComments" />	
									<jstl:out value="${viewComments1}(${commentsSize})" />		
							</a>
					</display:column>
					
					
	</display:table>
	
	<!-- Autorización para que solo el handy worker pueda crear -->
	<security:authorize access="hasRole('HANDYWORKER')">
			<spring:url var="create" value="miscellaneousRecord/handyWorker/create.do?recordId={recordId}">
					<spring:param name="recordId" value="${row.id}" />
			</spring:url>
						
			<a href="${editUrl}">
					<spring:message code="miscellaneousRecord.handyWorker.createMiscellaneousRecord" />	
			</a>
	</security:authorize>
	<!-- Fin de la Autorización -->	
	
	
</security:authorize>