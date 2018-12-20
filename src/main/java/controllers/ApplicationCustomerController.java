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

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ApplicationService;
import services.CustomerService;
import services.FixUpTaskService;
import domain.Application;
import domain.CreditCard;
import domain.Status;

@Controller
@RequestMapping("/application/customer/")
public class ApplicationCustomerController extends AbstractController {

	@Autowired
	private FixUpTaskService	fixUpTaskService;
	@Autowired
	private ApplicationService	applicationService;
	@Autowired
	private CustomerService		customerService;


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
			this.customerService.addComment(this.applicationService.findOne(applicationId), comment);
			result = new ModelAndView("redirect:listComments.do");
			result.addObject("fixUpTaskId", fixUpTaskId);
			result.addObject("applicationId", applicationId);
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

					CreditCard creditCard = new CreditCard();

					result.addObject("creditCard", creditCard);
				} else {
					Application applicationSave = this.applicationService.save(application);
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

	@RequestMapping(value = "/changeStatusWithCreditCard", method = RequestMethod.POST, params = "create")
	public ModelAndView changeApplicationStatusWithCreditCard(@Valid int applicationId, @Valid CreditCard creditCard, BindingResult binding) {
		ModelAndView result = null;

		if (binding.hasErrors()) {
			result = new ModelAndView("customer/creditCard");
			result.addObject("applicationId", applicationId);
			result.addObject("creditCard", creditCard);
			result.addObject("message", "operation.error");
		} else {
			try {
				Application application = this.applicationService.findOne(applicationId);
				application.setStatus(Status.ACCEPTED);
				Application applicationSave = this.customerService.editApplication(application, creditCard);
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
