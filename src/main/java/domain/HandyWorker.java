
package domain;

import java.util.List;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class HandyWorker extends Endorser {

	private String				make;

	private List<Application>	applications;
	private Finder				finder;
	private List<Tutorial>		tutorials;
	private Curriculum			curriculum;


	@OneToMany(mappedBy = "handyWorker")
	@Valid
	public List<Application> getApplications() {
		return this.applications;
	}

	public void setApplications(final List<Application> applications) {
		this.applications = applications;
	}

	@OneToOne(cascade = CascadeType.ALL)
	@NotNull
	public Finder getFinder() {
		return this.finder;
	}

	public void setFinder(final Finder finder) {
		this.finder = finder;
	}

	@OneToMany
	@Valid
	public List<Tutorial> getTutorials() {
		return this.tutorials;
	}

	public void setTutorials(final List<Tutorial> tutorials) {
		this.tutorials = tutorials;
	}

	//Al ser el curriculum una composicion opcional 0..1
	//en lugar de One to Many como la mayoria de composiciones,
	//es necesario esta etiqueta.

	@OneToOne(optional = true, cascade = CascadeType.ALL)
	@Valid
	public Curriculum getCurriculum() {
		return this.curriculum;
	}

	public void setCurriculum(final Curriculum curriculum) {
		this.curriculum = curriculum;
	}

	@NotBlank
	public String getMake() {
		return this.make;
	}

	public void setMake(final String make) {
		this.make = make;
	}

}
