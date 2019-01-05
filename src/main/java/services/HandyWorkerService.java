
package services;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.HandyWorkerRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Application;
import domain.Box;
import domain.Complaint;
import domain.Curriculum;
import domain.Customer;
import domain.Endorsement;
import domain.Finder;
import domain.FixUpTask;
import domain.HandyWorker;
import domain.Message;
import domain.Note;
import domain.Phase;
import domain.Report;
import domain.Section;
import domain.SocialProfile;
import domain.Status;
import domain.Tutorial;

@Service
@Transactional
public class HandyWorkerService {

	// Managed repository ---------------------------------------------------------------------------------------------

	@Autowired
	private HandyWorkerRepository	handyWorkerRepository;

	// Supporting Services --------------------------------------------------------------------------------------------

	@Autowired
	private TutorialService			tutorialService;
	@Autowired
	private EndorsementService		endorsmentService;
	@Autowired
	private CurriculumService		curriculumService;
	@Autowired
	private NoteService				noteService;
	@Autowired
	private ReportService			reportService;
	@Autowired
	private PhaseService			phaseService;
	@Autowired
	private ApplicationService		applicationService;
	@Autowired
	private FinderService			finderService;
	@Autowired
	private FixUpTaskService		fixUpTaskService;
	@Autowired
	private ComplaintService		complaintService;
	@Autowired
	private EndorserService			endorserSevice;
	@Autowired
	private CustomerService			customerService;
	@Autowired
	private BoxService				boxService;
	@Autowired
	private ConfigurationService	configurationService;
	@Autowired
	private SectionService			sectionService;


	// Simple CRUD methods --------------------------------------------------------------------------------------------

	public HandyWorker createHandyWorker() {

		HandyWorker handyWorker = new HandyWorker();

		//SE CREAN LAS LISTAS VACÍAS	
		//Actor
		List<SocialProfile> socialProfiles = new ArrayList<SocialProfile>();
		List<Box> boxes = new ArrayList<Box>();
		//Endorser
		List<Endorsement> endorsements = new ArrayList<Endorsement>();
		//Handy Worker
		List<Application> applications = new ArrayList<Application>();
		List<Tutorial> tutorials = new ArrayList<Tutorial>();
		Curriculum curriculum = new Curriculum();

		//curriculum = this.curriculumService.create();

		Finder finder = new Finder();

		//finder = this.finderService.createFinder();

		UserAccount userAccountActor = new UserAccount();
		userAccountActor.setUsername("");
		userAccountActor.setPassword("");

		//		Box spamBox = new Box();
		//		List<Message> messages1 = new ArrayList<>();
		//		spamBox.setIsSystem(true);
		//		spamBox.setMessages(messages1);
		//		spamBox.setName("Spam");
		//
		//		Box trashBox = new Box();
		//		List<Message> messages2 = new ArrayList<>();
		//		trashBox.setIsSystem(true);
		//		trashBox.setMessages(messages2);
		//		trashBox.setName("Trash");
		//
		//		Box sentBox = new Box();
		//		List<Message> messages3 = new ArrayList<>();
		//		sentBox.setIsSystem(true);
		//		sentBox.setMessages(messages3);
		//		sentBox.setName("Sent messages");
		//
		//		Box receivedBox = new Box();
		//		List<Message> messages4 = new ArrayList<>();
		//		receivedBox.setIsSystem(true);
		//		receivedBox.setMessages(messages4);
		//		receivedBox.setName("Received messages");
		//
		//		boxes.add(receivedBox);
		//		boxes.add(sentBox);
		//		boxes.add(spamBox);
		//		boxes.add(trashBox);

		handyWorker.setTutorials(tutorials);
		handyWorker.setName("");
		handyWorker.setSurname("");
		handyWorker.setAddress("");
		handyWorker.setEmail("");
		handyWorker.setMiddleName("");
		handyWorker.setPhoneNumber("");
		handyWorker.setSocialProfiles(socialProfiles);
		handyWorker.setScore(0.0);
		handyWorker.setEndorsements(endorsements);
		handyWorker.setMake("");
		handyWorker.setApplications(applications);
		handyWorker.setPhoto("");
		handyWorker.setFinder(finder);
		handyWorker.setBoxes(boxes);
		handyWorker.setCurriculum(curriculum);

		handyWorker.setHasSpam(false);

		List<Authority> authorities = new ArrayList<Authority>();

		Authority authority = new Authority();
		authority.setAuthority(Authority.HANDYWORKER);
		authorities.add(authority);

		userAccountActor.setAuthorities(authorities);
		//NOTLOCKED A TRUE EN LA INICIALIZACION, O SE CREARA UNA CUENTA BANEADA
		userAccountActor.setIsNotLocked(true);

		handyWorker.setUserAccount(userAccountActor);

		return handyWorker;

	}

