<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<p><spring:message code="unassigned.complaints" /></p>	

<security:authorize access="hasRole('REFEREE')">

	<display:table pagesize="5" name="complaints" id="row"
	class="displaytag" requestURI="complaint/referee/listUnassigned.do">
	
		<display:column property="description" titleKey="complaint.description" /> 
		
		<display:column titleKey="referee.autoassign">
				<spring:url var="refComplaints" value="/complaint/referee/assign.do">
					<spring:param name="comp" value="${row.id}"/>
				</spring:url>
				<a href="${refComplaints}">
							<spring:message var ="assign" code="complaint.assign" />
							<jstl:out value="${assign}" />
				</a>
		</display:column>
		
	</display:table>
	
</security:authorize>