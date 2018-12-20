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

<p><spring:message code="customer.endorsment" /></p>

<security:authorize access="hasRole('CUSTOMER')">

<jstl:set var="loggedActor" value="${loggedActor}" /> 

<display:table name="endorsment" id="endorsmentsList" requestURI="${endorsment/customer/list.do}"
	pagesize="5" class="displaytag">
	
 	  <display:column>
 	  	<jstl:if test="${endorsment.writtenBy.id != loggedActor.id}"><jstl:out value="disabled='disabled'"/></jstl:if>
 	  	<a href="endorsment/customer/edit.do?endorsmentId=${endorsment.id}">
 	  		<spring:message code="endorsment.edit" />
 	  	</a>
 	  </display:column>
 	  
      <display:column property="name" title="<spring:message code="endorsment.name" />" sortable="true">
      		<jstl:forEach var="i" begin="1" end="${endorsment.lenght()}">
      		<jstl:out value="${endorsment.name i}" />
      		</jstl:forEach>
      </display:column>
      <display:column property="moment" titleKey="endorsment.moment" sortable="true">
      		<jstl:out value="${endorsmentsList.moment}" />
      </display:column>
      
		<display:column titleKey="endorsment.comments">
				<jstl:set var="commentsSize" value="${endorsment.comments.size()}" />
				<spring:url var="attachmetsUrl" value="/comments/list.do?endorsmentId={compId}">
							<spring:param name="compId" value="${endorsment.id}"/>
				</spring:url>
				<a href="${commentsUrl}">
							<spring:message var ="viewComments1" code="endorsment.viewComments" />
							<jstl:out value="$viewComments1}(${commentsSize})" />		
				</a>
		</display:column> 
      
      <display:column property="writtenBy" titleKey="endorsment.writtenBy" >
      		<jstl:out value="${endorsmentsList.writtenBy}" />
      </display:column>
      <display:column property="writtenTo" titleKey="endorsment.writtenTo" >
      		<jstl:out value="${endorsmentsList.writtenTo}" />
      </display:column>
</display:table>

<div>
	<a href="endorsment/customer/create.do">
		<spring:message code="endorsment.create" />
	</a>
</div>

</security:authorize>
