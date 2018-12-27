
package controllers;

import java.util.Collection;
import java.util.Date;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import security.UserAccount;
import services.CustomerService;
import services.FinderService;
import services.HandyWorkerService;
import domain.Customer;
import domain.Finder;
import domain.FixUpTask;
import domain.HandyWorker;

@Controller
@RequestMapping("/finder/handyWorker/")
public class FinderHandyWorkerController extends AbstractController {

	@Autowired
	private HandyWorkerService	handyWorkerService;
	@Autowired
	private FinderService		finderService;
	@Autowired
	private CustomerService		customerService;


	// Constructors -----------------------------------------------------------

	public FinderHandyWorkerController() {
		super();
	}

	//List
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView fixUpTasksList() {
		ModelAndView result;

		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		HandyWorker logguedHandyWorker = new HandyWorker();
		logguedHandyWorker = this.handyWorkerService.getHandyWorkerByUsername(userAccount.getUsername());

		this.handyWorkerService.filterFixUpTasksByFinder();

		Collection<FixUpTask> fixUpTasks = logguedHandyWorker.getFinder().getFixUpTasks();
		Map<FixUpTask, Customer> map = this.handyWorkerService.getFixUpTaksAndCustomer(fixUpTasks);

		result = new ModelAndView("handy-worker/finderResult");

		result.addObject("fixUpTasks", fixUpTasks);
		result.addObject("map", map);
		result.addObject("requestURI", "finder/handyWorker/list.do");

		return result;
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

	//Save
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid Finder finder, BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors()) {
			result = this.createEditModelAndView(finder);
		} else {
			try {
				Date date = new Date();
				finder.setLastEdit(date);
				this.finderService.save(finder);

				result = new ModelAndView("redirect:list.do");
			} catch (Throwable oops) {
				result = this.createEditModelAndView(finder, "finder.commit.error");
			}
		}
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
