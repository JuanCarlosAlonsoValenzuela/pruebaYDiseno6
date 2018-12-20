
package services;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import repositories.SectionRepository;
import domain.Section;

@Service
@Transactional
public class SectionService {

	@Autowired
	private SectionRepository	sectionRepository;

	@Autowired
	private HandyWorkerService	handyWorkerService;


	public Section create() {
		Section section = new Section();
		List<String> pictures = new ArrayList<String>();

		section.setSectionTitle("");
		section.setText("");
		section.setSectionPictures(pictures);

		section.setNumber(0);

		return section;
	}

	public Section create(String title, String text, Integer number) {
		Section section = new Section();
		List<String> pictures = new ArrayList<String>();

		section.setSectionTitle(title);
		section.setText(text);
		section.setSectionPictures(pictures);

		section.setNumber(number);

		return section;
	}

	public Section save(Section s) {

		return this.sectionRepository.save(s);
	}

	public Section save1(Section s) {
		s.setNumber(s.getId());
		return this.sectionRepository.save(s);
	}

	public List<Section> findAll() {

		return this.sectionRepository.findAll();
	}

	public Section findOne(Integer id) {

		return this.sectionRepository.findOne(id);
	}

	public void delete(Section s) {
		this.sectionRepository.delete(s);
	}

}
