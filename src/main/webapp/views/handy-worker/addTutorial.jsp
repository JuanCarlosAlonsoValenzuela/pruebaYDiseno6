<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<p><spring:message code="tutorial.addTutorial" /></p>			

<security:authorize access="hasRole('HANDYWORKER')">

<form:form action="tutorial/handyWorker/edit.do" modelAttribute="tutorial" >
	<form:hidden path="id"/>
	<form:hidden path="version" />
	<form:hidden path="lastUpdate"/>
	<form:hidden path="pictures" />	
	<form:hidden path="sections"/>
	<form:hidden path="sponsorships" />
	
		
	<form:label path="title" >
			<spring:message code="tutorial.title"  />:		
	</form:label>
	<form:input path="title" />
	<form:errors cssClass="error" path="title" />
	<br />
	<br />
	<form:label path="summary">
			<spring:message code="tutorial.summary" />:		
	</form:label>
	<br />
	<form:textarea rows="12" cols="50" path="summary"  />
	<form:errors cssClass="error" path="summary" />
	<br />
	<br />
	<spring:message code="tutorial.pictures" />:
	<br />
	<input type="hidden" name="newPictures" value=""/>
	<textarea rows="12" cols="50" name="newPictures" id="newPictures" placeholder="<spring:message code="tutorial.placePictures"/>" ><jstl:forEach items="${tutorial.pictures}" var="picture"><jstl:out value="${picture},"></jstl:out></jstl:forEach></textarea>
	<br />
	
	<jstl:choose><jstl:when test="${tutorial.id == 0}">
		<input type="submit" name="save" value="<spring:message code="tutorial.save"/>"/>
	</jstl:when><jstl:otherwise>
		<input type="submit" name="edit" value="<spring:message code="tutorial.edit"/>"/>
		<input type="submit" name="delete" value="<spring:message code="tutorial.delete"/>" onclick="return confirm('<spring:message code="tutorial.delete.confirm" />')"/>
	</jstl:otherwise>
	 </jstl:choose>
	 
	<input type="button" name="cancel" value="<spring:message code="tutorial.cancel" />"
		onClick="javascript:relativeRedir('tutorial/handyWorker/listHandyTutorials.do');" />
</form:form>

</security:authorize>