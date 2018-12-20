<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<jstl:if test="${report.id == 0}">
<p><spring:message code="complaint.newReport" /></p>
</jstl:if>
<jstl:if test="${report.id != 0}">
<p><spring:message code="complaint.editReport" /></p>
</jstl:if>

<security:authorize access="hasRole('REFEREE')">

<form:form action="/complaint/customer/edit.do" modelAttribute="complaint">


		<!-- Hidden Attributes -->
		<form:hidden path="id"/>
		<form:hidden path="moment" />
		<form:hidden path="attachment" />
		<form:hidden path="notes" />
		
				
		<!-- Description -->
		<form:label path="description">	<!-- Tiles -->
			<spring:message code="report.description" />	
		</form:label>
		<form:textarea path="description"/>
		<form:errors cssClass="error" path="description" />
		
		
		<!--  Final Mode -->
		<form:select path="finalMode">
              <form:options
                  itemLabel=<spring:message code="report.true" />
                  itemValue="true"
              />
              <form:options
                  itemLabel=<spring:message code="report.false" />
                  itemValue="false"
              />
      </form:select>
      
      	
		<input type="submit" name="create" value="<spring:message code="report.create.button"/>" />	
		
		<input type="submit" <jstl:if test="${report.id == 0}"><jstl:out value="disabled='disabled'"/></jstl:if>
		 name="delete" value="<spring:message code="referee.delete" />" 
		onClick="return confirm('<spring:message code="referee.verificationDelete" />')">
			
		
		<input type="submit" name="cancel" value="<spring:message code="cancel.button"/>" />
		
		


</form:form>


</security:authorize>
