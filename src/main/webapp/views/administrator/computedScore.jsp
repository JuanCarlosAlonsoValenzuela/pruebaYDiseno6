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

<display:table name="endorsers" id="endorser" requestURI="score/administrator/list.do"
	pagesize="5" class="displaytag">
	
	  <display:column titleKey="administrator.scoreEndorser" sortable="true">
	  
      		<jstl:out value="${endorser.userAccount.username}" />
    
      </display:column>
      
       <display:column titleKey="administrator.scoreValue" sortable="true">
      
      		<jstl:out value="${endorser.score}" />
    
      </display:column>
</display:table>


<div>
	<a href="score/administrator/list.do">
		<spring:message code="endorsment.recalculate" />
	</a>
</div>


</security:authorize>


