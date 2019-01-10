<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<p><spring:message code="complaint.customer.create" /></p>	

<security:authorize access="hasRole('CUSTOMER')">

<form:form action="complaint/customer/create.do" modelAttribute="complaint">

		<!-- Hidden Attributes -->
		<form:hidden path="id"/>
		<form:hidden path="ticker" />
		<form:hidden path="moment" />
		
			
		<!-- Description -->
		<form:label path="description">	<!-- Tiles -->
			<spring:message code="fixUpTask.description" />	
		</form:label>
		<form:textarea path="description"/>
		<form:errors cssClass="error" path="description" />
		<br />
		<br />
		
			
		<!-- Attachments -->
		<%-- <form:label path="attachments">	<!-- Tiles Revisar -->
			<spring:message code="complaint.attachments" />	
		</form:label>
		<form:textarea path="attachments"/>
		<form:errors cssClass="error" path="attachments" /> --%>
		
		<input type="hidden" name="fix" value="${fix}" />
		
		<spring:message code="complaint.attachments" />:
		<br />
		<input type="hidden" name="newAttachments" value=""/>
		<textarea rows="12" cols="50" name="newAttachments" id="newAttachments" placeholder="http://www.aaa.com, http://www.bbb.com" ></textarea>
		<br />
		
		<input type="submit" name="save" value="<spring:message code="fixUpTask.create.button"/>" onclick="return confirm('<spring:message code="complaint.create.confirm" />')"/>	
		
		<input type="button"
		name="cancel"
		value="<spring:message code="customer.cancel"/>" onclick="javascript:relativeRedir('fixUpTask/customer/list.do');" />


</form:form>

</security:authorize>

