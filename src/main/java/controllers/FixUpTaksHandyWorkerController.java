
package controllers;

import java.util.Collection;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.CustomerService;
import services.HandyWorkerService;
import domain.Customer;
import domain.FixUpTask;

@Controller
@RequestMapping("/fixUpTask/handyWorker/")
public class FixUpTaksHandyWorkerController extends AbstractController {

	@Autowired
	private HandyWorkerService	handyWorkerService;
	@Autowired
	private CustomerService		customerService;


	// Constructors -----------------------------------------------------------

	public FixUpTaksHandyWorkerController() {
		super();
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView fixUpTasksList() {
		ModelAndView result;
		Map<FixUpTask, Customer> map = this.handyWorkerService.showFixUpTasksAndCustomer();
		Collection<FixUpTask> fixUpTasks = map.keySet();

		result = new ModelAndView("handy-worker/fixUpTask");

		result.addObject("fixUpTasks", fixUpTasks);
		result.addObject("map", map);
		result.addObject("requestURI", "fixUpTask/handyWorker/list.do");

		return result;
	}
}
