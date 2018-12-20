<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<p><spring:message code="finder.handyWorker.title" /></p>	<!-- Añadir --> <!-- Tiles -->		

<security:authorize access="hasRole('HANDYWORKER')">

	<form:form action="finder/handyWorker/edit.do" modelAttribute="finder">
			<!-- Hidden Attributes -->
			<form:hidden path="id" />
			<form:hidden path="version" />
			
			<!-- Keyword -->
			<form:label path="keyWord"> 
					<spring:message code="finder.keyWord" /> <!-- Añadir -->	
			</form:label>
			<form:input path="keyWord" />
			<form:errors cssClass="error" path="keyWord" />
			
			<!-- Category -->
			<form:label path="category"> 
					<spring:message code="finder.category" /> <!-- Añadir -->	
			</form:label>
		
			<form:select path="category">
					<jstl:forEach var="category" items="categories">
							<form:options
									itemLabel=<jstl:out value="${category.name}" />			
									itemValue=<jstl:out value="${category.name}" />
							/>
						
					</jstl:forEach>
		
			</form:select>
			<form:errors cssClass="error" path="category" />
			
			<!-- Warranty -->
			<form:label path="warranty"> 
					<spring:message code="finder.warranty" /> <!-- Añadir -->	
			</form:label>
			<form:select path="warranty">
					<jstl:forEach var="warranty" items="warranties">
							<form:options
									itemLabel=<jstl:out value="${warranty.name}" />			
									itemValue=<jstl:out value="${warranty.name}" />
							/>
					</jstl:forEach>
			</form:select>
			<form:errors cssClass="error" path="warranty" />
			
			<!-- Minimum Price -->
			<form:label path="minPrice">
					<spring:message code="finder.minPrice" /> <!-- Añadir -->
			</form:label>
			<form:input path="minPrice" />
			<form:errors cssClass="error" path="minPrice" />
			
			<!-- Maximum Price -->
			<form:label path="maxPrice">
					<spring:message code="finder.maxPrice" /> <!-- Añadir -->
			</form:label>
			<form:input path="maxPrice" />
			<form:errors cssClass="error" path="maxPrice" />
		
			<!-- Start Date -->
			<form:label path="startDate">
					<spring:message code="finder.startDate" />	<!-- Añadir -->
			</form:label>
			<form:input path="startDate" placeholder="dd/MM/yyyy HH:mm" />
			<form:errors cssClass="error" path="startDate" />
			
			<!-- Start Date -->
			<form:label path="endDate">
					<spring:message code="finder.endDate" />	<!-- Añadir -->
			</form:label>
			<form:input path="endDate" placeholder="dd/MM/yyyy HH:mm" />
			<form:errors cssClass="error" path="endDate" />
	
			
			<!-- Buttons -->
			<input type="submit" name="create" value="<spring:message code="finder.create.button"/>" />		<!-- Añadir -->
		
			<input type="submit" name="cancel" value="<spring:message code="finder.cancel.button"/>" />		<!-- Añadir -->
	
	
	</form:form>

</security:authorize>