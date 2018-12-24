
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.CustomerRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Application;
import domain.Box;
import domain.Category;
import domain.Complaint;
import domain.CreditCard;
import domain.Customer;
import domain.Endorsment;
import domain.Finder;
import domain.FixUpTask;
import domain.HandyWorker;
import domain.Message;
import domain.Note;
import domain.Phase;
import domain.Report;
import domain.SocialProfile;
import domain.Status;
import domain.Warranty;

@Service
@Transactional
public class CustomerService {

	// Managed repository
	@Autowired
	private CustomerRepository		customerRepository;

	// Supporting services
	@Autowired
	private FixUpTaskService		fixUpTaskService;
	@Autowired
	private ComplaintService		complaintService;
	@Autowired
	private ApplicationService		applicationService;
	@Autowired
	private NoteService				noteService;
	@Autowired
	private ReportService			reportService;
	@Autowired
	private EndorsmentService		endorsmentService;
	@Autowired
	private ConfigurationService	configurationService;
	@Autowired
	private FinderService			finderService;
	@Autowired
	private HandyWorkerService		handyWorkerService;


	public Customer create() {

		// SE DECLARA EL SPONSOR
		Customer s = new Customer();

		// SE CREAN LAS LISTAS VACIAS
		List<SocialProfile> socialProfiles = new ArrayList<SocialProfile>();
		List<Box> boxes = new ArrayList<Box>();
		List<FixUpTask> fixUpTasks = new ArrayList<FixUpTask>();
		List<Endorsment> endorsements = new ArrayList<Endorsment>();

		// SE AÑADE EL USERNAME Y EL PASSWORD
		UserAccount userAccountActor = new UserAccount();
		userAccountActor.setUsername("");
		userAccountActor.setPassword("");

		// SE CREAN LAS CAJAS POR DEFECTO
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

		// SE AÑADEN TODOS LOS ATRIBUTOS
		s.setName("");
		s.setMiddleName("");
		s.setSurname("");
		s.setPhoto("");
		s.setEmail("");
		s.setPhoneNumber("");
		s.setAddress("");
		s.setSocialProfiles(socialProfiles);
		s.setBoxes(boxes);

		s.setFixUpTasks(fixUpTasks);
		s.setScore(0.);
		s.setEndorsments(endorsements);
		// SPAM SIEMPRE A FALSE EN LA INICIALIZACION
		s.setHasSpam(false);

		List<Authority> authorities = new ArrayList<Authority>();

		Authority authority = new Authority();
		authority.setAuthority(Authority.CUSTOMER);
		authorities.add(authority);

		userAccountActor.setAuthorities(authorities);
		// NOTLOCKED A TRUE EN LA INICIALIZACION, O SE CREARA UNA CUENTA BANEADA
		userAccountActor.setIsNotLocked(true);

		s.setUserAccount(userAccountActor);
		return s;
	}

	// Simple CRUD methods
	public Customer create(String name, String middleName, String surname, String photo, String email, String phoneNumber, String address, String userName, String password) {

		// SE DECLARA EL SPONSOR
		Customer s = new Customer();

		// SE CREAN LAS LISTAS VACIAS
		List<SocialProfile> socialProfiles = new ArrayList<SocialProfile>();
		List<Box> boxes = new ArrayList<Box>();
		List<FixUpTask> fixUpTasks = new ArrayList<FixUpTask>();

		// SE AÑADE EL USERNAME Y EL PASSWORD
		UserAccount userAccountActor = new UserAccount();
		userAccountActor.setUsername(userName);
		userAccountActor.setPassword(password);

		// SE CREAN LAS CAJAS POR DEFECTO
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

		// SE AÑADEN TODOS LOS ATRIBUTOS
		s.setName(name);
		s.setMiddleName(middleName);
		s.setSurname(surname);
		s.setPhoto(photo);
		s.setEmail(email);
		s.setPhoneNumber(phoneNumber);
		s.setAddress(address);
		s.setSocialProfiles(socialProfiles);
		s.setBoxes(boxes);
		s.setUserAccount(userAccountActor);
		s.setFixUpTasks(fixUpTasks);
		s.setScore(0.);
		// SPAM SIEMPRE A FALSE EN LA INICIALIZACION
		s.setHasSpam(false);

		List<Authority> authorities = new ArrayList<Authority>();

		Authority authority = new Authority();
		authority.setAuthority(Authority.CUSTOMER);
		authorities.add(authority);

		s.getUserAccount().setAuthorities(authorities);
		// NOTLOCKED A TRUE EN LA INICIALIZACION, O SE CREARA UNA CUENTA BANEADA
		s.getUserAccount().setIsNotLocked(true);

		return s;
	}

