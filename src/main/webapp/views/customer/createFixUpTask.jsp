<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<p><spring:message code="fixUpTask.customer.create" /></p>			

<security:authorize access="hasRole('CUSTOMER')">

<form:form action="fixUpTask/customer/saveFixUpTask.do" modelAttribute="fixUpTask">
		<!-- Hidden Attributes -->
		<form:hidden path="id"/>
		<form:hidden path="version" />
		<form:hidden path="ticker" />
		<form:hidden path="momentPublished" />
		<form:hidden path="applications"/>
		<form:hidden path="phases"/>
		<form:hidden path="complaints"/>
		
		<!-- Description -->
		<form:label path="description">	<!-- Tiles -->
			<spring:message code="fixUpTask.description" />	
		</form:label>
		<form:textarea path="description"/>
		<form:errors cssClass="error" path="description" />
		<br/>
		
		<!-- Address -->
		<form:label path="address"> <!-- Tiles -->
			<spring:message code="fixUpTask.address" />		
		</form:label>
		<form:input path="address" />
		<form:errors cssClass="error" path="description" />
		<br/>
		
		<!-- Max Price -->
		<form:label path="maxPrice"> <!-- Tiles -->
			<spring:message code="fixUpTask.maxPrice" />		
		</form:label>
		<form:input path="maxPrice" />
		<form:errors cssClass="error" path="maxPrice" />
		<br/>
	
		
		<!-- Realization Time -->
		<form:label path="realizationTime"> <!-- Tiles -->
			<spring:message code="fixUpTask.realizationTime" />	
		</form:label>
		<form:input path="realizationTime" placeholder="dd/MM/yyyy HH:mm" />
		<form:errors cssClass="error" path="realizationTime" />
		<br/>
		
		<!-- Category -->
		<form:label path="category"> <!-- Tiles -->
			<spring:message code="fixUpTask.category" />	
		</form:label>
		
		<form:select path="category">
				<jstl:forEach var="category" items="${categories}">
						<form:option
								label="${category.name}"		
								value="${category.id}"
						/>
						
				</jstl:forEach>
		
		</form:select>
		<form:errors cssClass="error" path="category" />
		<br/>
		
		<!-- Warranty -->
		<form:label path="warranty"> <!-- Tiles -->
			<spring:message code="fixUpTask.warranty" />		
		</form:label>
		<form:select path="warranty">
				<jstl:forEach var="warranty" items="${warranties}">
						<form:option
								label="${warranty.title}"		
								value="${warranty.id}"
						/>
				</jstl:forEach>
		</form:select>
		<form:errors cssClass="error" path="warranty" />
		<br/><br/>
	
		<input type="submit" name="create" value="<spring:message code="fixUpTask.create.button"/>" />
		<br/>

</form:form>

<input type="submit" name="cancel" onclick="history.back()" value="<spring:message code="fixUpTask.cancel.button"/>" />	

</security:authorize>