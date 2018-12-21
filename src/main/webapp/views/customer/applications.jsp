<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<%@ page import="java.util.Date" %>
<%@ page import="java.sql.Timestamp"%>
  
<%
java.util.Date utilDate = new java.util.Date();
java.sql.Timestamp now = new java.sql.Timestamp(utilDate.getTime());
%>
<jstl:set var="now" value="<%=now%>"/>

<security:authorize access="hasRole('CUSTOMER')">
	
	<display:table pagesize="5" name="applications" id="row" class="displaytag" 
					requestURI="/application/customer/list.do">
		
		<jstl:choose>
			<jstl:when test="${row.status.toString()=='ACCEPTED'}">
				<jstl:set var="color" value="green" />
			</jstl:when>
			
			<jstl:when test="${row.status.toString()=='REJECTED'}">
				<jstl:set var="color" value="red" />
			</jstl:when>
			
			<jstl:when test="${row.fixUpTask.realizationTime < now}">
				<jstl:set var="color" value="grey" />
			</jstl:when>
			
			<jstl:otherwise>
				<jstl:set var="color" value="black" />
			</jstl:otherwise>
		</jstl:choose>
		

		<display:column titleKey="application.changeStatus">
			<!-- Solo deja cambiar el status si no está aceptado -->	
			<jstl:if test="${row.status.toString()!='ACCEPTED'}">
					<spring:url var="statusUrl" value="/application/customer/edit.do?applicationId={appId}">
							<spring:param name="appId" value="${row.id}"/>
					</spring:url>
			
					<a href="${statusUrl}">
							<spring:message code="application.changeStatus" />
					</a>
			</jstl:if>	
		</display:column>
		
		<display:column property="moment" titleKey="application.moment"	
						format="{0,date,dd/MM/yyyy HH:mm}" style="color:${color}"/>
		
		<display:column titleKey="application.status" property="status" style="color:${color}"/>	
		
		<display:column property="offeredPrice" titleKey="application.offeredPrice" style="color:${color}"/>	
		
		<display:column titleKey="application.comments" style="color:${color}">
				<jstl:set var="commentsSize" value="${row.comments.size()}" />
				<spring:url var="commentsUrl" value="/application/customer/listComments.do?fixUpTaskId={fixId}&applicationId={appId}">
							<spring:param name="appId" value="${row.id}"/>
							<spring:param name="fixId" value="${fixUpTaskId}"/>
				</spring:url>
				<a href="${commentsUrl}">
							<spring:message var ="viewComments1" code="application.viewComments" />
							<jstl:out value="${viewComments1}(${commentsSize})" />		
				</a>
		</display:column>
		
		<display:column titleKey="application.make" style="color:${color}">		
				<jstl:out value="${row.handyWorker.make}"/>
		</display:column>
		
		<display:column titleKey="application.score"  style="color:${color}">		
				<jstl:out value="${row.handyWorker.score}" />
		</display:column>
	</display:table>

</security:authorize>