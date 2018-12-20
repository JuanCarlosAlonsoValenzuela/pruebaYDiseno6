<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<p><spring:message code="tutorial.addTutorial" /></p>			

<security:authorize access="hasRole('HANDYWORKER')">

<form:form action="tutorial/handyWorker/add.do" modelAttribute="tutorial" >
	<form:hidden path="id"/>
	<form:hidden path="version" />
	
	<form:label path="title">
			<spring:message code="tutorial.title" />:		
	</form:label>
	<form:input path="title" />
	<form:errors cssClass="error" path="title" />
	<br />
	
	<form:label path="summary">
			<spring:message code="tutorial.summary" />:		
	</form:label>
	<form:textarea path="summary" />
	<form:errors cssClass="error" path="summary" />
	<br />
	
	<form:label path="pictures">
			<spring:message code="tutorial.pictures" />:		
	</form:label>
	<form:input path="pictures" />
	<form:errors cssClass="error" path="pictures" />
	<br />
	
	<input type="submit" name="create" value="<spring:message code="tutorial.create"/>"
	onClick="javascript: relativeRedir('tutorial/handy-worker/edit.do');" />	
	
	<input type="submit" name="cancel" value="<spring:message code="tutorial.cancel" />"
		onClick="javascript: relativeRedir('handy-worker/showProfile.do');" />
</form:form>

</security:authorize>