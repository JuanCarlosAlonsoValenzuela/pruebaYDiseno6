
package domain;

import java.util.Date;
import java.util.List;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Tutorial extends DomainEntity {

	private String				title;
	private Date				lastUpdate;
	private String				summary;
	private List<String>		pictures;

	private List<Section>		sections;
	private List<Sponsorship>	sponsorships;


	//Use of @OneToMany or @ManyToMany targeting an unmapped class: domain.Tutorial.section

	@ManyToMany
	public List<Sponsorship> getSponsorships() {
		return this.sponsorships;
	}

	public void setSponsorships(List<Sponsorship> sponsorships) {
		this.sponsorships = sponsorships;
	}

	@NotBlank
	public String getTitle() {
		return this.title;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

	@NotNull
	@Past
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getLastUpdate() {
		return this.lastUpdate;
	}

	public void setLastUpdate(final Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	@NotBlank
	public String getSummary() {
		return this.summary;
	}

	public void setSummary(final String sumary) {
		this.summary = sumary;
	}

	@ElementCollection
	public List<String> getPictures() {
		return this.pictures;
	}

	public void setPictures(final List<String> pictures) {
		this.pictures = pictures;
	}

	@NotNull
	@OneToMany(cascade = CascadeType.ALL)
	public List<Section> getSections() {
		return this.sections;
	}

	public void setSections(final List<Section> sections) {
		this.sections = sections;
	}
}
