<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<p><spring:message code="handyWorker.editPersonalRecord.title" /></p>	

<security:authorize access="hasRole('HANDYWORKER')">

	<form:form modelAttribute="personalRecord" action="personalRecord/handyworker/edit.do">
		
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
		<input type="submit" name="save" value="<spring:message code="handyWorker.save" />" />

		<input type="submit" <jstl:if test="${endorsment.id == 0}"><jstl:out value="disabled='disabled'"/></jstl:if>
		 			name="delete" value="<spring:message code="handyWorker.delete" />" 
					onClick="return confirm('<spring:message code="handyWorker.verificationDelete" />')">
	
		<input type="submit" name="cancel" value="<spring:message code="handyWorker.cancel" />"
				onClick="javascript: relativeRedir('handyWorker/endorsementList.do');" />
	
	</form:form>

</security:authorize>