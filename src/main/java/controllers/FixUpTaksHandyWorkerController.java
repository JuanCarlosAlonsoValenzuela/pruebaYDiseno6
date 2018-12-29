
package controllers;

import java.util.Collection;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import security.UserAccount;
import services.ConfigurationService;
import services.CustomerService;
import services.FinderService;
import services.HandyWorkerService;
import domain.Customer;
import domain.Finder;
import domain.FixUpTask;
import domain.HandyWorker;

@Controller
@RequestMapping("/fixUpTask/handyWorker/")
public class FixUpTaksHandyWorkerController extends AbstractController {

	@Autowired
	private HandyWorkerService		handyWorkerService;
	@Autowired
	private CustomerService			customerService;
	@Autowired
	private ConfigurationService	configuarionService;
	@Autowired
	private FinderService			finderService;


	// Constructors -----------------------------------------------------------

	public FixUpTaksHandyWorkerController() {
		super();
	}

	//List
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView fixUpTasksList() {
		ModelAndView result;

		Collection<FixUpTask> fixUpTasks;
		Map<FixUpTask, Customer> map;

		map = this.handyWorkerService.showFixUpTasksAndCustomer();
		fixUpTasks = map.keySet();

		result = new ModelAndView("handy-worker/fixUpTask");

		result.addObject("fixUpTasks", fixUpTasks);
		result.addObject("map", map);
		result.addObject("requestURI", "fixUpTask/handyWorker/list.do");

		return result;
	}

	//Clean Filter
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save() {
		ModelAndView result;

		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		HandyWorker logguedHandyWorker = new HandyWorker();
		logguedHandyWorker = this.handyWorkerService.getHandyWorkerByUsername(userAccount.getUsername());

		Finder finder = logguedHandyWorker.getFinder();

		finder.setCategory("");
		finder.setEndDate(null);
		finder.setKeyWord("");
		finder.setMaxPrice(0.0);
		finder.setMinPrice(0.0);
		finder.setStartDate(null);
		finder.setWarranty("");
		this.finderService.save(finder);

		result = new ModelAndView("redirect:list.do");

		return result;

	}
	//Customer List
	@RequestMapping(value = "/customerList", method = RequestMethod.GET)
	public ModelAndView applicationList(@RequestParam int customerId) {
		ModelAndView result;
		Collection<FixUpTask> fixUpTasks;

		Customer customer = this.customerService.findOne(customerId);
		fixUpTasks = customer.getFixUpTasks();

		result = new ModelAndView("handy-worker/fixUpTaskCustomerInfo");

		result.addObject("fixUpTasks", fixUpTasks);
		result.addObject("customer", customer);
		result.addObject("requestURI", "fixUpTask/handyWorker/customerList.do");

		return result;
	}

}
