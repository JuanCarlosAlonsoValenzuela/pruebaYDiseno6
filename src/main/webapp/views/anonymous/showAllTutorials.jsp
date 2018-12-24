<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<p><spring:message code="anonymous.showAlltutorials" /></p>

<display:table
	pagesize="5" name="tutorials" id="row"
	requestURI="tutorial/showAllTutorials.do">
	
	<display:column property="title" titleKey="tutorial.title">
	</display:column>
	<display:column property="summary" titleKey="tutorial.summary">
	</display:column>
	<security:authorize access="isAnonymous()">
	<display:column titleKey="tutorial.sections">
		<spring:url var="sectionsUrl" value="/section/anonymous/list.do?tutorialId={tutorialId}">
			<spring:param name="tutorialId" value="${row.id}"/>
		</spring:url>
		<a href="${sectionsUrl}">
			<spring:message code="tutorial.sections" />		
		</a>
	</display:column>
	</security:authorize>
		<security:authorize access="isAuthenticated()">
	<display:column titleKey="tutorial.sections">
		<spring:url var="sectionsUrl" value="/section/actor/list.do?tutorialId={tutorialId}">
			<spring:param name="tutorialId" value="${row.id}"/>
		</spring:url>
		<a href="${sectionsUrl}">
			<spring:message code="tutorial.sections" />		
		</a>
	</display:column>
	</security:authorize>
	<display:column property="lastUpdate" titleKey="tutorial.lastUpdate" format="{0,date,dd/MM/yyyy HH:mm}">
	</display:column>	
	<display:column titleKey="tutorial.author" value="${authors.get(tutorials.indexOf(row)).make}">
	</display:column>
	<display:column titleKey="tutorial.sponsorship">
		<a href="${sponsorships.get(tutorials.indexOf(row)).link}">
			<img src="${sponsorships.get(tutorials.indexOf(row)).bannerUrl}" alt="${sponsorships.get(tutorials.indexOf(row)).link}" style="width:50px;height:50px;border:0;">
		</a>	
	</display:column>
	<display:column titleKey="tutorial.pictures">
		<jstl:forEach  items="${row.pictures}" var="picture">
			<a href="${picture}">Picture ${row.pictures.indexOf(picture)+1}</a>
			<br />  
		</jstl:forEach>
	</display:column>
								
</display:table>
