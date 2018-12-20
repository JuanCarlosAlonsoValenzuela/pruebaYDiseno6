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

<p><spring:message code="administrator.createReferee" /></p>

<security:authorize access="hasRole('ADMIN')">

<form:form modelAttribute="admin" action="register/administrator/create.do">

	<form:hidden path ="id"/>
	<form:hidden path ="version"/>
	<form:hidden path ="hasSpam"/>
	<form:hidden path ="boxes"/>
	
	
	<form:label path="name">
		<spring:message code="administrator.name" />
	</form:label>
	<form:input path="name" required/>
	<form:errors cssClass="error" path="name"/>
	<br />
	
	<form:label path="middleName">
		<spring:message code="administrator.middleName" />
	</form:label>
	<form:input path="middlename" />
	<form:errors cssClass="error" path="middleName"/>
	<br />
	
	<form:label path="surname">
		<spring:message code="administrator.surname" />
	</form:label>
	<form:input path="surname" required/>
	<form:errors cssClass="error" path="surname"/>
	<br />
	
	<form:label path="photo">
		<spring:message code="administrator.photo" />
	</form:label>
	<form:input path="photo"/>
	<form:errors cssClass="error" path="photo"/>
	<br />
	
	<form:label path="email">
		<spring:message code="administrator.email" />
	</form:label>
	<form:input path="email" required/>
	<form:errors cssClass="error" path="email"/>
	<br />
	
	<form:label path="phonenNumber">
		<spring:message code="administrator.phonenNumber" />
	</form:label>
	<form:input path="phoneNumber"/>
	<form:errors cssClass="error" path="phoneNumber"/>
	<br />
	
	<form:label path="address">
		<spring:message code="administrator.address" />
	</form:label>
	<form:input path="address"/>
	<form:errors cssClass="error" path="address"/>
	<br />
	
	<form:label path="userName">
		<spring:message code="administrator.userName" />
	</form:label>
	<form:input path="userName" required/>
	<form:errors cssClass="error" path="userName"/>
	<br />
	
	<form:label path="password">
		<spring:message code="administrator.password" />
	</form:label>
	<form:password path="password" required/>
	<form:errors cssClass="error" path="password"/>
	<br />
	
	<input type="radio" name="authority" value="REFEREE" checked> Referee<br>
	
	<input type="submit" name="socialProfiles" value="<spring:message code="administrator.socialProfile" />"
		onClick="javascript: relativeRedir('administrator/createSocialProfile.do');" />
	
	<input type="submit" name="save" value="<spring:message code="administrator.save" />" />
	
	<input type="submit" name="cancel" value="<spring:message code="administrator.cancel" />"
		onClick="javascript: relativeRedir('administrator/profile.do');" />
	
	
	</form:form>
	
</security:authorize>