	public HandyWorker createHandyWorker(String name, String middleName, String surname, String photo, String email, String phoneNumber, String address, String userName, String password, Double score, List<Tutorial> tutorials, Curriculum curriculum) {

		HandyWorker handyWorker = new HandyWorker();

		List<SocialProfile> socialProfiles = new ArrayList<SocialProfile>();
		List<Box> boxes = new ArrayList<Box>();
		List<Endorsement> endorsments = new ArrayList<Endorsement>();
		List<FixUpTask> f = new ArrayList<FixUpTask>();
		List<Application> applications = new ArrayList<Application>();

		UserAccount userAccountActor = new UserAccount();
		userAccountActor.setUsername(userName);
		userAccountActor.setPassword(password);

		Box spamBox = new Box();
		List<Message> messages1 = new ArrayList<>();
		spamBox.setIsSystem(true);
		spamBox.setMessages(messages1);
		spamBox.setName("Spam");

		Box trashBox = new Box();
		List<Message> messages2 = new ArrayList<>();
		trashBox.setIsSystem(true);
		trashBox.setMessages(messages2);
		trashBox.setName("Trash");

		Box sentBox = new Box();
		List<Message> messages3 = new ArrayList<>();
		sentBox.setIsSystem(true);
		sentBox.setMessages(messages3);
		sentBox.setName("Sent messages");

		Box receivedBox = new Box();
		List<Message> messages4 = new ArrayList<>();
		receivedBox.setIsSystem(true);
		receivedBox.setMessages(messages4);
		receivedBox.setName("Received messages");

		boxes.add(receivedBox);
		boxes.add(sentBox);
		boxes.add(spamBox);
		boxes.add(trashBox);

		Date thisMoment = new Date();
		thisMoment.setTime(thisMoment.getTime());
		Date afterMoment = new Date();
		thisMoment.setTime(thisMoment.getTime() + 1);

		Finder finder = this.finderService.createFinder("finder", "s", "s", 0.0, 0.0, thisMoment, afterMoment, f);

		handyWorker.setFinder(finder);
		handyWorker.setCurriculum(curriculum);
		handyWorker.setTutorials(tutorials);
		handyWorker.setName(name);
		handyWorker.setBoxes(boxes);
		handyWorker.setUserAccount(userAccountActor);
		handyWorker.setAddress(address);
		handyWorker.setEmail(email);
		handyWorker.setMiddleName(middleName);
		handyWorker.setPhoneNumber(phoneNumber);
		handyWorker.setSocialProfiles(socialProfiles);
		handyWorker.setScore(score);
		handyWorker.setEndorsements(endorsments);
		handyWorker.setMake(name + "" + middleName + "" + surname);
		handyWorker.setApplications(applications);
		handyWorker.setPhoto(photo);
		handyWorker.setSurname(surname);

		handyWorker.setHasSpam(false);

		List<Authority> authorities = new ArrayList<Authority>();

		Authority authority = new Authority();
		authority.setAuthority(Authority.HANDYWORKER);
		authorities.add(authority);

		handyWorker.getUserAccount().setAuthorities(authorities);
		//NOTLOCKED A TRUE EN LA INICIALIZACION, O SE CREARA UNA CUENTA BANEADA
		handyWorker.getUserAccount().setIsNotLocked(true);

		return handyWorker;

	}
	public void editHandyWorker(HandyWorker handyworker) {
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Assert.isTrue(userAccount.getId() == handyworker.getId());

		this.handyWorkerRepository.save(handyworker);

	}
	public Collection<HandyWorker> findAll() {
		return this.handyWorkerRepository.findAll();
	}

	public HandyWorker findOne(int id) {
		return this.handyWorkerRepository.findOne(id);
	}

	public HandyWorker saveCreate(HandyWorker handyWorker) {

		handyWorker.setMake(handyWorker.getName() + "" + handyWorker.getMiddleName() + "" + handyWorker.getSurname());
		HandyWorker saved = new HandyWorker();

		List<Box> boxes = new ArrayList<>();

		//Boxes
		Box box1 = this.boxService.createSystem();
		box1.setName("Spam");
		Box saved1 = this.boxService.saveSystem(box1);
		boxes.add(saved1);

		Box box2 = this.boxService.createSystem();
		box2.setName("Trash");
		Box saved2 = this.boxService.saveSystem(box2);
		boxes.add(saved2);

		Box box3 = this.boxService.createSystem();
		box3.setName("Sent messages");
		Box saved3 = this.boxService.saveSystem(box3);
		boxes.add(saved3);

		Box box4 = this.boxService.createSystem();
		box4.setName("Received messages");
		Box saved4 = this.boxService.saveSystem(box4);
		boxes.add(saved4);

		handyWorker.setBoxes(boxes);

		Finder savedFinder = new Finder();
		Curriculum savedCurriculum = new Curriculum();

		savedFinder = this.finderService.save(handyWorker.getFinder());
		savedCurriculum = this.curriculumService.save(handyWorker.getCurriculum());

		//handyWorker.setFinder(savedFinder);
		//handyWorker.setCurriculum(savedCurriculum);

		saved = this.handyWorkerRepository.save(handyWorker);

		return saved;
	}

	public HandyWorker save(HandyWorker handyWorker) {
		return this.handyWorkerRepository.save(handyWorker);
	}

	public void delete(HandyWorker handyWorker) {
		this.handyWorkerRepository.delete(handyWorker);
	}

