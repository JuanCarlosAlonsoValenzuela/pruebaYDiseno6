<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<p><spring:message code="category.editSon" /></p>			

<security:authorize access="hasRole('ADMIN')">




<form name="subCategory" id="subCategory" action="category/administrator/editSubCategory.do" method="post" >

		
		
		<jstl:if test="${possibleCategories.size()>0}">
		<jstl:if test="${locale=='EN'}" >
			<jstl:forEach var="i" begin="0" end="${possibleCategories.size()-1}">
				<input type="radio" name="possibleCategoryName" id="possibleCategoryName" value="${possibleCategories.get(i).name}">
			
				<label for="possibleCategoryId"> 
					<jstl:out value="${possibleCategories.get(i).name }" /> 
				</label>
			
				<br />
			</jstl:forEach>
		</jstl:if>
		
		<jstl:if test="${locale=='ES'}" >
		<jstl:forEach var="i" begin="0" end="${possibleCategories.size()-1}">
				<input type="radio" name="possibleCategoryName" id="possibleCategoryName" value="${possibleCategories.get(i).name}">
			
				<label for="possibleCategoryId"> 
					<jstl:out value="${possibleCategories.get(i).nameSpanish }" /> 
				</label>
				<br />
			</jstl:forEach>
		</jstl:if>
		</jstl:if>	
	

	<input type="hidden" name="categoryId" id="categoryId" value="<jstl:out value="${categoryId}"/>"/>
	
	<input type="submit" name="create" value="<spring:message code="category.add"/>" />	
	
</form>

	<spring:url var="categoryURL" value="/category/administrator/list.do" />
	<a href="${categoryURL}">
		<spring:message code="category.cancel" />			
	</a>


</security:authorize>