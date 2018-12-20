
package services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Tutorial;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class TutorialServiceTest extends AbstractTest {

	@Autowired
	private TutorialService	tutorialService;


	@Test
	public void testCreateTutorial() throws ParseException {

		Tutorial tutorial = new Tutorial();
		Tutorial saved = new Tutorial();
		String dateInString = "2016-10-20";
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date date = formatter.parse(dateInString);

		tutorial = this.tutorialService.create("titulo", date, "sumario");

		saved = this.tutorialService.save(tutorial);

		Assert.isTrue(this.tutorialService.findAll().contains(saved));
	}
}
