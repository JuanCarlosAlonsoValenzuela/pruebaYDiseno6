<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<p><spring:message code="anonymous.showHandyProfile" /></p>

<br/>

<table>
	<tr>
		<td><spring:message code="handy-worker.fullName" /></td> 
		<td><jstl:out value="${handy-worker.name} ${handy-worker.name} ${handy-worker.lastName}" /> </td>
	</tr>
	<tr>
		<td><spring:message code="handy-worker.email"/></td> 
		<td><jstl:out value="${handy-worker.email}" /> </td>
	</tr>
	<tr>
		<td><spring:message code="handy-worker.phoneNumber"/></td> 
		<td><jstl:out value="${handy-worker.phoneNumber}" /> </td>
	</tr>

</table>
<br/>

<p><spring:message code="anonymous.showHandyProfileTutorials" /></p>

<display:table
	pagesize="10" name="tutorials" id="row"
	class="displaytag" requestURI="handy-worker/showProfile.do">
	
	
	<security:authorize access="hasRole('HANDYWORKER')">
	<jstl:if test="visitor.tutorials.contains(tutorial)">
	<a href="tutorial/handy-worker/edit.do">
		<spring:message code="handy-worker.editTutorial" />	
	</a>
	<br/>
	</jstl:if>
	</security:authorize>
	
	
	<display:column property="title" titleKey="tutorial.title">
	</display:column>
	<display:column property="summary" titleKey="tutorial.summary">
	</display:column>
	<display:column titleKey="tutorial.lastUpdate">
		<jstl:out value="${row.lastUpdate}" />
	</display:column>		
	<display:column titleKey="tutorial.author">
			<strong>
			<a href="${row.authorProfileLink}">
				<jstl:out value="${row.author}" />
			</a>
		</strong>
	</display:column>
	<display:column titleKey="tutorial.sponsor">
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
	</display:column>
								
</display:table>
<br/>

<security:authorize access="hasRole('HANDYWORKER')">
<jstl:if test="visitor.id == handy-worker.id">
<a href="tutorial/handy-worker/add.do">
	<spring:message code="handy-worker.createTutorial" />	
</a>
<br/>
</jstl:if>
</security:authorize>

<br/>
<a href="tutorial/showAllTutorials.do">
	<spring:message code="anonymous.allTutorials" />	
</a>
