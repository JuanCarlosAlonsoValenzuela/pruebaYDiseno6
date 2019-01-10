<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<p><spring:message code="handyWorker.fixUpTasksCustomerInfo" /></p>			

<security:authorize access="hasRole('HANDYWORKER')">

		<display:table pagesize="5" name="customer" id="row" class="displaytag"  requestURI="/fixUpTask/handyWorker/list.do">
					
					<display:column property="name" titleKey="fixUpTaskCustomerInfo.name" />  
					
					<display:column property="middleName" titleKey="fixUpTaskCustomerInfo.middleName" /> 
					
					<display:column property="surname" titleKey="fixUpTaskCustomerInfo.surname" /> 
					
					<display:column property="photo" titleKey="fixUpTaskCustomerInfo.photo" />
					 
					<display:column property="email" titleKey="fixUpTaskCustomerInfo.email" />
					
					<display:column property="phoneNumber" titleKey="fixUpTaskCustomerInfo.phoneNumber" /> 
					
					<display:column property="address" titleKey="fixUpTaskCustomerInfo.address" />
					
					<display:column property="score" titleKey="fixUpTaskCustomerInfo.score" /> 
					
		</display:table>
		
<p><spring:message code="handyWorker.fixUpTasksCustomerInfo.fixUpTask" /></p>		
		
		<display:table pagesize="5" name="fixUpTasks" id="row" class="displaytag"  requestURI="/fixUpTaskCustomerInfo/handyWorker/list.do">
					
					<display:column>
					
					<jstl:set var="isAccepted" value="0"/>
			<jstl:forEach
				var="application"
				items="${row.applications}">
				<jstl:set var="count" value="0" />
				<jstl:if test="${application.status.toString()=='ACCEPTED'}">	
				<jstl:set var="count" value="${count+1}"/>
				<jstl:set var="isAccepted" value="${count}"/>
				</jstl:if>
			</jstl:forEach>
					<jstl:if test="${isAccepted==0}">
					
					<!-- Si la fix up task no ha sido aceptada, me permite proponer una aplicación -->
					<spring:url var="createApplicationUrl" value="/application/handyWorker/edit.do?fixUpTaskId={fixId}">
							<spring:param name="fixId" value="${row.id}" />
					</spring:url>
					
					<a href="${createApplicationUrl}">
							<spring:message code="fixUpTask.createApplication" />		
					</a>
					</jstl:if>
					</display:column>
	
		
					<display:column property="momentPublished" titleKey="fixUpTask.momentPublished" format="{0,date,dd/MM/yyyy HH:mm}" />
		
					<display:column property="description" titleKey="fixUpTask.description" /> 
		
					<display:column property="address" titleKey="fixUpTask.address"/>
					
					<display:column property="maxPrice" titleKey="fixUpTask.maxPrice"/>
		
					<display:column property="realizationTime" titleKey="fixUpTask.realizationTime"/> 
		
					<!-- See Warranties -->
					<display:column titleKey="fixUpTask.warranties">								
				
						<spring:url var="warrantiesUrl" value="/warranty/handyWorker/list.do?fixUpTaskId={fixId}">
						<spring:param name="fixId" value="${row.id}" />
						</spring:url>
				
					<a href="${warrantiesUrl}">
						<jstl:out value="${row.warranty.title}" />
					</a>
				
					</display:column>
					
		
					<!-- Category -->
					<display:column titleKey="fixUpTask.categories">		
							<jstl:set var="category" value="${row.category.name}"/>
					<jstl:if test="${locale=='ES'}">
							<jstl:set var="category" value="${row.category.nameSpanish}"/>
					</jstl:if>
					<jstl:out value="${category}"/>
					</display:column>
					
							<!-- Complaints -->
		
				<display:column titleKey="fixUpTask.complaints">
		
					<jstl:set var="isInvolved" value="${0}"/>
		<jstl:forEach
				var="application"
				items="${row.applications}">
				
				<jstl:set var="counts" value="0" />
				<jstl:if test="${application.status.toString()=='ACCEPTED'}">	
				<jstl:set var="counts" value="${counts+1}"/>
				<jstl:if test="${application.getHandyWorker().getUserAccount().getUsername().equals(currentUsername)}">	
				<jstl:set var="counts" value="${counts+1}"/>
				<jstl:set var="isInvolved" value="${counts}"/>
				</jstl:if>
				</jstl:if>
				</jstl:forEach>

			<jstl:if test="${isInvolved==2}">
			
					
					<jstl:set var="complaintsSize" value="${row.complaints.size()}" />
					<spring:url var="complaintsUrl" value="/complaint/handyWorker/list.do?fixUpTaskId={fixId}">
								<spring:param name="fixId" value="${row.id}" />
					</spring:url>
					
					<a href="${complaintsUrl}">
									<spring:message var="seeComplaints" code="fixUpTask.seeComplaints"/> 	
									<jstl:out value="${seeComplaints}(${complaintsSize})" />
								</a>
					</jstl:if>
				</display:column>
					
		
	
		
		</display:table>
		
			<spring:url var="fixUpTaskUrl" value="/fixUpTask/handyWorker/list.do?"/>
	<a href="${fixUpTaskUrl}">
		<spring:message code="comment.back" />			
	</a>

</security:authorize>