package services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.ActorRepository;
import security.LoginService;
import security.UserAccount;
import domain.Actor;
import domain.Box;
import domain.HandyWorker;
import domain.Message;
import domain.SocialProfile;
import domain.Tutorial;

@Service
@Transactional
public class ActorService {

	@Autowired
	private ActorRepository			actorRepository;

	@Autowired
	private SocialProfileService	socialProfileService;

	@Autowired
	private TutorialService			tutorialService;

	@Autowired
	private HandyWorkerService		handyWorkerService;

	@Autowired
	private ConfigurationService	configurationService;


	public HandyWorker saveHandyWorker(final HandyWorker handyWorker) {
		return this.actorRepository.save(handyWorker);
	}

	public List<Actor> findAll() {
		return this.actorRepository.findAll();
	}

	public Actor findOne(int id) {
		return this.actorRepository.findOne(id);
	}

	public Actor getActorByUsername(String a) {
		return this.actorRepository.getActorByUserName(a);
	}

	public List<Actor> getActors() {
		return this.actorRepository.getActors();
	}

	public void loggedAsActor() {
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Assert.isTrue(userAccount.getAuthorities().size() > 0);
	}

	public Actor createActor() {

		Actor actor = new Actor();
		List<SocialProfile> socialProfiles = new ArrayList<SocialProfile>();
		List<Box> boxes = new ArrayList<Box>();

		UserAccount userAccountActor = new UserAccount();

		userAccountActor.setUsername("");
		userAccountActor.setPassword("");

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

		actor.setName("");
		actor.setMiddleName("");
		actor.setSurname("");
		actor.setPhoto("");
		actor.setEmail("");
		actor.setPhoneNumber("");
		actor.setAddress("");
		actor.setSocialProfiles(socialProfiles);
		actor.setBoxes(boxes);
		actor.setUserAccount(userAccountActor);

		return actor;
	}

	public Actor createActor(String name, String middleName, String surname, String photo, String email, String phoneNumber, String address, String userName, String password) {

		Actor actor = new Actor();
		List<SocialProfile> socialProfiles = new ArrayList<SocialProfile>();
		List<Box> boxes = new ArrayList<Box>();

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

		actor.setName(name);
		actor.setMiddleName(middleName);
		actor.setSurname(surname);
		actor.setPhoto(photo);
		actor.setEmail(email);
		actor.setPhoneNumber(phoneNumber);
		actor.setAddress(address);
		actor.setSocialProfiles(socialProfiles);
		actor.setBoxes(boxes);
		actor.setUserAccount(userAccountActor);

		return actor;
	}

	// TODO: PREGUNTAR MARTES PUEDE QUE NO HAGA FALTA PORQUE TENEMOS EL SAVE
	public Actor updateActor(Actor actor, String name, String middleName, String surname, String photo, String email, String phoneNumber, String address) {

		// LA COMPROBACION DE QUE ESTAS LOGUEADO SE HACE EN EL ACTOR
		actor.setName(name);
		actor.setMiddleName(middleName);
		actor.setSurname(surname);
		actor.setPhoto(photo);
		actor.setEmail(email);
		actor.setPhoneNumber(phoneNumber);
		actor.setAddress(address);

		this.actorRepository.save(actor);

		return actor;
	}

	public Actor save(Actor a) {
		return this.actorRepository.save(a);
	}

	/*
	 * public Actor save(Actor actor) { Assert.isTrue(actor.getId() == 0 ||
	 * userAccount.equals(actor.getUserAccount()));
	 * this.actorRepository.save(actor); }
	 */

	// TODO: PREGUNTAR MARTES PUEDE QUE NO HAGA FALTA PORQUE TENEMOS EL SAVE
	public SocialProfile updateSocialProfiles(SocialProfile socialProfile, String nick, String name, String profileLink) {
		/*
		 * UserAccount userAccount; userAccount = LoginService.getPrincipal();
		 * Actor actor = new Actor(); actor =
		 * ActorService.getActorByUsername(userAccount.getUsername());
		 */

		// List<SocialProfile> socialProfiles = new ArrayList<SocialProfile>();
		// socialProfiles = actor.getSocialProfiles();

		// COMPROBAR EN CADA ACTOR QUE ES ESE EL ACTOR QUE ESTA LOGUEADO
		socialProfile.setName(name);
		socialProfile.setNick(nick);
		socialProfile.setProfileLink(profileLink);

		return this.socialProfileService.save(socialProfile);

	}

	/*
	 * 2. Browse the catalogue of tutorials in the system and display any of
	 * them. Note that actors must be able to see the profile of the
	 * corresponding handy workers, which includes his or her personal data and
	 * the list of tutorials that he or she’s written.
	 */

	public Map<String, Tutorial> showTutorials() {
		Map<String, Tutorial> result = new HashMap<String, Tutorial>();

		List<Tutorial> tutorials = new ArrayList<Tutorial>();

		tutorials = this.tutorialService.findAll();

		for (Tutorial t : tutorials)
			result.put(t.getTitle(), t);

		return result;
	}

	public Map<HandyWorker, List<Tutorial>> showHandyWorkers(Tutorial tutorial) {
		Map<HandyWorker, List<Tutorial>> result = new HashMap<HandyWorker, List<Tutorial>>();
		List<HandyWorker> handyWorkers = new ArrayList<HandyWorker>();
		HandyWorker requiredHandyWorker = new HandyWorker();

		handyWorkers = (List<HandyWorker>) this.handyWorkerService.findAll();

		for (HandyWorker h : handyWorkers)
			if (h.getTutorials().contains(tutorial))
				requiredHandyWorker = h;

		result.put(requiredHandyWorker, requiredHandyWorker.getTutorials());

		return result;

	}

	public List<Box> getlistOfBoxes(Actor actor) {

		return this.actorRepository.listOfBoxes(actor);
	}

	// TODO: METODO HASSPAM PARA LLAMANDO A LA FUNCION DE COMPROBAR SI TIENE
	// SPAM,
	// PORNERLE LA VARIABLE A TRUE

	public void updateActorSpam(Actor a) {

		if (this.configurationService.isActorSuspicious(a)) {
			a.setHasSpam(true);
			this.actorRepository.save(a);
		}
	}

}
