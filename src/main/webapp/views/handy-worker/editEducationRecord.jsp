<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<p><spring:message code="handyWorker.editEducationRecord.title" /></p>		<!-- Tiles -->	<!-- Añadir -->	

<security:authorize access="hasRole('HANDYWORKER')">

	<form:form modelAttribute="educationRecord" action="educationRecord/handyworker/edit.do">
		
		<!-- Hidden Attributes -->
		<form:hidden path ="id"/>
		<form:hidden path ="version"/>
		
		<!-- Title -->
		<form:label path="title">
			<spring:message code="educationRecord.title"/>	
		</form:label>
		<form:input path="title" />
		<form:errors cssClass="error" path="title"/>
		<br />
		
		<!-- Start Date -->
		<form:label path="startDateStudy"> 
			<spring:message code="educationRecord.startDateStudy" />		
		</form:label>
		<form:input path="startDateStudy" placeholder="dd/MM/yyyy HH:mm" />
		<form:errors cssClass="error" path="startDateStudy" />
		
		<!-- End Date -->
		<form:label path="endDateStudy"> 
			<spring:message code="educationRecord.endDateStudy" />		
		</form:label>
		<form:input path="endDateStudy" placeholder="dd/MM/yyyy HH:mm" />
		<form:errors cssClass="error" path="endDateStudy" />
		
		<!-- Institution -->
		<form:label path="institution">
			<spring:message code="educationRecord.institution"/>	
		</form:label>
		<form:input path="institution" />
		<form:errors cssClass="error" path="institution"/>
		<br />
		
		<!-- URL -->
		<form:label path="url">
			<spring:message code="educationRecord.url"/>	
		</form:label>
		<form:input path="url" />
		<form:errors cssClass="error" path="url"/>
		<br />
		
		
		<!-- Buttons -->
		<input type="submit" name="save" value="<spring:message code="handyWorker.save" />" />

		<input type="submit" <jstl:if test="${endorsment.id == 0}"><jstl:out value="disabled='disabled'"/></jstl:if>
		 			name="delete" value="<spring:message code="handyWorker.delete" />" 
					onClick="return confirm('<spring:message code="handyWorker.verificationDelete" />')">
	
		<input type="submit" name="cancel" value="<spring:message code="handyWorker.cancel" />"
				onClick="javascript: relativeRedir('handyWorker/endorsementList.do');" />
		
	
	</form:form>
</security:authorize>