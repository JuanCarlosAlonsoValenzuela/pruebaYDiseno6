<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<p><spring:message code="note.comment.create" /></p>

<form:form action="complaint/attachment/edit.do" modelAttribute="attachment">


	<form:label path="attachment" ><spring:message code="complaint.attachment" />: </form:label>
	<form:input path="attachment" />
	<form:errors path="attachment" />
	<br/>
	
	
		<input type="submit" name="create" value="<spring:message code="attachment.create.button"/>" />	
		
		
		
		
	<input type="submit" name="cancel" value="<spring:message code="comment.cancel.button" />"
		onClick="javascript: relativeRedir('report/note/comment/list.do?noteId{noteId}');" />
					<spring:param name="notId" value="${note.id}"/>
	

</form:form>	