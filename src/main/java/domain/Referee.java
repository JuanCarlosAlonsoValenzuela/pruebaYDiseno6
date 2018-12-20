
package domain;

import java.util.List;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.Valid;

@Entity
@Access(AccessType.PROPERTY)
public class Referee extends Actor {

	private List<Report>	reports;


	@OneToMany
	@Valid
	public List<Report> getReports() {
		return this.reports;
	}

	public void setReports(final List<Report> reports) {
		this.reports = reports;
	}

}
