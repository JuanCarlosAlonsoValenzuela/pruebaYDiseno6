<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>		

<security:authorize access="hasRole('CUSTOMER')">

<form:form action="fixUpTask/customer/save.do" modelAttribute="fixUpTask">
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
				
					<jstl:set var="categoryName" value="${category.name}"/>
					<jstl:if test="${locale=='ES'}">
						<jstl:set var="categoryName" value="${category.nameSpanish}"/>
					</jstl:if>
				
					<form:option
						label="${categoryName}"		
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
		
		<jstl:if test="${fixUpTask.id==0}">
			<jstl:set var="submitButton" value="fixUpTask.create.button"/>
		</jstl:if>
		<jstl:if test="${fixUpTask.id!=0}">
			<jstl:set var="submitButton" value="fixUpTask.update.button"/>
		</jstl:if>
		
		<input type="submit" name="save" value="<spring:message code="${submitButton}"/>" />
		
		<jstl:if test="${fixUpTask.id!=0}">
			<input type="submit" name="delete" onclick="return confirm('<spring:message code="fixUpTask.delete.confirmation" />')" value="<spring:message code="fixUpTask.delete.button"/>"/>
		</jstl:if>
		
		<input type="button" name="cancel" onclick="javascript:relativeRedir('fixUpTask/customer/list.do');"  value="<spring:message code="fixUpTask.cancel.button"/>" />	
</form:form>

</security:authorize>