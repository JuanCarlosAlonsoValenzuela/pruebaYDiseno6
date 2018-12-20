
package domain;

import java.util.List;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Section extends DomainEntity {

	private String			sectionTitle;
	private String			text;
	private List<String>	sectionPictures;
	private int				number;


	@NotBlank
	public String getSectionTitle() {
		return this.sectionTitle;
	}

	public void setSectionTitle(final String sectionTitle) {
		this.sectionTitle = sectionTitle;
	}

	@NotBlank
	public String getText() {
		return this.text;
	}

	public void setText(final String text) {
		this.text = text;
	}

	@ElementCollection(targetClass = String.class)
	public List<String> getSectionPictures() {
		return this.sectionPictures;
	}

	public void setSectionPictures(final List<String> sectionPictures) {
		this.sectionPictures = sectionPictures;
	}

	@Min(0)
	@NotNull
	public int getNumber() {
		return this.number;
	}

	public void setNumber(final int number) {
		this.number = number;
	}

}