	public Collection<Customer> findAll() {
		return this.customerRepository.findAll();
	}

	public Customer findOne(int customerId) {
		return this.customerRepository.findOne(customerId);
	}

	public Customer save(Customer customer) {
		return this.customerRepository.save(customer);
	}

	public void delete(Customer customer) {
		this.customerRepository.delete(customer);
	}

	public Customer getCustomerByUserName(String username) {
		return this.customerRepository.getCustomerByUsername(username);
	}

	// Auxiliar methods
	public Customer securityAndCustomer() {
		UserAccount userAccount = LoginService.getPrincipal();
		String username = userAccount.getUsername();
		Customer loggedCustomer = this.customerRepository.getCustomerByUsername(username);

		List<Authority> authorities = (List<Authority>) loggedCustomer.getUserAccount().getAuthorities();
		Assert.isTrue(authorities.get(0).toString().equals("CUSTOMER"));

		return loggedCustomer;
	}

	public static boolean validateCreditCardNumber(String str) {

		int[] ints = new int[str.length()];
		for (int i = 0; i < str.length(); i++) {
			ints[i] = Integer.parseInt(str.substring(i, i + 1));
		}
		for (int i = ints.length - 2; i >= 0; i = i - 2) {
			int j = ints[i];
			j = j * 2;
			if (j > 9) {
				j = j % 10 + 1;
			}
			ints[i] = j;
		}
		int sum = 0;
		for (int i = 0; i < ints.length; i++) {
			sum += ints[i];
		}
		if (sum % 10 == 0) {
			return true;
		} else {
			return false;
		}
	}

	// Métodos solicitados
	public Collection<FixUpTask> showFixUpTasks(int customerId) {
		return this.customerRepository.findFixUpTasksById(customerId);
	}

	public Customer getCustomerByUsername(String username) {
		return this.customerRepository.getCustomerByUsername(username);
	}

	// FixUpTasks
	public Collection<FixUpTask> showFixUpTasks() {
		Customer loggedCustomer = this.securityAndCustomer();

		return this.customerRepository.findFixUpTasksById(loggedCustomer.getId());
	}

	public FixUpTask getFixUpTask(int fixUpTaskId) {
		Customer loggedCustomer = this.securityAndCustomer();
		Collection<FixUpTask> fixUpTasks = this.customerRepository.findFixUpTasksById(loggedCustomer.getId());

		FixUpTask fixUpTask = null;
		for (FixUpTask f : fixUpTasks) {
			if (f.getId() == fixUpTaskId) {
				fixUpTask = f;
				break;
			}
		}

		Assert.notNull(fixUpTask);

		return fixUpTask;
	}

	public FixUpTask createFixUpTask() {
		Customer loggedCustomer = this.securityAndCustomer();

		FixUpTask fixUpTask = this.fixUpTaskService.create();

		FixUpTask fixUpTaskSaved = this.fixUpTaskService.save(fixUpTask);

		List<FixUpTask> listf = new ArrayList<>();
		listf.addAll(loggedCustomer.getFixUpTasks());
		listf.add(fixUpTask);
		loggedCustomer.setFixUpTasks(listf);

		this.save(loggedCustomer);

		this.configurationService.isActorSuspicious(loggedCustomer);

		return fixUpTaskSaved;

	}

	public FixUpTask createFixUpTask(String description, String address, Double maxPrice, Date realizationTime, Warranty warranty, Collection<Phase> phases, Category category, Collection<Complaint> complaints, Collection<Application> applications) {
		Customer loggedCustomer = this.securityAndCustomer();

		FixUpTask fixUpTask = this.fixUpTaskService.create(description, address, maxPrice, realizationTime, warranty, phases, category, complaints, applications);

		FixUpTask fixUpTaskSaved = this.fixUpTaskService.save(fixUpTask);

		List<FixUpTask> listf = new ArrayList<>();
		listf.addAll(loggedCustomer.getFixUpTasks());
		listf.add(fixUpTask);
		loggedCustomer.setFixUpTasks(listf);

		this.save(loggedCustomer);

		this.configurationService.isActorSuspicious(loggedCustomer);

		return fixUpTaskSaved;

	}

