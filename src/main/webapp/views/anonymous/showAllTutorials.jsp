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
	<display:column property="lastUpdate" titleKey="tutorial.lastUpdate" format="{0,date,dd/MM/yyyy HH:mm}">
	</display:column>	
	<display:column titleKey="tutorial.author" value="${authors.get(tutorials.indexOf(row)).make}">
	</display:column>
	<display:column titleKey="tutorial.sponsorship">
		<a href="${sponsorships.get(tutorials.indexOf(row)).link}">
			<img src="${sponsorships.get(tutorials.indexOf(row)).bannerUrl}" alt="${sponsorships.get(tutorials.indexOf(row)).link}" style="width:50px;height:50px;border:0;">
		</a>
		
	</display:column>
								
</display:table>
