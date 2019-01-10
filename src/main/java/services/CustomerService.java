
package services;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
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
import domain.Complaint;
import domain.CreditCard;
import domain.Customer;
import domain.Endorsement;
import domain.Finder;
import domain.FixUpTask;
import domain.HandyWorker;
import domain.Note;
import domain.Report;
import domain.SocialProfile;
import domain.Status;

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
	private EndorsementService		endorsmentService;
	@Autowired
	private ConfigurationService	configurationService;
	@Autowired
	private FinderService			finderService;
	@Autowired
	private HandyWorkerService		handyWorkerService;
	@Autowired
	private BoxService				boxService;


	public Customer create() {

		// SE DECLARA EL CUSTOMER
		Customer s = new Customer();

		// SE CREAN LAS LISTAS VACIAS
		List<SocialProfile> socialProfiles = new ArrayList<SocialProfile>();
		List<Box> boxes = new ArrayList<Box>();
		List<FixUpTask> fixUpTasks = new ArrayList<FixUpTask>();
		List<Endorsement> endorsements = new ArrayList<Endorsement>();

		// SE AÑADE EL USERNAME Y EL PASSWORD
		UserAccount userAccountActor = new UserAccount();
		userAccountActor.setUsername("");
		userAccountActor.setPassword("");

		// SE CREAN LAS CAJAS POR DEFECTO (NO)

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
		s.setEndorsements(endorsements);
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

	public Customer saveCreate(Customer customer) {	//Tenemos un listBox vac�a

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

		customer.setBoxes(boxes);

		return this.customerRepository.save(customer);
	}

	public Customer save(Customer customer) {
		return this.customerRepository.save(customer);
	}

	/*
	 * // Simple CRUD methods
	 * public Customer create(String name, String middleName, String surname, String photo, String email, String phoneNumber, String address, String userName, String password) {
	 * 
	 * // SE DECLARA EL SPONSOR
	 * Customer s = new Customer();
	 * 
	 * // SE CREAN LAS LISTAS VACIAS
	 * List<SocialProfile> socialProfiles = new ArrayList<SocialProfile>();
	 * List<Box> boxes = new ArrayList<Box>();
	 * List<FixUpTask> fixUpTasks = new ArrayList<FixUpTask>();
	 * 
	 * // SE AÑADE EL USERNAME Y EL PASSWORD
	 * UserAccount userAccountActor = new UserAccount();
	 * userAccountActor.setUsername(userName);
	 * userAccountActor.setPassword(password);
	 * 
	 * // SE CREAN LAS CAJAS POR DEFECTO
	 * Box spamBox = new Box();
	 * List<Message> messages1 = new ArrayList<>();
	 * spamBox.setIsSystem(true);
	 * spamBox.setMessages(messages1);
	 * spamBox.setName("Spam");
	 * 
	 * Box trashBox = new Box();
	 * List<Message> messages2 = new ArrayList<>();
	 * trashBox.setIsSystem(true);
	 * trashBox.setMessages(messages2);
	 * trashBox.setName("Trash");
	 * 
	 * Box sentBox = new Box();
	 * List<Message> messages3 = new ArrayList<>();
	 * sentBox.setIsSystem(true);
	 * sentBox.setMessages(messages3);
	 * sentBox.setName("Sent messages");
	 * 
	 * Box receivedBox = new Box();
	 * List<Message> messages4 = new ArrayList<>();
	 * receivedBox.setIsSystem(true);
	 * receivedBox.setMessages(messages4);
	 * receivedBox.setName("Received messages");
	 * 
	 * boxes.add(receivedBox);
	 * boxes.add(sentBox);
	 * boxes.add(spamBox);
	 * boxes.add(trashBox);
	 * 
	 * // SE AÑADEN TODOS LOS ATRIBUTOS
	 * s.setName(name);
	 * s.setMiddleName(middleName);
	 * s.setSurname(surname);
	 * s.setPhoto(photo);
	 * s.setEmail(email);
	 * s.setPhoneNumber(phoneNumber);
	 * s.setAddress(address);
	 * s.setSocialProfiles(socialProfiles);
	 * s.setBoxes(boxes);
	 * s.setUserAccount(userAccountActor);
	 * s.setFixUpTasks(fixUpTasks);
	 * s.setScore(0.);
	 * // SPAM SIEMPRE A FALSE EN LA INICIALIZACION
	 * s.setHasSpam(false);
	 * 
	 * List<Authority> authorities = new ArrayList<Authority>();
	 * 
	 * Authority authority = new Authority();
	 * authority.setAuthority(Authority.CUSTOMER);
	 * authorities.add(authority);
	 * 
	 * s.getUserAccount().setAuthorities(authorities);
	 * // NOTLOCKED A TRUE EN LA INICIALIZACION, O SE CREARA UNA CUENTA BANEADA
	 * s.getUserAccount().setIsNotLocked(true);
	 * 
	 * return s;
	 * }
	 */

	public Collection<Customer> findAll() {
		return this.customerRepository.findAll();
	}

	public Customer findOne(int customerId) {
		return this.customerRepository.findOne(customerId);
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
		List<String> spam = new ArrayList<String>();
		Boolean bol;
		spam = this.configurationService.getSpamWords();
		FixUpTask fixUpTask = this.fixUpTaskService.create();

		bol = this.configurationService.isStringSpam(fixUpTask.getDescription(), spam);
		bol = this.configurationService.isStringSpam(fixUpTask.getAddress(), spam);

		FixUpTask fixUpTaskSaved = this.fixUpTaskService.save(fixUpTask);

		List<FixUpTask> listf = new ArrayList<>();
		listf.addAll(loggedCustomer.getFixUpTasks());
		listf.add(fixUpTask);
		loggedCustomer.setFixUpTasks(listf);

		this.save(loggedCustomer);

		return fixUpTaskSaved;

	}

	public FixUpTask createFixUpTask(FixUpTask fixUpTask) {
		Customer loggedCustomer = this.securityAndCustomer();
		List<String> spam = new ArrayList<String>();
		Boolean bol;
		spam = this.configurationService.getSpamWords();

		bol = this.configurationService.isStringSpam(fixUpTask.getDescription(), spam);
		bol = this.configurationService.isStringSpam(fixUpTask.getAddress(), spam);

		FixUpTask fixUpTaskSaved = this.fixUpTaskService.save(fixUpTask);

		List<FixUpTask> listf = new ArrayList<>();
		listf.addAll(loggedCustomer.getFixUpTasks());
		listf.add(fixUpTaskSaved);
		loggedCustomer.setFixUpTasks(listf);

		this.customerRepository.save(loggedCustomer);

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

		List<String> spam = new ArrayList<String>();
		Boolean bol;
		spam = this.configurationService.getSpamWords();

		bol = this.configurationService.isStringSpam(fixUpTask.getDescription(), spam);
		bol = this.configurationService.isStringSpam(fixUpTask.getAddress(), spam);

		FixUpTask fixUpTaskSaved = this.fixUpTaskService.save(fixUpTask);

		return fixUpTaskSaved;

	}

	public FixUpTask saveFixUpTask(FixUpTask fixUpTask) {
		Customer loggedCustomer = this.securityAndCustomer();

		FixUpTask fixUpTaskSaved;

		if (fixUpTask.getId() == 0) {
			fixUpTaskSaved = this.fixUpTaskService.save(fixUpTask);

			List<FixUpTask> listf = new ArrayList<>();
			listf.addAll(loggedCustomer.getFixUpTasks());
			listf.add(fixUpTaskSaved);
			loggedCustomer.setFixUpTasks(listf);

			this.customerRepository.save(loggedCustomer);
		} else {
			Collection<FixUpTask> fixUpTasks = this.customerRepository.findFixUpTasksById(loggedCustomer.getId());

			FixUpTask fixUpTaskFound = null;
			for (FixUpTask f : fixUpTasks) {
				if (fixUpTask.getId() == f.getId()) {
					fixUpTaskFound = f;
					break;
				}
			}

			Assert.isTrue(!fixUpTaskFound.equals(null));

			List<String> spam = new ArrayList<String>();
			Boolean bol;
			spam = this.configurationService.getSpamWords();

			bol = this.configurationService.isStringSpam(fixUpTask.getDescription(), spam);
			bol = this.configurationService.isStringSpam(fixUpTask.getAddress(), spam);

			fixUpTaskSaved = this.fixUpTaskService.save(fixUpTask);
		}

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

		List<Application> applicationsNew = (List<Application>) this.applicationService.getApplicationsFix(fixUpTaskFounded);

		//BIEN HASTA AQU�

		List<HandyWorker> workers = (List<HandyWorker>) this.handyWorkerService.findAll();

		for (HandyWorker h : workers) {

			List<Application> applicationsHw = h.getApplications();
			for (Application ap : applicationsNew) {
				if (applicationsHw.contains(ap)) {
					applicationsHw.remove(ap);
				}
			}
			h.setApplications(applicationsHw);
			this.handyWorkerService.save(h);
		}

		for (Application app : applicationsNew) {
			this.applicationService.delete2(app);
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

	public Complaint createComplaint(FixUpTask fixUpTask, Complaint complaint) {
		Customer loggedCustomer = this.securityAndCustomer();

		List<FixUpTask> fixUpTasks = loggedCustomer.getFixUpTasks();

		Assert.isTrue(fixUpTasks.contains(fixUpTask));

		Assert.notNull(fixUpTask);

		complaint.setAttachments(this.listUrlsAttachmentsComplaint(complaint));

		List<Complaint> complaints = (List<Complaint>) fixUpTask.getComplaints();
		complaints.add(complaint);
		fixUpTask.setComplaints(complaints);

		//this.complaintService.save(complaint);

		List<String> spam = new ArrayList<String>();
		Boolean bol;
		spam = this.configurationService.getSpamWords();

		bol = this.configurationService.isStringSpam(complaint.getDescription(), spam);

		this.fixUpTaskService.save(fixUpTask);

		return complaint;
	}

	public Complaint createComplaint(FixUpTask fixUpTask, String description, List<String> attachments) {
		Customer loggedCustomer = this.securityAndCustomer();

		Complaint complaint = this.complaintService.create(description, attachments);

		List<FixUpTask> fixUpTasks = loggedCustomer.getFixUpTasks();

		Assert.isTrue(fixUpTasks.contains(fixUpTask));

		Assert.notNull(fixUpTask);

		List<Complaint> complaints = (List<Complaint>) fixUpTask.getComplaints();
		complaints.add(complaint);
		fixUpTask.setComplaints(complaints);

		List<String> spam = new ArrayList<String>();
		Boolean bol;
		spam = this.configurationService.getSpamWords();

		bol = this.configurationService.isStringSpam(complaint.getDescription(), spam);

		this.fixUpTaskService.save(fixUpTask);

		return complaint;
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

		List<String> spam = new ArrayList<String>();
		Boolean bol;
		spam = this.configurationService.getSpamWords();

		bol = this.configurationService.isStringSpam(comment, spam);

		List<String> comments = (List<String>) applicationFound.getComments();
		comments.add(comment);
		applicationFound.setComments(comments);

		Application applicationSave = this.applicationService.save(application);

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

		return savedNote;
	}
	// ENDORSMENTS
	public Collection<Endorsement> showEndorsments() {
		Customer loggedCustomer = this.securityAndCustomer();

		return this.customerRepository.AllEndorsmentsById(loggedCustomer.getId());
	}

	public List<Endorsement> showEndorsments1() {
		UserAccount userAccount = LoginService.getPrincipal();
		List<Authority> authorities = (List<Authority>) userAccount.getAuthorities();
		Assert.isTrue(authorities.get(0).toString().equals("CUSTOMER"));

		Customer logguedCustomer = new Customer();
		logguedCustomer = this.customerRepository.getCustomerByUsername(userAccount.getUsername());

		List<Endorsement> endorsments = logguedCustomer.getEndorsements();
		return endorsments;
	}

	public Endorsement getEndorsment(int endorsmentId) {
		Customer loggedCustomer = this.securityAndCustomer();

		Collection<Endorsement> endorsments = this.customerRepository.AllEndorsmentsById(loggedCustomer.getId());

		Endorsement endorsmentFound = null;
		for (Endorsement e : endorsments) {
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

	public void createEndorsment(Endorsement endorsement) {
		Customer logguedCustomer = this.customerRepository.getCustomerByUsername(LoginService.getPrincipal().getUsername());
		endorsement.setWrittenBy(logguedCustomer);
		HandyWorker handyWorker = this.handyWorkerService.findOne(endorsement.getWrittenTo().getId());

		Endorsement newEndorsement = this.endorsmentService.save(endorsement);

		List<Endorsement> end = logguedCustomer.getEndorsements();
		end.add(newEndorsement);
		logguedCustomer.setEndorsements(end);

		end = handyWorker.getEndorsements();
		end.add(newEndorsement);
		handyWorker.setEndorsements(end);

		this.save(logguedCustomer);
		this.handyWorkerService.save(handyWorker);
		this.configurationService.computeScore(endorsement.getWrittenBy());
		this.configurationService.computeScore(endorsement.getWrittenTo());

	}

	public Endorsement updateEndorsment(Endorsement endorsment) {
		Customer loggedCustomer = this.securityAndCustomer();

		Collection<Endorsement> endorsments = this.customerRepository.endorsmentsOfById(loggedCustomer.getId());

		Endorsement endorsmentFound = null;
		for (Endorsement e : endorsments) {
			if (e.getId() == endorsment.getId()) {
				endorsmentFound = e;
				break;
			}
		}

		Assert.notNull(endorsmentFound);

		Endorsement endorsmentSave = this.endorsmentService.save(endorsment);

		return endorsmentSave;
	}

	public void deleteEndorsment(Endorsement endorsement) {
		//		Customer loggedCustomer = this.securityAndCustomer();
		//
		//		Collection<Endorsement> endorsments = this.customerRepository.endorsmentsOfById(loggedCustomer.getId());
		//
		//		Endorsement endorsmentFound = null;
		//		for (Endorsement e : endorsments) {
		//			if (e.getId() == endorsment.getId()) {
		//				endorsmentFound = e;
		//				break;
		//			}
		//		}
		//
		//		Assert.notNull(endorsmentFound);
		//	
		//		this.endorsmentService.delete(endorsment);

		//Hacer un set de la lista de endorsements
		//Hacer save del endorser
		//Hacer delete del endorsement
		Customer logguedCustomer = this.customerRepository.getCustomerByUsername(LoginService.getPrincipal().getUsername());
		Assert.isTrue(logguedCustomer.getEndorsements().contains(endorsement));
		Assert.isTrue(endorsement.getWrittenBy().equals(logguedCustomer));

		List<Endorsement> customerEndorsements = logguedCustomer.getEndorsements();
		customerEndorsements.remove(endorsement);
		logguedCustomer.setEndorsements(customerEndorsements);
		this.customerRepository.save(logguedCustomer);

		HandyWorker handyWorkerReceptor = new HandyWorker();

		for (HandyWorker handyWorker : this.handyWorkerService.findAll()) {
			if (handyWorker.equals(endorsement.getWrittenTo())) {
				handyWorkerReceptor = handyWorker;
				break;
			}
		}
		Assert.isTrue(handyWorkerReceptor != null);

		List<Endorsement> handyWorkerEndorsements = handyWorkerReceptor.getEndorsements();
		handyWorkerEndorsements.remove(endorsement);
		handyWorkerReceptor.setEndorsements(handyWorkerEndorsements);
		this.handyWorkerService.save(handyWorkerReceptor);

		this.endorsmentService.delete(endorsement);
		this.configurationService.computeScore(handyWorkerReceptor);
		this.configurationService.computeScore(logguedCustomer);
	}

	// REPORTS
	public Report showReport(Report report) {
		this.securityAndCustomer();
		Assert.isTrue(report.getFinalMode());
		return this.reportService.findOne(report.getId());
	}

	public List<Report> listReports(Complaint complaint) {
		this.securityAndCustomer();
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

	public List<Application> findApplicationsById(int customerId) {
		return (List<Application>) this.customerRepository.findApplicationsById(customerId);
	}

	public List<String> listUrlsAttachmentsComplaint(Complaint c) {
		List<String> att = new ArrayList<String>();

		if (c.getAttachments().size() == 1 && c.getAttachments().get(0).contains(",")) {
			String attach = c.getAttachments().get(0).trim();
			List<String> attachments = Arrays.asList(attach.split(","));

			for (String a : attachments) {
				if (!a.isEmpty() && !att.contains(a.trim()) && this.isUrl(a)) {
					att.add(a.trim());
				}
			}
		}
		return att;
	}

	public Boolean isUrl(String url) {
		try {
			new URL(url).toURI();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

}
