<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<p>
	<spring:message code="customer.comment.list" />
</p>

<security:authorize access="hasRole('CUSTOMER')">

	<display:table pagesize="5" name="comments" id="row"
		requestURI="endorsement/customer/listComments.do">
			
			<display:column titleKey="customer.comment.list">
				<jstl:out value="${row}" />
			</display:column>
		
	</display:table>	
	
<a href="endorsement/customer/list.do"><spring:message code="comment.back" /></a>

</security:authorize>