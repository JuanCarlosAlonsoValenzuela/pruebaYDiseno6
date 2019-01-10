<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<br/>

<table>
	<tr>
		<td><spring:message code="socialProfile.fullName" /></td> 
		<td><jstl:out value="${curriculum.personalRecord.fullName}" /> </td>
	</tr>
	<tr>
		<td><spring:message code="socialProfile.email"/></td> 
		<td><jstl:out value="${curriculum.personalRecord.email}" /> </td>
	</tr>
	<tr>
		<td><spring:message code="socialProfile.phoneNumber"/></td> 
		<td><jstl:out value="${curriculum.personalRecord.phoneNumber}" /> </td>
		<jstl:if test="${canEdit}">
			<td>
				<spring:url var="editPersonalRecord" value="curriculum/handyWorker/editPersonalRecord.do">
					<spring:param name="personalRecordId" value="${curriculum.personalRecord.id}"/>
				</spring:url>	
				<a href="${editPersonalRecord}">
					<strong><spring:message code="curriculum.edit" /></strong>
				</a>
		</td>
		</jstl:if>
	</tr>
	<tr>
		<td><spring:message code="socialProfile.profileLink"/></td> 
		<td><a href="${curriculum.personalRecord.urlLinkedInProfile}" target="_blank" ><jstl:out value="${curriculum.personalRecord.urlLinkedInProfile}"/></a></td>
	</tr>
	<tr>
		<td><spring:message code="socialProfile.photo"/></td> 
		<td><img src="${curriculum.personalRecord.photo}" alt="${curriculum.personalRecord.photo}" style="width:100px;height:100px;border:0;"/> </td>
	</tr>

</table>
<jstl:choose>
	<jstl:when test="${canEdit}">
		<input type="button" name="cancel" value="<spring:message code="curriculum.back" />"
		onClick="javascript: relativeRedir('handyWorker/handyWorker/showProfile.do');" />
	</jstl:when><jstl:otherwise>
		<input type="button" name="cancel" value="<spring:message code="curriculum.back" />"
		onClick="javascript: relativeRedir('handyWorker/anonymous/showProfile.do?handyId=${handyId}');" />
	</jstl:otherwise>
 </jstl:choose>


<display:table
	pagesize="${curriculum.educationRecords.size()}" name="${curriculum.educationRecords}" id="row"
	requestURI="${requestURI}">
	
	<jstl:if test="${canEdit}">
		<display:column>
			<spring:url var="editEducationalRecord" value="curriculum/handyWorker/editEducationRecord.do">
				<spring:param name="educationRecordId" value="${row.id}"/>
			</spring:url>	
			<a href="${editEducationalRecord}">
				<strong><spring:message code="curriculum.edit" /></strong>
			</a>	
		</display:column>
	</jstl:if>
	
	<display:column property="title" titleKey="educationRecord.title">
	</display:column>
	<display:column titleKey="educationRecord.startDateStudy">
		<jstl:out value="${row.startDateStudy.getDate()}/${row.startDateStudy.getMonth()+1}/${row.startDateStudy.getYear()+1900}"></jstl:out>
	</display:column>
	<display:column titleKey="educationRecord.endDateStudy">
		<jstl:if test="${row.endDateStudy.getYear()+1900 != 1900}"><jstl:out value="${row.endDateStudy.getDate()}/${row.endDateStudy.getMonth()+1}/${row.endDateStudy.getYear()+1900}"/></jstl:if> 
	</display:column>
	<display:column property="institution" titleKey="educationRecord.institution">
	</display:column>
	<display:column titleKey="educationRecord.attachment">
		<jstl:if test="${!row.url.isEmpty()}"><a href="${row.url}" target="_blank"><spring:message code="educationRecord.attachment" /></a></jstl:if>
	</display:column>
	<display:column titleKey="note.comments">
		<jstl:set var="commentsSize" value="${row.comments.size()}" />
		<spring:url var="commentsUrl" value="curriculum/anonymous/showEducationComments.do">
			<spring:param name="educationRecordId" value="${row.id}"/>
			<spring:param name="handyId" value="${handyId}"/>
			<spring:param name="canEdit" value="${canEdit}"/>
		</spring:url>
		<a href="${commentsUrl}">
			<spring:message var ="viewComments1" code="note.viewComments" />
			<jstl:out value="${viewComments1}(${commentsSize})" />		
		</a>
	</display:column>
</display:table>
<jstl:if test="${canEdit}">
	<spring:url var="addEducationalRecord" value="curriculum/handyWorker/addEducationRecord.do"/>	
	<a href="${addEducationalRecord}">
		<strong><spring:message code="handyWorker.addEducationalRecord" /></strong>
	</a>	
