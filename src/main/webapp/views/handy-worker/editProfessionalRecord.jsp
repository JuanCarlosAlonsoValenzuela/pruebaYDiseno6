<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<p><spring:message code="handyWorker.editProfessionalRecord.title" /></p>		<!-- Tiles -->	<!-- Añadir -->	

<security:authorize access="hasRole('HANDYWORKER')">

	<form:form modelAttribute="professionalRecord" action="curriculum/handyWorker/editProfessionalRecord.do">
		
		<!-- Hidden Attributes -->
		<form:hidden path ="id"/>
		<form:hidden path ="version"/>
		<form:hidden path ="comments"/>
		
		
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
		<form:input path="startDate" placeholder="dd/MM/yyyy" />
		<form:errors cssClass="error" path="startDate" />
		<br />
		<!-- End Date -->
		<form:label path="endDate"> 
			<spring:message code="professionalRecord.endDate" />		
		</form:label>
		<form:input path="endDate" placeholder="dd/MM/yyyy" />
		<form:errors cssClass="error" path="endDate" />
		<br />
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
		<form:input path="linkAttachment" placeholder="https://www.example.com/..." />
		<form:errors cssClass="error" path="linkAttachment"/>
		<br />
		
		<spring:message code="comments.create"/>
		<input type="hidden" name="newComment" value=""/>
		<textarea rows="12" cols="50" name="newComment" id="newComment" placeholder="<spring:message code="comments.create"/>" ></textarea>
		<br />
		
		
		<!-- Buttons -->
		<jstl:choose>
			<jstl:when test="${professionalRecord.id == 0}">
				<input type="submit" name="save" value="<spring:message code="handyWorker.save" />" />
			</jstl:when><jstl:otherwise>
				<input type="submit" name="edit" value="<spring:message code="curriculum.edit" />" />
				<input type="submit" name="delete" value="<spring:message code="handyWorker.delete" />" onclick="return confirm('<spring:message code="professionalRecord.delete.confirm" />')" />
			</jstl:otherwise>
		</jstl:choose>

		<input type="button" name="cancel" value="<spring:message code="handyWorker.cancel" />"
				onClick="javascript: relativeRedir('curriculum/handyWorker/show.do');" />
		
	
	</form:form>
</security:authorize>