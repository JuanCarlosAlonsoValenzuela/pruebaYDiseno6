
package domain;

import java.util.Date;
import java.util.List;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

@Entity
@Access(AccessType.PROPERTY)
public class EducationRecord extends DomainEntity {

	private String			title;
	private Date			startDateStudy;
	private Date			endDateStudy;
	private String			institution;
	private String			url;
	private List<String>	comments;


	public EducationRecord() {		//Created for Json Purposes
		super();
	}

	@NotBlank
	public String getTitle() {
		return this.title;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

	@Temporal(TemporalType.DATE)
	@Past
	@NotNull
	public Date getStartDateStudy() {
		return this.startDateStudy;
	}

	public void setStartDateStudy(final Date startDateStudy) {
		this.startDateStudy = startDateStudy;
	}

	@Temporal(TemporalType.DATE)
	@Past
	public Date getEndDateStudy() {
		return this.endDateStudy;
	}

	public void setEndDateStudy(final Date endDateStudy) {
		this.endDateStudy = endDateStudy;
	}

	@NotBlank
	public String getInstitution() {
		return this.institution;
	}

	public void setInstitution(final String institution) {
		this.institution = institution;
	}

	@URL
	public String getUrl() {
		return this.url;
	}

	public void setUrl(final String url) {
		this.url = url;
	}

	@Valid
	@ElementCollection(targetClass = String.class)
	public List<String> getComments() {
		return this.comments;
	}

	public void setComments(final List<String> comments) {
		this.comments = comments;
	}

}
