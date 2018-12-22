<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<p><spring:message code="report.addNote" /></p>	

<security:authorize access="hasRole('CUSTOMER')">

<form:form action="note/customer/create.do" modelAttribute="noteTest" id="add" method="post">

		<!-- Hidden Attributes -->
		<form:hidden path="id"/>
		<form:hidden path="version"/>
		<form:hidden path="moment" />
		<form:hidden path="optionalComments" />
		<form:hidden path="usernames" />
		
		<!--  Mandatory Comment -->
		<form:label path="mandatoryComment">	<!-- Tiles -->
			<spring:message code="note.mandatoryComment" />	:
		</form:label>
		</br>
		<form:textarea rows="15" cols="100" path="mandatoryComment"/>
		<form:errors cssClass="error" path="mandatoryComment"/>
		</br>
		<input type="hidden" name="reportId" value="${reportId}"/>	
			<input type="submit" name="save" value="<spring:message code="note.save.button" />" onclick="return confirm('<spring:message code="note.create.confirm" />')"
		/>
			<input type="hidden" name="reportId" value="${reportId}"/>	
			<input type="button" name="cancel" value="<spring:message code="note.cancel.button" />" onclick="javascript:relativeRedir('note/customer/show.do?reportId=${reportId}');" 
		/>
</form:form>
</security:authorize>


