
package domain;

import java.util.List;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.Valid;

@Entity
@Access(AccessType.PROPERTY)
public class Customer extends Endorser {

	private List<FixUpTask>	fixUpTasks;


	@Valid
	@OneToMany
	public List<FixUpTask> getFixUpTasks() {
		return this.fixUpTasks;
	}

	public void setFixUpTasks(final List<FixUpTask> fixUpTasks) {
		this.fixUpTasks = fixUpTasks;
	}

}
