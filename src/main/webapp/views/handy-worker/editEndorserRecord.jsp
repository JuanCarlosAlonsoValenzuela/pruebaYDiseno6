<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<p><spring:message code="handyWorker.editEndorserRecord.title" /></p>	

<security:authorize access="hasRole('HANDYWORKER')">

	<form:form modelAttribute="endorserRecord" action="curriculum/handyWorker/editEndorserRecord.do">
		
		<!-- Hidden Attributes -->
		<form:hidden path ="id"/>
		<form:hidden path ="version"/>
		<form:hidden path ="comments"/>

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
			<!-- Phone Number -->
		<form:label path="phoneNumber">
			<spring:message code="personalRecord.phoneNumber"/>	
		</form:label>
		<form:input path="phoneNumber" />
		<form:errors cssClass="error" path="phoneNumber"/>
		<br />
		
		
		<!-- URL LinkedIn Profile -->
		<form:label path="linkLinkedInProfile">
			<spring:message code="endorserRecord.linkLinkedInProfile"/>	
		</form:label>
		<form:input path="linkLinkedInProfile" placeholder="https://www.linkedin.com/..." />
		<form:errors cssClass="error" path="linkLinkedInProfile"/>
		<br />
		
		<spring:message code="comments.create"/>
		<input type="hidden" name="newComment" value=""/>
		<textarea rows="12" cols="50" name="newComment" id="newComment" placeholder="<spring:message code="comments.create"/>" ></textarea>
		<br />
		
		
		<!-- Buttons -->
		<jstl:choose>
			<jstl:when test="${endorserRecord.id == 0}">
				<input type="submit" name="save" value="<spring:message code="handyWorker.save" />" />
			</jstl:when><jstl:otherwise>
				<input type="submit" name="edit" value="<spring:message code="curriculum.edit" />" />
				<input type="submit" name="delete" value="<spring:message code="handyWorker.delete" />" onclick="return confirm('<spring:message code="endorserRecord.delete.confirm" />')" />
			</jstl:otherwise>
		</jstl:choose>
		<input type="button" name="cancel" value="<spring:message code="handyWorker.cancel" />"
				onClick="javascript: relativeRedir('curriculum/handyWorker/show.do');" />
	
	</form:form>

</security:authorize>