<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<p><spring:message code="anonymous.showPictures" /> <jstl:out value="${tutorial.title}"></jstl:out></p>


<display:table pagesize="5" name="${pictures}" id="picture"
	requestURI="${requestURI}">
	<display:column titleKey="tutorial.picture">
		<a href="${picture.trim()}" target="_blank"><spring:message code="tutorial.picture" /> ${pictures.indexOf(picture)+1}</a>
	</display:column>
	
</display:table>

<input type="button"
		name="cancel"
		value="<spring:message code="handyWorker.cancel"/>" onclick="javascript:relativeRedir('tutorial/anonymous/list.do');" />
																						


