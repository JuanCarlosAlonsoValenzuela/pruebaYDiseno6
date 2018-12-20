
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<p><spring:message code="administrator.suspicious" /></p>

<security:authorize access="hasRole('ADMIN')">

<display:table name="suspiciousActor" id="actorList" requestURI="${admin/administrator/list.do}"
	pagesize="5" class="displaytag">

 	  <display:column>
 	  	<jstl:choose>
 	  	<jstl:when test="${actorList.userAccount.isNotLocked == false}">
 	  		<input type="submit" name="Ban" value="<spring:message code="administrator.ban" />"
 	  			onclick="return confirm('<spring:message code="administrator.verificationBan" />')">
 	  	</jstl:when>
 	  	
 	  	<jstl:when test="${actorList.userAccount.isNotLocked == true}">
 	  		<input type="submit" name="Ban" value="<spring:message code="administrator.unban" />"
 	  			onclick="return confirm('<spring:message code="administrator.verificationUnban" />')">
 	  	</jstl:when>
 	  	</jstl:choose>
 	  </display:column>
 	  
      <display:column property="actorName" title="<spring:message code="administrator.actorName" />" sortable="true">
      		<jstl:out value="${suspiciousActor.username}" />
      </display:column>
</display:table>

<input type="submit" name="cancel" value="<spring:message code="administrator.cancel" />"
		onClick="javascript: relativeRedir('administrator/profile.do');" />

</security:authorize>