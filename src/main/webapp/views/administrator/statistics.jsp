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

<spring:message code="statistics.maxPricePerFixUpTask" />			
<br />
<table style="width: 100%">
	<tr>
		<td><b><spring:message code="statistics.average" /></b></td> 
		<td><jstl:out value="${statistics.get('maxPricePerFixUpTask')[0]}" /> </td>
	</tr>
	<tr>
		<td><b><spring:message code="statistics.minimum"/></b></td> 
		<td><jstl:out value="${statistics.get('maxPricePerFixUpTask')[1]}" /> </td>
	</tr>
	<tr>
		<td><b><spring:message code="statistics.maximum"/></b></td>
		<td><jstl:out value="${statistics.get('maxPricePerFixUpTask')[2]}" /> </td>
	</tr>
	<tr>
		<td><b><spring:message code="statistics.standardDeviation"/></b></td> 
		<td><jstl:out value="${statistics.get('maxPricePerFixUpTask')[3]}" /> </td>
	</tr>
</table>
<br />

<spring:message code="statistics.fixUpTaskPerUser" />			
<br />
<table style="width: 100%">
	<tr>
		<td><b><spring:message code="statistics.average" /></b></td> 
		<td><jstl:out value="${statistics.get('fixUpTaskPerUser')[0]}" /> </td>
	</tr>
	<tr>
		<td><b><spring:message code="statistics.minimum"/></b></td> 
		<td><jstl:out value="${statistics.get('fixUpTaskPerUser')[1]}" /> </td>
	</tr>
	<tr>
		<td><b><spring:message code="statistics.maximum"/></b></td>
		<td><jstl:out value="${statistics.get('fixUpTaskPerUser')[2]}" /> </td>
	</tr>
	<tr>
		<td><b><spring:message code="statistics.standardDeviation"/></b></td> 
		<td><jstl:out value="${statistics.get('fixUpTaskPerUser')[3]}" /> </td>
	</tr>
</table>
<br />

<spring:message code="statistics.numberComplaintsPerFixUpTask" />			
<br />
<table style="width: 100%">
	<tr>
		<td><b><spring:message code="statistics.average" /></b></td> 
		<td><jstl:out value="${statistics.get('numberComplaintsPerFixUpTask')[0]}" /> </td>
	</tr>
	<tr>
		<td><b><spring:message code="statistics.minimum"/></b></td> 
		<td><jstl:out value="${statistics.get('numberComplaintsPerFixUpTask')[1]}" /> </td>
	</tr>
	<tr>
		<td><b><spring:message code="statistics.maximum"/></b></td>
		<td><jstl:out value="${statistics.get('numberComplaintsPerFixUpTask')[2]}" /> </td>
	</tr>
	<tr>
		<td><b><spring:message code="statistics.standardDeviation"/></b></td> 
		<td><jstl:out value="${statistics.get('numberComplaintsPerFixUpTask')[3]}" /> </td>
	</tr>
</table>
<br />

<spring:message code="statistics.notesPerReferee" />			
<br />
<table style="width: 100%">
	<tr>
		<td><b><spring:message code="statistics.average" /></b></td> 
		<td><jstl:out value="${statistics.get('notesPerReferee')[0]}" /> </td>
	</tr>
	<tr>
		<td><b><spring:message code="statistics.minimum"/></b></td> 
		<td><jstl:out value="${statistics.get('notesPerReferee')[1]}" /> </td>
	</tr>
	<tr>
		<td><b><spring:message code="statistics.maximum"/></b></td>
		<td><jstl:out value="${statistics.get('notesPerReferee')[2]}" /> </td>
	</tr>
	<tr>
		<td><b><spring:message code="statistics.standardDeviation"/></b></td> 
		<td><jstl:out value="${statistics.get('notesPerReferee')[3]}" /> </td>
	</tr>
</table>
<br />

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
  <tr>
    <td><b><spring:message code="statistics.fixUpTaskWithComplain" /></b></td>
    <td><jstl:out  value="${statisticsRatios.get('fixUpTaskWithComplain')}"/></td>
  </tr>
</table>

<spring:message code="administrator.statisticPercentCust" />

<table style="width:100%">
  <tr> 
	<td><b><spring:message code="administrator.customerName" /></b></td>
  </tr>
  <jstl:forEach items="${tenPercentCustomers}" var="customer">
  <tr>
  	<td><jstl:out value="${customer}"/></td>
  </tr>
  </jstl:forEach>
</table> 

<spring:message code="administrator.statisticPercetHw" />

<table style="width:100%">
  <tr> 
	<td><b><spring:message code="administrator.handyWorkerName" /></b></td>
  </tr>
  <jstl:forEach items="${tenPercentHandyWorkers}" var="handyWorker">
  <tr>
  	<td><jstl:out value="${handyWorker}"/></td>
  </tr>
  </jstl:forEach>
</table> 

<spring:message code="aministrator.statisticTop3Custo" />

<table style="width:100%">
  <tr> 
	<td><b><spring:message code="administrator.customerName" /></b></td>
  </tr>
  <jstl:forEach items="${topThreeCustomers}" var="customer">
  <tr>
  	<td><jstl:out value="${customer}"/></td>
  </tr>
  </jstl:forEach>
</table> 

<spring:message code="aministrator.statisticTop3Hw"/>

<table style="width:100%">
  <tr> 
	<td><b><spring:message code="administrator.handyWorkerName" /></b></td>
  </tr>
  <jstl:forEach items="${topThreeHandyWorkers}" var="handyWorker">
  <tr>
  	<td><jstl:out value="${handyWorker}"/></td>
  </tr>
  </jstl:forEach>
</table> 

</security:authorize>
