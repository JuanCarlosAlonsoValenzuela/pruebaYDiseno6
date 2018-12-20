<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<p>
	<spring:message code="warranty.action.3" />
</p>

<security:authorize access="hasRole('ADMIN')">

	<display:table pagesize="5" name="terms" id="row"
		requestURI="warraty/terms/list.do">

		<display:column>
			<input type="submit"
				name="delete" value="<spring:message code="warranty.delete" />"
				onclick="return confirm('<spring:message code="term.verificationDelete" />')" />
		</display:column>

		<display:column property="terms" titlekey="terms.name">
			<jstl:out value="${terms}" />
		</display:column>
	</display:table>
	
<a href="warranty/term/create.do"><spring:message code="term.create" /></a>

</security:authorize>