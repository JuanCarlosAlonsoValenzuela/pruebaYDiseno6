
package domain;

import java.util.List;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.validation.Valid;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Warranty extends DomainEntity {

	private String			title;
	private List<String>	terms;
	private List<String>	laws;
	private Boolean			isDraftMode;


	@NotBlank
	public String getTitle() {
		return this.title;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

	@Valid
	@ElementCollection(targetClass = String.class)
	public List<String> getTerms() {
		return this.terms;
	}

	public void setTerms(final List<String> terms) {
		this.terms = terms;
	}

	@Valid
	@ElementCollection(targetClass = String.class)
	public List<String> getLaws() {
		return this.laws;
	}

	public void setLaws(final List<String> laws) {
		this.laws = laws;
	}

	@Valid
	public Boolean getIsDraftMode() {
		return this.isDraftMode;
	}

	public void setIsDraftMode(Boolean isDraftMode) {
		this.isDraftMode = isDraftMode;
	}

}
