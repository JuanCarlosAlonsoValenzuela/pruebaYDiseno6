
package services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import repositories.MessageRepository;
import security.LoginService;
import security.UserAccount;
import domain.Actor;
import domain.Box;
import domain.Message;
import domain.Priority;

@Service
@Transactional
public class MessageService {

	@Autowired
	private MessageRepository		messageRepository;

	@Autowired
	private ActorService			actorService;

	@Autowired
	private BoxService				boxService;

	@Autowired
	private ConfigurationService	configurationService;

	@Autowired
	private HandyWorkerService		handyWorkerService;


	// Actualizar caja que tiene el mensaje EN ESTE ORDEN
	// ACTUALIZAR CAJA SIN EL MENSAJE
	// BORRAR EL MENSAJE Y TODAS SUS COPIAS
	public void delete(Message m) {
		this.messageRepository.delete(m);
	}

	public Message sendMessageBroadcasted(Message message) {

		this.actorService.loggedAsActor();

		Box boxRecieved = new Box();
		Box boxSpam = new Box();
		Box boxSent = new Box();

		List<String> spam = new ArrayList<String>();

		spam = this.configurationService.getSpamWords();

		Message messageSaved = this.messageRepository.saveAndFlush(message);
		Message messageCopy = this.create(messageSaved.getSubject(), messageSaved.getBody(), messageSaved.getPriority(), messageSaved.getReceiver());
		Message messageCopySaved = this.messageRepository.save(messageCopy);

		boxSent = this.boxService.getSentBoxByActor(messageSaved.getSender());
		boxRecieved = this.boxService.getRecievedBoxByActor(messageSaved.getReceiver());
		boxSpam = this.boxService.getSpamBoxByActor(messageSaved.getReceiver());

		// Guardar la box con ese mensaje;

		if (this.configurationService.isStringSpam(messageSaved.getBody(), spam) || this.configurationService.isStringSpam(messageSaved.getSubject(), spam)) {
			boxSent.getMessages().add(messageSaved);
			boxSpam.getMessages().add(messageCopySaved);

			this.boxService.saveSystem(boxSent);
			this.boxService.saveSystem(boxSpam);
			this.actorService.save(messageSaved.getSender());
			this.actorService.flushSave(messageSaved.getReceiver());

		} else {
			boxRecieved.getMessages().add(messageCopySaved);
			boxSent.getMessages().add(messageSaved);
			//boxRecieved.setMessages(list);
			this.boxService.saveSystem(boxSent);
			this.boxService.saveSystem(boxRecieved);
			this.actorService.save(messageSaved.getSender());
			this.actorService.flushSave(messageSaved.getReceiver());
		}
		return messageSaved;
	}

	// Metodo para enviar un mensaje a un ACTOR (O varios, que tambien puede ser)
	public Message sendMessage(Message message) {

		this.actorService.loggedAsActor();
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();

		Actor sender = this.actorService.getActorByUsername(userAccount.getUsername());

		Actor actorRecieved = message.getReceiver();

		Box boxRecieved = new Box();
		Box boxSpam = new Box();
		Box boxSent = new Box();

		List<String> spam = new ArrayList<String>();

		spam = this.configurationService.getSpamWords();

		Message messageSaved = this.messageRepository.save(message);
		Message messageCopy = this.create(messageSaved.getSubject(), messageSaved.getBody(), messageSaved.getPriority(), messageSaved.getReceiver());
		Message messageCopySaved = this.messageRepository.save(messageCopy);
		boxSent = this.boxService.getSentBoxByActor(messageSaved.getSender());
		boxRecieved = this.boxService.getRecievedBoxByActor(actorRecieved);
		boxSpam = this.boxService.getSpamBoxByActor(actorRecieved);

		// Guardar la box con ese mensaje;

		if (this.configurationService.isStringSpam(messageSaved.getBody(), spam) || this.configurationService.isStringSpam(messageSaved.getSubject(), spam)) {
			boxSent.getMessages().add(messageSaved);
			boxSpam.getMessages().add(messageCopySaved);

			this.boxService.saveSystem(boxSent);
			this.boxService.saveSystem(boxSpam);
			this.actorService.save(messageSaved.getSender());
			this.actorService.save(actorRecieved);

		} else {
			boxRecieved.getMessages().add(messageCopySaved);
			boxSent.getMessages().add(messageSaved);
			//boxRecieved.setMessages(list);
			this.boxService.saveSystem(boxSent);
			this.boxService.saveSystem(boxRecieved);
			this.actorService.save(messageSaved.getSender());
			this.actorService.save(actorRecieved);
		}
		return messageSaved;
	}

