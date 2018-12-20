<%--
 * action-2.jsp
 *
 * Copyright (C) 2018 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<p><spring:message code="administrator.customersAboveAverage.title" /></p>	<!-- Tiles --> <!-- Añadir -->

<security:authorize access="hasRole('ADMIN')">

	<display:table pagesize="5" name="customers" id="row" class="displaytag"
			requestURI="/customersAboveAverage/admin/list.do">			
			
			<display:column property="name" titleKey="administrator.customerAboveAverage.name" />	<!--Añadir -->
			
			<display:column property="middleName" titleKey="administrator.customerAboveAverage.middleName" />	<!--Añadir -->
			
			<display:column property="surname" titleKey="administrator.customerAboveAverage.surname" />		<!--Añadir -->
			
			<display:column property="photo" titleKey="administrator.customerAboveAverage.photo" />		<!--Añadir -->
			
			<display:column property="email" titleKey="administrator.customerAboveAverage.email" />		<!--Añadir -->
			
			<display:column property="phoneNumber" titleKey="administrator.customerAboveAverage.phoneNumber" />		<!--Añadir -->
			
			<display:column property="address" titleKey="administrator.customerAboveAverage.address" />		<!--Añadir -->
			
			<display:column property="hasSpam" titleKey="administrator.customerAboveAverage.hasSpam" />		<!--Añadir -->
			
			<display:column property="score" titleKey="administrator.customerAboveAverage.score" />			<!--Añadir -->

	</display:table>
			
</security:authorize>
