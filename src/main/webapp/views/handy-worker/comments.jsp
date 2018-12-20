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

<p><spring:message code="handyWorker.comment.list" /></p>

<security:authorize access="hasRole('HANDYWORKER')">

<display:table pagesize="5" name="comments" id="row" class="displaytag" 
					requestURI="/application/handyWorker/listComments.do">
	<display:column titleKey="application.comments">
		<jstl:out value="${row}"/>
	</display:column>
</display:table>

			
	<spring:url var="createCommentUrl" value="/application/handyWorker/newComment.do?applicationId={appId}">
		<spring:param name="appId" value="${param.applicationId}"/>
	</spring:url>
	
	<a href="${createCommentUrl}">
		<spring:message code="comments.create" />			
	</a>
	</br>
	
	<spring:url var="applicationUrl" value="/application/handyWorker/list.do"/>
	<a href="${applicationUrl}">
		<spring:message code="comment.back" />			
	</a>


</security:authorize>


