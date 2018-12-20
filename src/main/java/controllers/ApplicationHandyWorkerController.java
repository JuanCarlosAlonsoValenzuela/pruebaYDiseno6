
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.HandyWorkerService;
import domain.Application;

@Controller
@RequestMapping("/application/handyWorker/")
public class ApplicationHandyWorkerController extends AbstractController {

	@Autowired
	private HandyWorkerService	handyWorkerService;


	// Constructors -----------------------------------------------------------

	public ApplicationHandyWorkerController() {
		super();
	}

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

}
