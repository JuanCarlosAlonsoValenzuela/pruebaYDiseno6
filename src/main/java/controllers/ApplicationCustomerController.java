/*
 * CustomerController.java
 * 
 * Copyright (C) 2018 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers;

import java.util.Collection;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import security.UserAccount;
import services.ActorService;
import services.ApplicationService;
import services.ConfigurationService;
import services.CustomerService;
import services.FixUpTaskService;
import services.MessageService;
import domain.Actor;
import domain.Application;
import domain.CreditCard;
import domain.Message;
import domain.Priority;
import domain.Status;

@Controller
@RequestMapping("/application/customer/")
public class ApplicationCustomerController extends AbstractController {

	@Autowired
	private FixUpTaskService		fixUpTaskService;
	@Autowired
	private ApplicationService		applicationService;
	@Autowired
	private CustomerService			customerService;
	@Autowired
	private MessageService			messageService;
	@Autowired
	private ActorService			actorService;
	@Autowired
	private ConfigurationService	configurationService;


	// Constructors -----------------------------------------------------------

	public ApplicationCustomerController() {
		super();
	}

	// List ---------------------------------------------------------------		

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView applicationsList(@RequestParam int fixUpTaskId) {
		ModelAndView result;
		Collection<Application> applications;

		applications = this.applicationService.getApplicationsFix(this.fixUpTaskService.findOne(fixUpTaskId));

		result = new ModelAndView("customer/applications");

		result.addObject("applications", applications);
		result.addObject("fixUpTaskId", fixUpTaskId);
		result.addObject("requestURI", "application/customer/list.do");

		return result;
	}

	@RequestMapping(value = "/newComment", method = RequestMethod.GET)
	public ModelAndView newComment(@RequestParam int applicationId, @RequestParam int fixUpTaskId) {
		ModelAndView result;

		result = new ModelAndView("customer/addComment");
		result.addObject("applicationId", applicationId);
		result.addObject("fixUpTaskId", fixUpTaskId);

		return result;
	}

	@RequestMapping(value = "/saveComment", method = RequestMethod.POST, params = "create")
	public ModelAndView saveComment(@Valid int applicationId, @Valid String comment, @Valid int fixUpTaskId) {
		ModelAndView result;

		try {
			if (comment.trim().isEmpty()) {
				result = new ModelAndView("customer/addComment");
				result.addObject("comment", comment);
				result.addObject("applicationId", applicationId);
				result.addObject("fixUpTaskId", fixUpTaskId);
			} else {
				this.customerService.addComment(this.applicationService.findOne(applicationId), comment);
				result = new ModelAndView("redirect:listComments.do");
				result.addObject("fixUpTaskId", fixUpTaskId);
				result.addObject("applicationId", applicationId);
			}
		} catch (Throwable oops) {
			result = new ModelAndView("customer/addComment");
			result.addObject("comment", comment);
			result.addObject("applicationId", applicationId);
			result.addObject("fixUpTaskId", fixUpTaskId);
		}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView editApplication(@RequestParam int applicationId) {
		ModelAndView result;

		Application application = this.applicationService.findOne(applicationId);

		result = new ModelAndView("customer/changeStatus");
		result.addObject(application);
		result.addObject("requestURI", "application/customer/edit.do");

		return result;
	}

	@RequestMapping(value = "/changeStatus", method = RequestMethod.POST, params = "save")
	public ModelAndView changeApplicationStatus(@Valid Application application, BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors()) {
			result = new ModelAndView("customer/changeStatus");
			result.addObject("application", application);
			result.addObject("message", "operation.error");
		} else {
			try {
				if (application.getStatus().equals(Status.ACCEPTED)) {
					result = new ModelAndView("customer/creditCard");
					result.addObject("applicationId", application.getId());

					List<String> cards = this.configurationService.getConfiguration().getCardType();

					result.addObject("cards", cards);

					CreditCard creditCard = new CreditCard();

					result.addObject("creditCard", creditCard);
				} else {
					Application applicationSave = this.applicationService.save(application);

					//Messages
					this.sendMessagesToActorsInvolved(applicationSave);

					result = new ModelAndView("redirect:list.do");
					result.addObject("fixUpTaskId", applicationSave.getFixUpTask().getId());
					result.addObject("applicationId", applicationSave.getId());
				}
			} catch (Throwable oops) {
				result = new ModelAndView("customer/changeStatus");
				result.addObject("application", application);
				result.addObject("message", "operation.error");
			}
		}

		return result;
	}

	private void sendMessagesToActorsInvolved(Application application) {
		//Messages
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		String userName = userAccount.getUsername();
		Actor customer = this.actorService.getActorByUsername(userAccount.getUsername());

		String statusES = "";
		String statusEN = "";
		if (application.getStatus().equals(Status.ACCEPTED)) {
			statusES = "ACEPTADA";
			statusEN = "ACCEPTED";
		} else {
			statusES = "RECHAZADA";
			statusEN = "REJECTED";
		}

		String subject = "Application updated: " + statusEN + " / Solicitud actualizada: " + statusES;

		//		String bodyC = "The application offered by handy worker: " + application.getHandyWorker().getMake() + ", with the next offered price: " + application.getOfferedPrice() + ", has been updated to: " + statusEN + " // "
		//			+ "La solicitud presentada por el handy worker: " + application.getHandyWorker().getMake() + ", con el siguiente precio ofertado: " + application.getOfferedPrice() + ", ha sido actualizada a: " + statusES;
		//		String bodyHW = "The application that you sent regarding the fix up task of the customer: " + customer.getName() + ", with the next offered price: " + application.getOfferedPrice() + ", has been updated to: " + statusEN + " // "
		//			+ "La solicitud que enviaste en relación al trabajo del cliente: " + customer.getName() + ", con el siguiente precio ofertado: " + application.getOfferedPrice() + ", ha sido actualizada a: " + statusES;

		String body = "Application with offered price: " + application.getOfferedPrice() + " has been updated" + " / " + "Solicitud con precio ofertado: " + application.getOfferedPrice() + " ha sido actualizada";

		Message messageC = this.messageService.createNotification(subject, body, Priority.NEUTRAL, customer);
		Message messageHW = this.messageService.createNotification(subject, body, Priority.NEUTRAL, application.getHandyWorker());

		this.messageService.sendMessageAnotherSender(messageC);
		this.messageService.sendMessageAnotherSender(messageHW);
	}

	@RequestMapping(value = "/changeStatusWithCreditCard", method = RequestMethod.POST, params = "create")
	public ModelAndView changeApplicationStatusWithCreditCard(@Valid int applicationId, @Valid CreditCard creditCard, BindingResult binding) {
		ModelAndView result = null;

		if (binding.hasErrors() || Long.toString(creditCard.getNumber()).length() != 16 || Integer.toString(creditCard.getExpirationMonth()).length() != 2 || Integer.toString(creditCard.getExpirationYear()).length() != 2
			|| Integer.toString(creditCard.getCvvCode()).length() != 3 || !(Integer.toString(creditCard.getExpirationMonth()).startsWith("0") || Integer.toString(creditCard.getExpirationMonth()).startsWith("1"))) {
			result = new ModelAndView("customer/creditCard");
			result.addObject("applicationId", applicationId);
			result.addObject("creditCard", creditCard);
			result.addObject("message", "operation.error");
		} else {
			try {
				Application application = this.applicationService.findOne(applicationId);
				application.setStatus(Status.ACCEPTED);
				Application applicationSave = this.customerService.editApplication(application, creditCard);

				//Messages
				this.sendMessagesToActorsInvolved(application);

				result = new ModelAndView("redirect:list.do");
				result.addObject("fixUpTaskId", applicationSave.getFixUpTask().getId());
				result.addObject("applicationId", applicationSave.getId());
			} catch (Throwable oops) {
				result = new ModelAndView("customer/creditCard");
				result.addObject("applicationId", applicationId);
				result.addObject("creditCard", creditCard);
				result.addObject("message", "operation.error");
			}
		}

		return result;
	}

	@RequestMapping(value = "/listComments", method = RequestMethod.GET)
	public ModelAndView applicationsCommentsList(@RequestParam int fixUpTaskId, @RequestParam int applicationId) {
		ModelAndView result;

		result = new ModelAndView("customer/comments");
		result.addObject("applicationId", applicationId);
		result.addObject("fixUpTaskId", fixUpTaskId);

		result.addObject("comments", this.applicationService.findOne(applicationId).getComments());
		result.addObject("requestURI", "application/customer/listComments.do");

		return result;
	}

}
