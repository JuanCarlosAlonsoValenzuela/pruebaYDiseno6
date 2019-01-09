<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<p>
	<spring:message code="referee.myComplaints" />
</p>

<security:authorize access="hasRole('REFEREE')">

	<display:table pagesize="5" name="complaints" id="row"
		class="displaytag" requestURI="complaint/referee/list.do">

		<display:column>
			<spring:url var="createReport"
				value="report/referee/create.do?complaintId={complaintId}">
				<spring:param name="complaintId" value="${row.id}" />
			</spring:url>

			<a href="${createReport}"> <spring:message
					code="complaint.createReport" />
			</a>
		</display:column>

		<display:column titleKey="complaint.report">
			<spring:url var="reportsUrl"
				value="/report/referee/list.do?complaintId={complaintId}">
				<spring:param name="complaintId" value="${row.id}" />
			</spring:url>
			<a href="${reportsUrl}"> <spring:message var="reports"
					code="complaint.reports" /> <jstl:out value="${reports}" />
			</a>


		</display:column>

		<display:column property="ticker" titleKey="complaint.ticker" />

		<display:column property="moment" titleKey="complaint.moment"
			sortable="true" format="{0,date,dd/MM/yyyy HH:mm}" />

		<display:column property="description"
			titleKey="complaint.description" />


		<display:column titleKey="complaint.attachments">
			<jstl:set var="attachmentsSize" value="${row.attachments.size()}" />
			<spring:url var="attachmentsUrl"
				value="/attachment/referee/list.do?complaintId={complaintId}">
				<spring:param name="complaintId" value="${row.id}" />
			</spring:url>
			<a href="${attachmentsUrl}"> <spring:message
					var="viewAttachments1" code="complaint.viewAttachments" /> <jstl:out
					value="${viewAttachments1}(${attachmentsSize})" />
			</a>
		</display:column>

	</display:table>

</security:authorize>