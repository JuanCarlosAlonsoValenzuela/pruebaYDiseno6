<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<p><spring:message code="report.addNote" /></p>	


<security:authorize access="hasRole('REFEREE')">
	<input type="hidden" name="reportId" value="${reportId}"/>

	<jstl:choose>
		<jstl:when test="${note.getId() == 0}">
			<p><spring:message code="note.mandatoryCommentAdd" /></p>	
			<form:form action="note/referee/create.do" modelAttribute="note" id="add" method="post">

				<!-- Hidden Attributes -->
				<form:hidden path="id"/>
				<form:hidden path="version"/>
				<form:hidden path="moment" />
				<form:hidden path="usernames" />
				<form:hidden path="optionalComments" />
					
				<!--  Mandatory Comment -->
				<form:label path="mandatoryComment">
					<spring:message code="note.mandatoryComment" />:		
				</form:label>
				<form:textarea rows="15" cols="100" path="mandatoryComment" />
				<form:errors cssClass="error" path="mandatoryComment"/>
				<br/>				
				<input type="hidden" name="reportId" value="${reportId}"/>
				<input type="submit" name="save" value="<spring:message code="note.save.button" />" onclick="return confirm('<spring:message code="note.create.confirm" />')"/>
				<input type="button" name="cancel" value="<spring:message code="note.cancel.button" />" onclick="javascript:relativeRedir('note/referee/list.do?reportId=${reportId}');" />
			</form:form>
		</jstl:when>
		<jstl:otherwise>
			<p><spring:message code="note.addComment" /></p>	
			<form name="comment" id="comment" action="note/referee/addComment.do" method="post">
				<textarea rows="15" cols="100" name="comment" id="comment" placeholder="<spring:message code="comment.writeComment"/>" ></textarea>
				<input type="hidden" name="noteId" value="${noteId}"/>
				<br/>
				<input type="submit" name="Comment" value="<spring:message code="note.saveComment.button" />" onclick="return confirm('<spring:message code="note.addComment.confirm" />')"/>			
				<input type="button" name="cancel" value="<spring:message code="note.cancel.button" />" onclick="javascript:relativeRedir('note/referee/listComments.do?noteId=${noteId}');" />
			</form>

			<jstl:if test="${comment=='' || comment!=null}">
				<p style="color:red"><spring:message code="operation.error"/></p>
			</jstl:if>

		</jstl:otherwise>
		
	</jstl:choose>

</security:authorize>