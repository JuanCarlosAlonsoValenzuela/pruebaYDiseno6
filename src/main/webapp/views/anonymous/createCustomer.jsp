<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<p><spring:message code="anonymous.createCustomer" /></p>

<security:authorize access="isAnonymous()">

<form:form modelAttribute="customer" action="anonymous/createCustomer.do">
    <!--Hidden Attributes -->
	<form:hidden path ="id"/>
	<form:hidden path ="version"/>
	<form:hidden path ="hasSpam"/>
	<form:hidden path ="boxes"/>
	<form:hidden path ="socialProfiles"/>
	<form:hidden path ="userAccount.authorities"/>
	<form:hidden path ="userAccount.isNotLocked"/>
	<form:hidden path ="score"/>
	<form:hidden path ="fixUpTasks"/>
	<form:hidden path ="endorsements"/>
	
	
	<!-- Actor Attributes -->
	<form:label path="name">
		<spring:message code="customer.name" />
	</form:label>
	<form:input path="name" />
	<form:errors cssClass="error" path="name"/>
	<br />
	
	
	<form:label path="middleName">
		<spring:message code="customer.middleName" />
	</form:label>
	<form:input path="middleName" />
	<form:errors cssClass="error" path="middleName"/>
	<br />

	
	<form:label path="surname">
		<spring:message code="customer.surname" />
	</form:label>
	<form:input path="surname" />
	<form:errors cssClass="error" path="surname"/>
	<br />
	
	
	<form:label path="photo">
		<spring:message code="customer.photo" />
	</form:label>
	<form:input path="photo"/>
	<form:errors cssClass="error" path="photo"/>
	<br />
	
	
	<form:label path="email">
		<spring:message code="customer.email" />
	</form:label>
	<form:input path="email" />
	<form:errors cssClass="error" path="email"/>
	<br />

	<form:label path="phoneNumber">
		<spring:message code="customer.phoneNumber" />
	</form:label>
	<form:input path="phoneNumber"/>
	<form:errors cssClass="error" path="phoneNumber"/>
	<br />

	
	<form:label path="address">
		<spring:message code="customer.address" />
	</form:label>
	<form:input path="address"/>
	<form:errors cssClass="error" path="address"/>
	<br />
	
	
	<form:label path="userAccount.username">
		<spring:message code="customer.username" />
	</form:label>
	<form:input path="userAccount.username" />
	<form:errors cssClass="error" path="userAccount.username"/>
	<br />
	
	
	<form:label path="userAccount.password">
		<spring:message code="customer.password" />
	</form:label>
	<form:password path="userAccount.password" />
	<form:errors cssClass="error" path="userAccount.password"/>
	<br />
	
	
	
	
	
		
	<input type="submit" name="save" value="<spring:message code="customer.save" />" /> 
	
	<a href="#" style="text-decoration: none;">
    	<input type="button" value="<spring:message code="customer.cancel" />" />
	</a>
	
	</form:form>
	
</security:authorize>