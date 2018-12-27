
package controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.EndorsementService;
import services.HandyWorkerService;
import domain.Endorsement;

@Controller
@RequestMapping("/endorsement/handyWorker")
public class EndorsementHandyWorkerController extends AbstractController {

	@Autowired
	EndorsementService	endorsementService;
	@Autowired
	HandyWorkerService	handyWorkerService;


	public EndorsementHandyWorkerController() {
		super();
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView listAnonymous() {
		ModelAndView result;

		List<Endorsement> endorsements = this.handyWorkerService.showEndorsments();
		result = new ModelAndView("endorsement/handyWorker/list");

		result.addObject("endorsements", endorsements);
		result.addObject("requestURI", "endorsement/handyWorker/list.do");

		return result;

	}
}
