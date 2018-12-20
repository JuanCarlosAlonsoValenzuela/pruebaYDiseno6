
package services;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Actor;
import domain.HandyWorker;
import domain.Tutorial;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class ActorServiceTest extends AbstractTest {

	// Service under test

	@Autowired
	private ActorService			actorService;

	@Autowired
	private ConfigurationService	configurationService;

	@Autowired
	private TutorialService			tutorialService;


	// TEST---------------------------------------------------------------------

	/*
	 * public void loggedAsActor() { UserAccount userAccount; userAccount =
	 * LoginService.getPrincipal();
	 * Assert.isTrue(userAccount.getAuthorities().size() > 0); }
	 */

	@Test
	public void testNumberOfActors() {
		Collection<Actor> all;
		super.authenticate("PacoCustomer");
		all = this.actorService.findAll();
		Assert.isTrue(all.size() == 15);
		super.authenticate(null);
	}

	@Test
	public void testActorByUsername() {
		Actor actor = new Actor();
		super.authenticate("PacoCustomer");
		actor = this.actorService.getActorByUsername("PacoCustomer");
		Assert.isTrue(actor.getName().equals("Paco"));
		super.authenticate(null);
	}

	@Test
	public void testShowTutorials() {
		super.authenticate("PacoCustomer");
		Assert.isTrue(this.actorService.showTutorials().size() == 2);
		super.authenticate(null);
	}

	@Test
	public void testNumberOfBoxes() {
		Actor actor = new Actor();
		super.authenticate("PacoCustomer");
		actor = this.actorService.getActorByUsername("PacoCustomer");
		Assert.isTrue(this.actorService.getlistOfBoxes(actor).size() == 4);
		super.authenticate(null);
	}

	@Test
	public void TestShowHandyWorkers() {
		// Actor actor = new Actor();
		Tutorial tutorial = new Tutorial();
		Map<HandyWorker, List<Tutorial>> map = new HashMap<HandyWorker, List<Tutorial>>();
		// List<Tutorial>>();

		// HandyWorker handy = new HandyWorker();
		// List<HandyWorker> listHandy = new ArrayList<HandyWorker>();

		super.authenticate("PacoCustomer");
		tutorial = this.tutorialService.findOne(1904);
		map = this.actorService.showHandyWorkers(tutorial);
		Assert.isTrue(!map.isEmpty());
		super.authenticate(null);
	}

	@Test
	public void testUpdateActorSpam() {
		Actor actor = new Actor();
		super.authenticate("PacoCustomer");
		actor = this.actorService.getActorByUsername("PacoCustomer");
		this.configurationService.isActorSuspicious(actor);
		Assert.isTrue(actor.getHasSpam() == false);
		super.authenticate(null);
	}
}
