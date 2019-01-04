
package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.FixUpTaskService;
import domain.FixUpTask;
import domain.Warranty;

@Controller
@RequestMapping("/warranty/handyWorker")
public class WarrantyHandyWorkerController extends AbstractController {

	//Services
	@Autowired
	private FixUpTaskService	fixUpTaskService;


	// Constructors -----------------------------------------------------------

	public WarrantyHandyWorkerController() {
		super();
	}

	//List
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView listWarranty(@RequestParam int fixUpTaskId) {
		ModelAndView result;

		FixUpTask fixUpTask = this.fixUpTaskService.findOne(fixUpTaskId);

		Warranty warranty = fixUpTask.getWarranty();

		result = new ModelAndView("handy-worker/warranty");
		result.addObject("warranty", warranty);

		result.addObject("terms", warranty.getTerms());
		result.addObject("laws", warranty.getLaws());

		result.addObject("requestURI", "warranty/handyWorker/list.do");

		return result;

	}
}
