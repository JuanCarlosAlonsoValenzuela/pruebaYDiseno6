<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<p><spring:message code="anonymous.showTutorial" /></p>
<br/>

		<ol>
<jstl:forEach var="sponsor" items="tutorial.sponsors">
				<spring:url var="sponsorUrl" value="${sponsor.link}">
				</spring:url>
				<a href="${sponsorUrl}">
							<spring:message var ="sponsorBanner" code="sponsor.bannerUrl" />
							<jstl:out value="${sponsorBanner}" />		
				</a>
			</jstl:forEach>
		</ol>
<br/>

<table>
	<tr>
		<td><spring:message code="tutorial.title" /></td> 
		<td><jstl:out value="${tutorial.title}" /> </td>
	</tr>
	<tr>
		<td><spring:message code="tutorial.summary"/></td> 
		<td><jstl:out value="${tutorial.summary}" /> </td>
	</tr>
	<tr>
		<td><spring:message code="tutorial.pictures"/></td> 
		<jstl:forEach var="picture" items="tutorial.pictures">
		<td><img src="picture" alt="picture" width="300" height="300"> </td>
		</jstl:forEach>
	</tr>

</table>
<br/>


<display:table
	pagesize="10" name="sections" id="row"
	class="displaytag" requestURI="tutorial/showTutorial.do">
	
	<display:column property="number" titleKey="section.number">
	</display:column>
	<display:column property="title" titleKey="section.title">
	</display:column>
	<display:column property="summary" titleKey="section.text">
	</display:column>
	<display:column titleKey="section.pictures">
		<jstl:forEach var="picture" items="section.pictures">
			<img src="picture" alt="picture" width="300" height="300">
		</jstl:forEach>
	</display:column>
								
</display:table>

<br/>


<a href="handy-worker/showProfile.do">
	<spring:message code="anonymous.profile" />	
</a>
<br/>
<a href="tutorial/showAllTutorials.do">
	<spring:message code="anonymous.allTutorials" />	
</a>
<br/>
<jstl:if test="handy-worker.tutorials.contains(tutorial)">
<security:authorize access="hasRole('HANDYWORKER')">
<a href="tutorial/handy-worker/edit.do">
	<spring:message code="anonymous.editTutorial" />	
</a>
<br/>
</security:authorize>
</jstl:if>
<ol>
	<jstl:forEach var="sponsor" items="tutorial.sponsors">
		<spring:url var="sponsorUrl" value="${sponsor.link}">
		</spring:url>
		<a href="${sponsorUrl}">
			<spring:message var ="sponsorBanner" code="sponsor.bannerUrl" />
			<jstl:out value="${sponsorBanner}" />		
		</a>
	</jstl:forEach>
</ol>




    