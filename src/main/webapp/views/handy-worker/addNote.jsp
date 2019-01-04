<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<p><spring:message code="report.addNote" /></p>	


<form:form action="note/handyWorker/edit.do" modelAttribute="note">

		<!-- Hidden Attributes -->
		<form:hidden path="id"/>
		<form:hidden path="moment" />
		<input type="hidden" name="reportId" id="reportId" value="<jstl:out value="${param.reportId}"/>"/>
		<input type="hidden" name="complaintId" id="complaintId" value="<jstl:out value="${param.complaintId}"/>"/>
		<input type="hidden" name="fixUpTaskId" id="fixUpTaskId" value="<jstl:out value="${param.fixUpTaskId}"/>"/>
		
		<!--  Mandatory Comment -->
		<label for="text"><spring:message code="note.mandatoryComment" />: </label>
		<input type="text" name="mandatoryComment" required="required" id="mandatoryComment" placeholder="<spring:message code="comment.writeComment"/>"/>
		
		</br>
		<input type="submit" name="save" value="<spring:message code="note.create.button"/>" />	


</form:form>


<spring:url var="noteUrl" value="/note/handyWorker/list.do?reportId={reportId}">
	<spring:param name="reportId" value="${param.reportId}"/>
	<spring:param name="complaintId" value="${param.complaintId}"/>	
	<spring:param name="fixUpTaskId" value="${param.fixUpTaskId}"/>
	</spring:url>
	<a href="${noteUrl}">
	<button><spring:message code="comment.cancel" /></button>	 	
	</a>