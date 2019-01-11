
package services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import repositories.TutorialRepository;
import domain.HandyWorker;
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
		List<Sponsorship> sponsorships = this.sponsorshipService.findAll();
		List<String> pictures = new ArrayList<String>();
		Date date = new Date();
		date.setTime(date.getTime() - 1);

		tutorial.setLastUpdate(date);
		tutorial.setTitle("");
		tutorial.setSummary("");
		tutorial.setSections(sections);
		tutorial.setSponsorships(sponsorships);
		tutorial.setPictures(pictures);

		return tutorial;
	}
	public Tutorial create(String title, Date lastUpdate, String sumary) {
		Tutorial tutorial = new Tutorial();
		List<Section> sections = new ArrayList<Section>();
		List<Sponsorship> sponsorships = new ArrayList<Sponsorship>();
		List<String> pictures = new ArrayList<String>();

		sponsorships.addAll(this.sponsorshipService.findAll());

		tutorial.setLastUpdate(lastUpdate);
		tutorial.setTitle(title);
		tutorial.setSummary(sumary);
		tutorial.setSections(sections);
		tutorial.setSponsorships(sponsorships);
		tutorial.setPictures(pictures);

		return tutorial;
	}

	public Tutorial save(Tutorial tutorial) {
		Date date = new Date();
		date.setTime(date.getTime() - 1);
		tutorial.setLastUpdate(date);
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

	public HandyWorker getAuthor(Tutorial tutorial) {
		return this.tutorialRepository.getTutorialAuthor(tutorial.getId());
	}

	public Sponsorship getRandomSponsorShip(Tutorial tutorial) {
		List<Sponsorship> sponsorships = tutorial.getSponsorships();
		Random random = new Random();
		return sponsorships.get(random.nextInt(sponsorships.size()));
	}

	public List<HandyWorker> getAuthors(List<Tutorial> tutorials) {
		List<HandyWorker> result = new ArrayList<HandyWorker>();
		for (Tutorial t : tutorials) {
			result.add(this.tutorialRepository.getTutorialAuthor(t.getId()));
		}
		return result;
	}

	public List<Sponsorship> getRandomSponsorShips(List<Tutorial> tutorials) {
		List<Sponsorship> result = new ArrayList<Sponsorship>();
		Random random = new Random();
		for (Tutorial t : tutorials) {
			result.add(t.getSponsorships().get(random.nextInt(t.getSponsorships().size())));
		}
		return result;
	}

}
