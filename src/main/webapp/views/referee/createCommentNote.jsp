<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<p><spring:message code="note.addComment" /></p>

<form:form action="report/note/comment/edit.do?" modelAttribute="comment">


	<form:label path="comment" ><spring:message code="note.comment" />: </form:label>
	<form:textarea path="comment" />
	<form:errors path="comment" />
	<br/>
	
	
		<input type="submit" name="create" value="<spring:message code="comment.create.button"/>" />	
		
		
		
		
	<input type="submit" name="cancel" value="<spring:message code="cancel.button" />"
		onClick="javascript: relativeRedir('report/note/comment/list.do?noteId={noteId}');" />
					<spring:param name="notId" value="${note.id}"/>
	

</form:form>	