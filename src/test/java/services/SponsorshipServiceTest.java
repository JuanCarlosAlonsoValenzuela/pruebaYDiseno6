
package services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.CreditCard;
import domain.Sponsorship;
import domain.Tutorial;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class SponsorshipServiceTest extends AbstractTest {

	@Autowired
	private SponsorshipService	sponsorshipService;

	@Autowired
	private TutorialService		tutorialService;


	@Test
	public void testCreateSponsorship() throws ParseException {

		Sponsorship sponsorship = new Sponsorship();
		Sponsorship savedSponsorship = new Sponsorship();

		super.authenticate("pepeSponsor");

		Long number = 4599997102987605L;
		CreditCard creditCard = new CreditCard();
		creditCard.setBrandName("Visa");
		creditCard.setCvvCode(405);
		creditCard.setExpirationMonth(02);
		creditCard.setExpirationYear(22);
		creditCard.setHolderName("Alejandro Gomez Caballero");
		creditCard.setNumber(number);

		Tutorial tutorial = new Tutorial();
		Tutorial savedTutorial = new Tutorial();
		String dateInString = "2016-10-20";
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date date = formatter.parse(dateInString);

		tutorial = this.tutorialService.create("titulo", date, "sumario");

		savedTutorial = this.tutorialService.save(tutorial);

		sponsorship = this.sponsorshipService.create("https://www.codepile.net/pile/Vd4vRNL2", "https://github.com/DaviidGilB/pruebaYDiseno4", creditCard);

		savedSponsorship = this.sponsorshipService.save(sponsorship);

		System.out.println(savedSponsorship);

		List<Sponsorship> lista = new ArrayList<Sponsorship>();

		lista.add(this.sponsorshipService.findOne(savedSponsorship.getId()));

		System.out.println(lista);

		Assert.isTrue(!lista.isEmpty());

		super.authenticate(null);
	}
}
