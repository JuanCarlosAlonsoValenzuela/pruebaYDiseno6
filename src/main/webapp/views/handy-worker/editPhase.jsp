<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<p><spring:message code="handyWorker.phaseEdit" /></p>	<!-- Tiles -->	

<security:authorize access="hasRole('HANDYWORKER')">

<spring:url var="actionUrl" value="/phase/handyWorker/edit.do">
								<spring:param name="phaseId" value="${param.phaseId}"/>
								<spring:param name="applicationId" value="${applicationId}"/>
						</spring:url>
					
	
		<form:form modelAttribute="phase" action="phase/handyWorker/edit.do">
				<!-- Hidden Attributes -->
				<form:hidden path="id"/>
				<form:hidden path="version"/>
				<input type="hidden" name="applicationId" id="applicationId" value="<jstl:out value="${param.applicationId}"/>"/>
				
				<!-- Title -->
				<form:label path="title">
					<spring:message code="phase.title" />	
				</form:label>
				<form:input path="title"/>
				<form:errors cssClass="error" path="title"/>
				<br />
				
				<!-- Description -->
				<form:label path="description">
				<spring:message code="phase.description" />	
				</form:label>
				<form:input path="description"/>
				<form:errors cssClass="error" path="description"/>
				<br />
				
				<!-- Start Moment -->
				<form:label path="startMoment"> 
						<spring:message code="phase.startMoment" />		
				</form:label>
				<form:input path="startMoment" placeholder="dd/MM/yyyy HH:mm" />
				<form:errors cssClass="error" path="startMoment" />
				<br />
				<!-- End Moment -->
				<form:label path="endMoment">
						<spring:message code="phase.endMoment" />		
				</form:label>
				<form:input path="endMoment" placeholder="dd/MM/yyyy HH:mm" />
				<form:errors cssClass="error" path="endMoment" />
				<br />
				
				<!-- Buttons (modify) -->
				<input type="submit" name="save" value="<spring:message code="phase.save" />" />
				<br />

				<input type="submit" <jstl:if test="${phase.id == 0}"><jstl:out value="disabled='disabled'"/></jstl:if>
		 					name="delete" value="<spring:message code="phase.delete" />" 
							onClick="return confirm('<spring:message code="phase.verificationDelete" />')">
	
			
				
		</form:form>
		
		
		<spring:url var="backUrl" value="/phase/handyWorker/list.do?applicationId={appId}">
				<spring:param name="appId" value="${param.applicationId}"/>
		</spring:url>
		<a href="${backUrl}">
		<input type="submit" name="cancel" value="<spring:message code="phase.cancel" />" />
		</a>	
		
</security:authorize>