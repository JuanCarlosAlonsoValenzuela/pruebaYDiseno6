<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<security:authorize access="hasRole('CUSTOMER')">		 	

	<display:table pagesize="5" name="phases" id="row" class="displaytag" 
					requestURI="phase/customer/list.do">
					
		<display:column property="title" titleKey="phase.title" />
		
		<display:column property="description" titleKey="phase.description" />
		
		<display:column property="startMoment" titleKey="phase.startMoment" format="{0,date,dd/MM/yyyy HH:mm}" />
			
		<display:column property="endMoment" titleKey="phase.endMoment" format="{0,date,dd/MM/yyyy HH:mm}" />
				
	</display:table>
	
	<input type="button" name="cancel" onclick="javascript:relativeRedir('fixUpTask/customer/list.do');"  value="<spring:message code="phase.back.button"/>" />	
	
</security:authorize>