	private HandyWorker securityAndHandy() {
		UserAccount userAccount = LoginService.getPrincipal();
		String username = userAccount.getUsername();
		HandyWorker loggedHandy = this.handyWorkerRepository.findOne(userAccount.getId());

		Assert.isTrue(userAccount.getAuthorities().contains("HANDYWORKER"));

		return loggedHandy;
	}

	public Report showReport(Report report) {
		HandyWorker loggedHandy = this.securityAndHandy();
		Assert.isTrue(report.getFinalMode());
		return this.reportService.findOne(report.getId());
	}

	public List<Report> listReports() {
		HandyWorker loggedHandy = this.securityAndHandy();
		List<Report> lr = this.reportService.findAll();
		for (Report report : lr) {
			Assert.isTrue(report.getFinalMode());
		}
		return lr;
	}

	public void addCurriculum(Curriculum curriculum) {

		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		List<Authority> authorities = (List<Authority>) userAccount.getAuthorities();
		Assert.isTrue(authorities.get(0).toString().equals("HANDYWORKER"));

		HandyWorker logguedHandyWorker = new HandyWorker();
		logguedHandyWorker = this.handyWorkerRepository.getHandyWorkerByUsername(userAccount.getUsername());

		Assert.isNull(logguedHandyWorker.getCurriculum());

		logguedHandyWorker.setCurriculum(curriculum);
		this.handyWorkerRepository.save(logguedHandyWorker);

	}

	public void deleteCurriculum(Curriculum curriculum) {
		HandyWorker logguedHandyWorker = this.handyWorkerRepository.getHandyWorkerByUsername(LoginService.getPrincipal().getUsername());

		Assert.isTrue(logguedHandyWorker.getCurriculum().equals(curriculum));
		logguedHandyWorker.setCurriculum(null);
		this.handyWorkerRepository.save(logguedHandyWorker);

		this.curriculumService.delete(curriculum);

	}

	public Curriculum editCurriculum(Curriculum curriculum) {
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		List<Authority> authorities = (List<Authority>) userAccount.getAuthorities();
		Assert.isTrue(authorities.get(0).toString().equals("HANDYWORKER"));

		HandyWorker logguedHandyWorker = this.handyWorkerRepository.getHandyWorkerByUsername(userAccount.getUsername());

		Assert.isTrue(logguedHandyWorker.getCurriculum().equals(curriculum));
		return this.curriculumService.save(curriculum);
	}

	public Application addComment(Application application, String comment) {

		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		List<Authority> authorities = (List<Authority>) userAccount.getAuthorities();
		Assert.isTrue(authorities.get(0).toString().equals("HANDYWORKER"));

		HandyWorker logguedHandyWorker = this.handyWorkerRepository.getHandyWorkerByUsername(userAccount.getUsername());

		Collection<Application> applications = logguedHandyWorker.getApplications();

		Application applicationFound = null;
		for (Application a : applications) {
			if (application.getId() == a.getId()) {
				applicationFound = a;
				break;
			}
		}

		Assert.isTrue(!applicationFound.equals(null));

		List<String> comments = (List<String>) applicationFound.getComments();
		comments.add(comment);
		applicationFound.setComments(comments);

		Application applicationSave = this.applicationService.save(application);

		this.configurationService.isActorSuspicious(logguedHandyWorker);

		return applicationSave;
	}

	// Other business methods -------------------------------------------------------------------------------------------

	//11.1 ------------------------------------------------------------------------------------------------------------------

	public Collection<FixUpTask> showFixUpTasks() {

		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		List<Authority> authorities = (List<Authority>) userAccount.getAuthorities();
		Assert.isTrue(authorities.get(0).toString().equals("HANDYWORKER"));

		return this.fixUpTaskService.findAll();
	}

	public Map<FixUpTask, Customer> showFixUpTasksAndCustomer() {
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		List<Authority> authorities = (List<Authority>) userAccount.getAuthorities();
		Assert.isTrue(authorities.get(0).toString().equals("HANDYWORKER"));

		Map<FixUpTask, Customer> res = new HashMap<FixUpTask, Customer>();
		Collection<Customer> customers = this.customerService.findAll();

		for (Customer c : customers) {
			for (FixUpTask f : c.getFixUpTasks()) {
				res.put(f, c);
			}
		}
		return res;

	}
	public Map<FixUpTask, Customer> getFixUpTaksAndCustomer(Collection<FixUpTask> fixUpTasks) {
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		List<Authority> authorities = (List<Authority>) userAccount.getAuthorities();
		Assert.isTrue(authorities.get(0).toString().equals("HANDYWORKER"));
		Map<FixUpTask, Customer> res = new HashMap<FixUpTask, Customer>();
		Collection<Customer> customers = this.customerService.findAll();

		for (Customer c : customers) {
			for (FixUpTask f : c.getFixUpTasks()) {
				if (fixUpTasks.contains(f)) {
					res.put(f, c);
				}
			}
		}

		return res;

	}
	public Map<Customer, Collection<FixUpTask>> getFixUpTaskPerCustomer(int idFixUpTask) {

		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		List<Authority> authorities = (List<Authority>) userAccount.getAuthorities();
		Assert.isTrue(authorities.get(0).toString().equals("HANDYWORKER"));

		Map<Customer, Collection<FixUpTask>> res = new HashMap<Customer, Collection<FixUpTask>>();

		Customer customer = this.handyWorkerRepository.getCustomerByFixUpTask(idFixUpTask);
		FixUpTask fixUpTask = this.fixUpTaskService.findOne(idFixUpTask);
		Assert.isTrue(customer.getFixUpTasks().contains(fixUpTask));

		res.put(customer, customer.getFixUpTasks());

		return res;

	}
	//11.2 ------------------------------------------------------------------------------------------------------------------

