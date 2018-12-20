<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<p><spring:message code="handyWorker.fixUpTasksCustomerInfo" /></p>			

<security:authorize access="hasRole('HANDYWORKER')">

		<display:table pagesize="5" name="customer" id="row" class="displaytag"  requestURI="/fixUpTaskCustomerInfo/handyWorker/list.do">
					
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
		
		<display:table pagesize="5" name="customer.fixUpTask" id="row" class="displaytag"  requestURI="/fixUpTaskCustomerInfo/handyWorker/list.do">
					
					<display:column>
						<jstl:if test="${row.application.status != 'ACCEPTED'}">
						<!-- Si la fix up task no ha sido aceptada, me permite proponer una aplicación -->
							<spring:url var="createApplicationUrl" value="/application/handyWorker/edit.do?fixUpTaskId={fixId}">
								<spring:param name="fixId" value="${row.id}" />
							</spring:url>
					
							<a href="${createApplicationUrl}">
								<spring:message code="fixUpTask.createApplication" />		
							</a>
						</jstl:if>
					</display:column>
		
					<display:column property="ticker" titleKey="fixUpTask.ticker" />	
		
					<display:column property="momentPublished" titleKey="fixUpTask.momentPublished" format="{0,date,dd/MM/yyyy HH:mm}" />
		
					<display:column property="description" titleKey="fixUpTask.description" /> 
		
					<display:column property="address" titleKey="fixUpTask.address"/>
		
					<display:column property="realizationTime" titleKey="fixUpTask.realizationTime"/> 
		
					<!-- See Warranties -->
					<display:column titleKey="fixUpTask.warranties">								
				
						<spring:url var="warrantiesUrl" value="/warranty/customer/list.do?fixUpTaskId={fixId}">
							<spring:param name="fixId" value="${row.id}" />
						</spring:url>
				
						<a href="${warrantiesUrl}">
							<jstl:out value="${row.warranty.title}" />
						</a>
				
					</display:column>
					
		
					<!-- Category -->
					<display:column titleKey="fixUpTask.categories">		
							<jstl:out value="${row.category.name}"/>
					</display:column>
					
		
					<!-- Customer -->.
					<display:column titleKey="fixUpTask.customerUsername">	
				
							<jstl:set var="username" value="${row.customer.username}" />
				
							<spring:url var="customerUrl" value="/fixUpTask/handyWorker/list.do?customerId={customerId}">
								<spring:param name="customerId" value="${row.customer.id}" />
							</spring:url>
				
							<a href="${customerUrl}">
								<jstl:out value="${username}" />
							</a>
					</display:column>
		
		</display:table>

</security:authorize>