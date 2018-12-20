
package services;

import java.util.ArrayList;
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

import security.Authority;
import security.UserAccount;
import security.UserAccountService;
import utilities.AbstractTest;
import domain.Actor;
import domain.Admin;
import domain.Box;
import domain.Category;
import domain.Customer;
import domain.HandyWorker;
import domain.Message;
import domain.Priority;
import domain.Warranty;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class AdminServiceTest extends AbstractTest {

	@Autowired
	private AdminService		adminService;

	@Autowired
	private UserAccountService	userAccountService;

	@Autowired
	private WarrantyService		warrantyService;

	@Autowired
	private CategoryService		categoryService;

	@Autowired
	private HandyWorkerService	handyWorkerService;

	@Autowired
	private ActorService		actorService;

	@Autowired
	private MessageService		messageService;

	@Autowired
	private BoxService			boxService;


	@Test
	public void testNumberOfAdmin() {
		List<Admin> all;

		all = this.adminService.findAll();
		Assert.isTrue(all.size() == 1);
		super.authenticate(null);
	}

	@Test
	public void testSaveUserAccount() {

		UserAccount userAccount = new UserAccount();
		List<Authority> authorities = new ArrayList<>();
		Authority authority = new Authority();
		authority.setAuthority(Authority.HANDYWORKER);
		authorities.add(authority);
		userAccount.setAuthorities(authorities);
		userAccount.setIsNotLocked(true);
		userAccount.setPassword("password");
		userAccount.setUsername("usernameTestJ");

		UserAccount saved = this.userAccountService.save(userAccount);

		List<UserAccount> userAccounts = this.userAccountService.findAll();

		Assert.isTrue(userAccounts.contains(saved));
		super.authenticate(null);
	}

	@Test
	public void testSaveWarranty() {
		super.authenticate("admin");
		Warranty warranty = this.adminService.createWarranty("warrantyTest");
		Warranty saved = this.adminService.saveWarranty(warranty);

		List<Warranty> warranties = this.warrantyService.findAll();

		Assert.isTrue(warranties.contains(saved));

		super.authenticate(null);
	}

	// Warranty
	@Test
	public void testUpdateWarranty() {
		super.authenticate("admin");

		// Creamos una warranty (ya se ha comprobado que no da error en la
		// prueba anterior
		Warranty warranty = this.adminService.createWarranty("warrantyTest");
		Warranty saved = this.adminService.saveWarranty(warranty);
		List<String> laws = new ArrayList<String>();
		laws.add("law");
		List<String> terms = new ArrayList<String>();
		terms.add("term");

		this.adminService.updateWarranty(saved, "newTitle", laws, terms);

		List<Warranty> warranties = this.warrantyService.findAll();

		Assert.isTrue(warranties.contains(saved));
		Assert.isTrue(saved.getTitle().equals("newTitle"));
		Assert.isTrue(saved.getLaws().size() == 1);
		Assert.isTrue(saved.getTerms().size() == 1);
		Assert.isTrue(saved.getTerms().get(0).equals("term"));
		Assert.isTrue(saved.getLaws().get(0).equals("law"));

		super.authenticate(null);
	}

	@Test
	public void testWarrantyToFinalMode() {
		super.authenticate("admin");
		// Creamos una warranty (ya se ha comprobado que no da error en la
		// prueba anterior
		Warranty warranty = this.adminService.createWarranty("warrantyTest");
		Warranty saved = this.adminService.saveWarranty(warranty);

		this.adminService.warrantyToFinalMode(saved);

		List<Warranty> warranties = this.warrantyService.findAll();

		Assert.isTrue(warranties.contains(saved));
		Assert.isTrue(!saved.getIsDraftMode());

		super.authenticate(null);
	}

	@Test
	public void testListWarranty() {
		super.authenticate("admin");
		List<Warranty> warranties = this.adminService.listWarranty();
		Assert.isTrue(warranties.size() == 7);
		super.authenticate(null);
	}

	@Test
	public void testShowWarranties() {
		super.authenticate("admin");
		Map<String, Warranty> warranties = this.adminService.showWarranties();
		Assert.isTrue(warranties.keySet().size() == 7);
		super.authenticate(null);
	}

	@Test
	public void testDeleteWarranty() {
		super.authenticate("admin");
		Warranty warranty = this.adminService.createWarranty("warrantyTest");
		Warranty saved = this.adminService.saveWarranty(warranty);

		List<Warranty> warranties = this.warrantyService.findAll();

		Assert.isTrue(warranties.contains(saved));

		this.adminService.deleteWarranty(saved);

		warranties = this.warrantyService.findAll();

		Assert.isTrue(!warranties.contains(saved));

		super.authenticate(null);
	}

	// Category
	@Test
	public void testListCategory() {
		super.authenticate("admin");
		List<Category> categories = this.adminService.listCategory();
		Assert.isTrue(categories.size() == 4);
		super.authenticate(null);
	}

	@Test
	public void testShowCategories() {
		super.authenticate("admin");
		Map<String, Category> categories = this.adminService.showCategory();
		Assert.isTrue(categories.keySet().size() == 4);
		super.authenticate(null);
	}

	@Test
	public void testSaveCategory() { // Contains save and findAll
		super.authenticate("admin");
		List<Category> subCategories = new ArrayList<Category>();
		Category category = this.adminService.createCategory(subCategories, "myCategory");
		Category save = this.categoryService.save(category);

		List<Category> categories = this.categoryService.findAll();

		Assert.isTrue(categories.contains(save));
		super.authenticate(null);
	}

	@Test
	public void testDeleteCategory() {
		super.authenticate("admin");
		List<Category> subCategories = new ArrayList<Category>();
		Category category = this.adminService.createCategory(subCategories, "categoryTest");
		Category saved = this.categoryService.save(category);

		List<Category> categories = this.categoryService.findAll();

		Assert.isTrue(categories.contains(saved));

		this.adminService.deleteCategory(saved);

		categories = this.categoryService.findAll();

		Assert.isTrue(!categories.contains(saved));

		super.authenticate(null);
	}

	@Test
	public void testComputeStatistics() {
		this.authenticate("admin");

		Map<String, Double[]> result = new HashMap<String, Double[]>();

		result = this.adminService.computeStatistics();
		//Collection<List<Float>> values = 
		//for (Map.Entry<String, List<Float>> entry : result.entrySet()) {

		Double[] calculations1 = result.get("fixUpTaskPerUser");
		Double[] calculations2 = result.get("applicationPerFixUpTask");
		Double[] calculations3 = result.get("maxPricePerFixUpTask");
		Double[] calculations4 = result.get("priceOferredPerApplication");
		Double[] calculations5 = result.get("numberComplaintsPerFixUpTask");
		Double[] calculations6 = result.get("notesPerReferee");

		Double res1 = 0.875;
		Double res2 = 0.0;
		Double res3 = 4.0;
		Double res4 = 1.2686114456365274;

		Double res5 = 1.2857;
		Double res6 = 1.0;
		Double res7 = 2.0;
		Double res8 = 0.4517539533274045;

		Double res9 = 301.14285714285717;
		Double res10 = 20.0;
		Double res11 = 703.0;
		Double res12 = 225.93641872465454;

		Double res13 = 51.988888888888894;
		Double res14 = 4.5;
		Double res15 = 123.0;
		Double res16 = 52.432781188964945;

		Double res17 = 1.5714;
		Double res18 = 1.0;
		Double res19 = 2.0;
		Double res20 = 0.49487166037761543;

		Double res21 = 0.8;
		Double res22 = 0.0;
		Double res23 = 1.0;
		Double res24 = 0.4;

		Assert.isTrue(result.get("fixUpTaskPerUser")[0].equals(res1));
		Assert.isTrue(calculations1[1].equals(res2));
		Assert.isTrue(calculations1[2].equals(res3));
		Assert.isTrue(calculations1[3].equals(res4));

		Assert.isTrue(calculations2[0].equals(res5));
		Assert.isTrue(calculations2[1].equals(res6));
		Assert.isTrue(calculations2[2].equals(res7));
		Assert.isTrue(calculations2[3].equals(res8));

		Assert.isTrue(calculations3[0].equals(res9));
		Assert.isTrue(calculations3[1].equals(res10));
		Assert.isTrue(calculations3[2].equals(res11));
		Assert.isTrue(calculations3[3].equals(res12));

		Assert.isTrue(calculations4[0].equals(res13));
		Assert.isTrue(calculations4[1].equals(res14));
		Assert.isTrue(calculations4[2].equals(res15));
		Assert.isTrue(calculations4[3].equals(res16));

		Assert.isTrue(calculations5[0].equals(res17));
		Assert.isTrue(calculations5[1].equals(res18));
		Assert.isTrue(calculations5[2].equals(res19));
		Assert.isTrue(calculations5[3].equals(res20));

		Assert.isTrue(calculations6[0].equals(res21));
		Assert.isTrue(calculations6[1].equals(res22));
		Assert.isTrue(calculations6[2].equals(res23));
		Assert.isTrue(calculations6[3].equals(res24));

		this.authenticate(null);
	}

	@Test
	public void testFixUpTaskWithAComplaint() {
		this.authenticate("admin");
		Map<String, Double> result = new HashMap<String, Double>();

		result = this.adminService.computeStatisticsRatios();

		Double calculations1 = result.get("ratioPendingApplications");
		Double calculations2 = result.get("ratioAcceptedApplications");
		Double calculations3 = result.get("ratioRejectedApplications");
		Double calculations4 = result.get("ratioPendingElapsedApplications");
		Double calculations5 = result.get("fixUpTaskWithComplain");

		Double res1 = 55.555599212646484;
		Double res2 = 33.33330154418945;
		Double res3 = 11.111100196838379;
		Double res4 = 22.222200393676758;
		Double res5 = 100.0;

		Assert.isTrue(calculations1.equals(res1));
		Assert.isTrue(calculations2.equals(res2));
		Assert.isTrue(calculations3.equals(res3));
		Assert.isTrue(calculations4.equals(res4));
		Assert.isTrue(calculations5.equals(res5));
		this.authenticate(null);
	}

	@Test
	public void testTenPercentMoreApplicationsCustomers() {
		this.authenticate("admin");
		Map<String, List<Customer>> map = this.adminService.tenPercentMoreApplicationsCustomers();
		List<Customer> customers = map.get("customers10PercentMoreApplications");
		Assert.isTrue(customers.size() == 1);
		this.authenticate(null);
	}

	@Test
	public void testTop3Customer() {
		this.authenticate("admin");

		Map<String, List<Customer>> map = this.adminService.top3Customers();
		Assert.isTrue(map.get("customers10PercentMoreApplications").size() == 3);
		this.authenticate(null);
	}

	@Test
	public void testTop3HandyWorker() {
		this.authenticate("admin");

		Map<String, List<HandyWorker>> map = this.adminService.top3HandyWorker();
		Assert.isTrue(map.get("HandyWorkerTermsofComplainsOrdered").size() == 3);
		this.authenticate(null);
	}

	@Test
	public void testBroadcastMessageVersion2() {

		this.authenticate("davidAdmin");

		Admin admin = this.adminService.getAdminByUsername("davidAdmin");
		HandyWorker handyWorker = this.handyWorkerService.getHandyWorkerByUsername("PepeHW");

		//System.out.println(message);
		//System.out.println(saved);

		List<Actor> allActors = new ArrayList<>();
		allActors = this.actorService.findAll();

		Integer count = 0;
		for (Actor a : allActors) {

			Message message = this.messageService.create("sujetoPrueba", "BodyPrueba", Priority.LOW, a);
			Message saved = this.messageService.save(message);
			this.adminService.broadcastMessage(saved);

			Box received = this.boxService.getRecievedBoxByActor(a);
			List<Message> receivedMessages = received.getMessages();
			for (Message m : receivedMessages)
				if (m.getBody().equals(saved.getBody()) && m.getSubject().equals(saved.getSubject()) && m.getSender().equals(saved.getSender()) && m.getReceiver().equals(saved.getReceiver()))
					count = count + 1;

			Box spam = this.boxService.getSpamBoxByActor(a);
			List<Message> spamMessages = spam.getMessages();

			for (Message m : spamMessages)
				if (m.getBody().equals(saved.getBody()) && m.getSubject().equals(saved.getSubject()) && m.getSender().equals(saved.getSender()) && m.getReceiver().equals(saved.getReceiver()))
					count = count + 1;

		}
		System.out.println(count);
		System.out.println(allActors.size());

		Assert.isTrue(count == allActors.size());

		this.authenticate(null);
	}

	@Test
	public void testBanSuspiciousActor() {
		this.authenticate("admin");

		Actor actor = this.actorService.getActorByUsername("PepeHW");
		actor.setHasSpam(true);

		this.actorService.save(actor);

		this.adminService.banSuspiciousActor(actor);

		Assert.isTrue(!actor.getUserAccount().getIsNotLocked());

		this.authenticate(null);
	}

	@Test
	public void testUnBanSuspiciousActor() {
		this.authenticate("admin");

		Actor actor = this.actorService.getActorByUsername("PepeHW");
		actor.setHasSpam(true);

		this.actorService.save(actor);

		this.adminService.banSuspiciousActor(actor);

		Assert.isTrue(!actor.getUserAccount().getIsNotLocked());

		this.adminService.unBanSuspiciousActor(actor);

		Assert.isTrue(actor.getUserAccount().getIsNotLocked());

		this.authenticate(null);
	}
}
