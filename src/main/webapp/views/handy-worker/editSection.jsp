<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<p><spring:message code="section.editSection" /></p>			

<security:authorize access="hasRole('HANDYWORKER')">

<form:form action="section/handyWorker/edit.do" modelAttribute="section" >
	<form:hidden path="id"/>
	<form:hidden path="version" />
	<input type="hidden" name="tutorialId" value="${tutorialId}"/>

	
	<form:label path="number" >
			<spring:message code="section.number"  />:		
	</form:label>
	<form:input type="number" min="1" path="number" />
	<form:errors cssClass="error" path="number" />
	<br/>
	<form:label path="sectionTitle" >
			<spring:message code="section.sectionTitle"  />:		
	</form:label>
	<form:input path="sectionTitle" />
	<form:errors cssClass="error" path="sectionTitle" />
	<br />
	<br />
	<form:label path="text">
			<spring:message code="section.text" />:		
	</form:label>
	<br />
	<form:textarea rows="12" cols="50" path="text"  />
	<form:errors cssClass="error" path="text" />
	<br />
	<br />
	<spring:message code="section.sectionPictures" />:
	<br />
	<input type="hidden" name="newPictures" value=""/>
	<textarea rows="12" cols="50" name="newPictures" id="newPictures" placeholder="<spring:message code="tutorial.placePictures"/>" ><jstl:forEach items="${pictures}" var="picture"><jstl:out value="${picture},"></jstl:out></jstl:forEach></textarea>
	<br />
	
	<jstl:choose><jstl:when test="${section.id == 0}">
		<input type="submit" name="save" value="<spring:message code="tutorial.save"/>"/>
	</jstl:when><jstl:otherwise>
		<input type="submit" name="edit" value="<spring:message code="tutorial.edit"/>"/>
		<input type="submit" name="delete" value="<spring:message code="tutorial.delete"/>" onclick="return confirm('<spring:message code="section.delete.confirm" />')"/>
	</jstl:otherwise>
	 </jstl:choose>
	 
	<input type="button" name="cancel" value="<spring:message code="tutorial.cancel" />"
		onClick="javascript:relativeRedir('section/handyWorker/list.do?tutorialId=${tutorialId}');" />
</form:form>

</security:authorize>