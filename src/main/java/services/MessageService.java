
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


	// Actualizar caja que tiene el mensaje EN ESTE ORDEN
	// ACTUALIZAR CAJA SIN EL MENSAJE
	// BORRAR EL MENSAJE Y TODAS SUS COPIAS
	public void delete(Message m) {
		this.messageRepository.delete(m);
	}

	// Metodo para enviar un mensaje a un ACTOR (O varios, que tambien puede ser)
	public void sendMessage(Message message) {

		this.actorService.loggedAsActor();
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();

		Actor sender = this.actorService.getActorByUsername(userAccount.getUsername());

		Actor actor = message.getReceiver();

		Box boxRecieved = new Box();
		Box boxSpam = new Box();

		Message messageCopy = this.create(message.getSubject(), message.getBody(), message.getPriority(), message.getReceiver());
		Message messageCopySaved = this.save(messageCopy);

		boxRecieved = this.boxService.getRecievedBoxByActor(actor);
		boxSpam = this.boxService.getSpamBoxByActor(actor);

		// Guardar la box con ese mensaje;

		if (this.configurationService.isActorSuspicious(sender)) {
			List<Message> list = boxSpam.getMessages();
			list.add(messageCopySaved);
			boxSpam.setMessages(list);

		} else {
			List<Message> list = boxRecieved.getMessages();
			list.add(messageCopySaved);
			boxRecieved.setMessages(list);
		}
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
		thisMoment.setTime(thisMoment.getTime() - 1);
		List<String> tags = new ArrayList<String>();

		Message message = new Message();
		Actor sender = this.actorService.getActorByUsername(userAccount.getUsername());
		Actor receiver = new Actor();
		message.setMoment(thisMoment);
		message.setSubject("");
		message.setBody("");
		message.setPriority(null);
		message.setReceiver(receiver);
		message.setTags(tags);
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
		List<String> tags = new ArrayList<String>();

		Message message = new Message();

		Actor sender = this.actorService.getActorByUsername(userAccount.getUsername());

		message.setMoment(thisMoment);
		message.setSubject(Subject);
		message.setBody(body);
		message.setPriority(priority);
		message.setReceiver(recipient);
		message.setTags(tags);
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
				list.remove(message);
				b.setMessages(list);
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

		for (Box b : actor.getBoxes())
			if (b.getMessages().contains(message))
				currentBoxes.add(b);

		Box trash = this.boxService.getTrashBoxByActor(actor);

		// When an actor removes a message from a box other than trash box, it
		// is moved to the trash box;
		for (Box currentBox : currentBoxes)
			if (currentBox.equals(trash))
				for (Box b : actor.getBoxes())
					this.messageRepository.delete(message);
			else
				this.updateMessage(message, trash);
		// this.messageRepository.save(message); Si se pone en el metodo
		// updateMessage no hace falta aqui
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
