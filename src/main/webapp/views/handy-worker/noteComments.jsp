
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<p><spring:message code="note.comment.list" /></p>

<security:authorize access="hasRole('HANDYWORKER')">

<display:table pagesize="5" name="comments" id="row" class="displaytag" 
					requestURI="/note/handyWorker/listComments.do">
	<display:column titleKey="note.comments">
		<jstl:out value="${row}"/>
	</display:column>
</display:table>


<jstl:set var="count" value="0" />

<jstl:if test="${usernames.size()!='0'}">	
<jstl:forEach
				var="usernameWrite"
				items="${usernames}">

				<jstl:if test="${usernameWrite==username}">	
				<jstl:set var="count" value="${count+1}"/>
				</jstl:if>
			
			
			</jstl:forEach>
</jstl:if>

	<jstl:if test="${count=='0'}">

	</br>
	<spring:url var="createCommentUrl" value="note/handyWorker/newComment.do">
	<spring:param name="noteId" value="${param.noteId}"/>
	<spring:param name="reportId" value="${param.reportId}"/>
	<spring:param name="complaintId" value="${param.complaintId}"/>	
	<spring:param name="fixUpTaskId" value="${param.fixUpTaskId}"/>
	</spring:url>
		
		<input type="button"
		name="cancel"
		value="<spring:message code="comments.create"/>" onclick="javascript:relativeRedir('${createCommentUrl}');" />
	
	</jstl:if>
	
	<spring:url var="noteUrl" value="note/handyWorker/list.do?reportId={reportId}">
	<spring:param name="noteId" value="${param.noteid}"/>
	<spring:param name="reportId" value="${param.reportId}"/>
	<spring:param name="complaintId" value="${param.complaintId}"/>	
	<spring:param name="fixUpTaskId" value="${param.fixUpTaskId}"/>
	</spring:url>

	
	
		<input type="button"
		name="cancel"
		value="<spring:message code="comment.back"/>" onclick="javascript:relativeRedir('${noteUrl}');" />
	
	


</security:authorize>

	


