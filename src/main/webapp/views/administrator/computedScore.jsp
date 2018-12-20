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

<p><spring:message code="administrator.computedScore" /></p>

<security:authorize access="hasRole('ADMIN')">

<display:table name="scores" id="computedScores" requestURI="${computedScore/administrator/list.do}"
	pagesize="5" class="displaytag">
	  <display:column property="endorser" titleKey="administrator.scoreEndorser" sortable="true">
      		<jstl:out value="${computedScores.key}" />
      </display:column>
      
       <display:column property="score" titleKey="administrator.scoreValue" sortable="true">
      		<jstl:out value="${computedScores.value}" />
      </display:column>
</display:table>

<div>
	<a href="index.do">
		<spring:message code="andministrator.backToMainMenu" />
	</a>
</div>

<div>
	<a href="computedScore/administrator/list.do">
		<spring:message code="endorsment.recalculate" />
	</a>
</div>


</security:authorize>


