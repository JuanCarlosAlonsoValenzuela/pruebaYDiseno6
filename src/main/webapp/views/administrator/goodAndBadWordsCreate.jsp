
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<p><spring:message code="administrator.GoodAndBadWords" /></p>

<security:authorize access="hasRole('ADMIN')">


<form:form modelAttribute="word" action="goodandbadwords/administrator/edit.do">

	<form:hidden path ="id"/>
	<form:hidden path ="version"/>
	
	<form:label path="word">
		<spring:message code="administrator.word" />
	</form:label>
	<form:input path="word" required/>
	<form:errors cssClass="error" path="word"/>

<jstl:choose>
	
	<jstl:when test="${word == null}">

	<input type="submit" name="goodWords" value="<spring:message code="administrator.createGood" />" />
	<input type="submit" name="badWords" value="<spring:message code="administrator.CreateBad" />" />
	
	</jstl:when>
	
	<jstl:otherwise>
	
	<input type="submit" name="editgoodWord" value="<spring:message code="administrator.editGood" />" />
	<input type="submit" name="editbadWord" value="<spring:message code="administrator.editBad" />" />
	<input type="submit" name="deleteWord" value="<spring:message code="administrator.deleteWord" />" />
	
	</jstl:otherwise>
	
</jstl:choose>

	<input type="submit" name="cancel" value="<spring:message code="administrator.cancel" />"
		onClick="javascript: relativeRedir('administrator/profile.do');" />



</form:form>



</security:authorize>