<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<p><spring:message code="handyWorker.editMiscellaneousRecord.title" /></p>	<!-- Añadir -->	

<security:authorize access="hasRole('HANDYWORKER')">

	<form:form modelAttribute="miscellaneousRecord" action="curriculum/handyWorker/editMiscellaneousRecord.do">
		
		<!-- Hidden Attributes -->
		<form:hidden path ="id"/>
		<form:hidden path ="version"/>
		<form:hidden path ="comments"/>
	
		<!-- Title -->
		<form:label path="title">
			<spring:message code="miscellaneousRecord.title"/>			<!-- Añadir -->
		</form:label>
		<form:input path="title" />
		<form:errors cssClass="error" path="title"/>
		<br />
		
		<!-- Link Attachements -->
		<form:label path="linkAttachment">
			<spring:message code="miscellaneousRecord.linkAttachment"/>			<!-- Añadir -->
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
			<jstl:when test="${miscellaneousRecord.id == 0}">
				<input type="submit" name="save" value="<spring:message code="handyWorker.save" />" />
			</jstl:when><jstl:otherwise>
				<input type="submit" name="edit" value="<spring:message code="curriculum.edit" />" />
				<input type="submit" name="delete" value="<spring:message code="handyWorker.delete" />" onclick="return confirm('<spring:message code="miscellaneousRecord.delete.confirm" />')" />
			</jstl:otherwise>
		</jstl:choose>
		<input type="button" name="cancel" value="<spring:message code="handyWorker.cancel" />"
				onClick="javascript: relativeRedir('curriculum/handyWorker/show.do');" />
	
	</form:form>

</security:authorize>