</jstl:if>
<br/>
<br/>
<display:table
	pagesize="${curriculum.professionalRecords.size()}" name="${curriculum.professionalRecords}" id="row"
	requestURI="${requestURI}">
	
	<jstl:if test="${canEdit}">
		<display:column>
			<spring:url var="editProfessionalRecord" value="curriculum/handyWorker/editProfessionalRecord.do">
				<spring:param name="professionalRecordId" value="${row.id}"/>
			</spring:url>	
			<a href="${editProfessionalRecord}">
				<strong><spring:message code="curriculum.edit" /></strong>
			</a>	
		</display:column>
	</jstl:if>
	
	<display:column property="nameCompany" titleKey="professionalRecords.nameCompany">
	</display:column>
	<display:column titleKey="professionalRecords.startDate">
		<jstl:out value="${row.startDate.getDate()}/${row.startDate.getMonth()+1}/${row.startDate.getYear()+1900}"></jstl:out>
	</display:column>
	<display:column titleKey="professionalRecords.endDate">
		<jstl:if test="${row.endDate.getYear()+1900 != 1900}"><jstl:out value="${row.endDate.getDate()}/${row.endDate.getMonth()+1}/${row.endDate.getYear()+1900}"/></jstl:if>
	</display:column>
	<display:column property="role" titleKey="professionalRecords.role">
	</display:column>
	<display:column titleKey="professionalRecords.linkAttachment">
		<jstl:if test="${!row.linkAttachment.isEmpty()}"><a href="${row.linkAttachment}" target="_blank"><spring:message code="educationRecord.attachment" /></a></jstl:if>
	</display:column>
	<display:column titleKey="note.comments">
		<jstl:set var="commentsSize" value="${row.comments.size()}" />
		<spring:url var="commentsUrl" value="curriculum/anonymous/showProfessionalComments.do">
			<spring:param name="professionalRecordId" value="${row.id}"/>
			<spring:param name="handyId" value="${handyId}"/>
			<spring:param name="canEdit" value="${canEdit}"/>
		</spring:url>
		<a href="${commentsUrl}">
			<spring:message var ="viewComments1" code="note.viewComments" />
			<jstl:out value="${viewComments1}(${commentsSize})" />		
		</a>
	</display:column>
</display:table>
<jstl:if test="${canEdit}">
	<spring:url var="addProfessionalRecord" value="curriculum/handyWorker/addProfessionalRecord.do"/>	
	<a href="${addProfessionalRecord}">
		<strong><spring:message code="handyWorker.addProfessionalRecord" /></strong>
	</a>	
</jstl:if>
<br/>
<br/>
<display:table
	pagesize="${curriculum.endorserRecords.size()}" name="${curriculum.endorserRecords}" id="row"
	requestURI="${requestURI}">
	
	<jstl:if test="${canEdit}">
		<display:column>
			<spring:url var="editEndorserRecord" value="curriculum/handyWorker/editEndorserRecord.do">
				<spring:param name="endorserRecordId" value="${row.id}"/>
			</spring:url>	
			<a href="${editEndorserRecord}">
				<strong><spring:message code="curriculum.edit" /></strong>
			</a>	
		</display:column>
	</jstl:if>
	
	<display:column property="fullName" titleKey="endorserRecords.fullName">
	</display:column>
	<display:column property="email" titleKey="endorserRecords.email">
	</display:column>
	<display:column property="phoneNumber" titleKey="endorserRecords.phoneNumber">
	</display:column>
	<display:column titleKey="endorserRecords.linkLinkedInProfile">
		<a href="${row.linkLinkedInProfile}" target="_blank"><spring:message code="handyWorker.linkProfile" /></a>
	</display:column>
	<display:column titleKey="note.comments">
		<jstl:set var="commentsSize" value="${row.comments.size()}" />
		<spring:url var="commentsUrl" value="curriculum/anonymous/showEndorserComments.do">
			<spring:param name="endorserRecordId" value="${row.id}"/>
			<spring:param name="handyId" value="${handyId}"/>
			<spring:param name="canEdit" value="${canEdit}"/>
		</spring:url>
		<a href="${commentsUrl}">
			<spring:message var ="viewComments1" code="note.viewComments" />
			<jstl:out value="${viewComments1}(${commentsSize})" />		
		</a>
	</display:column>
</display:table>
<jstl:if test="${canEdit}">
	<spring:url var="addEndorserRecord" value="curriculum/handyWorker/addEndorserRecord.do"/>	
	<a href="${addEndorserRecord}">
		<strong><spring:message code="handyWorker.addEndorserRecord" /></strong>
	</a>	
</jstl:if>
<br/>
<br/>
<display:table
	pagesize="${curriculum.miscellaneousRecords.size()}" name="${curriculum.miscellaneousRecords}" id="row"
	requestURI="${requestURI}">
	
	<jstl:if test="${canEdit}">
		<display:column>
			<spring:url var="editMiscellaneousRecord" value="curriculum/handyWorker/editMiscellaneousRecord.do">
				<spring:param name="miscellaneousRecordId" value="${row.id}"/>
			</spring:url>	
			<a href="${editMiscellaneousRecord}">
				<strong><spring:message code="curriculum.edit" /></strong>
			</a>	
		</display:column>
	</jstl:if>
	
	<display:column property="title" titleKey="miscellaneousRecords.title">
	</display:column>
	<display:column titleKey="miscellaneousRecords.linkAttachment">
		<a href="${row.linkAttachment}" target="_blank"><spring:message code="educationRecord.attachment" /></a>
	</display:column>	
	<display:column titleKey="note.comments">
		<jstl:set var="commentsSize" value="${row.comments.size()}" />
		<spring:url var="commentsUrl" value="curriculum/anonymous/showMiscellaneousComments.do">
			<spring:param name="miscellaneousRecordId" value="${row.id}"/>
			<spring:param name="handyId" value="${handyId}"/>
			<spring:param name="canEdit" value="${canEdit}"/>
		</spring:url>
		<a href="${commentsUrl}">
			<spring:message var ="viewComments1" code="note.viewComments" />
			<jstl:out value="${viewComments1}(${commentsSize})" />		
		</a>
	</display:column>
</display:table>
<jstl:if test="${canEdit}">
	<spring:url var="addMiscellaneousRecord" value="curriculum/handyWorker/addMiscellaneousRecord.do"/>	
	<a href="${addMiscellaneousRecord}">
		<strong><spring:message code="handyWorker.addMiscellaneousRecord" /></strong>
	</a>	
</jstl:if>