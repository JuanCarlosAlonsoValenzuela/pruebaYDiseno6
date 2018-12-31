<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<p><spring:message code="handyWorker.editEducationRecord.title" /></p>		<!-- Tiles -->	<!-- Añadir -->	

<security:authorize access="hasRole('HANDYWORKER')">

	<form:form modelAttribute="educationRecord" action="curriculum/handyWorker/editEducationRecord.do">
		
		<!-- Hidden Attributes -->
		<form:hidden path ="id"/>
		<form:hidden path ="version"/>
		<form:hidden path ="comments"/>
		
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
		<form:input path="startDateStudy" placeholder="dd/MM/yyyy" />
		<form:errors cssClass="error" path="startDateStudy" />
		<br />
		<!-- End Date -->
		<form:label path="endDateStudy"> 
			<spring:message code="educationRecord.endDateStudy" />		
		</form:label>
		<form:input path="endDateStudy" placeholder="dd/MM/yyyy" />
		<form:errors cssClass="error" path="endDateStudy" />
		<br />
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
		<form:input path="url" placeholder="https://www.example.com/..." />
		<form:errors cssClass="error" path="url"/>
		<br />
		<spring:message code="comments.create"/>
		<input type="hidden" name="newComment" value=""/>
		<textarea rows="12" cols="50" name="newComment" id="newComment" placeholder="<spring:message code="comments.create"/>" ></textarea>
		<br />
		
		<!-- Buttons -->
		<jstl:choose>
			<jstl:when test="${educationRecord.id == 0}">
				<input type="submit" name="save" value="<spring:message code="handyWorker.save" />" />
			</jstl:when><jstl:otherwise>
				<input type="submit" name="edit" value="<spring:message code="curriculum.edit" />" />
				<input type="submit" name="delete" value="<spring:message code="handyWorker.delete" />" onclick="return confirm('<spring:message code="educationRecord.delete.confirm" />')" />
			</jstl:otherwise>
		</jstl:choose>
		
	
		<input type="button" name="cancel" value="<spring:message code="handyWorker.cancel" />"
				onClick="javascript: relativeRedir('curriculum/handyWorker/show.do');" />
		
	
	</form:form>
</security:authorize>