
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>


<br /> 
<display:table pagesize="5" name="${comments}" id="comment"
	requestURI="${requestURI}">
<display:column titleKey="curriculum.comment">
	<strong><jstl:out value="${comment}" /></strong>
	</display:column>
	
	
	
	
</display:table>

	<jstl:choose><jstl:when test="${canEdit}">
	
		<input type="button"
		name="cancel"
		value="<spring:message code="tutorial.comment.back"/>" onclick="javascript:relativeRedir('curriculum/handyWorker/show.do');" />
		</jstl:when><jstl:otherwise>
		
		<input type="button"
		name="cancel"
		value="<spring:message code="tutorial.comment.back"/>" onclick="javascript:relativeRedir('curriculum/anonymous/show.do?handyId=${handyId}');" />
		</jstl:otherwise>
		  </jstl:choose>
