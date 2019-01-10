<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<p><spring:message code="admin.editPersonalData" /></p>


<script type="text/javascript">

  function phonenumberval() {
	  
  var phoneNumber;
  phoneNumber = document.getElementById("phoneNumber").value;

		
  var res = false;
 
  if (/(\+[0-9]{1,3})(\([0-9]{1,3}\))([0-9]{4,})$/.test(phoneNumber)) {
    res = true;
  }
  if (/(\+[0-9]{1,3})([0-9]{4,})$/.test(phoneNumber)) {
	res = true;
  }
  if(res == false) {
	  
    confirm("<spring:message code="anonymous.confirmationPhone" />");
  }
 
}
  </script>


<security:authorize access="hasRole('ADMIN')">
	<form:form modelAttribute="admin" action="personalData/administrator/edit.do">
    <!--Hidden Attributes -->
	<form:hidden path ="id"/>
	<form:hidden path ="version"/>
	<form:hidden path ="hasSpam"/>
	<form:hidden path ="boxes"/>
	<form:hidden path ="socialProfiles"/>
	<form:hidden path="userAccount" />
	
	<form:label path="name">
		<spring:message code="administrator.name" />
	</form:label>
	<form:input path="name" />
	<form:errors cssClass="error" path="name"/>
	<br />	
	
	<!-- Middle Name -->
	<form:label path="middleName">
		<spring:message code="administrator.middleName" />
	</form:label>
	<form:input path="middleName" />
	<form:errors cssClass="error" path="middleName"/>
	<br />

	<!-- Surname -->
	<form:label path="surname">
		<spring:message code="administrator.surname" />
	</form:label>
	<form:input path="surname" />
	<form:errors cssClass="error" path="surname"/>
	<br />
	
	<!-- Photo -->
	<form:label path="photo">
		<spring:message code="administrator.photo" />
	</form:label>
	<form:input path="photo"/>
	<form:errors cssClass="error" path="photo"/>
	<br />
	
	<!-- Email -->
	<form:label path="email">
		<spring:message code="administrator.email" />
	</form:label>
	<form:input path="email" />
	<form:errors cssClass="error" path="email"/>
	<br />
	
	<!-- Phone Number -->
	<form:label path="phoneNumber">
		<spring:message code="administrator.phoneNumber" />
	</form:label>
	<form:input path="phoneNumber"/>
	<form:errors cssClass="error" path="phoneNumber"/>
	<br />

	<!-- Address -->
	<form:label path="address">
		<spring:message code="administrator.address" />
	</form:label>
	<form:input path="address"/>
	<form:errors cssClass="error" path="address"/>
	<br />
	
	
	<input type="submit" name="save" value="<spring:message code="administrator.save" />" 
	onclick="phonenumberval()"/> 
	
	<a href="#" style="text-decoration: none;">
    	<input type="button" value="<spring:message code="administrator.cancel" />" />
	</a>
		

	</form:form>
</security:authorize>