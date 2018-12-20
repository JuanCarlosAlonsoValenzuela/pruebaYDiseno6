
package services;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Application;
import domain.HandyWorker;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class ApplicationServiceTest extends AbstractTest {

	@Autowired
	private ApplicationService	applicationService;


	@Test
	public void testSaveApplication() {
		Application application, saved;
		List<Application> applications;
		List<String> comments = new ArrayList<String>();

		applications = (List<Application>) this.applicationService.findAll();
		application = applications.get(0);
		Assert.notEmpty(application.getComments());
		application.setComments(comments);
		Assert.isTrue(application.getComments().isEmpty());

		saved = this.applicationService.save(application);
		applications = (List<Application>) this.applicationService.findAll();
		Assert.isTrue(applications.contains(saved));
		Assert.isTrue(applications.get(0).getComments().isEmpty());

	}

	@Test
	public void testDeleteApplication() {
		Application application, saved;
		List<Application> applications;
		List<String> comments = new ArrayList<String>();

		applications = (List<Application>) this.applicationService.findAll();
		application = applications.get(0);

		Assert.isTrue(this.applicationService.getApplicationsFix(application.getFixUpTask()).contains(application));
		Assert.isTrue(this.applicationService.getApplicationsHandy(application.getHandyWorker()).contains(application));

		this.applicationService.delete(application);
		applications = (List<Application>) this.applicationService.findAll();

		Assert.isTrue(!applications.contains(application));
		Assert.isTrue(!this.applicationService.getApplicationsHandy(application.getHandyWorker()).contains(application));
		Assert.isTrue(!this.applicationService.getApplicationsFix(application.getFixUpTask()).contains(application));
	}
	@Test
	public void testDeleteAll() {

		List<Application> l = (List<Application>) this.applicationService.findAll();
		HandyWorker h = l.get(0).getHandyWorker();

		this.applicationService.deleteAllFromHandyWorker(h.getApplications());
		Assert.isTrue(h.getApplications().isEmpty());

	}
}
