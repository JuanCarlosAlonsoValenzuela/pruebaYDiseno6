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
	requestURI="${requestURI}">
	
	<jstl:if test="${canEdit}">
		<display:column >
			<spring:url var="editTutorialUrl" value="tutorial/handyWorker/edit.do">
				<spring:param name="tutorialId" value="${row.id}"/>
			</spring:url>
			<a href="${editTutorialUrl}">
				<spring:message code="tutorial.edit" />			
			</a>
		</display:column>
	</jstl:if>
	
	<display:column property="title" titleKey="tutorial.title" sortable="true">
	</display:column>
	
	<display:column property="summary" titleKey="tutorial.summary">
	</display:column>
	
	<security:authorize access="isAnonymous()">
		<display:column titleKey="tutorial.sections">
			<spring:url var="sectionsUrl" value="/section/anonymous/list.do">
				<spring:param name="tutorialId" value="${row.id}"/>
			</spring:url>
			<a href="${sectionsUrl}">
				<spring:message code="tutorial.sections" />		
			</a>
		</display:column>
	</security:authorize>
	
	<security:authorize access="isAuthenticated()">
		<jstl:choose>
			<jstl:when test="${tutorials.size() != 0 && username.equals(authors.get(tutorials.indexOf(row)).getUserAccount().getUsername())}">
				<display:column titleKey="tutorial.sections">
					<spring:url var="sectionsUrl" value="/section/handyWorker/list.do">
						<spring:param name="tutorialId" value="${row.id}"/>
					</spring:url>
					<a href="${sectionsUrl}">
						<spring:message code="tutorial.sections" />		
					</a>
				</display:column>
			</jstl:when>
			<jstl:otherwise>
				<display:column titleKey="tutorial.sections">
					<spring:url var="sectionsUrl" value="/section/anonymous/list.do">
						<spring:param name="tutorialId" value="${row.id}"/>
					</spring:url>
					<a href="${sectionsUrl}">
						<spring:message code="tutorial.sections" />		
					</a>
				</display:column>
			</jstl:otherwise>
		</jstl:choose>
	</security:authorize>
	
	<jstl:if test="${noAuthor}">	
		<security:authorize access="isAnonymous()">		
			<display:column titleKey="tutorial.author" sortable="true">
				<spring:url var="perfilUrl" value="/handyWorker/anonymous/showProfile.do">
					<spring:param name="handyId" value="${authors.get(tutorials.indexOf(row)).id}"/>
				</spring:url>
				<a href="${perfilUrl}">
					<jstl:set var ="make" value="${authors.get(tutorials.indexOf(row)).make}" />
					<jstl:out value="${make}" />		
				</a>
			</display:column>
		</security:authorize>
		<security:authorize access="isAuthenticated()">
			<jstl:choose>
				<jstl:when test="${tutorials.size() != 0 && username.equals(authors.get(tutorials.indexOf(row)).getUserAccount().getUsername())}">
					<display:column titleKey="tutorial.author" sortable="true">
						<spring:url var="perfilUrl" value="/handyWorker/handyWorker/showProfile.do"/>
						<a href="${perfilUrl}">
							<jstl:set var ="make" value="${authors.get(tutorials.indexOf(row)).make}" />
							<jstl:out value="${make}" />		
						</a>
					</display:column>
				</jstl:when>
				<jstl:otherwise>
					<display:column titleKey="tutorial.author" sortable="true">
						<spring:url var="perfilUrl" value="/handyWorker/anonymous/showProfile.do">
							<spring:param name="handyId" value="${authors.get(tutorials.indexOf(row)).id}"/>
						</spring:url>
						<a href="${perfilUrl}">
							<jstl:set var ="make" value="${authors.get(tutorials.indexOf(row)).make}" />
							<jstl:out value="${make}" />		
						</a>
					</display:column>
				</jstl:otherwise>
			</jstl:choose>
		</security:authorize>
	</jstl:if>
	
	
	<display:column property="lastUpdate" titleKey="tutorial.lastUpdate" format="{0,date,dd/MM/yyyy HH:mm}" sortable="true">
	</display:column>	
	
	<display:column titleKey="tutorial.pictures">
		<jstl:set var="picturesSize" value="${row.pictures.size()}" />
		<spring:url var="picturesUrl" value="tutorial/anonymous/showPictures.do">
			<spring:param name="tutorialId" value="${row.id}"/>
		</spring:url>
		<a href="${picturesUrl}">
			<spring:message var ="viewPic" code="tutorial.picture" />
			<jstl:out value="${viewPic}(${picturesSize})" />		
		</a>
	</display:column>
	
	<display:column titleKey="tutorial.sponsorship">
		<a href="${sponsorships.get(tutorials.indexOf(row)).link}" target="_blank">
			<img src="${sponsorships.get(tutorials.indexOf(row)).bannerUrl}" alt="${sponsorships.get(tutorials.indexOf(row)).link}" style="width:50px;height:50px;border:0;">
		</a>	
	</display:column>
								
</display:table>

<jstl:if test="${canEdit}">
	<spring:url var="createTutorialUrl" value="tutorial/handyWorker/create.do">	
	</spring:url>
	<a href="${createTutorialUrl}">
		<spring:message code="tutorial.create" />			
	</a>
</jstl:if>
