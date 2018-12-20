
package services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import repositories.SponsorshipRepository;
import domain.CreditCard;
import domain.Sponsorship;
import domain.Tutorial;

@Service
@Transactional
public class SponsorshipService {

	@Autowired
	private SponsorshipRepository	sponsorshipRepository;


	public Sponsorship create() {
		Sponsorship sporsorship = new Sponsorship();
		CreditCard creditCard = new CreditCard();
		Tutorial tutorial = new Tutorial();

		sporsorship.setCreditCard(creditCard);
		sporsorship.setBannerUrl("");
		sporsorship.setLink("");

		return sporsorship;
	}

	public Sponsorship create(String banner, String link, CreditCard creditCard) {
		Sponsorship sporsorship = new Sponsorship();

		sporsorship.setCreditCard(creditCard);
		sporsorship.setBannerUrl(banner);
		sporsorship.setLink(link);

		return sporsorship;
	}

	public Sponsorship save(Sponsorship s) {
		// this.sponsorService.loggedAsSponsor();
		return this.sponsorshipRepository.save(s);
	}

	public List<Sponsorship> findAll() {
		// this.sponsorService.loggedAsSponsor();
		return this.sponsorshipRepository.findAll();
	}

	public Sponsorship findOne(Integer id) {
		// this.sponsorService.loggedAsSponsor();
		return this.sponsorshipRepository.findOne(id);
	}

	public void delete(Sponsorship s) {
		// this.sponsorService.loggedAsSponsor();
		this.sponsorshipRepository.delete(s);
	}

}
