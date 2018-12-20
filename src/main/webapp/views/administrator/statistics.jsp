<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<style>
td, th {
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
  background-color:  #FFFFFF;
}
</style>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<security:authorize access="hasRole('ADMIN')">

<spring:message code="statistics.applicationsPerFixUpTask" />	
<br />

<table style="width: 100%">
	<tr> 
		<td><b><spring:message code="statistics.average" /></b></td> 
		<td><jstl:out value="${statistics.get('applicationPerFixUpTask')[0]}" /> </td>
	</tr>
	<tr>
		<td><b><spring:message code="statistics.minimum"/></b></td> 
		<td><jstl:out value="${statistics.get('applicationPerFixUpTask')[1]}" /> </td>
	</tr>
	<tr>
		<td><b><spring:message code="statistics.maximum"/></b></td> 
		<td><jstl:out value="${statistics.get('applicationPerFixUpTask')[2]}" /> </td>
	</tr>
	<tr>
		<td><b><spring:message code="statistics.standardDeviation"/></b></td> 
		<td><jstl:out value="${statistics.get('applicationPerFixUpTask')[3]}" /> </td>
	</tr>
</table>
<br />
 
<spring:message code="statistics.priceOfferedPerApplication" />			
<br />
<table style="width: 100%">
	<tr>
		<td><b><spring:message code="statistics.average" /></b></td> 
		<td><jstl:out value="${statistics.get('priceOferredPerApplication')[0]}" /> </td>
	</tr>
	<tr>
		<td><b><spring:message code="statistics.minimum"/></b></td> 
		<td><jstl:out value="${statistics.get('priceOferredPerApplication')[1]}" /> </td>
	</tr>
	<tr>
		<td><b><spring:message code="statistics.maximum"/></b></td>
		<td><jstl:out value="${statistics.get('priceOferredPerApplication')[2]}" /> </td>
	</tr>
	<tr>
		<td><b><spring:message code="statistics.standardDeviation"/></b></td> 
		<td><jstl:out value="${statistics.get('priceOferredPerApplication')[3]}" /> </td>
	</tr>
</table>
<br />

<%--
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
--%>

<spring:message code="statistics.ratios"/>				

<table style="width:100%">
  <tr>
    <td><b><spring:message code="statistics.ratioPendingApplications" /></b></td>
    <td><jstl:out  value="${statisticsRatios.get('ratioPendingElapsedApplications')}"/></td>
  </tr>
  <tr>
    <td><b><spring:message code="statistics.ratioAcceptedApplications" /></b></td>
    <td><jstl:out  value="${statisticsRatios.get('ratioAcceptedApplications')}"/></td>
  </tr>
  <tr>
    <td><b><spring:message code="statistics.ratioRejectedApplications" /></b></td>
    <td><jstl:out  value="${statisticsRatios.get('ratioRejectedApplications')}"/></td>
  </tr>
  <tr>
    <td><b><spring:message code="statistics.ratioPendingElapsedApplications" /></b></td>
    <td><jstl:out  value="${statisticsRatios.get('ratioPendingElapsedApplications')}"/></td>
  </tr>
</table>
	
</security:authorize>
