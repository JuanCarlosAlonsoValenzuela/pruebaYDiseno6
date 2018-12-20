
package services;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.SponsorRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Box;
import domain.Message;
import domain.SocialProfile;
import domain.Sponsor;
import domain.Sponsorship;

@Service
@Transactional
public class SponsorService {

	@Autowired
	private SponsorRepository	sponsorRepository;


	public void loggedAsSponsor() {

		UserAccount userAccount = LoginService.getPrincipal();
		String username = userAccount.getUsername();
		Sponsor loggedSponsor = this.sponsorRepository.getSponsorByUsername(username);
		List<Authority> authorities = (List<Authority>) loggedSponsor.getUserAccount().getAuthorities();
		Assert.isTrue(authorities.get(0).toString().equals("SPONSOR"));

	}

	public Sponsor create() {

		// SE DECLARA EL SPONSOR
		Sponsor s = new Sponsor();

		// SE CREAN LAS LISTAS VACIAS
		List<SocialProfile> socialProfiles = new ArrayList<SocialProfile>();
		List<Box> boxes = new ArrayList<Box>();
		List<Sponsorship> sponsorships = new ArrayList<Sponsorship>();

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
		s.setSponsorships(sponsorships);
		// SPAM SIEMPRE A FALSE EN LA INICIALIZACION
		s.setHasSpam(false);

		List<Authority> authorities = new ArrayList<Authority>();

		Authority authority = new Authority();
		authority.setAuthority(Authority.SPONSOR);
		authorities.add(authority);

		s.getUserAccount().setAuthorities(authorities);
		// NOTLOCKED A TRUE EN LA INICIALIZACION, O SE CREARA UNA CUENTA BANEADA
		s.getUserAccount().setIsNotLocked(true);

		return s;
	}

	public Sponsor create(String name, String middleName, String surname, String photo, String email, String phoneNumber, String address, String userName, String password) {

		// SE DECLARA EL SPONSOR
		Sponsor s = new Sponsor();

		// SE CREAN LAS LISTAS VACIAS
		List<SocialProfile> socialProfiles = new ArrayList<SocialProfile>();
		List<Box> boxes = new ArrayList<Box>();
		List<Sponsorship> sponsorships = new ArrayList<Sponsorship>();

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
		s.setSponsorships(sponsorships);
		// SPAM SIEMPRE A FALSE EN LA INICIALIZACION
		s.setHasSpam(false);

		List<Authority> authorities = new ArrayList<Authority>();

		Authority authority = new Authority();
		authority.setAuthority(Authority.SPONSOR);
		authorities.add(authority);

		s.getUserAccount().setAuthorities(authorities);
		// NOTLOCKED A TRUE EN LA INICIALIZACION, O SE CREARA UNA CUENTA BANEADA
		s.getUserAccount().setIsNotLocked(true);

		return s;
	}

	public Sponsor save(Sponsor s) {
		return this.sponsorRepository.save(s);
	}

	public List<Sponsor> findAll() {
		return this.sponsorRepository.findAll();
	}

	public Sponsor findOne(Integer id) {
		return this.sponsorRepository.findOne(id);
	}

	public void delete(Sponsor s) {
		this.sponsorRepository.delete(s);
	}

	public Sponsor getSponsorByUsername(String username) {
		return this.sponsorRepository.getSponsorByUsername(username);
	}

}
