<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<p><spring:message code="customer.endorsmentEdit" /></p>

<security:authorize access="hasRole('CUSTOMER')">

<form:form modelAttribute="endorsement" action="endorsement/customer/create.do">

	<form:hidden path ="id"/>
	<form:hidden path ="version"/>
	<form:hidden path ="moment"/>
	<form:hidden path ="writtenBy"/>
	<form:hidden path ="comments"/>
	
	<spring:message code="endorsment.comments" />
	<br />
	<input type="hidden" name="comment" />
	<textarea rows="12" cols="50" name="comment" id="comment" placeholder="<spring:message code="endorsement.place"/>" ></textarea>
	<br />

		<form:select path="writtenTo">
			<form:options items="${handyWorkers}" itemLabel="userAccount.username" itemValue="id"/>								 		
		</form:select>
		<form:errors cssClass="error" path="writtenTo"/>
		<br/>
		
		
		<input type="submit" name="save" value="<spring:message code="customer.addComment" />" />
	
		<input type="button" name="cancel" value="<spring:message code="customer.cancel" />"
			onClick="javascript:relativeRedir('endorsement/customer/list.do');" />
</form:form>	

</security:authorize>