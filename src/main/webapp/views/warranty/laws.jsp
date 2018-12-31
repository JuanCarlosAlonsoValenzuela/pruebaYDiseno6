<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<p>
	<spring:message code="warranty.action.5" />
</p>

<security:authorize access="hasRole('ADMIN')">

	<display:table pagesize="5" name="laws" id="row"
		requestURI="warranty/administrator/laws/list.do">
			
			<display:column titleKey="warranty.laws">
				<jstl:out value="${row}" />
			</display:column>
		
	</display:table>

<jstl:if test="${warranty.isDraftMode}" >
	<spring:url var="createLawUrl" value="/warranty/administrator/laws/create.do?warrantyId={warrantyId}">
		<spring:param name="warrantyId" value="${warrantyId}"/>
	</spring:url>
	
	<a href="${createLawUrl}"><spring:message code="law.create" /></a>
	<br />
</jstl:if>

<a href="warranty/administrator/list.do"><spring:message code="warranty.back" /></a>

</security:authorize>