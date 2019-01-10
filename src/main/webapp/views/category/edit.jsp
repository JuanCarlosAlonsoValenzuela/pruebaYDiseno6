<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<p><spring:message code="category.create" /></p>

<security:authorize access="hasRole('ADMIN')"> 

<form:form modelAttribute="category" action="category/administrator/edit.do">
    <!--Hidden Attributes -->
	<form:hidden path ="id"/>
	<form:hidden path ="version"/>
	<form:hidden path ="subCategories"/>
	

	<!-- Category Attributes -->
	<jstl:if test="${row.name!='CATEGORY'}" >
		<form:label path="name">
			<spring:message code="category.name" />
		</form:label>
		<form:input path="name" />
		<form:errors cssClass="error" path="name"/>
		<br />
	</jstl:if>
	
	<jstl:if test="${row.name!='CATEGORY'}" >
		<form:label path="nameSpanish">
			<spring:message code="category.nameSpanish" />
		</form:label>
		<form:input path="nameSpanish" />
		<form:errors cssClass="error" path="nameSpanish"/>
		<br />
	</jstl:if>
	
	<jstl:if test="${row.name=='CATEGORY'}" >
		<form:hidden path ="name"/>
		<form:hidden path ="nameSpanish"/>
	</jstl:if>
	
	
	<!-- BOTONES -->	
	<!-- Create/Edit -->
	<input type="submit" name="save" value="<spring:message code="category.save" />" /> 
	
	<!-- Edit -->
	<jstl:if test="${category.id > 0}">
	<input type="submit" name="delete" value="<spring:message code="category.delete" />"
		onclick="return confirm('<spring:message code="category.verificationDelete" />')" />
	</jstl:if>
	
	<!-- Cancel -->
	<spring:url var="cancelURL"	value="/category/administrator/list.do" />
	<a href="${cancelURL}" style="text-decoration: none;">
    	<input type="button" value="<spring:message code="category.cancel" />" />
	</a>
		
	</form:form>
	
</security:authorize>