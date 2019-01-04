<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<p><spring:message code="handyWorker.endorsmentEdit" /></p>

<security:authorize access="hasRole('HANDYWORKER')">

<form:form modelAttribute="endorsement" action="endorsement/handyWorker/edit.do">

	<form:hidden path ="id"/>
	<form:hidden path ="version"/>
	<form:hidden path ="moment"/>
	<form:hidden path ="writtenBy"/>
	<form:hidden path ="comments"/>
	
	<spring:message code="endorsment.comments" />
	<br />
	<input type="hidden" name="comment" />
	<textarea rows="12" cols="50" name="comment" id="comment" placeholder="<spring:message code="endorsement.place"/>" ></textarea>
	<br />

	<jstl:choose>
		<jstl:when test="${endorsement.id == 0}">
		<form:select path="writtenTo">
			<form:options items="${customers}" itemLabel="userAccount.username" itemValue="id"/>								 		
		</form:select>
		<form:errors cssClass="error" path="writtenTo"/>
		<br/>
			<input type="submit" name="save" value="<spring:message code="handyWorker.save" />" />
		</jstl:when><jstl:otherwise>
			<form:hidden path ="writtenTo"/>
			<input type="submit" name="edit" value="<spring:message code="handyWorker.edit" />" />
			<jstl:if test="${username == username2}">
			<input type="submit" name="delete" value="<spring:message code="handyWorker.delete" />" onClick="return confirm('<spring:message code="handyWorker.verificationDelete" />')">
			</jstl:if>
		</jstl:otherwise>
	</jstl:choose>
	
	<input type="button" name="cancel" value="<spring:message code="handyWorker.cancel" />"
		onClick="javascript:relativeRedir('endorsement/handyWorker/list.do');" />
</form:form>	

</security:authorize>

