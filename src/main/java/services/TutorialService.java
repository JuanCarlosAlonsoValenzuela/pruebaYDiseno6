
package services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import repositories.TutorialRepository;
import domain.Section;
import domain.Sponsorship;
import domain.Tutorial;

@Service
@Transactional
public class TutorialService {

	@Autowired
	private TutorialRepository	tutorialRepository;

	@Autowired
	private SponsorshipService	sponsorshipService;


	public Tutorial create() {
		Tutorial tutorial = new Tutorial();
		List<Section> sections = new ArrayList<Section>();
		List<Sponsorship> sponsorships = new ArrayList<Sponsorship>();

		tutorial.setLastUpdate(null);
		tutorial.setTitle("");
		tutorial.setSumary("");
		tutorial.setSections(sections);
		tutorial.setSponsorships(sponsorships);

		return tutorial;
	}

	public Tutorial create(String title, Date lastUpdate, String sumary) {
		Tutorial tutorial = new Tutorial();
		List<Section> sections = new ArrayList<Section>();
		List<Sponsorship> sponsorships = new ArrayList<Sponsorship>();

		sponsorships.addAll(this.sponsorshipService.findAll());

		tutorial.setLastUpdate(lastUpdate);
		tutorial.setTitle(title);
		tutorial.setSumary(sumary);
		tutorial.setSections(sections);
		tutorial.setSponsorships(sponsorships);

		return tutorial;
	}

	public Tutorial save(Tutorial tutorial) {
		return this.tutorialRepository.save(tutorial);
	}

	public List<Tutorial> findAll() {
		return this.tutorialRepository.findAll();
	}

	public void delete(Tutorial tutorial) {
		this.tutorialRepository.delete(tutorial);
	}

	public Tutorial findOne(int id) {
		return this.tutorialRepository.findOne(id);
	}

	public void deleteAll(List<Tutorial> tutorials) {
		this.tutorialRepository.deleteInBatch(tutorials);
	}
}
