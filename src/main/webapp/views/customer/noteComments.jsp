
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<p><spring:message code="note.comment.list" /></p>

<security:authorize access="hasRole('CUSTOMER')">

	<jstl:forEach  items="${optionalComments}" var="comment">
			<jstl:out value="${comment}" />
			<br /> 
			<br /> 
	</jstl:forEach> 
	
	
			
	<spring:url var="createCommentUrl" value="report/note/comment/edit.do?noteId={notId}">
			<spring:param name="notId" value="${note.id}"/>
	</spring:url>
	
	<jstl:choose>
		<jstl:when  test="${canComment}">
			<a href="${createCommentUrl}">
				<spring:message code="comments.create" />			
			</a>
		</jstl:when>
		<jstl:otherwise>
			<spring:message code="comments.cannotCreate" />
		</jstl:otherwise>
	</jstl:choose>
	</br>
</security:authorize>