	public void filterFixUpTasksByFinder() {
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		List<Authority> authorities = (List<Authority>) userAccount.getAuthorities();
		Assert.isTrue(authorities.get(0).toString().equals("HANDYWORKER"));

		HandyWorker logguedHandyWorker = new HandyWorker();
		logguedHandyWorker = this.handyWorkerRepository.getHandyWorkerByUsername(userAccount.getUsername());

		Finder finder = logguedHandyWorker.getFinder();
		List<FixUpTask> result = new ArrayList<FixUpTask>();
		result = this.fixUpTaskService.findAll();
		Collection<FixUpTask> filter = new ArrayList<FixUpTask>();

		//KeyWord
		if (!finder.getKeyWord().equals(null) && !finder.getKeyWord().equals("")) {
			filter = this.handyWorkerRepository.getFixUpTaskByKeyWord(finder.getKeyWord());
			result.retainAll(filter);
		}
		//Category
		if (!finder.getCategory().equals(null) && !finder.getCategory().equals("")) {
			filter = this.handyWorkerRepository.getFixUpTaskByCategory(finder.getCategory());
			result.retainAll(filter);
		}
		//Warranty
		if (!finder.getWarranty().equals(null) && !finder.getWarranty().equals("")) {
			filter = this.handyWorkerRepository.getFixUpTasksByWarranty(finder.getWarranty());
			result.retainAll(filter);
		}

		//Prices
		if (!(finder.getMaxPrice().equals(0.0) && finder.getMinPrice().equals(0.0))) {
			Assert.isTrue(finder.getMinPrice() <= finder.getMaxPrice());
			filter = this.handyWorkerRepository.getFixUpTasksByPrice(finder.getId());
			result.retainAll(filter);
		}

		//Dates
		if (finder.getStartDate() != null && finder.getEndDate() != null) {
			Assert.isTrue(finder.getStartDate().before(finder.getEndDate()));
			filter = this.handyWorkerRepository.getFixUpTasksByDate(finder.getId());
			result.retainAll(filter);
		}

		Finder finderResult = new Finder();
		finderResult = finder;
		finderResult.setFixUpTasks(result);
		Finder finderRes = this.finderService.save(finderResult);
		logguedHandyWorker.setFinder(finderRes);
		this.save(logguedHandyWorker);
	}
	//11.3 ------------------------------------------------------------------------------------------------------------------

	public Collection<Application> showApplications() {
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		List<Authority> authorities = (List<Authority>) userAccount.getAuthorities();
		Assert.isTrue(authorities.get(0).toString().equals("HANDYWORKER"));

		HandyWorker h = this.handyWorkerRepository.getHandyWorkerByUsername(userAccount.getUsername());

		Collection<Application> listApplications = this.handyWorkerRepository.getAllApplicationsFromAHandyWorker(h.getId());
		return listApplications;
	}

	public Application createApplicationHandyWorker(double offeredPrice, List<String> comments, FixUpTask fixUpTask) {

		UserAccount userAccount = LoginService.getPrincipal();
		List<Authority> authorities = (List<Authority>) userAccount.getAuthorities();
		Assert.isTrue(authorities.get(0).toString().equals("HANDYWORKER"));

		HandyWorker logguedHandyWorker = new HandyWorker();
		logguedHandyWorker = this.handyWorkerRepository.getHandyWorkerByUsername(userAccount.getUsername());

		Application application = this.applicationService.createApplication(offeredPrice, fixUpTask, logguedHandyWorker);

		application.setComments(comments);
		//Revisar
		List<Application> applications = logguedHandyWorker.getApplications();
		applications.add(application);
		logguedHandyWorker.setApplications(applications);

		Collection<Application> applicationsF = fixUpTask.getApplications();
		applicationsF.add(application);
		this.handyWorkerRepository.save(logguedHandyWorker);
		this.fixUpTaskService.save(fixUpTask);
		this.applicationService.save(application);

		return application;
	}

	//11.4 ------------------------------------------------------------------------------------------------------------------
	public Collection<Phase> showPhaseForHandyWorker(FixUpTask fixUpTask) {
		UserAccount userAccount = LoginService.getPrincipal();
		List<Authority> authorities = (List<Authority>) userAccount.getAuthorities();
		Assert.isTrue(authorities.get(0).toString().equals("HANDYWORKER"));

		HandyWorker logguedHandyWorker = new HandyWorker();
		logguedHandyWorker = this.handyWorkerRepository.getHandyWorkerByUsername(userAccount.getUsername());

		Assert.isTrue(this.handyWorkerRepository.getFixUpTasksFromHandyWorker(logguedHandyWorker.getId()).contains(fixUpTask));

		Collection<Phase> phases = this.handyWorkerRepository.getPhasesByFixUpTask(fixUpTask.getId());

		return phases;
	}

