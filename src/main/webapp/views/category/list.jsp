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
		requestURI="category/list.do">

		<display:column>
			<a href="category/edit.do?categoryId=${row.id}" />
			<spring:message code="category.edit" />
		</display:column>

		<display:column property="name" titlekey="category.name">
			<jstl:out value="${row.name}" />
		</display:column>

		<display:column titleKey="category.subCategories">
			<jstl:set var="subCategoriesSize" value="${row.subCategories.size()}" />
			<spring:url var="subCategoriesUrl"
				value="/category/subcategories/list.do?categoryId={cateId}">
				<spring:param name="cateId" value="${row.id}" />
			</spring:url>
			<a href="${subCategoriesUrl}"> <spring:message
					var="viewSubCategories1" code="category.viewSubCategories" /> <jstl:out
					value="$viewSubCategories1}(${subCategoriesSize})" />
			</a>
		</display:column>
	</display:table>
	</br>

	<a href="category/edit.do"><spring:message code="warranty.create" /></a>

</security:authorize>