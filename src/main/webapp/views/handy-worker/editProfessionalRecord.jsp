<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<p><spring:message code="handyWorker.editProfessionalRecord.title" /></p>		<!-- Tiles -->	<!-- Añadir -->	

<security:authorize access="hasRole('HANDYWORKER')">

	<form:form modelAttribute="professionalRecord" action="professionalRecord/handyworker/edit.do">
		
		<!-- Hidden Attributes -->
		<form:hidden path ="id"/>
		<form:hidden path ="version"/>
		
		
		<!-- Company Name -->
		<form:label path="nameCompany">
			<spring:message code="professionalRecord.nameCompany"/>	
		</form:label>
		<form:input path="nameCompany" />
		<form:errors cssClass="error" path="nameCompany"/>
		<br />
		
		<!-- Start Date -->
		<form:label path="startDate"> 
			<spring:message code="professionalRecord.startDate" />		
		</form:label>
		<form:input path="startDate" placeholder="dd/MM/yyyy HH:mm" />
		<form:errors cssClass="error" path="startDate" />
		
		<!-- End Date -->
		<form:label path="endDate"> 
			<spring:message code="professionalRecord.endDate" />		
		</form:label>
		<form:input path="endDate" placeholder="dd/MM/yyyy HH:mm" />
		<form:errors cssClass="error" path="endDate" />
		
		<!-- Role -->
		<form:label path="role">
			<spring:message code="professionalRecord.role"/>	
		</form:label>
		<form:input path="role" />
		<form:errors cssClass="error" path="role"/>
		<br />
		
		<!-- Link Attachment -->
		<form:label path="linkAttachment">
			<spring:message code="professionalRecord.linkAttachment"/>	
		</form:label>
		<form:input path="linkAttachment" />
		<form:errors cssClass="error" path="linkAttachment"/>
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