	public FixUpTask updateFixUpTask(FixUpTask fixUpTask) {
		Customer loggedCustomer = this.securityAndCustomer();

		Collection<FixUpTask> fixUpTasks = this.customerRepository.findFixUpTasksById(loggedCustomer.getId());

		FixUpTask fixUpTaskFound = null;
		for (FixUpTask f : fixUpTasks) {
			if (fixUpTask.getId() == f.getId()) {
				fixUpTaskFound = f;
				break;
			}
		}

		Assert.isTrue(!fixUpTaskFound.equals(null));

		FixUpTask fixUpTaskSaved = this.fixUpTaskService.save(fixUpTask);

		this.configurationService.isActorSuspicious(loggedCustomer);

		return fixUpTaskSaved;

	}

	public void deleteFixUpTask(FixUpTask fixUpTask) {
		Customer loggedCustomer = this.securityAndCustomer();

		Collection<FixUpTask> fixUpTasks = this.customerRepository.findFixUpTasksById(loggedCustomer.getId());

		FixUpTask fixUpTaskFounded = null;
		for (FixUpTask f : fixUpTasks) {
			if (fixUpTask.getId() == f.getId()) {
				fixUpTaskFounded = f;
				break;
			}
		}

		Assert.notNull(fixUpTaskFounded);

		List<FixUpTask> fixUpTasks2 = loggedCustomer.getFixUpTasks();
		fixUpTasks2.remove(this.fixUpTaskService.findOne(fixUpTaskFounded.getId()));
		loggedCustomer.setFixUpTasks(fixUpTasks2);
		this.customerRepository.save(loggedCustomer);

		List<Application> applications = (List<Application>) this.applicationService.findAll();
		List<Application> applicationsNew = new ArrayList<>();
		for (Application a : applications) {
			if (a.getFixUpTask().equals(this.fixUpTaskService.findOne(fixUpTaskFounded.getId()))) {
				applicationsNew.add(a);
			}
		}

		List<HandyWorker> workers = (List<HandyWorker>) this.handyWorkerService.findAll();
		for (HandyWorker h : workers) {
			List<Application> applicationsHw = h.getApplications();
			for (Application ap : applicationsNew) {
				if (applicationsHw.contains(this.applicationService.findOne(ap.getId()))) {
					List<Application> applicationsHw2 = h.getApplications();
					applicationsHw2.remove(this.applicationService.findOne(ap.getId()));
					h.setApplications(applicationsHw2);
					this.handyWorkerService.save(h);
				}
			}
		}

		for (Application app : applicationsNew) {
			this.applicationService.delete2(this.applicationService.findOne(app.getId()));
		}

		List<Finder> finders = (List<Finder>) this.finderService.findAll();
		for (Finder fi : finders) {
			List<FixUpTask> fixUpTasksFi = fi.getFixUpTasks();
			if (fixUpTasksFi.contains(this.fixUpTaskService.findOne(fixUpTaskFounded.getId()))) {
				fixUpTasksFi.remove(this.fixUpTaskService.findOne(fixUpTaskFounded.getId()));
				fi.setFixUpTasks(fixUpTasksFi);
				this.finderService.save(fi);
			}
		}

		this.fixUpTaskService.delete(fixUpTaskFounded);
	}

	// COMPLAINTS
	public Collection<Complaint> showComplaints() {
		Customer loggedCustomer = this.securityAndCustomer();

		return this.customerRepository.findComplaintsById(loggedCustomer.getId());
	}

	public Complaint getComplaint(int complaintId) {
		Customer loggedCustomer = this.securityAndCustomer();

		Collection<Complaint> complaints = this.customerRepository.findComplaintsById(loggedCustomer.getId());

		Complaint complaintFound = null;
		for (Complaint c : complaints) {
			if (complaintId == c.getId()) {
				complaintFound = c;
				break;
			}
		}

		Assert.notNull(complaintFound);

		return complaintFound;
	}

	public Complaint createComplaint(FixUpTask fixUpTask) {
		Customer loggedCustomer = this.securityAndCustomer();

		Complaint complaint = this.complaintService.create();

		Complaint complaintSaved = this.complaintService.save(complaint);

		Collection<FixUpTask> fixUpTasks = this.customerRepository.findFixUpTasksById(loggedCustomer.getId());

		FixUpTask fixUpTaskFound = null;
		for (FixUpTask f : fixUpTasks) {
			if (fixUpTask.getId() == f.getId()) {
				fixUpTaskFound = f;
				break;
			}
		}

		Assert.isTrue(!fixUpTaskFound.equals(null));

		List<Complaint> complaints = (List<Complaint>) fixUpTaskFound.getComplaints();
		complaints.add(complaint);
		fixUpTaskFound.setComplaints(complaints);

		this.fixUpTaskService.save(fixUpTaskFound);

		this.configurationService.isActorSuspicious(loggedCustomer);

		return complaintSaved;
	}

