<%--
 * action-1.jsp
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

<p><spring:message code="servicios.servicios-generales" /></p>


<form action="/Acme-Handy-Worker/services/carpentry.do">
    <input type="submit" value=<spring:message code="servicios.carpinteria" /> /> 
    Servicio de carpinteria
</form>

<form action="/Acme-Handy-Worker/services/cleaning.do">
    <input type="submit" value=<spring:message code="servicios.limpieza" /> /> 
    Servicio de limpieza de hogares
</form>

<form action="/Acme-Handy-Worker/services/painting.do">
    <input type="submit" value=<spring:message code="servicios.pintura" /> />
    Servicio de pintura de hogares y aledaños
</form>

<form action="/Acme-Handy-Worker/services/moving.do">
    <input type="submit" value=<spring:message code="servicios.traslados" /> />
    Servicio de mudanzas
</form>



