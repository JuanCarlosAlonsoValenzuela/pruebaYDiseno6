<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<p>
	<spring:message code="report.referee.create" />
</p>

<security:authorize access="hasRole('REFEREE')">

	<form:form action="report/referee/edit.do" modelAttribute="report">

		<form:hidden path="id" />
		<form:errors cssClass="error" path="id" />
		<form:hidden path="moment" />
		<form:errors cssClass="error" path="moment" />
		<form:hidden path="notes" />
		<form:errors cssClass="error" path="notes" />
		<form:hidden path="version" />
		<form:errors cssClass="error" path="version" />

		<form:label path="description">
			<!-- Tiles -->
			<spring:message code="report.description" />
		</form:label>
		<form:textarea path="description" />
		<form:errors cssClass="error" path="description" />
		<br />
		<br />

		<input type="hidden" name="complaintId" value="${complaintId}" />

		<spring:message code="report.attachments" />:
		<br />
		<input type="hidden" name="newAttachments" value="" />
		<textarea rows="12" cols="50" name="newAttachments"
			id="newAttachments"
			placeholder="http://www.aaa.com, http://www.bbb.com"></textarea>
		<br />
		
		<form:radiobutton path="finalMode" value="${true}" />
		<spring:message code="report.finalmode" />
		<form:radiobutton path="finalMode" value="${false}" />
		<spring:message code="report.draftmode" />
		<br />
		<br />

		<input type="submit" name="save"
			value="<spring:message code="report.create.button"/>" />
			
		<input type="submit" <jstl:if test="${report.id == 0}">
		<jstl:out value="disabled='disabled'"/></jstl:if>
		name="delete" value="<spring:message code="report.delete" />"
		onclick="return confirm('<spring:message code="report.verificationDelete" />')" />

		<input type="button" name="cancel"
			value="<spring:message code="report.cancel.button"/>" onClick="javascript:relativeRedir('report/referee/list.do')" />

	</form:form>

</security:authorize>