	public Message sendMessageAnotherSender(Message message) {
		Actor sender = message.getSender();

		Actor actorRecieved = message.getReceiver();
		List<String> spam = new ArrayList<String>();

		spam = this.configurationService.getSpamWords();

		Box boxRecieved = new Box();
		Box boxSpam = new Box();
		Box boxSent = new Box();

		Message messageSaved = this.messageRepository.save(message);
		Message messageCopy = this.createNotification(messageSaved.getSubject(), messageSaved.getBody(), messageSaved.getPriority(), messageSaved.getReceiver());
		Message messageCopySaved = this.messageRepository.save(messageCopy);
		boxSent = this.boxService.getSentBoxByActor(messageSaved.getSender());
		boxRecieved = this.boxService.getRecievedBoxByActor(actorRecieved);
		boxSpam = this.boxService.getSpamBoxByActor(actorRecieved);

		// Guardar la box con ese mensaje;

		if (this.configurationService.isStringSpam(messageSaved.getBody(), spam) || this.configurationService.isStringSpam(messageSaved.getSubject(), spam)) {
			boxSent.getMessages().add(messageSaved);
			boxSpam.getMessages().add(messageCopySaved);

			this.boxService.saveSystem(boxSent);
			this.boxService.saveSystem(boxSpam);
			this.actorService.save(messageSaved.getSender());
			this.actorService.save(actorRecieved);

		} else {
			boxRecieved.getMessages().add(messageCopySaved);
			boxSent.getMessages().add(messageSaved);
			//boxRecieved.setMessages(list);
			this.boxService.saveSystem(boxSent);
			this.boxService.saveSystem(boxRecieved);
			this.actorService.save(messageSaved.getSender());
			this.actorService.save(actorRecieved);
		}
		return messageSaved;
	}
	public Message save(Message message) {
		return this.messageRepository.save(message);

	}

	public Message create() {

		this.actorService.loggedAsActor();

		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		String userName = userAccount.getUsername();

		Date thisMoment = new Date();
		thisMoment.setTime(thisMoment.getTime() - 1000);

		Message message = new Message();
		Actor sender = this.actorService.getActorByUsername(userAccount.getUsername());
		Actor receiver = new Actor();
		message.setMoment(thisMoment);
		message.setSubject("");
		message.setBody("");
		message.setPriority(Priority.LOW);
		message.setReceiver(receiver);
		message.setTags("");
		message.setSender(sender);

		return message;
	}

	public Message create(String Subject, String body, Priority priority, Actor recipient) {

		this.actorService.loggedAsActor();

		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		String userName = userAccount.getUsername();

		Date thisMoment = new Date();
		thisMoment.setTime(thisMoment.getTime() - 1);

		Message message = new Message();

		Actor sender = this.actorService.getActorByUsername(userAccount.getUsername());

		message.setMoment(thisMoment);
		message.setSubject(Subject);
		message.setBody(body);
		message.setPriority(Priority.LOW);
		message.setReceiver(recipient);
		message.setTags("");
		message.setSender(sender);

		return message;
	}

	public Message createNotification(String Subject, String body, Priority priority, Actor recipient) {
		this.actorService.loggedAsActor();

		Date thisMoment = new Date();
		thisMoment.setTime(thisMoment.getTime() - 1);
		List<String> tags = new ArrayList<String>();

		Message message = new Message();

		Actor sender = this.actorService.getActorByUsername("system");

		message.setMoment(thisMoment);
		message.setSubject(Subject);
		message.setBody(body);
		message.setPriority(priority);
		message.setReceiver(recipient);
		message.setTags("");
		message.setSender(sender);

		return message;
	}

	public void updateMessage(Message message, Box box) { // Posible problema
		// con copia

		this.actorService.loggedAsActor();
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Actor actor = this.actorService.getActorByUsername(userAccount.getUsername());

		for (Box b : actor.getBoxes()) {
			if (b.getMessages().contains(message)) {
				List<Message> list = b.getMessages();
				b.getMessages().remove(message);
				//list.remove(message);
				//b.setMessages(list);
			}
			if (b.getName().equals(box.getName())) {
				List<Message> list = b.getMessages();
				list.add(message);
				b.setMessages(list);
			}
		}
	}

	public void deleteMessageToTrashBox(Message message) {
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Actor actor = this.actorService.getActorByUsername(userAccount.getUsername());

		//Box currentBox = this.boxService.getCurrentBoxByMessage(message);

		List<Box> currentBoxes = new ArrayList<>();

		for (Box b : actor.getBoxes()) {
			if (b.getMessages().contains(message)) {
				currentBoxes.add(b);
			}
		}

		Box trash = this.boxService.getTrashBoxByActor(actor);

		// When an actor removes a message from a box other than trash box, it
		// is moved to the trash box;
		for (Box currentBox : currentBoxes) {
			if (currentBox.equals(trash)) {
				for (Box b : actor.getBoxes()) {
					if (b.getMessages().contains(message)) {
						b.getMessages().remove(message);
						this.messageRepository.delete(message);
					}
				}
			} else {
				this.updateMessage(message, trash);
				// this.messageRepository.save(message); Si se pone en el metodo
				// updateMessage no hace falta aqui
			}
		}
	}

	public void copyMessage(Message message, Box box) {

		this.actorService.loggedAsActor();
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Actor actor = this.actorService.getActorByUsername(userAccount.getUsername());

		for (Box b : actor.getBoxes()) {
			if (b.getName().equals(box.getName())) {
				List<Message> list = b.getMessages();
				list.add(message);
				b.setMessages(list);
			}
		}
	}

	public List<Message> findAll() {
		return this.messageRepository.findAll();
	}

	public List<Message> findAll2() {
		return this.messageRepository.findAll2();
	}

	public Message findOne(int id) {
		return this.messageRepository.findOne(id);
	}

	public List<Message> getMessagesByBox(Box b) {
		return b.getMessages();
	}
}
