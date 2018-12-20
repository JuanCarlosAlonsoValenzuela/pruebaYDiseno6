<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<p><spring:message code="customer.creditCard.edit" /></p>	

<security:authorize access="hasRole('CUSTOMER')">

<form:form action="creditCard/customer/edit.do" modelAttribute="creditCard" >
	<form:hidden path="id"/>
	<form:hidden path="version" />
	
	<!-- Holder Name -->	
	<form:label path="holderName">
		<spring:message code="creditCard.holder" />	
	</form:label>
		<form:input path="holderName" />
		<form:errors cssClass="error" path="holderName" />
		<br />
	
	<!-- Brand Name -->
	<form:label path="brandName">
			<spring:message code="creditCard.brandName" />		<!-- Tiles -->	
		</form:label>
		
		<form:select path="brandName">
			<form:options
				itemLabel="VISA"			
				itemValue="VISA" />									
			<form:options	
				itemLabel="Master Card"		
				itemValue="MASTER" />								
			<form:options
				itemLabel="Dinners"		
				itemValue="DINNERS" />							
			<form:options
				itemLabel="Amex"		
				itemValue="AMEX" />									
		</form:select>
		<form:errors cssClass="error" path="brandName" />
		<br />
	
	<!-- Number -->
	<form:label path="number">
			<spring:message code="creditCard.number" />		
		</form:label>
		
		<form:input path="number" placeholder="1234 1234 1234 1234"/>
		
		<form:errors cssClass="error" path="number" />
		<br />
		
	<!-- expirationMonth -->
	<form:label path="expirationMonth">
			<spring:message code="creditCard.expirationMonth" />		
		</form:label>
		
		<form:input path="expirationMonth" />
		
		<form:errors cssClass="error" path="expirationMonth" />
		<br />
		
	<!-- expirationYear -->
	<form:label path="expirationYear">
			<spring:message code="creditCard.expirationYear" />			
		</form:label>
		
		<form:input path="expirationYear" />
		
		<form:errors cssClass="error" path="expirationYear" />
		<br />
		
	<!-- cvv Code -->
	<form:label path="cvvCode">
			<spring:message code="creditCard.cvvCode" />			
		</form:label>
		<form:input path="cvvCode" />
		<form:errors cssClass="error" path="cvvCode" />
		<br />
	
	
	<input type="submit" name="create" value="<spring:message code="creditCard.create"/>" />		
	
	<input type="submit" name="cancel" value="<spring:message code="creditCard.cancel"/>" />	

</form:form>

</security:authorize>