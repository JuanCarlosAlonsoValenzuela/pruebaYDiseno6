
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
import domain.Actor;
import domain.Box;
import domain.Message;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class BoxServiceTest extends AbstractTest {

	@Autowired
	public BoxService		boxService;

	@Autowired
	public ActorService		actorService;

	@Autowired
	public MessageService	messageService;


	@Test
	public void testGetRecievedBoxByActor() {
		Actor actor = new Actor();
		Box box = new Box();
		super.authenticate("PacoCustomer");

		actor = this.actorService.getActorByUsername("PacoCustomer");
		box = this.boxService.getRecievedBoxByActor(actor);
		Assert.isTrue(box.getName().equals("Received messages"));
		super.authenticate(null);
	}

	@Test
	public void testGetSpamBoxByActor() {
		Actor actor = new Actor();
		Box box = new Box();
		super.authenticate("PacoCustomer");

		actor = this.actorService.getActorByUsername("PacoCustomer");
		box = this.boxService.getSpamBoxByActor(actor);
		Assert.isTrue(box.getName().equals("Spam"));
		super.authenticate(null);
	}

	@Test
	public void testGetTrashBoxByActor() {
		Actor actor = new Actor();
		Box box = new Box();
		super.authenticate("PacoCustomer");

		actor = this.actorService.getActorByUsername("PacoCustomer");
		box = this.boxService.getTrashBoxByActor(actor);
		Assert.isTrue(box.getName().equals("Trash"));
		super.authenticate(null);
	}

	@Test
	public void testGetCurrentBoxByMessage() {
		// message 1915 esta en la caja 1819 que es SENT del actor 1818 que es
		// Referee(Arbitraso) con userName ArbitrasoRF
		// Se le pasa un message Message m

		// Actor actor = new Actor();
		List<Box> box = new ArrayList<Box>();
		Message message = new Message();
		Box fatherBox = this.actorService.getActorByUsername("PacoCustomer").getBoxes().get(0);
		// fatherBox.getMessages().get(0);
		super.authenticate("PacoCustomer");
		// List<Message> messages = new ArrayList<Message>();
		// messages = this.messageService.findAll2();
		message = this.messageService.findOne(fatherBox.getMessages().get(0).getId());
		box = this.boxService.getCurrentBoxByMessage(message);

		System.out.println(box.size());
		Assert.isTrue(box.size() > 0);
		super.authenticate(null);

	}

	@Test
	public void testSaveBox() {
		this.authenticate("PacoCustomer");
		Box fatherBox = this.actorService.getActorByUsername("PacoCustomer").getBoxes().get(0);
		Box box = this.boxService.create("testBox", fatherBox);

		Box save = this.boxService.save(box);
		Assert.isTrue(this.actorService.getActorByUsername("PacoCustomer").getBoxes().contains(save));

		this.authenticate(null);
	}

	@Test
	public void testDelete() {
		this.authenticate("pepe4HW");
		Actor actor = new Actor();
		Box box = new Box();
		actor = this.actorService.getActorByUsername("Pepe4HW");
		box = actor.getBoxes().get(5);

		System.out.println();

		// HW with 6 box = pepe4HW
		this.boxService.deleteBox(box);

		System.out.println(actor.getBoxes());
		System.out.println(this.boxService.findOne(1872));

		Assert.isTrue(this.actorService.getActorByUsername("Pepe4HW").getBoxes().size() == 5);
		this.authenticate(null);
	}

	@Test
	public void testGetActorBoxes() {
		this.authenticate("pepeHW");

		List<Box> boxes = new ArrayList<>();

		boxes = this.boxService.getActorBoxes();

		Assert.isTrue(boxes.size() == 4);

		this.authenticate(null);
	}

	/*
	 * public void deleteBox(Box box) { Assert.isTrue(!box.getIsSystem());
	 * List<Box> sonBoxes = this.boxRepository.getSonsBox(box); if
	 * (sonBoxes.size() == 0) { for (Message m : box.getMessages())
	 * this.messageService.delete(m); this.boxRepository.delete(box); } else for
	 * (Box sonBox : sonBoxes) this.deleteBox(sonBox); this.deleteBox(box);
	 */

}