	public Complaint createComplaint(FixUpTask fixUpTask, String description, List<String> attachments) {
		Customer loggedCustomer = this.securityAndCustomer();

		Complaint complaint = this.complaintService.create(description, attachments);

		Complaint complaintSaved = this.complaintService.save(complaint);

		Collection<FixUpTask> fixUpTasks = this.customerRepository.findFixUpTasksById(loggedCustomer.getId());

		FixUpTask fixUpTaskFound = null;
		for (FixUpTask f : fixUpTasks) {
			if (fixUpTask.getId() == f.getId()) {
				fixUpTaskFound = f;
				break;
			}
		}

		Assert.isTrue(!fixUpTaskFound.equals(null));

		List<Complaint> complaints = (List<Complaint>) fixUpTaskFound.getComplaints();
		complaints.add(complaint);
		fixUpTaskFound.setComplaints(complaints);

		this.fixUpTaskService.save(fixUpTaskFound);

		this.configurationService.isActorSuspicious(loggedCustomer);

		return complaintSaved;
	}

	// APPLICATIONS
	public Collection<Application> showApplications() {
		Customer loggedCustomer = this.securityAndCustomer();

		return this.customerRepository.findApplicationsById(loggedCustomer.getId());
	}

	public Application editApplication(Application application, CreditCard creditCard) {
		Customer loggedCustomer = this.securityAndCustomer();

		Collection<Application> applications = this.customerRepository.findApplicationsById(loggedCustomer.getId());

		Application applicationFound = null;
		for (Application a : applications) {
			if (application.getId() == a.getId()) {
				applicationFound = a;
				break;
			}
		}

		Assert.isTrue(!applicationFound.equals(null));

		if (application.getStatus().equals(Status.ACCEPTED)) {
			Assert.notNull(creditCard);
			Long number = creditCard.getNumber();
			Assert.isTrue(CustomerService.validateCreditCardNumber(number.toString()));
		}

		Application applicationSave = this.applicationService.save(application);

		this.configurationService.isActorSuspicious(loggedCustomer);

		return applicationSave;
	}

	//SIN TESTEAR
	public Application addComment(Application application, String comment) {
		Customer loggedCustomer = this.securityAndCustomer();

		Collection<Application> applications = this.customerRepository.findApplicationsById(loggedCustomer.getId());

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

		this.configurationService.isActorSuspicious(loggedCustomer);

		return applicationSave;
	}

	// NOTES
	public void createNote(Report report, Note note) {
		Customer loggedCustomer = this.securityAndCustomer();

		Collection<Report> reports = this.customerRepository.findReportsById(loggedCustomer.getId());

		Assert.isTrue(reports.contains(report));

		List<Note> notes = report.getNotes();
		List<String> usernames = note.getUsernames();

		usernames.add(loggedCustomer.getUserAccount().getUsername());
		note.setUsernames(usernames);
		notes.add(note);

		report.setNotes(notes);
		this.reportService.save(report);

		this.configurationService.isActorSuspicious(loggedCustomer);

	}

	public List<Note> showNotes(Report report) {
		Customer loggedCustomer = this.securityAndCustomer();

		Collection<Report> reports = this.customerRepository.findReportsById(loggedCustomer.getId());

		Assert.isTrue(reports.contains(report));
		return report.getNotes();
	}

	public List<String> showNotesComments(Note note, Customer customer) {

		Collection<Note> notes = this.customerRepository.findNotesById(customer.getId());

		Assert.isTrue(notes.contains(note));
		return note.getOptionalComments();
	}

	//TODO Sobra
	public Note createNote(Report report, String mandatoryComment, List<String> optionalComments) {
		Customer loggedCustomer = this.securityAndCustomer();

		Note note = this.noteService.create(mandatoryComment, optionalComments);

		Collection<Report> reports = this.customerRepository.findReportsById(loggedCustomer.getId());

		Report reportFound = null;
		for (Report r : reports) {
			if (report.getId() == r.getId()) {
				reportFound = r;
				break;
			}
		}

		Assert.notNull(reportFound);

		List<Note> notes = report.getNotes();
		notes.add(note);

		report.setNotes(notes);

		Note noteSaved = this.noteService.save(note);
		this.reportService.save(report);

		this.configurationService.isActorSuspicious(loggedCustomer);

		return noteSaved;
	}

