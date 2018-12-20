
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

@Entity
@Access(AccessType.PROPERTY)
public class Note extends DomainEntity {

	private Date			moment;
	private String			mandatoryComment;
	private List<String>	optionalComments;


	@Past
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	public Date getMoment() {
		return this.moment;

	}

	public void setMoment(final Date localDate) {
		this.moment = localDate;
	}

	@NotBlank
	public String getMandatoryComment() {
		return this.mandatoryComment;
	}

	public void setMandatoryComment(final String mandatoryComment) {
		this.mandatoryComment = mandatoryComment;
	}

	@Valid
	@ElementCollection(targetClass = String.class)
	public List<String> getOptionalComments() {
		return this.optionalComments;
	}

	public void setOptionalComments(final List<String> optionalComments) {
		this.optionalComments = optionalComments;
	}

}
