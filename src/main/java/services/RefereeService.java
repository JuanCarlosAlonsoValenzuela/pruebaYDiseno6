
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.RefereeRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Box;
import domain.Complaint;
import domain.Message;
import domain.Note;
import domain.Referee;
import domain.Report;
import domain.SocialProfile;

@Service
@Transactional
public class RefereeService {

	// Managed repository ------------------------------------------

	@Autowired
	private RefereeRepository		refereeRepository;

	// Supporting Services ------------------------------------------

	@Autowired
	private ComplaintService		complaintService;
	@Autowired
	private NoteService				noteService;
	@Autowired
	private ReportService			reportService;
	@Autowired
	private ActorService			actorService;
	@Autowired
	private ConfigurationService	configurationService;


	//Aux
	public Referee securityAndReferee() {
		UserAccount userAccount = LoginService.getPrincipal();
		String username = userAccount.getUsername();
		Referee loggedReferee = this.refereeRepository.getRefereeByUsername(username);

		List<Authority> authorities = (List<Authority>) loggedReferee.getUserAccount().getAuthorities();
		Assert.isTrue(authorities.get(0).toString().equals("REFEREE"));

		return loggedReferee;
	}

	// CRUD Methods -------------------------------------------------

	public Referee create() {

		// SE DECLARA EL SPONSOR
		Referee s = new Referee();

		// SE CREAN LAS LISTAS VACIAS
		List<SocialProfile> socialProfiles = new ArrayList<SocialProfile>();
		List<Box> boxes = new ArrayList<Box>();
		List<Report> reports = new ArrayList<Report>();

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
		s.setUserAccount(userAccountActor);
		s.setReports(reports);
		// SPAM SIEMPRE A FALSE EN LA INICIALIZACION
		s.setHasSpam(false);

		List<Authority> authorities = new ArrayList<Authority>();

		Authority authority = new Authority();
		authority.setAuthority(Authority.SPONSOR);
		authorities.add(authority);

		s.getUserAccount().setAuthorities(authorities);
		//NOTLOCKED A TRUE EN LA INICIALIZACION, O SE CREARA UNA CUENTA BANEADA
		s.getUserAccount().setIsNotLocked(true);

		return s;
	}

	public Referee create(String name, String middleName, String surname, String photo, String email, String phoneNumber, String address, String userName, String password) {

		// SE DECLARA EL SPONSOR
		Referee s = new Referee();

		// SE CREAN LAS LISTAS VACIAS
		List<SocialProfile> socialProfiles = new ArrayList<SocialProfile>();
		List<Box> boxes = new ArrayList<Box>();
		List<Report> reports = new ArrayList<Report>();

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
		s.setReports(reports);
		// SPAM SIEMPRE A FALSE EN LA INICIALIZACION
		s.setHasSpam(false);

		List<Authority> authorities = new ArrayList<Authority>();

		Authority authority = new Authority();
		authority.setAuthority(Authority.SPONSOR);
		authorities.add(authority);

		s.getUserAccount().setAuthorities(authorities);
		//NOTLOCKED A TRUE EN LA INICIALIZACION, O SE CREARA UNA CUENTA BANEADA
		s.getUserAccount().setIsNotLocked(true);

		return s;
	}

	public Referee save(Referee referee) {
		return this.refereeRepository.save(referee);
	}

	public Referee findOne(int refereeId) {
		return this.findOne(refereeId);
	}

	public void delete(Referee referee) {
		this.refereeRepository.delete(referee);
	}

	public List<Referee> findAll() {
		return this.refereeRepository.findAll();
	}

	public Referee getRefereeByUsername(String username) {
		return this.refereeRepository.getRefereeByUsername(username);
	}

	public Collection<Complaint> complaintsUnassigned() {
		return this.refereeRepository.complaintsUnassigned();
	}

	public Collection<Note> notesReferee(int refereeId) {
		return this.refereeRepository.notesReferee(refereeId);
	}

	// Methods ------------------------------------------------------

	public List<Complaint> unassignedComplaints() {
		this.securityAndReferee();
		return (List<Complaint>) this.refereeRepository.complaintsUnassigned();
	}

	public Complaint assingComplaint(Complaint complaint) {
		Referee loggedReferee = this.securityAndReferee();
		List<Complaint> unassignedComplaints = (List<Complaint>) this.refereeRepository.complaintsUnassigned();
		Complaint comp = new Complaint();
		for (Complaint c : unassignedComplaints)
			if (c == complaint)
				c = comp;
		Assert.notNull(comp);
		complaint.setReferee(loggedReferee);
		Complaint complaintSaved = this.complaintService.save(complaint);
		this.configurationService.isActorSuspicious(loggedReferee);
		return complaintSaved;
	}