	public void createPhaseForApplication(Application app, Phase newPhase) {

		UserAccount userAccount = LoginService.getPrincipal();
		List<Authority> authorities = (List<Authority>) userAccount.getAuthorities();
		Assert.isTrue(authorities.get(0).toString().equals("HANDYWORKER"));

		HandyWorker logguedHandyWorker = new HandyWorker();
		logguedHandyWorker = this.handyWorkerRepository.getHandyWorkerByUsername(userAccount.getUsername());

		Application application = this.applicationService.findOne(app.getId());
		Assert.isTrue(application.getHandyWorker().equals(logguedHandyWorker));
		Assert.isTrue(application.getStatus().equals(Status.ACCEPTED));

		List<Phase> newPhases = (List<Phase>) this.handyWorkerRepository.getPhasesByApplication(application.getId());
		newPhases.add(newPhase);

		FixUpTask newFixUpTask = application.getFixUpTask();
		Assert.notNull(newFixUpTask);
		newFixUpTask.setPhases(newPhases);

		this.configurationService.isActorSuspicious(logguedHandyWorker);

		this.fixUpTaskService.save(newFixUpTask);

	}
	public void deletePhaseForApplication(int idPhase) {
		UserAccount userAccount = LoginService.getPrincipal();
		List<Authority> authorities = (List<Authority>) userAccount.getAuthorities();
		Assert.isTrue(authorities.get(0).toString().equals("HANDYWORKER"));

		HandyWorker logguedHandyWorker = new HandyWorker();
		logguedHandyWorker = this.handyWorkerRepository.getHandyWorkerByUsername(userAccount.getUsername());

		Phase phase = new Phase();
		phase = this.phaseService.findOne(idPhase);

		Collection<Phase> phases = this.handyWorkerRepository.getPhasesByHandyWorker(logguedHandyWorker.getId());
		Assert.isTrue(phases.contains(phase));

		FixUpTask f = this.handyWorkerRepository.getFixUpTaskByPhase(idPhase);
		List<Phase> phasesF = (List<Phase>) f.getPhases();
		phasesF.remove(phase);
		f.setPhases(phasesF);
		this.fixUpTaskService.save(f);
		this.phaseService.delete(phase);
	}

	public void updatePhaseForHandyWorker(Phase phase) {
		UserAccount userAccount = LoginService.getPrincipal();
		List<Authority> authorities = (List<Authority>) userAccount.getAuthorities();
		Assert.isTrue(authorities.get(0).toString().equals("HANDYWORKER"));

		HandyWorker logguedHandyWorker = new HandyWorker();
		logguedHandyWorker = this.handyWorkerRepository.getHandyWorkerByUsername(userAccount.getUsername());

		Collection<Phase> phases = this.handyWorkerRepository.getPhasesByHandyWorker(logguedHandyWorker.getId());
		Assert.isTrue(phases.contains(phase));

		FixUpTask f = this.handyWorkerRepository.getFixUpTaskByPhase(phase.getId());

		this.configurationService.isActorSuspicious(logguedHandyWorker);

		this.phaseService.save(phase);
	}
	//REQUISITO FUNCIONAL 37 *******************************************************************************************************************

	//37.1 --------------------------------------------------------------------------------------------------------------------------------------

	public void updateFinderFromHandyWorker(Finder finder) {
		UserAccount userAccount = LoginService.getPrincipal();
		List<Authority> authorities = (List<Authority>) userAccount.getAuthorities();
		Assert.isTrue(authorities.get(0).toString().equals("HANDYWORKER"));

		HandyWorker logguedHandyWorker = new HandyWorker();
		logguedHandyWorker = this.handyWorkerRepository.getHandyWorkerByUsername(userAccount.getUsername());

		Assert.isTrue(logguedHandyWorker.getFinder().getId() == finder.getId());

		logguedHandyWorker.setFinder(finder);
		this.finderService.save(finder);
		this.handyWorkerRepository.save(logguedHandyWorker);
	}

	//37.2 --------------------------------------------------------------------------------------------------------------------------------------
	public List<FixUpTask> showFinderResult() {
		UserAccount userAccount = LoginService.getPrincipal();
		List<Authority> authorities = (List<Authority>) userAccount.getAuthorities();
		Assert.isTrue(authorities.get(0).toString().equals("HANDYWORKER"));

		List<FixUpTask> resultFixUpTask = new ArrayList<FixUpTask>();

		HandyWorker logguedHandyWorker = new HandyWorker();
		logguedHandyWorker = this.handyWorkerRepository.getHandyWorkerByUsername(userAccount.getUsername());

		Finder finder = new Finder();

		finder = this.handyWorkerRepository.getFinderFromAHandyWorker(logguedHandyWorker.getId());
		resultFixUpTask = finder.getFixUpTasks();
		return resultFixUpTask;
	}

