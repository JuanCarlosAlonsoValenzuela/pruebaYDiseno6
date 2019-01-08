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
		<br/>
		
		<form:label path="iva21">
			<spring:message code="configuration.vatPercentage" />	
		</form:label>
		<form:input path="iva21"/>
		<form:errors cssClass="error" path="iva21" />
		<br/>
		
		
		<form:label path="cardType">
			<spring:message code="configuration.cardTypes" />	
		</form:label>
		<form:input path="cardType"/>
		<form:errors cssClass="error" path="cardType" />
		<br/>
		
		<form:label path="spainTelephoneCode">
			<spring:message code="configuration.spainTelephoneCode" />	
		</form:label>
		<form:input path="spainTelephoneCode"/>
		<form:errors cssClass="error" path="spainTelephoneCode" />
		<br/>
		
		<form:label path="welcomeMessageEnglish">
			<spring:message code="configuration.welcomeMessageEnglish" />	
		</form:label>
		<form:input path="welcomeMessageEnglish"/>
		<form:errors cssClass="error" path="welcomeMessageEnglish" />
		<br/>
		
		<form:label path="welcomeMessageSpanish">
			<spring:message code="configuration.welcomeMessageSpanish" />	
		</form:label>
		<form:input path="welcomeMessageSpanish"/>
		<form:errors cssClass="error" path="welcomeMessageSpanish" />
		<br/>
	
		<form:label path="systemName">
			<spring:message code="configuration.systemName" />	
		</form:label>
		<form:input path="systemName"/>
		<form:errors cssClass="error" path="systemName" />
		<br/>
		
		<form:label path="imageURL">
			<spring:message code="configuration.imageURL" />	
		</form:label>
		<form:input path="imageURL"/>
		<form:errors cssClass="error" path="imageURL" />
		<br/><br/>
		
		<input type="submit" name="save" value="<spring:message code="configuration.save.button"/>" />
		
		<input type="button" name="cancel" onclick="javascript:relativeRedir('configuration/administrator/list.do');"  value="<spring:message code="configuration.cancel.button"/>" />	
</form:form>

</security:authorize>