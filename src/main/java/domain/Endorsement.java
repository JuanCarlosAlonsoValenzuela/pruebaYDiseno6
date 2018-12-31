
package domain;

import java.util.Date;
import java.util.List;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Endorsement extends DomainEntity {

	private Date			moment;
	private List<String>	comments;
	private Endorser		writtenBy;
	private Endorser		writtenTo;


	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	@Past
	@NotNull
	public Date getMoment() {
		return this.moment;
	}

	public void setMoment(final Date moment) {
		this.moment = moment;
	}

	@ElementCollection(targetClass = String.class)
	public List<String> getComments() {
		return this.comments;
	}

	public void setComments(final List<String> comments) {
		this.comments = comments;
	}

	@ManyToOne(optional = false)
	public Endorser getWrittenBy() {
		return this.writtenBy;
	}

	public void setWrittenBy(final Endorser writtenBy) {
		this.writtenBy = writtenBy;
	}

	@NotNull
	@ManyToOne(optional = false)
	public Endorser getWrittenTo() {
		return this.writtenTo;
	}

	public void setWrittenTo(final Endorser writtenTo) {
		this.writtenTo = writtenTo;
	}

}
