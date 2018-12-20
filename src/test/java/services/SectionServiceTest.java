
package services;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Section;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class SectionServiceTest extends AbstractTest {

	@Autowired
	private SectionService	sectionService;


	@Test
	public void testSaveSection() {
		this.authenticate("PepeHW");

		Section section = this.sectionService.create("Section", "text", 10);
		Section saved = this.sectionService.save(section);
		Section saved1 = this.sectionService.save1(saved);

		List<Section> sections = this.sectionService.findAll();
		Assert.isTrue(sections.contains(saved1));

		this.authenticate(null);
	}

}
