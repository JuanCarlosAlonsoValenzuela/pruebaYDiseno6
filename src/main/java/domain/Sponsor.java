
package domain;

import java.util.List;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.Valid;

@Entity
@Access(AccessType.PROPERTY)
public class Sponsor extends Actor {

	private List<Sponsorship>	sponsorships;


	@Valid
	@OneToMany
	public List<Sponsorship> getSponsorships() {
		return this.sponsorships;
	}

	public void setSponsorships(final List<Sponsorship> sponsorships) {
		this.sponsorships = sponsorships;
	}

}
