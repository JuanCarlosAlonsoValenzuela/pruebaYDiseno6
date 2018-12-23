<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<security:authorize access="hasRole('CUSTOMER')">

<form:form action="application/customer/changeStatusWithCreditCard.do" modelAttribute="creditCard" >
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
			<form:option
				label="VISA"			
				value="VISA" />									
			<form:option
				label="Master Card"		
				value="MASTER" />								
			<form:option
				label="Dinners"		
				value="DINNERS" />							
			<form:option
				label="Amex"		
				value="AMEX" />									
		</form:select>
		<form:errors cssClass="error" path="brandName" />
		<br />
	
	<!-- Number -->
	<form:label path="number">
			<spring:message code="creditCard.number" />		
		</form:label>
		
		<form:input path="number" placeholder="1234123412341234"/>
		
		<form:errors cssClass="error" path="number" />
		<br />
		
	<!-- expirationMonth -->
	<form:label path="expirationMonth">
			<spring:message code="creditCard.expirationMonth" />		
		</form:label>
		
		<form:input path="expirationMonth" placeholder="12" />
		
		<form:errors cssClass="error" path="expirationMonth" />
		<br />
		
	<!-- expirationYear -->
	<form:label path="expirationYear">
			<spring:message code="creditCard.expirationYear" />			
		</form:label>
		
		<form:input path="expirationYear" placeholder="23"/>
		
		<form:errors cssClass="error" path="expirationYear" />
		<br />
		
	<!-- cvv Code -->
	<form:label path="cvvCode">
			<spring:message code="creditCard.cvvCode" />			
		</form:label>
		<form:input path="cvvCode" placeholder="123"/>
		<form:errors cssClass="error" path="cvvCode" />
		<br />
		
	<input type="hidden" name="applicationId" value="${applicationId}"/>
	
	<input type="submit" name="create" value="<spring:message code="creditCard.create"/>" />		

</form:form>

<input type="submit" name="cancel" onclick="history.back()" value="<spring:message code="creditCard.cancel"/>" />	

</security:authorize>