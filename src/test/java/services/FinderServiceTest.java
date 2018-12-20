
package services;

import java.util.Collection;
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
import domain.Finder;
import domain.FixUpTask;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class FinderServiceTest extends AbstractTest {

	@Autowired
	private FinderService		finderService;
	@Autowired
	private FixUpTaskService	fixUpTaskService;


	@Test
	public void testSaveFinderNull() {
		Finder finder, saved;
		Collection<Finder> finders;

		finder = this.finderService.createFinder(null, null, null, null, null, null, null, null);
		saved = this.finderService.save(finder);
		finders = this.finderService.findAll();
		Assert.isTrue(finders.contains(saved));

	}

	@Test
	public void testSaveFinder2() {
		Finder finder, saved;
		Collection<Finder> finders;
		List<FixUpTask> fixUpTasks = this.fixUpTaskService.findAll();

		finder = this.finderService.createFinder("k", "c", "w", 1.0, 0.0, null, null, fixUpTasks);
		saved = this.finderService.save(finder);
		finders = this.finderService.findAll();
		Assert.isTrue(finders.contains(saved));

	}

	@Test
	public void testSaveFinder3() {
		Finder finder, saved;
		Collection<Finder> finders;
		List<FixUpTask> fixUpTasks = this.fixUpTaskService.findAll();

		Date starDate = new Date();
		Date endDate = new Date();
		starDate.setYear(20);
		endDate.setYear(19);

		finder = this.finderService.createFinder("k", "c", "w", 1.0, 0.0, starDate, endDate, fixUpTasks);
		saved = this.finderService.save(finder);
		finders = this.finderService.findAll();
		Assert.isTrue(finders.contains(saved));

	}
}
