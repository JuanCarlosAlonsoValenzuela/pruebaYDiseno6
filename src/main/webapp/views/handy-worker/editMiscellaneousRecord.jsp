<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<p><spring:message code="handyWorker.editMiscellaneousRecord.title" /></p>	<!-- Añadir -->	

<security:authorize access="hasRole('HANDYWORKER')">

	<form:form modelAttribute="miscellaneousRecord" action="miscellaneousRecord/handyworker/edit.do">
		
		<!-- Hidden Attributes -->
		<form:hidden path ="id"/>
		<form:hidden path ="version"/>
	
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