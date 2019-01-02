
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<security:authorize access="hasRole('CUSTOMER')">

	<h2><jstl:out value="${warranty.title}" /></h2>

	<display:table pagesize="5" name="terms" id="row" class="displaytag" 
					requestURI="phase/customer/list.do">
					
		<display:column titleKey="warranty.customer.terms">
			<jstl:out value="${row}"/>
		</display:column>
		
	</display:table>
	
	<br/>
	
	<display:table pagesize="5" name="laws" id="row" class="displaytag" 
					requestURI="phase/customer/list.do">
					
		<display:column titleKey="warranty.customer.laws">
			<jstl:out value="${row}"/>
		</display:column>
		
	</display:table>
		
	<input type="button" name="cancel" onclick="javascript:relativeRedir('fixUpTask/customer/list.do?');"  value="<spring:message code="warranty.back.button"/>" />	
</security:authorize>