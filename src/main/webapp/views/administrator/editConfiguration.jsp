<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>		

<security:authorize access="hasRole('ADMIN')">

<form:form action="configuration/administrator/save.do" modelAttribute="configuration">
		<!-- Hidden Attributes -->
		<form:hidden path="id"/>
		<form:hidden path="version" />
		<form:hidden path="spamWords" />
		<form:hidden path="iva21" />
		<form:hidden path="cardType"/>
		<form:hidden path="spainTelephoneCode"/>
		<form:hidden path="goodWords"/>
		<form:hidden path="badWords"/>
		
		<form:label path="finderResult">
			<spring:message code="configuration.finderResult" />	
		</form:label>
		<form:input path="finderResult"/>
		<form:errors cssClass="error" path="finderResult" />
		<br/>
		
		<form:label path="minFinderResults">
			<spring:message code="configuration.minFinderResults" />	
		</form:label>
		<form:input path="minFinderResults"/>
		<form:errors cssClass="error" path="minFinderResults" />
		<br/>
		
		<form:label path="maxFinderResults">
			<spring:message code="configuration.maxFinderResults" />	
		</form:label>
		<form:input path="maxFinderResults"/>
		<form:errors cssClass="error" path="maxFinderResults" />
		<br/>
		
		<form:label path="timeFinder">
			<spring:message code="configuration.timeFinder" />	
		</form:label>
		<form:input path="timeFinder"/>
		<form:errors cssClass="error" path="timeFinder" />
		<br/>
		
		<form:label path="minTimeFinder">
			<spring:message code="configuration.minTimeFinder" />	
		</form:label>
		<form:input path="minTimeFinder"/>
		<form:errors cssClass="error" path="minTimeFinder" />
		<br/>
		
		<form:label path="maxTimeFinder">
			<spring:message code="configuration.maxTimeFinder" />	
		</form:label>
		<form:input path="maxTimeFinder"/>
		<form:errors cssClass="error" path="maxTimeFinder" />
		<br/><br/>
		
		<input type="submit" name="save" value="<spring:message code="configuration.save.button"/>" />
		
		<input type="button" name="cancel" onclick="javascript:relativeRedir('configuration/administrator/list.do');"  value="<spring:message code="configuration.cancel.button"/>" />	
</form:form>

</security:authorize>