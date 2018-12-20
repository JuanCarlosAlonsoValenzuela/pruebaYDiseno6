
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

import security.LoginService;
import security.UserAccount;
import services.ApplicationService;
import services.FixUpTaskService;
import services.HandyWorkerService;
import domain.Application;
import domain.FixUpTask;
import domain.HandyWorker;

@Controller
@RequestMapping("/application/handyWorker/")
public class ApplicationHandyWorkerController extends AbstractController {

	@Autowired
	private HandyWorkerService	handyWorkerService;
	@Autowired
	private ApplicationService	applicationService;
	@Autowired
	private FixUpTaskService	fixUpTaskService;


	// Constructors -----------------------------------------------------------

	public ApplicationHandyWorkerController() {
		super();
	}

	//List
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView applicationList() {
		ModelAndView result;
		Collection<Application> applications;

		applications = this.handyWorkerService.showApplications();

		result = new ModelAndView("handy-worker/applications");

		result.addObject("applications", applications);
		result.addObject("requestURI", "application/handyWorker/list.do");

		return result;
	}

	//Comments

	@RequestMapping(value = "/listComments", method = RequestMethod.GET)
	public ModelAndView commentList(@RequestParam int applicationId) {
		ModelAndView result;

		Application application = this.applicationService.findOne(applicationId);

		Collection<String> comments = application.getComments();

		result = new ModelAndView("handy-worker/comments");

		result.addObject("requestURI", "application/handyWorker/listComments.do");
		result.addObject("comments", comments);

		return result;
	}
	@RequestMapping(value = "/newComment", method = RequestMethod.GET)
	public ModelAndView newComment(@RequestParam int applicationId) {
		ModelAndView result;

		result = new ModelAndView("handy-worker/addComment");
		result.addObject("applicationId", applicationId);

		return result;
	}

	@RequestMapping(value = "/saveComment", method = RequestMethod.POST, params = "create")
	public ModelAndView saveComment(@Valid int applicationId, @Valid String comment) {
		ModelAndView result;

		try {
			this.handyWorkerService.addComment(this.applicationService.findOne(applicationId), comment);
			result = new ModelAndView("redirect:list.do");
			result.addObject("applicationId", applicationId);
		} catch (Throwable oops) {
			result = new ModelAndView("handy-worker/addComment");
			result.addObject("comment", comment);
			result.addObject("applicationId", applicationId);
		}

		return result;
	}

	//Create
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam int fixUpTaskId) {
		ModelAndView result;
		Application application;

		UserAccount userAccount = LoginService.getPrincipal();
		HandyWorker logguedHandyWorker = this.handyWorkerService.getHandyWorkerByUsername(userAccount.getUsername());
		FixUpTask fixUpTask = this.fixUpTaskService.findOne(fixUpTaskId);

		application = this.applicationService.createApplication();
		application.setFixUpTask(fixUpTask);
		application.setHandyWorker(logguedHandyWorker);
		result = this.createEditModelAndView(application);

		return result;
	}

	//Save
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "create")
	public ModelAndView save(@Valid Application application, @RequestParam int fixUpTaskId, BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors()) {
			result = this.createEditModelAndView(application);
		} else {
			try {
				UserAccount userAccount = LoginService.getPrincipal();
				HandyWorker logguedHandyWorker = this.handyWorkerService.getHandyWorkerByUsername(userAccount.getUsername());
				FixUpTask fixUpTask = this.fixUpTaskService.findOne(fixUpTaskId);
				application.setFixUpTask(fixUpTask);
				application.setHandyWorker(logguedHandyWorker);

				this.applicationService.save(application);

				result = new ModelAndView("redirect:list.do");
			} catch (Throwable oops) {
				result = this.createEditModelAndView(application, "application.commit.error");
			}
		}
		return result;
	}
	//CreateEditModelAndView
	protected ModelAndView createEditModelAndView(Application application) {
		ModelAndView result;

		result = this.createEditModelAndView(application, null);

		return result;
	}
	protected ModelAndView createEditModelAndView(Application application, String messageCode) {
		ModelAndView result;

		result = new ModelAndView("handy-worker/createApplication");

		result.addObject("application", application);
		result.addObject("message", messageCode);

		return result;

	}
}
