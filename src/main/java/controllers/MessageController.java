
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
	MessageService	messageService;

	@Autowired
	BoxService		boxService;

	@Autowired
	ActorService	actorService;


	//List
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam int boxId) {

		ModelAndView result;
		Box box = new Box();
		List<Message> messages;
		box = this.boxService.findOne(boxId);
		messages = this.messageService.getMessagesByBox(box);

		result = new ModelAndView("message/actor/list");
		result.addObject("messages", messages);
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
	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid Message message, BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors()) {
			result = this.createEditModelAndView(message);
		} else {
			try {
				this.messageService.sendMessage(message);
				result = new ModelAndView("redirect:#");
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

		try {
			this.messageService.deleteMessageToTrashBox(message);
			result = new ModelAndView("redirect:create.do");
		} catch (Throwable oops) {
			result = this.createEditModelAndView(message, "message.commit.error");

		}
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

		List<Actor> actors = new ArrayList<Actor>();
		actors = this.actorService.findAll();

		result = new ModelAndView("message/actor/create");
		result.addObject("messageTest", message);
		result.addObject("actors", actors);
		result.addObject("priority", Arrays.asList(Priority.values()));

		result.addObject("message", messageCode);

		return result;
	}
}
