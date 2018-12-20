<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<p><spring:message code="report.addNote" /></p>	


<form:form action="/report/note/edit.dodit.do" modelAttribute="complaint">

		<!-- Hidden Attributes -->
		<form:hidden path="id"/>
		<form:hidden path="moment" />
		<form:hidden path="optionalComment" />
		
		<!--  Mandatory Comment -->
		<form:label path="mandatoryComment">	<!-- Tiles -->
			<spring:message code="note.mandatoryComment" />	
		</form:label>
		<form:textarea path="mandatoryComment"/>
		<form:errors cssClass="error" path="mandatoryComment"/>
		
		<input type="submit" name="create" value="<spring:message code="note.create.button"/>" />	
			
		
		<input type="submit" name="cancel" value="<spring:message code="cancel.button"/>" />


</form:form>