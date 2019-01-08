<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<p><spring:message code="sponsor.sponsorships" /></p>			

<security:authorize access="hasRole('SPONSOR')">


<display:table pagesize="5" name="sponsorships" id="row" class="displaytag" 
					requestURI="/sponsorship/sponsor/list.do">
					
	
      <display:column property="bannerUrl" titleKey="sponsor.bannerUrl"/>
      
       <display:column property="link" titleKey="sponsor.link"/>

      
      
					
</display:table>

</security:authorize>