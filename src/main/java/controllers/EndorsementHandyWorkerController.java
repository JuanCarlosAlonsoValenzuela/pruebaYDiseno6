
package controllers;

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
import services.EndorsementService;
import services.HandyWorkerService;
import domain.Customer;
import domain.Endorsement;
import domain.HandyWorker;

@Controller
@RequestMapping("/endorsement/handyWorker")
public class EndorsementHandyWorkerController extends AbstractController {

	@Autowired
	private EndorsementService	endorsementService;
	@Autowired
	private HandyWorkerService	handyWorkerService;


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
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Endorsement endorsement;

		HandyWorker handyWorker = this.handyWorkerService.getHandyWorkerByUsername(LoginService.getPrincipal().getUsername());
		List<Customer> customers = this.handyWorkerService.getCustomersByHandyWorker(handyWorker);

		endorsement = this.endorsementService.create();
		endorsement.setWrittenBy(handyWorker);
		result = this.createEditModelAndView(endorsement);
		result.addObject("customers", customers);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam int endorsementId) {
		ModelAndView result;
		Endorsement endorsement = this.endorsementService.findOne(endorsementId);

		result = this.createEditModelAndView(endorsement);
		result.addObject("username", LoginService.getPrincipal().getUsername());
		result.addObject("username2", endorsement.getWrittenBy().getUserAccount().getUsername());
		return result;
	}

	@RequestMapping(value = "/listComments", method = RequestMethod.GET)
	public ModelAndView listComments(@RequestParam int endorsementId) {
		ModelAndView result;
		Endorsement endorsement = this.endorsementService.findOne(endorsementId);

		result = new ModelAndView("endorsement/handyWorker/listComments");
		result.addObject("comments", this.handyWorkerService.filterComments(endorsement.getComments()));
		result.addObject("endorsementId", endorsementId);
		result.addObject("requestURI", "endorsement/handyWorker/listComments.do");
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid Endorsement endorsement, BindingResult binding, @RequestParam String comment) {
		ModelAndView result;
		HandyWorker handyWorker = this.handyWorkerService.getHandyWorkerByUsername(LoginService.getPrincipal().getUsername());
		List<Customer> customers = this.handyWorkerService.getCustomersByHandyWorker(handyWorker);
		List<String> c = endorsement.getComments();
		if (!comment.trim().isEmpty() && !comment.trim().equals(",")) {
			c.add(comment.substring(1));
		}
		if (binding.hasErrors()) {
			result = this.createEditModelAndView(endorsement);
			result.addObject("customers", customers);
		} else {
			try {
				endorsement.setComments(this.handyWorkerService.filterComments(c));
				this.handyWorkerService.createEndorsment(endorsement);
				result = new ModelAndView("redirect:list.do");

			} catch (Throwable oops) {
				result = this.createEditModelAndView(endorsement, "endorsement.commit.error");
				result.addObject("customers", customers);
			}
		}
		return result;
	}
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "edit")
	public ModelAndView edit(@Valid Endorsement endorsement, BindingResult binding, @RequestParam String comment) {
		ModelAndView result;
		List<String> c = endorsement.getComments();
		if (!comment.trim().isEmpty() && !comment.trim().equals(",")) {
			c.add(comment.substring(1));
		}
		if (binding.hasErrors()) {
			result = this.createEditModelAndView(endorsement);
		} else {
			try {
				endorsement.setComments(c);
				this.handyWorkerService.updateEndorsment(endorsement);
				result = new ModelAndView("redirect:listComments.do?endorsementId=" + endorsement.getId());

			} catch (Throwable oops) {
				result = this.createEditModelAndView(endorsement, "endorsement.commit.error");
			}
		}
		return result;
	}
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(@Valid Endorsement endorsement, BindingResult binding) {
		ModelAndView result;

		try {
			this.handyWorkerService.deleteEndorsment(endorsement);
			result = new ModelAndView("redirect:list.do");

		} catch (Throwable oops) {
			result = this.createEditModelAndView(endorsement, "note.commit.error");
		}

		return result;
	}
	//CreateEditModelAndView
	protected ModelAndView createEditModelAndView(Endorsement endorsement) {
		ModelAndView result;

		result = this.createEditModelAndView(endorsement, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(Endorsement endorsement, String messageCode) {
		ModelAndView result;

		result = new ModelAndView("endorsement/handyWorker/edit");
		result.addObject("endorsement", endorsement);

		result.addObject("message", messageCode);

		return result;
	}
}
