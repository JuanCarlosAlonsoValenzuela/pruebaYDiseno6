<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<p>
	<spring:message code="category.listSubcategories" />
</p>

<security:authorize access="hasRole('ADMIN')">

	<display:table pagesize="5" name="subCategories" id="row"
		requestURI="category/administrator/subCategories/list.do">
			
			<jstl:if test="${locale=='EN'}" >
			<display:column titleKey="category.subCategories">
				<jstl:out value="${row.name}" />
			</display:column>
			</jstl:if>
			
			<jstl:if test="${locale=='ES'}" >
			<display:column titleKey="category.subCategories">
				<jstl:out value="${row.nameSpanish}" />
			</display:column>
			</jstl:if>
			
		
	</display:table>

	<spring:url var="createSubCategoryUrl" value="/category/administrator/subCategories/create.do?categoryId={categoryId}">
		<spring:param name="categoryId" value="${categoryId}"/>
	</spring:url>
	
	<a href="category/administrator/list.do"><spring:message code="category.back" /></a>

</security:authorize>