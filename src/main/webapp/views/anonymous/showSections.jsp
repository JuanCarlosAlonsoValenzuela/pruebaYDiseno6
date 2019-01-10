<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<p><spring:message code="anonymous.showSections" /> <jstl:out value="${tutorial.title}"/> <spring:message code="anonymous.by" /> <jstl:out value="${author.make}"/></p>

<display:table
	pagesize="5" name="sections" id="row"
	class="displaytag" requestURI="${requestURI}">
	
	<jstl:if test="${canEdit}">
		<display:column>
			<spring:url var="editSectionUrl" value="section/handyWorker/edit.do">
				<spring:param name="tutorialId" value="${tutorial.id}"/>
				<spring:param name="sectionId" value="${row.id}"/>
			</spring:url>
			<a href="${editSectionUrl}">
				<spring:message code="section.edit" />			
			</a>
		</display:column>
	</jstl:if>
	
	<display:column property="number" titleKey="section.number" sortable="true">
	</display:column>
	<display:column property="sectionTitle" titleKey="section.sectionTitle" sortable="true">
	</display:column>
	<display:column property="text" titleKey="section.text">
	</display:column>

	<display:column titleKey="tutorial.pictures">
		<jstl:set var="picturesSize" value="${row.sectionPictures.size()}" />
		<spring:url var="picturesUrl" value="section/anonymous/showPictures.do">
			<spring:param name="sectionId" value="${row.id}"/>
		</spring:url>
		<a href="${picturesUrl}">
			<spring:message var ="viewPic" code="tutorial.picture" />
			<jstl:out value="${viewPic}(${picturesSize})" />		
		</a>
	</display:column>						
</display:table>
<jstl:if test="${canEdit}">
	<spring:url var="editSectionUrl" value="section/handyWorker/create.do">
		<spring:param name="tutorialId" value="${tutorial.id}"/>	
	</spring:url>
	<a href="${editSectionUrl}">
		<spring:message code="section.create" />			
	</a>
</jstl:if>
<br /> 
<br /> 

<jstl:if test="${canEdit}">
	<input type="button" name="cancel" value="<spring:message code="section.back" />"
	onClick="javascript: relativeRedir('tutorial/handyWorker/listHandyTutorials.do');" />
</jstl:if>

<br /> 
<br /> 
<jstl:if test="${!sections.isEmpty()}">
<spring:message code="tutorial.sponsorship" /><jstl:out value=":"/>
	<a href="${sponsorship.link}" target="_blank">
		<img src="${sponsorship.bannerUrl}" alt="${sponsorship.link}" style="width:50px;height:50px;border:0;">
	</a>
</jstl:if>






    