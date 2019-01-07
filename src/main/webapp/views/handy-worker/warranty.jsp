
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>


<security:authorize access="hasRole('HANDYWORKER')">


	<h2><jstl:out value="${warranty.title}" /></h2>

	<display:table pagesize="5" name="terms" id="row" class="displaytag" 
					requestURI="/warranty/handyWorker/list.do">
					
		<display:column titleKey="warranty.handyWorker.terms">
			<jstl:out value="${row}"/>
		</display:column>
		
	</display:table>
	
	<br/>
	
	<display:table pagesize="5" name="laws" id="row" class="displaytag" 
					requestURI="/warranty/handyWorker/list.do">
					
		<display:column titleKey="warranty.handyWorker.laws">
			<jstl:out value="${row}"/>
		</display:column>
		
	</display:table>
	
	<input type="button"
		name="cancel"
		value="<spring:message code="handyWorker.back"/>" onclick="javascript:relativeRedir('fixUpTask/handyWorker/list.do');" />
	
	</security:authorize>