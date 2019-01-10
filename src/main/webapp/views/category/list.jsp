<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<p>
	<spring:message code="category.action.1" />
</p>

<security:authorize access="hasRole('ADMIN')">

	<display:table pagesize="5" name="categories" id="row"
		requestURI="category/administrator/list.do">

		<display:column>
			<jstl:if test="${row.name!='CATEGORY'}" >
			<a href="category/administrator/edit.do?categoryId=${row.id}" >
				<spring:message code="category.edit" />
			</a>
			</jstl:if>
		</display:column>
		
		
		<jstl:if test="${locale=='EN'}" >
		<display:column property="name" titleKey="category.name">
			<jstl:out value="${row.name}" />
		</display:column>
		</jstl:if>
		
		<jstl:if test="${locale=='ES'}" >
		<display:column property="nameSpanish" titleKey="category.nameSpanish">
			<jstl:out value="${row.nameSpanish}" />
		</display:column>
		</jstl:if>

		<display:column titleKey="category.subCategories">
			<jstl:set var="subCategoriesSize" value="${row.subCategories.size()}" />
			<spring:url var="subCategoriesUrl"
				value="/category/administrator/subCategories/list.do?categoryId={cateId}">
				<spring:param name="cateId" value="${row.id}" />
			</spring:url>
			<a href="${subCategoriesUrl}"> 
					<spring:message var="viewSubCategories1" code="category.viewSubCategories" /> 
					<jstl:out value="${viewSubCategories1}(${subCategoriesSize})" />
			</a>
		</display:column>
		
		<jstl:if test="${locale=='EN'}" >
		<display:column titleKey="category.fatherCategory">
			<jstl:if test="${row.name!='CATEGORY'}" >
					<jstl:out value="${mapCategories.get(row).name}" />
			</jstl:if>
		</display:column>
		</jstl:if>
		
		<jstl:if test="${locale=='ES'}" >
		<display:column titleKey="category.fatherCategory">
			<jstl:if test="${row.name!='CATEGORY'}" >
					<jstl:out value="${mapCategories.get(row).nameSpanish}" />
			</jstl:if>
		</display:column>
		</jstl:if>
		
		<display:column>
			
			<a href="category/administrator/editSubCategory.do?categoryId=${row.id}" >
				<spring:message code="category.editSon" />
			</a>
		
		</display:column>
		
	</display:table>
	<br />

	<a href="category/administrator/create.do"><spring:message code="category.create" /></a>

</security:authorize>