	public List<Complaint> selfAssignedComplaints() {
		Referee loggedReferee = this.securityAndReferee();
		List<Complaint> res = new ArrayList<>();
		List<Complaint> complaints = this.complaintService.findAll();
		for (Complaint c : complaints)
			if (c.getReferee() == loggedReferee) {
				Assert.notNull(c);
				res.add(c);
			}
		return res;
	}

	public Note writeNoteReport(Report report, String mandatoryComment, List<String> optionalComments) {
		Referee loggedReferee = this.securityAndReferee();
		List<Report> lr = loggedReferee.getReports();
		Report rep = null;
		for (Report r : lr)
			if (r.getId() == report.getId() && r.getFinalMode() && report.getFinalMode())
				rep = r;
		Assert.notNull(rep);
		Note note = this.noteService.create(mandatoryComment, optionalComments);
		Assert.notNull(note);
		Note noteSaved = this.noteService.save(note);
		Assert.notNull(noteSaved);

		List<Note> notes = this.reportService.findOne(rep.getId()).getNotes();
		notes.add(noteSaved);
		rep.setNotes(notes);
		Report reportSaved = this.reportService.save(rep);
		Assert.notNull(reportSaved);

		this.configurationService.isActorSuspicious(loggedReferee);
		return noteSaved;
	}

	public Note writeComment(String comment, Note note) {
		Referee loggedReferee = this.securityAndReferee();
		List<Note> notes = (List<Note>) this.refereeRepository.notesReferee(loggedReferee.getId());
		Note no = null;
		for (Note n : notes)
			if (n.getId() == note.getId())
				no = n;
		Assert.notNull(no);
		Note noteFound = this.noteService.findOne(no.getId());
		List<String> comments = noteFound.getOptionalComments();
		comments.add(comment);
		noteFound.setOptionalComments(comments);
		Note noteSaved = this.noteService.save(noteFound);
		this.configurationService.isActorSuspicious(loggedReferee);
		return noteSaved;
	}

	public Report writeReportRegardingComplaint(Complaint complaint, String description, List<String> attachments, List<Note> notes) {
		Referee loggedReferee = this.securityAndReferee();
		List<Complaint> lc = this.complaintService.findAll();
		Complaint com = null;
		for (Complaint c : lc)
			if (c.getId() == complaint.getId() && c.getReferee().equals(loggedReferee) && complaint.getReferee().equals(loggedReferee)) {
				com = c;
				break;
			}
		Assert.notNull(com);

		Report rep = this.reportService.create(description, attachments, notes);
		Assert.notNull(rep);
		Report reportSaved = this.reportService.save(rep);

		List<Report> repList = loggedReferee.getReports();
		repList.add(this.reportService.findOne(reportSaved.getId()));
		loggedReferee.setReports(repList);
		this.save(loggedReferee);

		List<Report> repList2 = com.getReports();
		repList2.add(this.reportService.findOne(reportSaved.getId()));
		com.setReports(repList2);
		this.complaintService.save(com);

		this.configurationService.isActorSuspicious(loggedReferee);
		return reportSaved;
	}

	public Report modifyReport(Report report) {
		Referee loggedReferee = this.securityAndReferee();
		Assert.isTrue(!report.getFinalMode());
		Assert.isTrue(loggedReferee.getReports().contains(this.reportService.findOne(report.getId())));
		Report rp = this.reportService.save(report);
		this.configurationService.isActorSuspicious(loggedReferee);
		return rp;
	}

	public void eliminateReport(Report report) {
		Referee loggedReferee = this.securityAndReferee();
		Assert.isTrue(!report.getFinalMode());
		Assert.isTrue(loggedReferee.getReports().contains(this.reportService.findOne(report.getId())));

		List<Report> reportsOfReferee = loggedReferee.getReports();
		reportsOfReferee.remove(this.reportService.findOne(report.getId()));
		loggedReferee.setReports(reportsOfReferee);
		this.save(loggedReferee);

		for (Complaint c : this.complaintService.findAll())
			if (c.getReports().contains(this.reportService.findOne(report.getId()))) {
				List<Report> reportsOfComplaint = c.getReports();
				reportsOfComplaint.remove(this.reportService.findOne(report.getId()));
				c.setReports(reportsOfComplaint);
				this.complaintService.save(c);
			}

		this.reportService.delete(report);
		this.configurationService.isActorSuspicious(loggedReferee);
	}

}
