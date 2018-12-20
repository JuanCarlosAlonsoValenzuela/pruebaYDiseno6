<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<h3><spring:message code="administrator.statistics" /></h3>	

<security:authorize access="hasRole('ADMIN')">

<spring:message code="statistics.applicationsPerFixUpTask" />	
<br />
<table>
	<tr>
		<td><spring:message code="statistics.average" /></td> 
		<td><jstl:out value="${statistics.get('applicationPerFixUpTask').get(0)}" /> </td>
	</tr>
	<tr>
		<td><spring:message code="statistics.minimum"/></td> 
		<td><jstl:out value="${statistics.get('applicationPerFixUpTask').get(1)}" /> </td>
	</tr>
	<tr>
		<td><spring:message code="statistics.maximum"/></td> 
		<td><jstl:out value="${statistics.get('applicationPerFixUpTask').get(2)}" /> </td>
	</tr>
	<tr>
		<td><spring:message code="statistics.standardDeviation"/></td> 
		<td><jstl:out value="${statistics.get('applicationPerFixUpTask').get(3)}" /> </td>
	</tr>
</table>
<br />
<br />

<spring:message code="statistics.priceOfferedPerApplication" />			
<br />
<table>
	<tr>
		<td><spring:message code="statistics.average" /></td> 
		<td><jstl:out value="${statistics.get('priceOferredPerApplication').get(0)}" /> </td>
	</tr>
	<tr>
		<td><spring:message code="statistics.minimum"/></td> 
		<td><jstl:out value="${statistics.get('priceOferredPerApplication').get(1)}" /> </td>
	</tr>
	<tr>
		<td><spring:message code="statistics.maximum"/></td>
		<td><jstl:out value="${statistics.get('priceOferredPerApplication').get(2)}" /> </td>
	</tr>
	<tr>
		<td><spring:message code="statistics.standardDeviation"/></td> 
		<td><jstl:out value="${statistics.get('priceOferredPerApplication').get(3)}" /> </td>
	</tr>
</table>
<br />
<br />


<spring:message code="statistics.maxPricePerFixUpTask" />			
<br />
<table>
	<tr>
		<td><spring:message code="statistics.average" /></td> 
		<td><jstl:out value="${statistics.get('maxPricePerFixUpTask').get(0)}" /> </td>
	</tr>
	<tr>
		<td><spring:message code="statistics.minimum"/></td> 
		<td><jstl:out value="${statistics.get('maxPricePerFixUpTask').get(1)}" /> </td>
	</tr>
	<tr>
		<td><spring:message code="statistics.maximum"/></td>
		<td><jstl:out value="${statistics.get('maxPricePerFixUpTask').get(2)}" /> </td>
	</tr>
	<tr>
		<td><spring:message code="statistics.standardDeviation"/></td> 
		<td><jstl:out value="${statistics.get('maxPricePerFixUpTask').get(3)}" /> </td>
	</tr>
</table>
<br />
<br />


<spring:message code="statistics.fixUpTaskPerUser" />			
<br />
<table>
	<tr>
		<td><spring:message code="statistics.average" /></td> 
		<td><jstl:out value="${statistics.get('fixUpTaskPerUser').get(0)}" /> </td>
	</tr>
	<tr>
		<td><spring:message code="statistics.minimum"/></td> 
		<td><jstl:out value="${statistics.get('fixUpTaskPerUser').get(1)}" /> </td>
	</tr>
	<tr>
		<td><spring:message code="statistics.maximum"/></td>
		<td><jstl:out value="${statistics.get('fixUpTaskPerUser').get(2)}" /> </td>
	</tr>
	<tr>
		<td><spring:message code="statistics.standardDeviation"/></td> 
		<td><jstl:out value="${statistics.get('fixUpTaskPerUser').get(3)}" /> </td>
	</tr>
</table>
<br />
<br />

<spring:message code="statistics.ratios"/>				

<table style="width:100%">
  <tr>
    <td><spring:message code="statistics.ratioPendingApplications" /></td>
    <td><jstl:out  value="${ratios.get('ratioPendingElapsedApplications')}"/></td>
  </tr>
  <tr>
    <td><spring:message code="statistics.ratioAcceptedApplications" /></td>
    <td><jstl:out  value="${ratios.get('ratioAcceptedApplications')}"/></td>
  </tr>
  <tr>
    <td><spring:message code="statistics.ratioRejectedApplications" /></td>
    <td><jstl:out  value="${ratios.get('ratioRejectedApplications')}"/></td>
  </tr>
  <tr>
    <td><spring:message code="statistics.ratioPendingElapsedApplications" /></td>
    <td><jstl:out  value="${ratios.get('ratioPendingElapsedApplications')}"/></td>
  </tr>
</table>
	
</security:authorize>
