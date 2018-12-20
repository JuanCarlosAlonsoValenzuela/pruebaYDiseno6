<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<p><spring:message code="handyWorker.endorsmentEdit" /></p>

<security:authorize access="hasRole('HANDYWORKER')">

<form:form modelAttribute="endorsment" action="endorsment/handyworker/edit.do">

	<form:hidden path ="id"/>
	<form:hidden path ="version"/>
	<form:hidden path ="moment"/>
	<form:hidden path ="writtenBy"/>
	
	<form:label path="comments">
		<spring:message code="endorsment.comments" />
	</form:label>
	<form:textarea path="comments"/>
	<form:errors cssClass="error" path="comments"/>
	<br />
	
	<form:label path="handyWorkers">
		<spring:message code="endorsment.handyWorkers" />
	</form:label>
	<form:select id="handyWorker" path = "handyWorkers">
		<form:options items="${handyWorkers}" itemLabel="name" itemValue="id"/>
	</form:select>
	<form:errors cssClass="error" path="handyWorkers"/>
	
	
	<input type="submit" <jstl:if test="${endorsment.id != 0}"><jstl:out value="disabled='disabled'"/></jstl:if>
		name="save" value="<spring:message code="handyWorker.save" />" />

	<input type="submit" <jstl:if test="${endorsment.id == 0}"><jstl:out value="disabled='disabled'"/></jstl:if>
		 name="delete" value="<spring:message code="handyWorker.delete" />" 
			onClick="return confirm('<spring:message code="handyWorker.verificationDelete" />')">
	
	<input type="submit" name="cancel" value="<spring:message code="handyWorker.cancel" />"
		onClick="javascript: relativeRedir('handyWorker/endorsementList.do');" />
</form:form>	

</security:authorize>

