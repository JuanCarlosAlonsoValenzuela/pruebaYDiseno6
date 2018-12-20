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

<p><spring:message code="servicios.reparaciones" /></p>


<form action="/Acme-Handy-Worker/services/repairs/ceiling-repair.do">
    <input type="submit" value=<spring:message code="servicios.ceiling-repair" /> /> 
    Reparacion de techo
</form>

<form action="/Acme-Handy-Worker/services/repairs/plumbing-repairs.do">
    <input type="submit" value=<spring:message code="servicios.plumbing-repairs" /> /> 
    Reparacion de tuberias
</form>

<form action="/Acme-Handy-Worker/services/repairs/sprinkler-repair.do">
    <input type="submit" value=<spring:message code="servicios.sprinkler-repair" /> />
    Reparacion de aspersores
</form>

<form action="/Acme-Handy-Worker/services/repairs/window-repair.do">
    <input type="submit" value=<spring:message code="servicios.window-repair" /> />
    Reparacion de ventanas
</form>

<form action="/Acme-Handy-Worker/services/repairs/lamp-repairs.do">
    <input type="submit" value=<spring:message code="servicios.lamp-repair" /> />
    Reparacion de lamparas
</form>

<form action="/Acme-Handy-Worker/services/repairs/fence-repair.do">
    <input type="submit" value=<spring:message code="servicios.fence-repair" /> />
    Reparacion de vallas
</form>









