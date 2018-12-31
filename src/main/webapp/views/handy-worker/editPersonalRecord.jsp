<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<p><spring:message code="handyWorker.editPersonalRecord.title" /></p>	

<security:authorize access="hasRole('HANDYWORKER')">

	<form:form modelAttribute="personalRecord" action="curriculum/handyWorker/editPersonalRecord.do">
		
		<!-- Hidden Attributes -->
		<form:hidden path ="id"/>
		<form:hidden path ="version"/>
		
	
		<!-- Full Name -->
		<form:label path="fullName">
			<spring:message code="personalRecord.fullName"/>	
		</form:label>
		<form:input path="fullName" />
		<form:errors cssClass="error" path="fullName"/>
		<br />
		
		<!-- Photo -->
		<form:label path="photo">
			<spring:message code="personalRecord.photo"/>	
		</form:label>
		<form:input path="photo" />
		<form:errors cssClass="error" path="photo"/>
		<br />
		
		<!-- Email -->
		<form:label path="email">
			<spring:message code="personalRecord.email"/>
		</form:label>
		<form:input path="email" />
		<form:errors cssClass="error" path="email"/>
		<br />
		
		<!-- Phone Number -->
		<form:label path="phoneNumber">
			<spring:message code="personalRecord.phoneNumber"/>	
		</form:label>
		<form:input path="phoneNumber" />
		<form:errors cssClass="error" path="phoneNumber"/>
		<br />
		
		<!-- URL LinkedIn Profile -->
		<form:label path="urlLinkedInProfile">
			<spring:message code="personalRecord.urlLinkedInProfile"/>	
		</form:label>
		<form:input path="urlLinkedInProfile" />
		<form:errors cssClass="error" path="urlLinkedInProfile"/>
		<br />
		
		
		<!-- Buttons -->
				<jstl:choose>
			<jstl:when test="${personalRecord.id == 0}">
				<input type="submit" name="save" value="<spring:message code="handyWorker.save" />" />
				<input type="button" name="cancel" value="<spring:message code="handyWorker.cancel" />"
				onClick="javascript: relativeRedir('handyWorker/handyWorker/showProfile.do');" />
			</jstl:when><jstl:otherwise>
				<input type="submit" name="edit" value="<spring:message code="curriculum.edit" />" />
				<input type="button" name="cancel" value="<spring:message code="handyWorker.cancel" />"
				onClick="javascript: relativeRedir('curriculum/handyWorker/show.do');" />
			</jstl:otherwise>
		</jstl:choose>	
	</form:form>

</security:authorize>