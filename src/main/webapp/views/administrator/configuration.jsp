<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<security:authorize access="hasRole('ADMIN')">

<display:table pagesize="5" name="configuration" id="row" class="displaytag" 
					requestURI="configuration/administrator/list.do">
					
	<display:column property="finderResult" titleKey="configuration.finderResult" /> 
					
	<display:column property="minFinderResults" titleKey="configuration.minFinderResults" />
	
	<display:column property="maxFinderResults" titleKey="configuration.maxFinderResults" />
	
	<display:column property="timeFinder" titleKey="configuration.timeFinder" /> 
					
	<display:column property="minTimeFinder" titleKey="configuration.minTimeFinder" />
	
	<display:column property="maxTimeFinder" titleKey="configuration.maxTimeFinder" />
	
</display:table>

<input type="button" name="back" onclick="javascript:relativeRedir('configuration/administrator/edit.do');"  value="<spring:message code="configuration.edit.button"/>" />	

</security:authorize>
