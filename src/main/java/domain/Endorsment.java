
package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

@Entity
@Access(AccessType.PROPERTY)
public class Endorsment extends DomainEntity {

	private Date				moment;
	private Collection<String>	comments;
	private Endorser			writtenBy;
	private Endorser			writtenTo;


	@Temporal(TemporalType.TIMESTAMP)
	@Past
	@NotNull
	public Date getMoment() {
		return this.moment;
	}

	public void setMoment(final Date moment) {
		this.moment = moment;
	}

	@Valid
	@ElementCollection(targetClass = String.class)
	public Collection<String> getComments() {
		return this.comments;
	}

	public void setComments(final Collection<String> comments) {
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
