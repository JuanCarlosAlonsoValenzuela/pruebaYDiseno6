
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<p><spring:message code="administrator.wordList" /></p>

<security:authorize access="hasRole('ADMIN')">


<display:table name="badWords" id="badWordList" requestURI="${goodandbadwords/administrator/list.do}"
	pagesize="5" class="displaytag">
	  <display:column property="edit" titleKey="aministrator.edit">
      		<a href="goodandbadwords/administrator/edit.do?word=${badWordList}">
 	  			<spring:message code="administrator.edit" />
 	  		</a>
      </display:column>
      
       <display:column property="valueStatistics" titleKey="administrator.badWords" sortable="true">
      		<jstl:out value="${badWordList}" />
      </display:column>
</display:table>


<display:table name="godWords" id="goodWordList" requestURI="${goodandbadwords/administrator/list.do}"
	pagesize="5" class="displaytag">
	  <display:column property="edit" titleKey="aministrator.edit">
      		<a href="goodandbadwords/administrator/edit.do?word=${goodWordList}">
 	  			<spring:message code="administrator.edit" />
 	  		</a>
      </display:column>
      
       <display:column property="valueStatistics" titleKey="administrator.goodWords" sortable="true">
      		<jstl:out value="${goodWordList}" />
      </display:column>
</display:table>

<div>
	<a href="goodandbadwords/administrator/create.do">
		<spring:message code="administrator.edit" />
	</a>
</div>

<div>
	<a href="administrator/profile.do">
		<spring:message code="administrator.cancel" />
	</a>
</div>





</security:authorize>