
package services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
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
import domain.Application;
import domain.Complaint;
import domain.Customer;
import domain.Endorsement;
import domain.Endorser;
import domain.Finder;
import domain.FixUpTask;
import domain.HandyWorker;
import domain.Note;
import domain.Phase;
import domain.Report;
import domain.Tutorial;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class HandyWorkerServiceTest extends AbstractTest {

	//Service under test

	@Autowired
	private HandyWorkerService		handyWorkerService;
	@Autowired
	private CustomerService			customerService;
	@Autowired
	private ActorService			actorService;
	@Autowired
	private FixUpTaskService		fixUpTaskService;
	@Autowired
	private PhaseService			phaseService;
	@Autowired
	private NoteService				noteServce;
	@Autowired
	private ReportService			reportService;
	@Autowired
	private ComplaintService		complaintService;
	@Autowired
	private TutorialService			tutoralService;
	@Autowired
	private EndorsementService		endorsmentService;
	@Autowired
	private EndorserService			endorserService;
	@Autowired
	private CurriculumService		curriculumService;
	@Autowired
	private PersonalRecordService	personalRecordService;


	@Test
	public void testCreateHandyWorkerService() {

		HandyWorker h = new HandyWorker();

		h = this.handyWorkerService.createHandyWorker();

		h.setName("Roberto");
		h.setMiddleName("s");
		h.setSurname("Hermoso");
		h.setPhoto("https://trello.com/b/MD1aM3qn/proyecto-4-dp");
		h.setEmail("rhermoso98@gmail.com");
		h.setPhoneNumber("+34686310633");
		h.setAddress("C/Falsa 123");
		h.getUserAccount().setUsername("Quimi");
		h.getUserAccount().setPassword("12345");
		h.setCurriculum(null);

		HandyWorker saved = new HandyWorker();
		saved = this.handyWorkerService.saveCreate(h);
		Assert.isTrue(this.handyWorkerService.findAll().contains(saved));
	}

	/*
	 * @Test
	 * public void addCurriculum() {
	 * Actor h = new Actor();
	 * h = this.actorService.getActorByUsername("Pepe2HW");
	 * super.authenticate("Pepe2HW");
	 * 
	 * HandyWorker handyWorker = new HandyWorker();
	 * handyWorker = this.handyWorkerService.getHandyWorkerByUsername("Pepe2HW");
	 * 
	 * Assert.isNull(handyWorker.getCurriculum());
	 * 
	 * List<EndorserRecord> endorserRecords = new ArrayList<>();
	 * List<MiscellaneousRecord> miscellaneousRecords = new ArrayList<>();
	 * List<EducationRecord> educationRecords = new ArrayList<>();
	 * List<ProfessionalRecord> professionalRecords = new ArrayList<>();
	 * PersonalRecord personalRecord = this.personalRecordService.create("Prueba", "https://trello.com/b/MD1aM3qn/proyecto-4-dp", "rhermoso98@gmail.com", "+34686310633", "https://trello.com/b/MD1aM3qn/proyecto-4-dp");
	 * Curriculum curriculum = this.handyWorkerService.addCurriculum(personalRecord, professionalRecords, educationRecords, miscellaneousRecords, endorserRecords);
	 * 
	 * Assert.notNull(handyWorker.getCurriculum());
	 * }
	 */

	/*
	 * @Test
	 * public void testEditCurriculum() {
	 * Actor h = new Actor();
	 * h = this.actorService.getActorByUsername("handyWorker1");
	 * super.authenticate("handyWorker1");
	 * 
	 * HandyWorker handyWorker = new HandyWorker();
	 * handyWorker = this.handyWorkerService.getHandyWorkerByUsername("handyWorker1");
	 * 
	 * Curriculum curriculum = handyWorker.getCurriculum();
	 * PersonalRecord personalRecord = curriculum.getPersonalRecord();
	 * personalRecord.setEmail("rhermoso98@gmail.com");
	 * 
	 * this.handyWorkerService.editCurriculum(curriculum, personalRecord, curriculum.getProfessionalRecords(), curriculum.getEducationRecords(), curriculum.getMiscellaneousRecords(), curriculum.getEndorserRecords());
	 * 
	 * Assert.isTrue(handyWorker.getCurriculum().getPersonalRecord().getEmail().equals("rhermoso98@gmail.com"));
	 * }
	 */
	//11.1
	@Test
	public void testShowFixUpTask() {
		Actor h = new Actor();
		h = this.actorService.getActorByUsername("handyWorker1");
		super.authenticate("handyWorker1");

		Collection<FixUpTask> fResults = this.handyWorkerService.showFixUpTasks();
		Collection<FixUpTask> f = this.fixUpTaskService.findAll();

		Assert.isTrue(f.equals(fResults));
		super.unauthenticate();
	}

	@Test
	public void testgetFixUpTaskPerCustomer() {
		Actor h = new Actor();
		h = this.actorService.getActorByUsername("handyWorker2");
		Customer customer = new Customer();
		super.authenticate("handyWorker2");

		customer = this.customerService.getCustomerByUserName("customer1");

		Map<Customer, Collection<FixUpTask>> m = this.handyWorkerService.getFixUpTaskPerCustomer(customer.getFixUpTasks().get(0).getId());

		Assert.isTrue(m.keySet().contains(customer) && m.get(customer).size() == customer.getFixUpTasks().size());
		super.unauthenticate();
	}

	//11.2

	@Test
	public void testFilterFixUpTasksByFinder() {
		Actor actor = new Actor();
		actor = this.actorService.getActorByUsername("handyWorker1");
		super.authenticate("handyWorker1");

		HandyWorker h = this.handyWorkerService.findOne(actor.getId());

		List<FixUpTask> fixUpTasksBeforeFinder = h.getFinder().getFixUpTasks();

		this.handyWorkerService.filterFixUpTasksByFinder();

		List<FixUpTask> fixUpTasksBeforeAfter = h.getFinder().getFixUpTasks();

		Assert.isTrue(!(fixUpTasksBeforeFinder.equals(fixUpTasksBeforeAfter)));
		super.unauthenticate();
	}

	//11.3
	@Test
	public void testShowApplication() {
		Actor actor = new Actor();
		actor = this.actorService.getActorByUsername("handyWorker1");
		super.authenticate("handyWorker1");

		HandyWorker h = this.handyWorkerService.findOne(actor.getId());

		Collection<Application> aResults = this.handyWorkerService.showApplications();
		Collection<Application> a = h.getApplications();

		Assert.isTrue(aResults.containsAll(a) && aResults.size() == a.size());
		super.unauthenticate();
	}

	@Test
	public void testCreateApplicationHandyWorker() {
		Actor actor = new Actor();
		actor = this.actorService.getActorByUsername("handyWorker1");
		super.authenticate("handyWorker1");

		HandyWorker h = new HandyWorker();
		h = this.handyWorkerService.getHandyWorkerByUsername("handyWorker1");

		List<String> comments = new ArrayList<String>();
		FixUpTask fixUpTask = h.getApplications().get(0).getFixUpTask();
		Application application = this.handyWorkerService.createApplicationHandyWorker(4.0, comments, fixUpTask);

		Assert.isTrue(fixUpTask.getApplications().contains(application) && h.getApplications().contains(application));
		super.unauthenticate();
	}
	//11.4
	@Test
	public void testShowPhasesForHandyWorker() {
		Actor actor = new Actor();
		actor = this.actorService.getActorByUsername("handyWorker1");
		super.authenticate("handyWorker1");

		HandyWorker h = new HandyWorker();
		h = this.handyWorkerService.getHandyWorkerByUsername("handyWorker1");

		FixUpTask fixUpTask = h.getApplications().get(0).getFixUpTask();
		Collection<Phase> phasesF = fixUpTask.getPhases();

		Collection<Phase> phasesResult = this.handyWorkerService.showPhaseForHandyWorker(fixUpTask);

		Assert.isTrue(phasesF.containsAll(phasesResult));
		super.unauthenticate();
	}

	@Test
	public void testCreatePhasesForApplicaion() {
		Actor actor = new Actor();
		actor = this.actorService.getActorByUsername("handyWorker1");
		super.authenticate("handyWorker1");

		HandyWorker h = new HandyWorker();
		h = this.handyWorkerService.getHandyWorkerByUsername("handyWorker1");

		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, 2018);
		cal.set(Calendar.MONTH, Calendar.DECEMBER);
		cal.set(Calendar.DAY_OF_MONTH, 12);
		Date startDate = cal.getTime();

		cal.set(Calendar.YEAR, 2019);
		cal.set(Calendar.MONTH, Calendar.NOVEMBER);
		cal.set(Calendar.DAY_OF_MONTH, 13);
		Date endDate = cal.getTime();

		Application app = h.getApplications().get(0);
		FixUpTask fixUpTask = app.getFixUpTask();

		Phase newPhase = this.phaseService.create("Phase1", "Description", startDate, endDate);

		Assert.notNull(newPhase);
		Assert.notNull(app);
		Assert.notNull(fixUpTask);
		Assert.isTrue(!this.handyWorkerService.getPhasesByApplication(app).contains(newPhase));

		Integer size = this.handyWorkerService.getPhasesByApplication(app).size();
		this.handyWorkerService.createPhaseForApplication(app, newPhase);

		Assert.isTrue(this.handyWorkerService.getPhasesByApplication(app).size() == size + 1);
		super.unauthenticate();
	}
	@Test
	public void testDeletePhaseForApplication() {
		Actor actor = new Actor();
		actor = this.actorService.getActorByUsername("handyWorker1");
		super.authenticate("handyWorker1");

		HandyWorker logguedHandyWorker = new HandyWorker();
		logguedHandyWorker = this.handyWorkerService.getHandyWorkerByUsername("handyWorker1");

		FixUpTask fixUpTaskBeforeDelePhase = this.handyWorkerService.getFixUpTaskByHandyWorker(logguedHandyWorker).get(0);

		List<Phase> phases = (List<Phase>) fixUpTaskBeforeDelePhase.getPhases();
		Phase phase = phases.get(0);

		Assert.isTrue((fixUpTaskBeforeDelePhase.getPhases().contains(phase)));

		this.handyWorkerService.deletePhaseForApplication(phase.getId());

		FixUpTask fixUpTaskAfterDelePhase = this.fixUpTaskService.findOne(fixUpTaskBeforeDelePhase.getId());

		Assert.isTrue(!(fixUpTaskAfterDelePhase.getPhases().contains(phase)));
		super.unauthenticate();
	}

	@Test
	public void testUpdatePhaseForHandyWorker() {
		Actor actor = new Actor();
		actor = this.actorService.getActorByUsername("handyWorker1");
		super.authenticate("handyWorker1");

		HandyWorker h = new HandyWorker();
		h = this.handyWorkerService.getHandyWorkerByUsername("handyWorker1");

		Application app = h.getApplications().get(0);
		FixUpTask fixUpTask = app.getFixUpTask();

		List<Phase> phases = (List<Phase>) fixUpTask.getPhases();
		Phase phaseBeforeUpdate = phases.get(0);

		Phase phaseAfterUpdate = phaseBeforeUpdate;

		phaseAfterUpdate.setTitle("Cambio");
		this.handyWorkerService.updatePhaseForHandyWorker(phaseAfterUpdate);

		List<Phase> newPhases = (List<Phase>) fixUpTask.getPhases();
		Assert.isTrue(newPhases.get(0).getTitle().equals(phaseAfterUpdate.getTitle()));
		super.unauthenticate();
	}

	//37.1

	@Test
	public void testShowFinderFromHandyWorker() {
		Actor actor = new Actor();
		actor = this.actorService.getActorByUsername("handyWorker1");
		super.authenticate("handyWorker1");

		HandyWorker h = this.handyWorkerService.findOne(actor.getId());
		Finder finder = h.getFinder();
		finder.setKeyWord("Cambio");

		this.handyWorkerService.updateFinderFromHandyWorker(finder);

		Assert.isTrue(h.getFinder().getKeyWord().equals(finder.getKeyWord()));
		super.unauthenticate();
	}

	//37.2

	@Test
	public void testShowFinderResult() {
		Actor actor = new Actor();
		actor = this.actorService.getActorByUsername("handyWorker1");
		super.authenticate("handyWorker1");

		HandyWorker h = this.handyWorkerService.findOne(actor.getId());

		List<FixUpTask> f = this.handyWorkerService.showFinderResult();

		Assert.isTrue(f.size() == h.getFinder().getFixUpTasks().size());
		super.unauthenticate();
	}

	//37.3

	@Test
	public void testShowComplaintsFromHandyWorker() {
		Actor actor = new Actor();
		actor = this.actorService.getActorByUsername("handyWorker1");
		super.authenticate("handyWorker1");

		HandyWorker h = this.handyWorkerService.findOne(actor.getId());

		List<Complaint> complaints = this.handyWorkerService.showComplaintsFromHandyWorker();

		Assert.isTrue(complaints.size() == 6);
		super.unauthenticate();

	}

	//37.4
	@Test
	public void testCreateNoteFromHandyWorker() {
		Actor actor = new Actor();
		actor = this.actorService.getActorByUsername("handyWorker1");
		super.authenticate("handyWorker1");

		List<String> optionalComments = new ArrayList<String>();
		Note newNote = this.noteServce.create("Prueba", optionalComments);

		HandyWorker h = new HandyWorker();
		h = this.handyWorkerService.getHandyWorkerByUsername("handyWorker1");

		List<Complaint> complaints = this.handyWorkerService.showComplaintsFromHandyWorker();
		Complaint complaint = complaints.get(0);

		Report report = complaint.getReports().get(0);
		Integer numNotesBeforeCreate = report.getNotes().size();
		this.handyWorkerService.createNoteFromHandyWorker(complaint.getId(), newNote, report.getId());

		List<Note> notesFromReport = this.reportService.findOne(report.getId()).getNotes();
		Integer numNotesAfterCreate = notesFromReport.size();
		Assert.isTrue(numNotesBeforeCreate + 1 == numNotesAfterCreate);
		super.unauthenticate();

	}

	//37.5
	@Test
	public void testWriteCommentFromHandyWorker() {
		Actor actor = new Actor();
		actor = this.actorService.getActorByUsername("handyWorker1");
		super.authenticate("handyWorker1");

		HandyWorker h = new HandyWorker();
		h = this.handyWorkerService.getHandyWorkerByUsername("handyWorker1");

		List<Complaint> complaints = this.handyWorkerService.showComplaintsFromHandyWorker();
		Complaint complaint = complaints.get(0);
		Report report = complaint.getReports().get(1);
		Note note = report.getNotes().get(0);

		Integer numCommentsBefore = note.getOptionalComments().size();

		this.handyWorkerService.writeCommentFromHandyWorker(complaint.getId(), "Prueba", report.getId(), note.getId());

		Integer numCommentsAfter = this.noteServce.findOne(note.getId()).getOptionalComments().size();

		Assert.isTrue(numCommentsBefore + 1 == numCommentsAfter);

		super.unauthenticate();
	}

	//49.1
	@Test
	public void testShowTutorials() {
		Actor actor = new Actor();
		actor = this.actorService.getActorByUsername("handyWorker1");
		super.authenticate("handyWorker1");

		HandyWorker h = this.handyWorkerService.findOne(actor.getId());

		List<Tutorial> tutorials = h.getTutorials();
		List<Tutorial> result = this.handyWorkerService.showTutorials();

		Assert.isTrue(tutorials.containsAll(result));
		super.unauthenticate();

	}

	@Test
	public void TestDeleteTutorial() {
		Actor actor = new Actor();
		actor = this.actorService.getActorByUsername("handyWorker1");
		super.authenticate("handyWorker1");

		HandyWorker h = this.handyWorkerService.findOne(actor.getId());

		Tutorial tutorial = h.getTutorials().get(0);

		this.handyWorkerService.deleteTutorial(tutorial);

		HandyWorker h2 = this.handyWorkerService.findOne(actor.getId());

		Assert.isTrue(!(h2.getTutorials().contains(tutorial)));
		super.unauthenticate();

	}

	@Test
	public void TestUpdateTutorial() {
		Actor actor = new Actor();
		actor = this.actorService.getActorByUsername("handyWorker1");
		super.authenticate("handyWorker1");

		HandyWorker h = this.handyWorkerService.findOne(actor.getId());

		Tutorial tutorial = h.getTutorials().get(0);
		String oldTitle = tutorial.getTitle();
		tutorial.setTitle("Prueba2");

		this.handyWorkerService.updateTutorial(tutorial);

		Tutorial newTutorial = this.tutoralService.findOne(tutorial.getId());

		String newTitle = newTutorial.getTitle();

		Assert.isTrue(!(oldTitle.equals(newTitle)));
		super.unauthenticate();
	}

	@Test
	public void testCreateTutorial() {
		Actor actor = new Actor();
		actor = this.actorService.getActorByUsername("handyWorker1");
		super.authenticate("handyWorker1");

		HandyWorker h = this.handyWorkerService.findOne(actor.getId());

		Integer numTutorialsBefore = h.getTutorials().size();
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, 2017);
		cal.set(Calendar.MONTH, Calendar.DECEMBER);
		cal.set(Calendar.DAY_OF_MONTH, 12);
		Date lastUpdate = new Date();
		lastUpdate.setTime(lastUpdate.getTime() - 1);

		Tutorial newTutorial = this.tutoralService.create("Prueba", lastUpdate, "Summary");

		this.handyWorkerService.createTutorial(newTutorial);

		Integer numTutorialsAfter = h.getTutorials().size();
		Assert.isTrue(numTutorialsBefore + 1 == numTutorialsAfter);
		super.unauthenticate();

	}
	//49.2

	@Test
	public void testDeleteEndorsment() {
		Actor actor = new Actor();
		actor = this.actorService.getActorByUsername("handyWorker1");
		super.authenticate("handyWorker1");

		HandyWorker h = this.handyWorkerService.findOne(actor.getId());
		Endorsement endorsment = h.getEndorsements().get(0);

		this.handyWorkerService.deleteEndorsment(endorsment);

		Assert.isTrue(!(h.getEndorsements().contains(endorsment)));
		super.unauthenticate();

	}

	@Test
	public void testUpdateEndorsment() {
		Actor actor = new Actor();
		actor = this.actorService.getActorByUsername("handyWorker1");
		super.authenticate("handyWorker1");

		HandyWorker h = this.handyWorkerService.findOne(actor.getId());
		Endorsement endorsment = h.getEndorsements().get(0);
		List<String> oldComments = endorsment.getComments();
		List<String> newComments = new ArrayList<>();
		newComments.add("Ejemplo");
		endorsment.setComments(newComments);

		this.handyWorkerService.updateEndorsment(endorsment);

		Endorsement endorsmentN = this.endorsmentService.findOne(endorsment.getId());
		Assert.isTrue(endorsmentN.getComments().contains("Ejemplo") && !(newComments.containsAll(oldComments)));

		super.unauthenticate();

	}

	@Test
	//TODO
	public void testCreateEndorsment() {
		Actor actor = new Actor();
		actor = this.actorService.getActorByUsername("handyWorker1");
		super.authenticate("handyWorker1");

		HandyWorker h = this.handyWorkerService.findOne(actor.getId());

		List<String> comments = new ArrayList<String>();
		comments.add("A");
		comments.add("B");

		List<Customer> l = this.handyWorkerService.getCustomersByHandyWorker(h);

		Endorser endorser = l.get(0);
		Endorser endorserBy = h;

		Integer numEndorsmentsBefore = endorser.getEndorsements().size();
		Integer numB = endorserBy.getEndorsements().size();
		Endorsement endorsment = this.endorsmentService.createEndorsment(comments, endorser);
		Assert.notNull(endorsment);
		endorsment.setWrittenTo(l.get(0));
		endorsment.setWrittenBy(h);
		endorsment.setComments(comments);

		Assert.isTrue(this.endorsmentService.findAll().size() == 2);
		this.handyWorkerService.createEndorsment(endorsment);
		Assert.isTrue(this.endorsmentService.findAll().size() == 3);

		Endorser endorser2 = this.customerService.findOne(l.get(0).getId());
		Integer numEndorsmentsAfter = endorser2.getEndorsements().size();
		Assert.isTrue(numEndorsmentsBefore + 1 == numEndorsmentsAfter);

		Endorser e2 = this.handyWorkerService.findOne(h.getId());
		Integer numA = e2.getEndorsements().size();
		Assert.isTrue(numB + 1 == numA);
		super.unauthenticate();
	}

	@Test
	public void testShowEndorsments() {
		Actor actor = new Actor();
		actor = this.actorService.getActorByUsername("handyWorker1");
		super.authenticate("handyWorker1");

		HandyWorker h = this.handyWorkerService.findOne(actor.getId());

		List<Endorsement> endorsments = h.getEndorsements();

		List<Endorsement> result = this.handyWorkerService.showEndorsments();
		Assert.isTrue(endorsments.size() != 0);

		Assert.isTrue(endorsments.size() == (result.size()));

	}
}
