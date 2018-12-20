<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<p><spring:message code="anonymous.showAlltutorials" /></p>

<display:table
	pagesize="10" name="tutorials" id="row"
	requestURI="tutorial/showAllTutorials.do">
	
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

#TODO
<a href="mainPage"><spring:message code="anonymous.mainPage" /></a>