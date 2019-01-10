<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<security:authorize access="hasRole('CUSTOMER')">		 	

	<display:table pagesize="5" name="fixUpTasks" id="row" class="displaytag" 
					requestURI="fixUpTask/customer/list.do">
		 
		<display:column>
					<!-- Si la fix up task no ha sido aceptada, me permite proponer una aplicación -->
					<spring:url var="updateFixUpTask" value="/fixUpTask/customer/edit.do?fixUpTaskId={fixId}">
							<spring:param name="fixId" value="${row.id}" />
					</spring:url>
					
					<a href="${updateFixUpTask}">
							<spring:message code="fixUpTask.updateFixUpTask" />				
					</a>
		</display:column>
		
		<display:column>
			
			<jstl:set var="appAccepted" value="false"/>
			<jstl:forEach items="${row.applications}" var="app">
				<jstl:if test="${app.status.toString()=='ACCEPTED'}">
					<jstl:set var="appAccepted" value="true"/>
				</jstl:if>
			</jstl:forEach>
					
			<spring:url var="createComplaint" value="/complaint/customer/create.do">
					<spring:param name="fix" value="${row.id}" />
			</spring:url>
			
			<jstl:if test="${appAccepted==true}">
				<a href="${createComplaint}">
						<spring:message code="fixUpTask.createComplaint" />				
				</a>
			</jstl:if>	
		</display:column>				
		
		<display:column property="momentPublished" titleKey="fixUpTask.momentPublished" format="{0,date,dd/MM/yyyy HH:mm}" />		
		
		<display:column property="description" titleKey="fixUpTask.description" /> 	
		
		<display:column property="address" titleKey="fixUpTask.address"/>			
		
		<display:column property="realizationTime" titleKey="fixUpTask.realizationTime" format="{0,date,dd/MM/yyyy HH:mm}"/> 		
		
		<!-- See Warranties -->
		
		<display:column titleKey="fixUpTask.warranties">								
				
				<spring:url var="warrantiesUrl" value="/warranty/customer/show.do?warrantyId={fixId}">
						<spring:param name="fixId" value="${row.warranty.id}" />
				</spring:url>
				
				<a href="${warrantiesUrl}">
						<jstl:out value="${row.warranty.title}" />
				</a>
				
		</display:column>
		
		<display:column titleKey="fixUpTask.categories">	
			<jstl:set var="category" value="${row.category.name}"/>
			<jstl:if test="${locale=='ES'}">
				<jstl:set var="category" value="${row.category.nameSpanish}"/>
			</jstl:if>
			<jstl:out value="${category}"/>
		</display:column>
		
		
		<!-- See Applications -->
		<display:column titleKey="fixUpTask.applications">									
				<jstl:set var="applicationsSize" value="${row.applications.size()}" />
				
				<jstl:if test="${applicationsSize > 0}">
						<spring:url var="applicationsUrl" value="/application/customer/list.do?fixUpTaskId={fixId}">
							<spring:param name="fixId" value="${row.id}"/>	
						</spring:url>
						<a href="${applicationsUrl}">
							<spring:message var="seeApplications" code="fixUpTask.seeApplications"/> 		
							<jstl:out value="${seeApplications}(${applicationsSize})" />
						</a>
				</jstl:if>
				
				<jstl:if test="${applicationsSize == 0}">
						<spring:message code="applications.noDisplay" />		
				</jstl:if>
				
		</display:column>
		
		<!-- See Phases -->
		<display:column titleKey="fixUpTask.phases">									
				<jstl:set var="phasesSize" value="${row.phases.size()}" />
						
				<jstl:if test="${phasesSize > 0}">
					<spring:url var="phasesUrl" value="/phase/customer/list.do?fixUpTaskId={fixId}">
						<spring:param name="fixId" value="${row.id}"/>	
					</spring:url>
					<a href="${phasesUrl}">
						<spring:message var="seePhases" code="fixUpTask.seePhases"/> 	
						<jstl:out value="${seePhases}(${phasesSize})" />
					</a>
				</jstl:if>
				
				<jstl:if test="${phasesSize == 0}">
					<spring:message code="phases.noDisplay"/>
				</jstl:if>
		</display:column>
		
		<!-- See Complaints -->
		<display:column titleKey="fixUpTask.complaints">									
				<jstl:set var="complaintsSize" value="${row.complaints.size()}" />
				
				<jstl:if test="${complaintsSize > 0}">
					<spring:url var="complaintsUrl" value="/complaint/customer/listPerTask.do?fixUpTaskId={fixId}">
						<spring:param name="fixId" value="${row.id}"/>	
					</spring:url>
					<a href="${complaintsUrl}">
						<spring:message var="seeComplaints" code="fixUpTask.seeComplaints"/> 	
						<jstl:out value="${seeComplaints}(${complaintsSize})" />
					</a>
				</jstl:if>
				
				<jstl:if test="${complaintsSize == 0}">
					<spring:message code="complaints.noDisplay"/>
				</jstl:if>
		</display:column>
		
	</display:table>
	
	<spring:url var="createFixUpTask" value="/fixUpTask/customer/create.do"/>
	<a href="${createFixUpTask}">
			<spring:message code="fixUpTask.create"/>					
	</a>
	
	
</security:authorize>