	//37.3 --------------------------------------------------------------------------------------------------------------------------------------
	public List<Complaint> showComplaintsFromHandyWorker() {
		UserAccount userAccount = LoginService.getPrincipal();
		List<Authority> authorities = (List<Authority>) userAccount.getAuthorities();
		Assert.isTrue(authorities.get(0).toString().equals("HANDYWORKER"));

		List<Complaint> complaints = new ArrayList<Complaint>();

		HandyWorker logguedHandyWorker = new HandyWorker();
		logguedHandyWorker = this.handyWorkerRepository.getHandyWorkerByUsername(userAccount.getUsername());

		complaints = this.handyWorkerRepository.getComplaintsFromHandyWorker(logguedHandyWorker.getId());

		return complaints;
	}

	//37.4 --------------------------------------------------------------------------------------------------------------------------------------
	public void createNoteFromHandyWorker(int idComplaint, Note note, int idReport) {

		UserAccount userAccount = LoginService.getPrincipal();
		List<Authority> authorities = (List<Authority>) userAccount.getAuthorities();
		Assert.isTrue(authorities.get(0).toString().equals("HANDYWORKER"));
		HandyWorker logguedHandyWorker = new HandyWorker();
		logguedHandyWorker = this.handyWorkerRepository.getHandyWorkerByUsername(userAccount.getUsername());

		Assert.isTrue(!note.getMandatoryComment().equals(""));
		Assert.notNull(note.getMandatoryComment());
		Assert.notNull(note.getMoment());

		List<Complaint> complaints = new ArrayList<Complaint>();
		complaints = this.handyWorkerRepository.getComplaintsFromHandyWorker(logguedHandyWorker.getId());
		Complaint complaint = new Complaint();
		complaint = this.complaintService.findOne(idComplaint);

		Assert.isTrue(complaints.contains(complaint));

		List<Report> reports = new ArrayList<Report>();
		reports = complaint.getReports();
		Report report = new Report();
		report = this.reportService.findOne(idReport);

		Assert.isTrue(reports.contains(report));

		//	this.noteService.save(note);

		List<Note> notes = new ArrayList<Note>();
		notes = report.getNotes();
		notes.add(note);
		report.setNotes(notes);

		this.configurationService.isActorSuspicious(logguedHandyWorker);

		this.reportService.save(report);

	}
	//37.5 --------------------------------------------------------------------------------------------------------------------------------------
	public void writeCommentFromHandyWorker(int idComplaint, String comment, int idReport, int idNote) {

		UserAccount userAccount = LoginService.getPrincipal();
		List<Authority> authorities = (List<Authority>) userAccount.getAuthorities();
		Assert.isTrue(authorities.get(0).toString().equals("HANDYWORKER"));
		HandyWorker logguedHandyWorker = new HandyWorker();
		logguedHandyWorker = this.handyWorkerRepository.getHandyWorkerByUsername(userAccount.getUsername());

		List<Complaint> complaints = new ArrayList<Complaint>();
		complaints = this.handyWorkerRepository.getComplaintsFromHandyWorker(logguedHandyWorker.getId());
		Complaint complaint = new Complaint();
		complaint = this.complaintService.findOne(idComplaint);

		Assert.isTrue(complaints.contains(complaint));

		List<Report> reports = new ArrayList<Report>();
		reports = complaint.getReports();
		Report report = new Report();
		report = this.reportService.findOne(idReport);

		Assert.isTrue(reports.contains(report));
		Note note = new Note();
		note = this.noteService.findOne(idNote);

		Assert.isTrue(report.getNotes().contains(note));
		note.getOptionalComments().add(comment);

		this.configurationService.isActorSuspicious(logguedHandyWorker);

		this.noteService.save(note);

	}
	//REQUISITO FUNCIONAL 49--------------------------------------------------------------------------------------------------------------------

	//49.1
	public List<Tutorial> showTutorials() {
		UserAccount userAccount = LoginService.getPrincipal();
		List<Authority> authorities = (List<Authority>) userAccount.getAuthorities();
		Assert.isTrue(authorities.get(0).toString().equals("HANDYWORKER"));
		HandyWorker logguedHandyWorker = new HandyWorker();
		logguedHandyWorker = this.handyWorkerRepository.getHandyWorkerByUsername(userAccount.getUsername());

		List<Tutorial> tutorials = new ArrayList<Tutorial>();

		tutorials = this.handyWorkerRepository.getAllTutorialsFromAHandyWorker(logguedHandyWorker.getId());

		return tutorials;
	}

	public void deleteTutorial(Tutorial tutorial) {
		UserAccount userAccount = LoginService.getPrincipal();
		HandyWorker logguedHandyWorker = new HandyWorker();
		logguedHandyWorker = this.handyWorkerRepository.getHandyWorkerByUsername(userAccount.getUsername());
		Assert.notNull(logguedHandyWorker);
		Assert.isTrue(logguedHandyWorker.getTutorials().contains(tutorial));

		List<Tutorial> tutorials = logguedHandyWorker.getTutorials();
		tutorials.remove(tutorial);

		logguedHandyWorker.setTutorials(tutorials);

		this.handyWorkerRepository.save(logguedHandyWorker);
		this.tutorialService.delete(tutorial);

	}

