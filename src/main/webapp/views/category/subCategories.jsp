<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<p>
	<spring:message code="category.action.5" />
</p>

<security:authorize access="hasRole('ADMIN')">

	<display:table pagesize="5" name="subCategories" id="row"
		requestURI="category/subcategories/list.do">

		<display:column>
			<input type="submit"
				name="delete" value="<spring:message code="category.delete" />"
				onclick="return confirm('<spring:message code="subCategory.verificationDelete" />')" />
		</display:column>

		<display:column property="subCategories" titlekey="subCategories.name">
			<jstl:out value="${subCategories}" />
		</display:column>
	</display:table>
	
<a href="category/subCategory/create.do"><spring:message code="subCategory.create" /></a>

</security:authorize>