<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<p><spring:message code="handyWorker.editEndorserRecord.title" /></p>	

<security:authorize access="hasRole('HANDYWORKER')">

	<form:form modelAttribute="endorserRecord" action="endorserRecord/handyworker/edit.do">
		
		<!-- Hidden Attributes -->
		<form:hidden path ="id"/>
		<form:hidden path ="version"/>
		
	
	
		<!-- Full Name -->
		<form:label path="fullName">
			<spring:message code="endorserRecord.fullName"/>	
		</form:label>
		<form:input path="fullName" />
		<form:errors cssClass="error" path="fullName"/>
		<br />
		
		<!-- Email -->
		<form:label path="email">
			<spring:message code="endorserRecord.email"/>	
		</form:label>
		<form:input path="email" />
		<form:errors cssClass="error" path="email"/>
		<br />
		
		<!-- URL LinkedIn Profile -->
		<form:label path="linkLinkedInProfile">
			<spring:message code="endorserRecord.linkLinkedInProfile"/>	
		</form:label>
		<form:input path="linkLinkedInProfile" />
		<form:errors cssClass="error" path="linkLinkedInProfile"/>
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