	public void deleteSection(Section section, Tutorial tutorial) {
		UserAccount userAccount = LoginService.getPrincipal();
		HandyWorker logguedHandyWorker = this.handyWorkerRepository.getHandyWorkerByUsername(userAccount.getUsername());
		Assert.notNull(logguedHandyWorker);
		Assert.isTrue(logguedHandyWorker.getTutorials().contains(tutorial));
		Assert.isTrue(tutorial.getSections().contains(section));

		List<Section> sections = tutorial.getSections();
		sections.remove(section);

		tutorial.setSections(sections);

		this.tutorialService.save(tutorial);
		this.sectionService.delete(section);

	}

	public void updateTutorial(Tutorial tutorial) {
		UserAccount userAccount = LoginService.getPrincipal();
		HandyWorker logguedHandyWorker = new HandyWorker();
		logguedHandyWorker = this.handyWorkerRepository.getHandyWorkerByUsername(userAccount.getUsername());
		Assert.notNull(logguedHandyWorker);
		Assert.isTrue(logguedHandyWorker.getTutorials().contains(tutorial));

		tutorial.setPictures(this.listUrlsTutorial(tutorial));
		this.tutorialService.save(tutorial);
		this.configurationService.isActorSuspicious(logguedHandyWorker);
	}

	public void updateSection(Section section, Tutorial tutorial) {
		UserAccount userAccount = LoginService.getPrincipal();
		HandyWorker logguedHandyWorker = new HandyWorker();
		logguedHandyWorker = this.handyWorkerRepository.getHandyWorkerByUsername(userAccount.getUsername());
		Assert.notNull(logguedHandyWorker);
		Assert.isTrue(logguedHandyWorker.getTutorials().contains(tutorial));
		Assert.isTrue(tutorial.getSections().contains(section));

		section.setSectionPictures(this.listUrlsSection(section));
		this.tutorialService.save(tutorial);
		this.sectionService.save(section);
		this.configurationService.isActorSuspicious(logguedHandyWorker);
	}

	public void createTutorial(Tutorial newTutorial) {

		HandyWorker logguedHandyWorker = this.handyWorkerRepository.getHandyWorkerByUsername(LoginService.getPrincipal().getUsername());

		Assert.isTrue(!logguedHandyWorker.getTutorials().contains(newTutorial));

		newTutorial.setPictures(this.listUrlsTutorial(newTutorial));

		Tutorial tutorial = this.tutorialService.save(newTutorial);
		List<Tutorial> tutorials = logguedHandyWorker.getTutorials();
		tutorials.add(tutorial);
		logguedHandyWorker.setTutorials(tutorials);

		this.configurationService.isActorSuspicious(logguedHandyWorker);

		this.handyWorkerRepository.save(logguedHandyWorker);

	}

	public void createSection(Section newSection, Tutorial tutorial) {

		HandyWorker logguedHandyWorker = this.handyWorkerRepository.getHandyWorkerByUsername(LoginService.getPrincipal().getUsername());

		Assert.isTrue(logguedHandyWorker.getTutorials().contains(tutorial));
		Assert.isTrue(!tutorial.getSections().contains(newSection));
		Assert.isTrue(newSection.getId() == 0);

		newSection.setSectionPictures(this.listUrlsSection(newSection));

		List<Section> sections = tutorial.getSections();
		sections.add(newSection);
		tutorial.setSections(sections);
		this.tutorialService.save(tutorial);

		this.configurationService.isActorSuspicious(logguedHandyWorker);

	}

	public List<String> listUrlsTutorial(Tutorial tutorial) {
		List<String> pic = new ArrayList<String>();

		if (tutorial.getPictures().size() == 1 && tutorial.getPictures().get(0).contains(",")) {
			String picture = tutorial.getPictures().get(0).trim();
			List<String> pictures = Arrays.asList(picture.split(","));

			for (String s : pictures) {
				if (!s.isEmpty() && !pic.contains(s.trim()) && this.isUrl(s)) {
					pic.add(s.trim());
				}
			}
		}
		return pic;
	}

	public List<String> listUrlsSection(Section section) {
		List<String> pic = new ArrayList<String>();

		if (section.getSectionPictures().size() == 1 && section.getSectionPictures().get(0).contains(",")) {
			String picture = section.getSectionPictures().get(0).trim();
			List<String> pictures = Arrays.asList(picture.split(","));

			for (String s : pictures) {
				if (!s.isEmpty() && !pic.contains(s.trim()) && this.isUrl(s)) {
					pic.add(s.trim());
				}
			}
		}
		return pic;
	}

