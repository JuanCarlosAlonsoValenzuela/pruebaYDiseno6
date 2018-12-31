
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<security:authorize access="hasRole('CUSTOMER')">

	<h2><jstl:out value="${warranty.title}" /></h2>

	<h3><spring:message code="warranty.customer.terms"/></h3> <!--Añadir --><!-- Tiles -->
		<ul>
		<jstl:forEach var="term" items="${warranty.terms}">
			<li><jstl:out value="${term}" /></li>
		</jstl:forEach>
		</ul>
	
	<h3><spring:message code="warranty.customer.laws"/></h3> <!--Añadir --><!-- Tiles -->
		<ul>
		<jstl:forEach var="law" items="${warranty.laws}">
			<li><jstl:out value="${law}" /></li>
		</jstl:forEach>
		</ul>
		
	<input type="button" name="cancel" onclick="javascript:relativeRedir('fixUpTask/customer/list.do?');"  value="<spring:message code="warranty.back.button"/>" />	
</security:authorize>