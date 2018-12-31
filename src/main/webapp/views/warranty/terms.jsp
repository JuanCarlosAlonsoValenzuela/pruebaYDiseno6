<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<p>
	<spring:message code="warranty.action.3" />
</p>

<security:authorize access="hasRole('ADMIN')">

	<display:table pagesize="5" name="terms" id="row" class="displaytag" 
		requestURI="warranty/administrator/terms/list.do"> 
			
			<display:column titleKey="warranty.terms">
				<jstl:out value="${row}" />
			</display:column>
			
	</display:table>

<jstl:if test="${warranty.isDraftMode}" >
	<spring:url var="createTermUrl" value="/warranty/administrator/terms/create.do?warrantyId={warrantyId}">
		<spring:param name="warrantyId" value="${warrantyId}"/>
	</spring:url>
	
	<a href="${createTermUrl}"><spring:message code="term.create" /></a>
	<br />
</jstl:if>
<a href="warranty/administrator/list.do"><spring:message code="warranty.back" /></a>

</security:authorize>