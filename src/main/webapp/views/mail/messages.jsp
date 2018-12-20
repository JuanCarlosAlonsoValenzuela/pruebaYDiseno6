<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<p><spring:message code="mail.myMessages" /></p>

<security:authorize access="isAuthenticated()">


<display:table
	pagesize="10" name="messages" id="row"
	requestURI="message/actor/list.do" >
	
	<!-- Date -->
	<display:column	property ="moment"
					titleKey="mail.message.moment"/>
	
	
	<display:column	titleKey="mail.message.subject">
	
		<spring:url var="showMessage" value="/message/authenticated/show.do?messageId={rowId}">
			<spring:param name="rowId" value="${row.id}"/>
		</spring:url>
	
		<a href="${showMessage}">
			<jstl:out value="${row.subject}" />
		</a>
	</display:column>
	
	<display:column	titleKey="mail.message.tags">	
		<jstl:if test="${row.tags.size() > 0}" >
				<jstl:set var="tagsSize" value="${row.tags.size()}" />
				<spring:url var="commentsUrl" value="/tag/actor/list.do?messageId={messageId}">
							<spring:param name="messageId" value="${row.id}"/>
				</spring:url>
				<a href="${tagsUrl}">
							<spring:message var ="viewTags" code="mail.message.viewTags" />	
							<jstl:out value="${viewTags}(${tagsSize})" />		
				</a>
		</jstl:if>
	</display:column>
							
	<display:column	titleKey="mail.message.sender">
		<jstl:out value="${row.sender.name}" />
	</display:column>	
	
	<display:column	titleKey="mail.message.receiver">
		<jstl:out value="${row.receiver.name}" />
	</display:column>
	
	<display:column property="priority" titleKey="mail.message.priority" />	
	
	<display:column>
		<spring:url var="deleteMessage" value="/message/authenticated/delete.do?messageId={rowId}">
			<spring:param name="rowId" value="${row.id}"/>
		</spring:url>
	
		<a href="${deleteMessage}" onclick="return confirm('<spring:message code="mail.delete" />')"><spring:message code="mail.message.delete"/></a>
	</display:column>	
															
</display:table>

<!-- Enlaces parte inferior -->
<spring:url var="newMessage" value="/message/actor/create.do"/>

<p><a href="${newMessage}"><spring:message code="mail.message.new" /></a></p>

<spring:url var="mail" value="/box/actor/list.do"/>

<p><a href="${mail}"><spring:message code="mail.back" /></a></p>

</security:authorize>