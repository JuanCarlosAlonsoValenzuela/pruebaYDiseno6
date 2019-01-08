<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
 
<security:authorize access="hasRole('CUSTOMER')">
	<form:form action="application/customer/changeStatus.do" modelAttribute="application">
		<form:hidden path="id"/>
		<form:hidden path="version" /> 
		<form:hidden path="moment"/>
		<form:hidden path="offeredPrice"/>
		<form:hidden path="comments" />
		<form:hidden path="fixUpTask" />
		<form:hidden path="handyWorker" />
		
		 <form:label path="status">
			<spring:message code="customer.application.status" />		
		</form:label>
		<form:select path="status">
			<spring:message var="accepted" code="customer.application.accepted"/>
			<form:option label="${accepted}" value="ACCEPTED" />		
			<spring:message var="pending" code="customer.application.pending"/>							
			<form:option label="${pending}" value="PENDING" />	
			<spring:message var="rejected" code="customer.application.rejected"/>
			<form:option label="${rejected}" value="REJECTED" />
		</form:select>

		<form:errors cssClass="error" path="status" />
		<br/><br/>
		
		<input type="submit" name="save" value="<spring:message code="application.save"/>" />
		
		<spring:message var="cancel" code="application.cancel"/> 
		<input type="button" onclick="javascript:relativeRedir('fixUpTask/customer/list.do');" value="${cancel}">

	</form:form>		
	
</security:authorize>