<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<p><spring:message code="handyWorker.endorsment" /></p>

<security:authorize access="hasRole('HANDYWORKER')">

<jstl:set var="loggedActor" value="${loggedActor}" /> 

<display:table name="endorsements" id="row" requestURI="${requestURI}"
	pagesize="5" class="displaytag">
	
 	  <display:column>
 	  	<a href="endorsement/handyWorker/edit.do?endorsementId=${row.id}">
 	  		<spring:message code="endorsment.addComment" />
 	  	</a>
 	  </display:column>

      <display:column property="moment" titleKey="endorsment.moment" sortable="true">
      		<jstl:out value="${row.moment}" />
      </display:column>

      <display:column titleKey="endorsment.writtenBy" sortable="true">
      		<jstl:out value="${row.writtenBy.name} ${row.writtenBy.middleName} ${row.writtenBy.surname}" />
      </display:column>
      <display:column titleKey="endorsment.score" sortable="true">
      		<jstl:out value="${row.writtenBy.score}" />
      </display:column>
      <display:column titleKey="endorsment.writtenTo" sortable="true">
      		<jstl:out value="${row.writtenTo.name} ${row.writtenTo.middleName} ${row.writtenTo.surname}" />
      </display:column>
      <display:column titleKey="endorsment.score" sortable="true">
      		<jstl:out value="${row.writtenTo.score}" />
      </display:column>
      
      <display:column titleKey="endorsment.comments">
				<jstl:set var="commentsSize" value="${row.comments.size()}" />
				<spring:url var="commentsUrl" value="endorsement/handyWorker/listComments.do">
							<spring:param name="endorsementId" value="${row.id}"/>
				</spring:url>
				<a href="${commentsUrl}">
							<spring:message var ="viewComments1" code="endorsment.viewComments" />
							<jstl:out value="${viewComments1}(${commentsSize})" />		
				</a>
		</display:column> 
</display:table>

<div>
	<a href="endorsement/handyWorker/create.do">
		<spring:message code="endorsment.create" />
	</a>
</div>

</security:authorize>

