<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<p><spring:message code="fixUpTask.customer.create" /></p>			

<security:authorize access="hasRole('CUSTOMER')">

<form:form action="fixUpTask/customer/edit.do" modelAttribute="fixUpTask">
		<!-- Hidden Attributes -->
		<form:hidden path="id"/>
		<form:hidden path="version" />
		<form:hidden path="ticker" />
		<form:hidden path="momentPublished" />
		
		<!-- Description -->
		<form:label path="description">	<!-- Tiles -->
			<spring:message code="fixUpTask.description" />	
		</form:label>
		<form:textarea path="description"/>
		<form:errors cssClass="error" path="description" />
		
		<!-- Address -->
		<form:label path="address"> <!-- Tiles -->
			<spring:message code="fixUpTask.address" />		
		</form:label>
		<form:input path="address" />
		<form:errors cssClass="error" path="description" />
		
		<!-- Max Price -->
		<form:label path="maxPrice"> <!-- Tiles -->
			<spring:message code="fixUpTask.maxPrice" />		
		</form:label>
		<form:input path="maxPrice" />
		<form:errors cssClass="error" path="maxPrice" />
		
		<!-- Realization Time -->
		<form:label path="realizationTime"> <!-- Tiles -->
			<spring:message code="fixUpTask.realizationTime" />	
		</form:label>
		<form:input path="realizationTime" placeholder="dd/MM/yyyy HH:mm" />
		<form:errors cssClass="error" path="realizationTime" />
		
		<!-- Category -->
		<form:label path="category"> <!-- Tiles -->
			<spring:message code="fixUpTask.category" />	
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
		<form:label path="warranty"> <!-- Tiles -->
			<spring:message code="fixUpTask.warranty" />		
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
		
		<input type="submit" name="create" value="<spring:message code="fixUpTask.create.button"/>" />	
		
		<input type="submit" name="cancel" value="<spring:message code="fixUpTask.cancel.button"/>" />

</form:form>

</security:authorize>