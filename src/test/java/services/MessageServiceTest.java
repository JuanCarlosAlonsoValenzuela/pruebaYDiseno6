
package services;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Admin;
import domain.Box;
import domain.Customer;
import domain.HandyWorker;
import domain.Message;
import domain.Priority;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class MessageServiceTest extends AbstractTest {

	@Autowired
	private MessageService		messageService;

	@Autowired
	private AdminService		adminService;

	@Autowired
	private HandyWorkerService	handyWorkerService;

	@Autowired
	private BoxService			boxService;

	@Autowired
	private CustomerService		customerService;


	@Test
	public void testSaveMessage() {
		this.authenticate("davidAdmin");

		Admin admin = this.adminService.getAdminByUsername("davidAdmin");
		HandyWorker handyWorker = this.handyWorkerService.getHandyWorkerByUsername("PepeHW");

		Message message = this.messageService.create("subject", "body", Priority.LOW, admin);
		Message saved = this.messageService.save(message);

		List<Message> messages = new ArrayList<>();
		Message message1 = this.messageService.findOne(saved.getId());
		messages.add(message1);

		Assert.isTrue(!messages.isEmpty());

		this.authenticate(null);
	}

	@Test
	public void testSendMessage() {
		this.authenticate("davidAdmin");

		Admin admin = new Admin();
		admin = this.adminService.getAdminByUsername("davidAdmin");

		HandyWorker handyWorker = new HandyWorker();
		handyWorker = this.handyWorkerService.getHandyWorkerByUsername("PepeHW");

		Message message = this.messageService.create("subject", "body", Priority.HIGH, admin);
		Message saved = this.messageService.save(message);
		this.messageService.sendMessage(saved);

		Box received = this.boxService.getRecievedBoxByActor(admin);
		List<Message> receivedMessages = received.getMessages();

		List<Message> result = new ArrayList<>();
		for (Message m : receivedMessages)
			if (m.getBody().equals(message.getBody()) && m.getSubject().equals(message.getSubject()) && m.getSender().equals(message.getSender()) && m.getReceiver().equals(message.getReceiver()))
				result.add(m);
		Assert.isTrue(result.size() != 0);

		Box received2 = this.boxService.getRecievedBoxByActor(admin);
		List<Message> receivedMessages2 = received.getMessages();

		for (Message m : receivedMessages2)
			if (m.getBody().equals(message.getBody()) && m.getSubject().equals(message.getSubject()) && m.getSender().equals(message.getSender()) && m.getReceiver().equals(message.getReceiver()))
				result.add(m);

		this.authenticate(null);
	}

	@Test
	public void testUpdateMessage() {	//TODO
		this.authenticate("davidAdmin");

		Admin admin = this.adminService.getAdminByUsername("davidAdmin");
		HandyWorker handyWorker = this.handyWorkerService.getHandyWorkerByUsername("PepeHW");

		Message message = this.messageService.create("subject", "body", Priority.LOW, admin);
		Message saved = this.messageService.save(message);

		Box trashBox = this.boxService.getTrashBoxByActor(admin);

		Assert.isTrue(!trashBox.getMessages().contains(saved));
		this.messageService.updateMessage(saved, trashBox);

		Assert.isTrue(trashBox.getMessages().contains(saved));

		this.authenticate(null);
	}

	@Test
	public void testDeleteMessage() {
		this.authenticate("PacoCustomer");
		Customer customer = this.customerService.getCustomerByUsername("PacoCustomer");
		Box recievedBox = this.boxService.getRecievedBoxByActor(customer);

		List<Message> messagesReceived = recievedBox.getMessages();
		Message messageReceived = messagesReceived.get(0);

		Assert.isTrue(messagesReceived.contains(messageReceived));

		this.messageService.deleteMessageToTrashBox(messageReceived);

		Box trashBox = this.boxService.getTrashBoxByActor(customer);
		recievedBox = this.boxService.getRecievedBoxByActor(customer);

		List<Message> messagesTrashBox = trashBox.getMessages();
		messagesReceived = recievedBox.getMessages();

		Assert.isTrue(!messagesReceived.contains(messageReceived));
		Assert.isTrue(messagesTrashBox.contains(messageReceived));

		this.authenticate(null);
	}
}
