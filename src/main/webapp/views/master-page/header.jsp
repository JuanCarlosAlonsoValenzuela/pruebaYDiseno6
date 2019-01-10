<%--
 * header.jsp
 *
 * Copyright (C) 2018 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page import="org.hibernate.engine.config.spi.ConfigurationService"%>
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<div>
	<a href="#"><img src="${imageURL}" height= 180px width= 700px alt="Acme-Handy-Worker Co., Inc." /></a>
</div>

	

<div>
	<ul id="jMenu">
		<!-- Do not forget the "fNiv" class for the first level links !! -->
		<security:authorize access="hasRole('ADMIN')">
			<li><a class="fNiv"><spring:message	code="master.page.administrator" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="suspicious/administrator/list.do"><spring:message code="master.page.administrator.banUnban" /></a></li>
					<li><a href="broadcast/administrator/send.do"><spring:message code="master.page.administrator.broadcast" /></a></li>	
					<li><a href="administrator/createAdmin.do"><spring:message code="master.page.administrator.createAdmin" /></a></li>					
					<li><a href="administrator/createReferee.do"><spring:message code="master.page.administrator.createReferee" /></a></li>			
					<li><a href="score/administrator/list.do"><spring:message code="master.page.administrator.computedscore" /></a></li>							
					<li><a href="words/administrator/list.do"><spring:message code="master.page.administrator.goodAndBadWordsList" /></a></li>	
					<li><a href="statistics/administrator/show.do"><spring:message code="master.page.administrator.showstaitsticspart1" /></a></li>			
					<li><a href="warranty/administrator/list.do"><spring:message code="master.page.administrator.listwarranty" /></a></li>
					<li><a href="category/administrator/list.do"><spring:message code="master.page.administrator.listCategory" /></a></li>
					<li><a href="configuration/administrator/list.do"><spring:message code="master.page.administrator.configuration" /></a></li>												
				</ul>
			</li>
			<li><a class="fNiv"><spring:message code="master.page.profile" /></a>
				<ul>
					<li><a href="authenticated/showProfile.do"><spring:message code="master.page.myProfile" /> </a></li>
					<li><a href="personalData/administrator/edit.do"><spring:message code="master.page.customer.editPersonalData" /></a></li>
					<li><a href="j_spring_security_logout"><spring:message code="master.page.logout" /> </a></li>	
				</ul>
			</li>
		</security:authorize>
		
		<security:authorize access="hasRole('CUSTOMER')">
			<li><a class="fNiv"><spring:message	code="master.page.customer" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="fixUpTask/customer/list.do"><spring:message code="master.page.customer.fixUpTask" /></a></li>
					<li><a href="endorsement/customer/list.do"><spring:message code="master.page.customer.listendorsment" /></a></li>									
					<li><a href="complaint/customer/list.do"><spring:message code="master.page.customer.listcomplaint" /></a></li>																
				</ul>
			</li>
			<li><a class="fNiv"><spring:message code="master.page.profile" /></a>
				<ul>
					<li><a href="authenticated/showProfile.do"><spring:message code="master.page.myProfile" /> </a></li>
					<li><a href="personalData/customer/edit.do"><spring:message code="master.page.customer.editPersonalData" /></a></li>
					<li><a href="j_spring_security_logout"><spring:message code="master.page.logout" /> </a></li>	
				</ul>
			</li>
		</security:authorize>
		
		<security:authorize access="hasRole('HANDYWORKER')">
			<li><a class="fNiv"><spring:message	code="master.page.handyworker" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="application/handyWorker/list.do"><spring:message code="master.page.handyworker.applications" /></a></li>
					<li><a href="endorsement/handyWorker/list.do"><spring:message code="master.page.handyworker.listendorsment" /></a></li>	
					<li><a href="fixUpTask/handyWorker/list.do"><spring:message code="master.page.handyworker.fixUpTask" /></a></li>								
					<li><a href="tutorial/handyWorker/listHandyTutorials.do"><spring:message code="master.page.handyworker.tutorials" /></a></li>							
				</ul>
			</li>
			<li><a class="fNiv"><spring:message code="master.page.profile" /></a>
				<ul>
					<li><a href="handyWorker/handyWorker/showProfile.do"><spring:message code="master.page.myProfile" /> </a></li>
					<li><a href="personalData/handyWorker/edit.do"><spring:message code="master.page.customer.editPersonalData" /></a></li>
					<li><a href="j_spring_security_logout"><spring:message code="master.page.logout" /> </a></li>	
				</ul>
			</li>
		</security:authorize>
		
		<security:authorize access="hasRole('REFEREE')">
			<li><a class="fNiv"><spring:message	code="master.page.referee" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="complaint/referee/list.do"><spring:message code="master.page.referee.selfassignedcomplaints" /></a></li>
					<li><a href="complaint/referee/listUnassigned.do"><spring:message code="master.page.referee.unassignedcomplaints" /></a></li>
				</ul>
			</li>
			<li><a class="fNiv"><spring:message code="master.page.profile" /></a>
				<ul>
					<li><a href="authenticated/showProfile.do"><spring:message code="master.page.myProfile" /> </a></li>
					<li><a href="personalData/referee/editReferee.do"><spring:message code="master.page.referee.editPersonalData" /></a></li>
					<li><a href="j_spring_security_logout"><spring:message code="master.page.logout" /> </a></li>	
				</ul>
			</li>
		</security:authorize>
		
		<security:authorize access="hasRole('SPONSOR')">
			<li><a class="fNiv"><spring:message	code="master.page.sponsor" /></a>
				<ul>
					<li><a href="sponsorship/sponsor/list.do"><spring:message code="master.page.sponsorships" /> </a></li>
														
				</ul>
			</li>
			<li><a class="fNiv"><spring:message code="master.page.profile" /></a>
				<ul>
					<li><a href="authenticated/showProfile.do"><spring:message code="master.page.myProfile" /> </a></li>
					<li><a href="j_spring_security_logout"><spring:message code="master.page.logout" /> </a></li>	
					<li><a href="personalData/sponsor/edit.do"><spring:message code="master.page.sponsor.editPersonalData" /></a></li>	
				</ul>
			</li>
		</security:authorize>
		
		<security:authorize access="isAnonymous()">
			<li><a class="fNiv" href="security/login.do"><spring:message code="master.page.login" /></a></li>
			<li><a class="fNiv"><spring:message	code="master.page.register" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="anonymous/createCustomer.do"><spring:message code="master.page.customer" /></a></li>
					<li><a href="anonymous/createSponsor.do"><spring:message code="master.page.sponsor" /></a></li>
					<li><a href="anonymous/createHandyWorker.do"><spring:message code="master.page.handyWorker" /></a></li>
				</ul>
			</li>
			<li><a class="fNiv" href="tutorial/anonymous/list.do"><spring:message code="master.page.tutorial" /></a></li>
		</security:authorize>
		
		<security:authorize access="isAuthenticated()">
			<li>
				<a class="fNiv"> 
					<spring:message code="master.page.mail" /> 
			        (<security:authentication property="principal.username" />)
				</a>
				<ul>
					<li class="arrow"></li>
					<li><a href="box/actor/list.do"><spring:message code="master.page.mailSystem" /> </a></li>
					<!-- <li><a href="profile/boxes.do"><spring:message code="master.page.profile.boxes" /></a></li>	-->
				</ul>
			</li>
			<li><a class="fNiv" href="tutorial/actor/list.do"><spring:message code="master.page.tutorial" /></a></li>	
		</security:authorize>
		
	</ul>
</div>

<div>
	<a href="?language=en">en</a> | <a href="?language=es">es</a>
</div>

