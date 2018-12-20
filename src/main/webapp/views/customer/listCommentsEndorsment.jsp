<%--
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

<p><spring:message code="customer.endorsmentComments" /></p>

<security:authorize access="hasRole('CUSTOMER')">

<jstl:set var="loggedActor" value="${loggedActor}" /> 

<display:table name="endorsmentComment" id="endorsmentsList" requestURI="${endorsment/customer/commentlist.do}"
	pagesize="5" class="displaytag">
	  
 	   <display:column property="comment" title="<spring:message code="endorsment.comment" />" sortable="true">
      		<jstl:forEach var="i" begin="1" end="${endorsment.comments.sice()}">
      		<jstl:out value="${endorsment.comments.get(i)}" />
      		</jstl:forEach>
      </display:column>
 </display:table>	   

	
<div>
	<a href="endorsment/customer/list.do">
		<spring:message code="endorsment.comeBack" />
	</a>
</div>

</security:authorize>
