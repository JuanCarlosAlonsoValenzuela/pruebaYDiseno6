
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<security:authorize access="hasRole('CUSTOMER')">

	<h3><jstl:out value="${warranty.title }" /></h3>
	

	<h4><spring:message code="warranty.customer.terms"/></h4> <!--Añadir --><!-- Tiles -->
	<br />
		<jstl:forEach var="term" items="terms">
				<jstl:out value="${term}" />
				<br />
		</jstl:forEach>
	<br />
	
	<h4><spring:message code="warranty.customer.laws"/></h4> <!--Añadir --><!-- Tiles -->
	<br />
		<jstl:forEach var="law" items="laws">
				<jstl:out value="${law}" />
				<br />
		</jstl:forEach>

</security:authorize>