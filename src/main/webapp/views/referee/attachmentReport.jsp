<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<style>
td,th {
	border: 1px solid #FFFFFF;
	text-align: left;
	padding: 8px;
}

table {
	background-color: #ffeeaa;
	font-family: arial, sans-serif;
	border-collapse: collapse;
	width: 50%;
}

tr:nth-child(even) {
	background-color: #FFFFFF;
}
</style>

<p>
	<spring:message code="complaint.attachments" />
</p>

<security:authorize access="hasRole('REFEREE')">

	<table style="width: 100%">
		<tr>
			<th><spring:message code="attachment.name"></spring:message></th>
		</tr>

		<jstl:forEach var="attachment" items="${attachments}">
			<tr>
				<td><jstl:out value="${attachment}"></jstl:out></td>
			</tr>
		</jstl:forEach>

	</table>
</security:authorize>