<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>


<p><spring:message code="mail.writeMessage" /></p>	

<security:authorize access="isAuthenticated()">



<form:form modelAttribute="messageTest" action="message/actor/edit.do">

	<form:hidden path="id"/>
	<form:hidden path="version"/>
	<form:hidden path="moment"/>
	<form:hidden path="sender"/>


	<form:label path="subject">
		<spring:message code="mail.message.subject"/>:
	</form:label>
	<form:input path="subject" />
	
	<form:errors cssClass="error" path="subject"/>
	<jstl:if test="${messageTest.subject==''}">
 		 <a style="color:red"><spring:message code="mail.subjectNotBlank"/></a>
	</jstl:if>
	<br />
	
	

	<form:label path="receiver">
			<spring:message code="mail.message.receiver"/>:
	</form:label>
	<form:select path="receiver">
			<form:options items="${actors}" itemLabel="userAccount.username" itemValue="id"/>								 		
		</form:select>
		<form:errors cssClass="error" path="receiver"/>
	<br />
	
	<form:label path="tags">
		<spring:message code="mail.message.tags"/>:
	</form:label>
	<form:input path="tags" />
	<form:errors cssClass="error" path="tags"/>
	<br />
	
	<form:label path="priority">
		<spring:message code="mail.message.priority"/>:
	</form:label>
	<form:select path="priority">
	<form:options items="${priority}" />
	</form:select>
	<form:errors cssClass="error" path="priority"/>
	
	<br />

    	
	<form:label path="body">
		<spring:message code="mail.message"/>:
	</form:label>
	<form:textarea path="body" />
	<form:errors cssClass="error" path="body"/>
	<jstl:if test="${messageTest.body==''}">
 		 <a style="color:red"><spring:message code="mail.bodyNotBlank"/></a>
	</jstl:if>
	<br />
	
	
	
	<input type="submit" <jstl:if test="${messageTest.id != 0}"><jstl:out value="disabled='disabled'"/></jstl:if>
		name="save" value="<spring:message code="mail.message.send"/>" />
	
	
	<input type="submit" <jstl:if test="${messageTest.id == 0}"><jstl:out value="disabled='disabled'"/></jstl:if>
		 name="delete" value="<spring:message code="mail.message.delete" />" 
			onClick="return confirm('<spring:message code="mail.message.confirmation" />')">
			
	

</form:form>

<spring:url var="mail" value="/box/actor/list.do"/>
<p><a href="${mail}"><spring:message code="mail.cancel"/></a></p>

</security:authorize>