	public Note addComent(Note note, String comment) {
		Customer loggedCustomer = this.securityAndCustomer();

		Collection<Note> notes = this.customerRepository.findNotesById(loggedCustomer.getId());

		Assert.isTrue(notes.contains(note));
		Assert.isTrue(!note.getUsernames().contains(loggedCustomer.getUserAccount().getUsername()));
		Assert.isTrue(comment != "");
		Assert.notNull(comment);

		List<String> usernames = note.getUsernames();
		List<String> comments = note.getOptionalComments();
		usernames.add(loggedCustomer.getUserAccount().getUsername());
		comments.add(comment);
		note.setOptionalComments(comments);
		note.setUsernames(usernames);
		Note savedNote = this.noteService.save(note);

		this.configurationService.isActorSuspicious(loggedCustomer);

		return savedNote;
	}
	// ENDORSMENTS
	public Collection<Endorsment> showEndorsments() {
		Customer loggedCustomer = this.securityAndCustomer();

		return this.customerRepository.AllEndorsmentsById(loggedCustomer.getId());
	}

	public Endorsment getEndorsment(int endorsmentId) {
		Customer loggedCustomer = this.securityAndCustomer();

		Collection<Endorsment> endorsments = this.customerRepository.AllEndorsmentsById(loggedCustomer.getId());

		Endorsment endorsmentFound = null;
		for (Endorsment e : endorsments) {
			if (e.getId() == endorsmentId) {
				endorsmentFound = e;
				break;
			}
		}

		Assert.notNull(endorsmentFound);

		return endorsmentFound;
	}

	public void loggedAsEndorser() {
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();

		List<Authority> authorities = (List<Authority>) userAccount.getAuthorities();
		Assert.isTrue(authorities.get(0).toString().equals("CUSTOMER"));
	}

	public Endorsment createEndorsment(List<String> comments, HandyWorker writtenTo) {
		Customer loggedCustomer = this.securityAndCustomer();

		Assert.isTrue(writtenTo.getClass().equals(HandyWorker.class));

		Collection<HandyWorker> handyWorkers = this.customerRepository.handyWorkersById(loggedCustomer.getId());

		HandyWorker handyWorkerFound = null;
		for (HandyWorker h : handyWorkers) {
			if (h.getId() == writtenTo.getId()) {
				handyWorkerFound = h;
				break;
			}
		}

		Assert.notNull(handyWorkerFound);

		Endorsment endorsment = this.endorsmentService.createEndorsment(comments, writtenTo);

		Endorsment endorsmentSave = this.endorsmentService.save(endorsment);

		this.configurationService.isActorSuspicious(loggedCustomer);

		return endorsmentSave;
	}

	public Endorsment updateEndorsment(Endorsment endorsment) {
		Customer loggedCustomer = this.securityAndCustomer();

		Collection<Endorsment> endorsments = this.customerRepository.endorsmentsOfById(loggedCustomer.getId());

		Endorsment endorsmentFound = null;
		for (Endorsment e : endorsments) {
			if (e.getId() == endorsment.getId()) {
				endorsmentFound = e;
				break;
			}
		}

		Assert.notNull(endorsmentFound);

		Endorsment endorsmentSave = this.endorsmentService.save(endorsment);

		this.configurationService.isActorSuspicious(loggedCustomer);

		return endorsmentSave;
	}

	public void deleteEndorsment(Endorsment endorsment) {
		Customer loggedCustomer = this.securityAndCustomer();

		Collection<Endorsment> endorsments = this.customerRepository.endorsmentsOfById(loggedCustomer.getId());

		Endorsment endorsmentFound = null;
		for (Endorsment e : endorsments) {
			if (e.getId() == endorsment.getId()) {
				endorsmentFound = e;
				break;
			}
		}

		Assert.notNull(endorsmentFound);

		this.endorsmentService.delete(endorsment);
	}

	// REPORTS
	public Report showReport(Report report) {
		Customer loggedCustomer = this.securityAndCustomer();
		Assert.isTrue(report.getFinalMode());
		return this.reportService.findOne(report.getId());
	}

	public List<Report> listReports(Complaint complaint) {
		Customer loggedCustomer = this.securityAndCustomer();
		Assert.isTrue(this.showComplaints().contains(complaint));
		List<Report> lr = complaint.getReports();
		List<Report> lr2 = new ArrayList<>();
		for (Report report : lr) {
			if (report.getFinalMode()) {
				lr2.add(report);
			}
		}
		return lr2;
	}

	public List<HandyWorker> getHandyWorkersById(int customerId) {
		return (List<HandyWorker>) this.customerRepository.handyWorkersById(customerId);
	}
}
