
package services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Application;
import domain.Category;
import domain.Complaint;
import domain.CreditCard;
import domain.Customer;
import domain.Endorsement;
import domain.FixUpTask;
import domain.HandyWorker;
import domain.Note;
import domain.Phase;
import domain.Report;
import domain.Warranty;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class CustomerServiceTest extends AbstractTest {

	// Service under test

	@Autowired
	private CustomerService		customerService;

	@Autowired
	private FixUpTaskService	fixUpTaskService;

	@Autowired
	private ComplaintService	complaintService;

	@Autowired
	private ApplicationService	applicationService;

	@Autowired
	private ReportService		reportService;

	@Autowired
	private NoteService			noteService;

	@Autowired
	private EndorsementService	endorsmentService;

	@Autowired
	private HandyWorkerService	handyWorkerService;

	@Autowired
	private WarrantyService		warrantyService;


	// TEST---------------------------------------------------------------------

	@Test
	public void testCreateCustomer() {

		Customer c = this.customerService.create();

		c.setName("Roberto");
		c.setMiddleName("s");
		c.setSurname("Hermoso");
		c.setPhoto("https://trello.com/b/MD1aM3qn/proyecto-4-dp");
		c.setEmail("rhermoso98@gmail.com");
		c.setPhoneNumber("+34686310633");
		c.setAddress("C/Falsa 123");
		c.getUserAccount().setUsername("Quimi");
		c.getUserAccount().setPassword("12345");

		Integer before = this.customerService.findAll().size();
		this.customerService.save(c);

		Assert.isTrue(this.customerService.findAll().size() == before + 1);
	}

	// Testeado
	@Test
	public void testCreditCardNumber() {
		super.authenticate("PacoCustomer");
		Assert.isTrue(CustomerService.validateCreditCardNumber("4536000785192332"));
		super.authenticate(null);
	}

	// Testeado
	@Test
	public void testCreditCardNumberInvalid() {
		super.authenticate("PacoCustomer");
		Assert.isTrue(!CustomerService.validateCreditCardNumber("4536000785192337"));
		super.authenticate(null);
	}

	@Test
	public void testShowFixUpTaskLoggedCustomer() {
		super.authenticate("PacoCustomer");
		Customer customer = this.customerService.securityAndCustomer();
		Assert.isTrue(customer.getFixUpTasks().size() == 4);
		super.authenticate(null);
	}

	@Test
	public void testShowFixUpTaskPerCustomer() {
		super.authenticate("PacoCustomer");
		Customer customer = new Customer();
		customer = this.customerService.getCustomerByUsername("PacoCustomer");
		Assert.isTrue(this.customerService.showFixUpTasks(customer.getId()).size() == 4);
		super.authenticate(null);
	}

	@Test
	public void testGetFixUpTask() {
		super.authenticate("PacoCustomer");
		Customer customer = new Customer();
		FixUpTask fixUpTask = new FixUpTask();
		customer = this.customerService.getCustomerByUsername("PacoCustomer");
		fixUpTask = customer.getFixUpTasks().get(0);
		Assert.isTrue(this.customerService.getFixUpTask(fixUpTask.getId()).getDescription() == this.fixUpTaskService.findOne(fixUpTask.getId()).getDescription());
		super.authenticate(null);
	}

	// Tested
	@Test
	public void testCreateFixUpTask() {
		super.authenticate("PacoCustomer");

		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, 2018);
		cal.set(Calendar.MONTH, Calendar.DECEMBER);
		cal.set(Calendar.DAY_OF_MONTH, 12);
		Date realizationTime = cal.getTime();

		Warranty warranties = new Warranty();
		Warranty warranty = this.warrantyService.create("titulo", new ArrayList<String>(), new ArrayList<String>());
		Warranty warrantySaved = this.warrantyService.save(warranty);
		warranties = (this.warrantyService.findOne(warrantySaved.getId()));

		Category category = this.fixUpTaskService.findAll().get(0).getCategory();

		FixUpTask fix = this.fixUpTaskService.create("Description", "Direction", 5., realizationTime, warranties, new ArrayList<Phase>(), category, new ArrayList<Complaint>(), new ArrayList<Application>());
		FixUpTask fixSaved = this.fixUpTaskService.save(fix);

		Assert.notNull(this.fixUpTaskService.findOne(fixSaved.getId()));

		super.authenticate(null);
	}

	@Test
	public void testUpdateFixUpTask() {
		super.authenticate("PacoCustomer");
		Customer customer = new Customer();
		FixUpTask fixUpTask = new FixUpTask();
		customer = this.customerService.getCustomerByUsername("PacoCustomer");
		fixUpTask = customer.getFixUpTasks().get(0);

		FixUpTask res = this.customerService.getFixUpTask(fixUpTask.getId());
		res.setDescription("hola");
		FixUpTask saved = this.customerService.updateFixUpTask(res);
		Assert.isTrue(saved.getDescription().contains("hola"));
		super.authenticate(null);
	}

	@Test
	public void testDeleteFixUpTask() {
		super.authenticate("PacoCustomer");

		Customer loggedCustomer = this.customerService.securityAndCustomer();
		FixUpTask fixUpTask = loggedCustomer.getFixUpTasks().get(0);

		this.customerService.deleteFixUpTask(fixUpTask);
		Assert.isTrue(!this.fixUpTaskService.findAll().contains(fixUpTask));

		super.authenticate(null);
	}

	@Test
	public void testShowComplaints() {
		super.authenticate("PacoCustomer");
		Customer customer = this.customerService.securityAndCustomer();
		Assert.isTrue(this.customerService.showComplaints().size() != 11);
		super.authenticate(null);
	}

	@Test
	public void testGetComplaint() {

		Customer customer = new Customer();
		FixUpTask fixUpTask = new FixUpTask();
		customer = this.customerService.getCustomerByUsername("PacoCustomer");
		fixUpTask = customer.getFixUpTasks().get(0);
		List<Complaint> complaints = new ArrayList<Complaint>();
		Complaint complaint = new Complaint();

		complaints = (List<Complaint>) fixUpTask.getComplaints();
		complaint = complaints.get(0);

		super.authenticate("PacoCustomer");
		Assert.isTrue(this.customerService.getComplaint(complaint.getId()).getDescription() == this.complaintService.findOne(complaint.getId()).getDescription());
		super.authenticate(null);
	}

	@Test
	public void testCreateComplaint() {
		super.authenticate("PacoCustomer");

		Customer customer = new Customer();
		FixUpTask fixUpTask = new FixUpTask();
		customer = this.customerService.getCustomerByUsername("PacoCustomer");
		fixUpTask = customer.getFixUpTasks().get(0);

		FixUpTask res = this.customerService.getFixUpTask(fixUpTask.getId());

		Complaint complaint = new Complaint();

		complaint = this.customerService.createComplaint(res, "descripcionn", new ArrayList<String>());

		Assert.isTrue(this.complaintService.findOne(complaint.getId()).equals(complaint));
		super.authenticate(null);
	}

	@Test
	public void testShowApplications() {
		super.authenticate("PacoCustomer");
		Customer customer = this.customerService.securityAndCustomer();
		Assert.isTrue(this.customerService.showApplications().size() != 9);
		super.authenticate(null);
	}

	@Test
	public void testEditApplication() {
		super.authenticate("PacoCustomer");
		List<Application> la = (List<Application>) this.applicationService.findAll();
		Application res = la.get(1);
		CreditCard creditCard = new CreditCard();
		creditCard.setBrandName("VISA");
		creditCard.setHolderName("Paco");
		creditCard.setCvvCode(667);
		creditCard.setExpirationMonth(06);
		creditCard.setExpirationYear(2021);
		Long num = 4539234009047017L;
		creditCard.setNumber(num);
		Application saved = this.customerService.editApplication(res, creditCard);
		Application a = this.applicationService.save(saved);
		Assert.isTrue(creditCard.getNumber().equals(num));
		super.authenticate(null);
	}

	@Test
	public void testCreateNote() {
		super.authenticate("PacoCustomer");
		List<Report> lr = this.reportService.findAll();
		Report r = lr.get(0);
		Note note = this.customerService.createNote(r, "hello", new ArrayList<String>());
		Note save = this.noteService.save(note);
		List<Note> ln = this.noteService.findAll();
		Assert.isTrue(ln.contains(save));
		super.authenticate(null);
	}

	@Test
	public void testAddComent() {
		super.authenticate("PacoCustomer");

		List<Note> notes = new ArrayList<Note>();
		notes = this.noteService.findAll();

		Note n = this.noteService.findOne(notes.get(0).getId());
		int comment = n.getOptionalComments().size();
		Note res = this.customerService.addComent(n, "hello");
		int comment2 = res.getOptionalComments().size();
		Assert.isTrue(comment + 1 == comment2);
		super.authenticate(null);
	}

	@Test
	public void testShowEndorsments() {
		super.authenticate("PacoCustomer");
		Customer customer = this.customerService.securityAndCustomer();
		Assert.isTrue(this.customerService.showEndorsments().size() == 2);
		super.authenticate(null);
	}

	@Test
	public void testGetEndorsment() {
		super.authenticate("PacoCustomer");
		Customer customer = this.customerService.securityAndCustomer();
		List<Endorsement> le = (List<Endorsement>) this.endorsmentService.findAll();
		Endorsement e = le.get(0);
		Assert.isTrue(customer.getEndorsements().contains(e));
		super.authenticate(null);
	}

	@Test
	public void testCreateEndorsment() {
		super.authenticate("PacoCustomer");

		List<HandyWorker> handyWorkers = new ArrayList<HandyWorker>();
		handyWorkers = (List<HandyWorker>) this.handyWorkerService.findAll();
		HandyWorker hk = new HandyWorker();
		hk = this.handyWorkerService.findOne(handyWorkers.get(0).getId());

		Endorsement endo = new Endorsement();

		// int numberEndorsments = this.endorsmentService.findAll().size();
		endo = this.customerService.createEndorsment(new ArrayList<String>(), hk);

		// int numberEndorsments2 = this.endorsmentService.findAll().size();
		Assert.isTrue(this.endorsmentService.findAll().contains(endo));
		super.authenticate(null);
	}

	@Test
	public void testUpdateEndorsment() {
		super.authenticate("PacoCustomer");
		Customer customer = this.customerService.securityAndCustomer();
		List<Endorsement> le = (List<Endorsement>) this.endorsmentService.findAll();
		Endorsement res = le.get(0);
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, 2018);
		cal.set(Calendar.MONTH, Calendar.NOVEMBER);
		cal.set(Calendar.DAY_OF_MONTH, 12);
		Date startDate = cal.getTime();
		res.setMoment(startDate);
		Endorsement saved = this.customerService.updateEndorsment(res);

		Customer customer2 = this.customerService.securityAndCustomer();

		Assert.isTrue(customer2.getEndorsements().contains(saved));
		super.authenticate(null);
	}

	@Test
	public void testDeleteEndorsment() {
		super.authenticate("PacoCustomer");
		Customer customer = this.customerService.securityAndCustomer();
		List<Endorsement> le = (List<Endorsement>) this.endorsmentService.findAll();
		Endorsement res = le.get(0);
		this.customerService.deleteEndorsment(res);
		Assert.isTrue(customer.getEndorsements().contains(res));
		super.authenticate(null);
	}

	@Test
	public void testShowReport() {
		super.authenticate("PacoCustomer");
		List<Report> lr = this.reportService.findAll();
		Report res = lr.get(0);
		Assert.notNull(res);
		super.authenticate(null);
	}

	@Test
	public void testListReports() {
		super.authenticate("PacoCustomer");
		Customer customer = this.customerService.securityAndCustomer();
		List<Complaint> complaints = (List<Complaint>) this.customerService.showComplaints();
		Assert.notNull(this.customerService.listReports(complaints.get(0)));
		Assert.isTrue(this.customerService.listReports(complaints.get(0)).size() == 1);
		super.authenticate(null);
	}
}
