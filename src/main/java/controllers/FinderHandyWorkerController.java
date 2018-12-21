
package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import security.UserAccount;
import services.HandyWorkerService;
import domain.Finder;
import domain.HandyWorker;

@Controller
@RequestMapping("/finder/handyWorker/")
public class FinderHandyWorkerController extends AbstractController {

	@Autowired
	private HandyWorkerService	handyWorkerService;


	// Constructors -----------------------------------------------------------

	public FinderHandyWorkerController() {
		super();
	}

	//Create
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {
		ModelAndView result;

		UserAccount userAccount = LoginService.getPrincipal();
		HandyWorker logguedHandyWorker = this.handyWorkerService.getHandyWorkerByUsername(userAccount.getUsername());

		Finder finder = logguedHandyWorker.getFinder();

		Assert.notNull(finder);

		result = this.createEditModelAndView(finder);

		return result;
	}

	//CreateEditModelAndView
	protected ModelAndView createEditModelAndView(Finder finder) {
		ModelAndView result;

		result = this.createEditModelAndView(finder, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(Finder finder, String messageCode) {
		ModelAndView result;

		result = new ModelAndView("handy-worker/finder");

		result.addObject("finder", finder);
		result.addObject("message", messageCode);

		return result;

	}
}