	public Boolean isUrl(String url) {
		try {
			new URL(url).toURI();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	//49.2
	public void deleteEndorsment(Endorsement endorsement) {
		HandyWorker logguedHandyWorker = this.handyWorkerRepository.getHandyWorkerByUsername(LoginService.getPrincipal().getUsername());
		HandyWorker handyWorker = this.findOne(endorsement.getWrittenBy().getId());
		HandyWorker handyWorker2 = this.findOne(endorsement.getWrittenTo().getId());
		Assert.isTrue(logguedHandyWorker.equals(handyWorker) || logguedHandyWorker.equals(handyWorker2));

		Customer customer = this.customerService.findOne(endorsement.getWrittenTo().getId());
		Customer customer2 = this.customerService.findOne(endorsement.getWrittenBy().getId());

		List<Customer> customers = this.getCustomersByHandyWorker(logguedHandyWorker);
		Assert.isTrue(customers.contains(customer) || customers.contains(customer2));

		Assert.isTrue(logguedHandyWorker.getEndorsements().contains(endorsement));

		List<Endorsement> endorsementsH = logguedHandyWorker.getEndorsements();
		endorsementsH.remove(endorsement);
		logguedHandyWorker.setEndorsements(endorsementsH);

		List<Endorsement> endorsementsC = new ArrayList<Endorsement>();
		if (customers.contains(customer)) {
			endorsementsC = customer.getEndorsements();
			endorsementsC.remove(endorsement);
			customer.setEndorsements(endorsementsC);
			this.customerService.save(customer);
		} else if (customers.contains(customer2)) {
			endorsementsC = customer2.getEndorsements();
			endorsementsC.remove(endorsement);
			customer2.setEndorsements(endorsementsC);
			this.customerService.save(customer2);
		}
		this.handyWorkerRepository.save(logguedHandyWorker);
		this.endorsmentService.delete(endorsement);

	}

	public void updateEndorsment(Endorsement endorsment) {
		HandyWorker logguedHandyWorker = this.handyWorkerRepository.getHandyWorkerByUsername(LoginService.getPrincipal().getUsername());
		HandyWorker handyWorker = this.findOne(endorsment.getWrittenBy().getId());
		HandyWorker handyWorker2 = this.findOne(endorsment.getWrittenTo().getId());
		Assert.isTrue(logguedHandyWorker.equals(handyWorker) || logguedHandyWorker.equals(handyWorker2));

		Customer customer = this.customerService.findOne(endorsment.getWrittenTo().getId());
		Customer customer2 = this.customerService.findOne(endorsment.getWrittenBy().getId());

		List<Customer> customers = this.getCustomersByHandyWorker(logguedHandyWorker);
		Assert.isTrue(customers.contains(customer) || customers.contains(customer2));

		Assert.isTrue(logguedHandyWorker.getEndorsements().contains(endorsment));

		this.configurationService.isActorSuspicious(logguedHandyWorker);

		this.endorsmentService.save(endorsment);
	}

	public void createEndorsment(Endorsement endorsment) {
		HandyWorker logguedHandyWorker = this.handyWorkerRepository.getHandyWorkerByUsername(LoginService.getPrincipal().getUsername());
		HandyWorker handyWorker = this.findOne(endorsment.getWrittenBy().getId());
		Assert.isTrue(logguedHandyWorker.equals(handyWorker));

		Customer customer = this.customerService.findOne(endorsment.getWrittenTo().getId());
		List<Customer> customers = this.getCustomersByHandyWorker(handyWorker);
		Assert.isTrue(customers.contains(customer));

		Endorsement newEndorsment = this.endorsmentService.save(endorsment);
		Assert.isTrue(this.endorsmentService.findAll().contains(newEndorsment));

		List<Endorsement> end = handyWorker.getEndorsements();
		end.add(newEndorsment);
		handyWorker.setEndorsements(end);

		end = customer.getEndorsements();
		end.add(newEndorsment);
		customer.setEndorsements(end);

		this.customerService.save(customer);
		this.handyWorkerRepository.save(handyWorker);
		this.configurationService.computeScore(endorsment.getWrittenBy());
		this.configurationService.computeScore(endorsment.getWrittenTo());

		Assert.isTrue(this.customerService.findOne(customer.getId()).getEndorsements().contains(newEndorsment));
		Assert.isTrue(this.findOne(handyWorker.getId()).getEndorsements().contains(newEndorsment));
	}

	public List<Endorsement> showEndorsments() {
		UserAccount userAccount = LoginService.getPrincipal();
		List<Authority> authorities = (List<Authority>) userAccount.getAuthorities();
		Assert.isTrue(authorities.get(0).toString().equals("HANDYWORKER"));

		HandyWorker logguedHandyWorker = new HandyWorker();
		logguedHandyWorker = this.handyWorkerRepository.getHandyWorkerByUsername(userAccount.getUsername());

		List<Endorsement> endorsments = logguedHandyWorker.getEndorsements();
		return endorsments;
	}

	public List<String> filterComments(List<String> comments) {
		List<String> result = new ArrayList<String>();
		for (String s : comments) {
			if (!s.trim().isEmpty()) {
				result.add(s);
			}
		}
		return result;
	}

	//Test Methods ------------------------------------------
	public HandyWorker getHandyWorkerByUsername(String username) {

		return this.handyWorkerRepository.getHandyWorkerByUsername(username);
	}
	public List<FixUpTask> getFixUpTaskByHandyWorker(HandyWorker handyWorker) {

		return this.handyWorkerRepository.getFixUpTasksFromHandyWorker(handyWorker.getId());
	}
	public Collection<Phase> getPhasesByApplication(Application application) {

		return this.handyWorkerRepository.getPhasesByApplication(application.getId());
	}
	public List<Customer> getCustomersByHandyWorker(HandyWorker handyWorker) {
		return this.handyWorkerRepository.getCustomersFromHandyWorker(handyWorker.getId());
	}
}
