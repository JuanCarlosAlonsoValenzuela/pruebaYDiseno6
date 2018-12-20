<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<p><spring:message code="handyWorker.workPlan" /></p>		<!-- Añadir --> <!-- Tiles -->

<security:authorize access="hasRole('HANDYWORKER')">
	
	<display:table pagesize="5" name="phases" id="row" class="displaytag" 
					requestURI="/workPlan/handyWorker/list.do">
					
				<display:column>
						
						<spring:url var="editUrl" value="/workPlan/handyWorker/edit.do?phaseId={phaseId}">
								<spring:param name="phaseId" value="${row.id}"/>
						</spring:url>
								
						<a href="${editUrl}">
								<spring:message code="workPlan.editPhase" />		<!-- Añadir -->
						</a>
					
				</display:column>  
				
				<display:column property="title" titleKey="workPlan.title"/>	<!-- Añadir --><!-- Tiles -->
				<display:column property="description" titleKey="workPlan.description" /> <!-- Añadir -->
				<display:column property="startMoment" titleKey="workPlan.startMoment" /> <!-- Añadir -->
				<display:column property="endMoment" titleKey="workPlan.endMoment" /> <!-- Añadir -->
				
	</display:table>
	
	<spring:url var="createUrl" value="/workPlan/handyWorker/edit.do">
								<spring:param name="phaseId" />
	</spring:url>
								
	<a href="${createUrl}">
			<spring:message code="workPlan.createPhase" />		<!-- Añadir -->
	</a>
	
	

</security:authorize>