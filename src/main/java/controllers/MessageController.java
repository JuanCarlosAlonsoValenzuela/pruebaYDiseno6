
package controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import security.UserAccount;
import services.ActorService;
import services.BoxService;
import services.MessageService;
import domain.Actor;
import domain.Box;
import domain.Message;
import domain.Priority;

@Controller
@RequestMapping("/message/actor")
public class MessageController extends AbstractController {

	@Autowired
	private MessageService	messageService;

	@Autowired
	private BoxService		boxService;

	@Autowired
	private ActorService	actorService;


	//List
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam int boxId) {

		ModelAndView result;
		Box box = new Box();
		List<Message> messages;
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Actor actor = new Actor();
		List<Box> boxes = new ArrayList<Box>();
		List<Integer> idBoxes = new ArrayList<Integer>();

		idBoxes = this.boxService.getActorBoxesId();

		actor = this.actorService.getActorByUsername(userAccount.getUsername());
		boxes = this.actorService.getlistOfBoxes(actor);

		box = this.boxService.findOne(boxId);
		messages = this.messageService.getMessagesByBox(box);

		result = new ModelAndView("message/actor/list");
		result.addObject("messages", messages);
		result.addObject("boxId", boxId);
		result.addObject("boxes", boxes);

		result.addObject("requestURI", "message/actor/list.do");

		return result;
	}

	//Create
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Message message;

		message = this.messageService.create();
		result = this.createEditModelAndView(message);

		return result;
	}

	//Save
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid Message message, BindingResult binding) {
		ModelAndView result;
		Message savedMessage;
		List<Box> boxes;
		Box box;

		if (binding.hasErrors()) {
			result = this.createEditModelAndView(message);
		} else {
			try {
				savedMessage = this.messageService.sendMessage(message);
				boxes = this.boxService.getCurrentBoxByMessage(savedMessage);
				box = boxes.get(0);
				result = new ModelAndView("redirect:list.do?boxId=" + box.getId());
			} catch (Throwable oops) {
				result = this.createEditModelAndView(message, "message.commit.error");
			}
		}
		return result;
	}
	//Create
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam int rowId) {
		ModelAndView result;
		Message message;

		message = this.messageService.findOne(rowId);

		Assert.notNull(message);
		result = this.createEditModelAndView(message);

		return result;
	}

	//Save
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(Message message, BindingResult binding) {
		ModelAndView result;
		List<Box> boxes;
		Box box;
		boxes = this.boxService.getCurrentBoxByMessage(message);
		box = boxes.get(0);
		try {

			this.messageService.deleteMessageToTrashBox(message);
			result = new ModelAndView("redirect:list.do?boxId=" + box.getId());
		} catch (Throwable oops) {
			result = this.createEditModelAndView(message, "message.commit.error");

		}
		return result;
	}

	//Create
	@RequestMapping(value = "/createmove", method = RequestMethod.GET)
	public ModelAndView createMove() {
		ModelAndView result;
		Message message;

		message = this.messageService.create();
		result = this.createEditModelAndViewMove(message);

		return result;
	}

	@RequestMapping(value = "/move", method = RequestMethod.GET)
	public ModelAndView update(@RequestParam int messageId, @RequestParam int boxId) {
		ModelAndView result;
		Message message;
		Box box;

		message = this.messageService.findOne(messageId);
		box = this.boxService.findOne(boxId);

		try {
			this.messageService.updateMessage(message, box);
			result = new ModelAndView("redirect:list.do?boxId=" + boxId);
		} catch (Throwable oops) {
			result = this.createEditModelAndViewMove(message, "message.commit.error");

		}
		return result;
	}

	@RequestMapping(value = "/copy", method = RequestMethod.GET)
	public ModelAndView copy(@RequestParam int messageId, @RequestParam int boxId) {
		ModelAndView result;
		Message message;
		Box box;

		message = this.messageService.findOne(messageId);
		box = this.boxService.findOne(boxId);

		try {
			this.messageService.copyMessage(message, box);
			result = new ModelAndView("redirect:list.do?boxId=" + boxId);
		} catch (Throwable oops) {
			result = this.createEditModelAndViewMove(message, "message.commit.error");

		}
		return result;
	}

	protected ModelAndView createEditModelAndViewMove(Message message) {
		ModelAndView result;

		result = this.createEditModelAndViewMove(message, null);

		return result;
	}

	protected ModelAndView createEditModelAndViewMove(Message message, String messageCode) {
		ModelAndView result;

		UserAccount userAccount = LoginService.getPrincipal();
		String username = userAccount.getUsername();
		Actor actor = new Actor();

		actor = this.actorService.getActorByUsername(username);
		List<Actor> actors = new ArrayList<Actor>();
		actors = this.actorService.findAll();

		List<Box> actorBoxes = new ArrayList<Box>();
		actorBoxes = this.actorService.getlistOfBoxes(actor);
		List<Priority> priotity = new ArrayList<Priority>();
		priotity = Arrays.asList(Priority.values());

		result = new ModelAndView("message/actor/move");
		result.addObject("messageTest", message);
		result.addObject("actors", actors);
		result.addObject("actorBoxes", actorBoxes);
		result.addObject("priority", priotity);

		result.addObject("message", messageCode);

		return result;
	}
	//CreateEditModelAndView
	protected ModelAndView createEditModelAndView(Message message) {
		ModelAndView result;

		result = this.createEditModelAndView(message, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(Message message, String messageCode) {
		ModelAndView result;

		UserAccount userAccount = LoginService.getPrincipal();
		String username = userAccount.getUsername();
		Actor actor = new Actor();

		actor = this.actorService.getActorByUsername(username);
		List<Actor> actors = new ArrayList<Actor>();
		actors = this.actorService.findAll();

		List<Box> actorBoxes = new ArrayList<Box>();
		actorBoxes = this.actorService.getlistOfBoxes(actor);

		result = new ModelAndView("message/actor/create");
		result.addObject("messageTest", message);
		result.addObject("actors", actors);
		result.addObject("actorBoxes", actorBoxes);
		result.addObject("priority", Arrays.asList(Priority.values()));
		result.addObject("message", messageCode);

		return result;
	}
}
