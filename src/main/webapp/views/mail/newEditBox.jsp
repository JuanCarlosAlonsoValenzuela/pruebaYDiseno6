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

<security:authorize access="isAuthenticated()">

<spring:url var="showActor" value="/actor/authenticated/edit.do"/>

<h4><spring:message code="actor.logged" /><a href="${showActor}"><jstl:out value=" ${username}"/></a></h4>

<spring:url var="boxSave" value="/box/authenticated/save.do"/>
<form:form modelAttribute="box" action="${boxSave}">
	<form:label path="name">
		<spring:message code="mail.box.name"/>:
	</form:label>
	<form:input path="name" value="${box.name}" required/>
	<form:errors cssClass="error" path="name"/>
	
	<form:hidden path="id"/>
	<form:hidden path="version"/>
	<form:hidden path="isSystem"/>
	<form:hidden path="fatherBox"/>
	<form:hidden path="messages"/>
	
	<input type="submit" name = "save" value="<spring:message code="mail.save"/>" onclick="return confirm('<spring:message code="mail.save" />')"/>
</form:form>

<spring:url var="mail" value="/box/authenticated/list.do"/>

<p><a href="${mail}"><spring:message code="mail.cancel"/></a></p>

</security:authorize>