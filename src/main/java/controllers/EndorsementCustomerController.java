
package controllers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import services.ApplicationService;
import services.ConfigurationService;
import services.CustomerService;
import services.EndorsementService;
import services.HandyWorkerService;
import domain.Application;
import domain.Customer;
import domain.Endorsement;
import domain.HandyWorker;

@Controller
@RequestMapping("/endorsement/customer")
public class EndorsementCustomerController extends AbstractController {

	@Autowired
	private EndorsementService		endorsementService;
	@Autowired
	private CustomerService			customerService;
	@Autowired
	private HandyWorkerService		handyWorkerService;
	@Autowired
	private LoginService			loginService;
	@Autowired
	private ConfigurationService	configurationService;
	@Autowired
	private ApplicationService		applicationService;


	public EndorsementCustomerController() {
		super();
	}

	//----------------------------LIST----------------------------
	//------------------------------------------------------------

	//List Endorsements
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView listAnonymous() {
		ModelAndView result;

		String currentUsername = LoginService.getPrincipal().getUsername();

		List<Endorsement> endorsements = this.customerService.showEndorsments1();
		result = new ModelAndView("endorsement/customer/list");
		result.addObject("endorsements", endorsements);
		result.addObject("currentUsername", currentUsername);
		result.addObject("requestURI", "endorsement/customer/list.do");

		return result;

	}
	//List Comments
	@RequestMapping(value = "/listComments", method = RequestMethod.GET)
	public ModelAndView listComments(@RequestParam int endorsementId) {
		ModelAndView result;
		Endorsement endorsement = this.endorsementService.findOne(endorsementId);

		result = new ModelAndView("endorsement/customer/listComments");
		result.addObject("comments", endorsement.getComments());
		result.addObject("endorsementId", endorsementId);
		return result;
	}

	//----------------------------CREATE----------------------------
	//--------------------------------------------------------------

	//CREATE AN ENDORSEMENT
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Endorsement endorsement;

		Customer customer = this.customerService.getCustomerByUsername(LoginService.getPrincipal().getUsername());
		Collection<Application> applications = this.customerService.findApplicationsById(customer.getId());
		List<HandyWorker> handyWorkers = new ArrayList<HandyWorker>();

		for (Application application : applications) {
			if ((application.getStatus().toString() == "ACCEPTED") && !handyWorkers.contains(application.getHandyWorker())) {
				handyWorkers.add(application.getHandyWorker());
			}
		}

		endorsement = this.endorsementService.create();
		endorsement.setWrittenBy(customer);
		result = this.createEditModelAndView1(endorsement);
		result.addObject("handyWorkers", handyWorkers);

		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid Endorsement endorsement, BindingResult binding, @RequestParam String comment) {

		ModelAndView result;
		Customer customer = this.customerService.getCustomerByUsername(LoginService.getPrincipal().getUsername());

		Collection<Application> applications = this.customerService.findApplicationsById(customer.getId());
		List<HandyWorker> handyWorkers = new ArrayList<HandyWorker>();

		///////////////////////////////////////////////////////////
		for (Application application : applications) {
			if ((application.getStatus().toString() == "ACCEPTED") && !handyWorkers.contains(application.getHandyWorker())) {
				handyWorkers.add(application.getHandyWorker());
			}
		}
		///////////////////////////////////////////////////////////
		List<String> c = endorsement.getComments();

		if (!comment.trim().isEmpty() && !comment.trim().equals(",")) {
			c.add(comment.substring(1));
		}
		if (binding.hasErrors()) {
			result = this.createEditModelAndView1(endorsement);
			result.addObject("handyWorkers", handyWorkers);
		} else {
			try {
				endorsement.setComments(c);
				this.customerService.createEndorsment(endorsement);

				//this.endorsementService.save(endorsement);
				result = new ModelAndView("redirect:list.do");

			} catch (Throwable oops) {
				result = this.createEditModelAndView1(endorsement, "endorsement.commit.error");
				result.addObject("handyWorkers", handyWorkers);
			}
		}
		return result;
	}
	//ADD A COMMENT
	@RequestMapping(value = "/comment/create", method = RequestMethod.GET)
	public ModelAndView newComment(@RequestParam int endorsementId) {
		ModelAndView result;

		result = new ModelAndView("endorsement/customer/comment/create");
		result.addObject("endorsementId", endorsementId);

		return result;
	}

	@RequestMapping(value = "/comment/create", method = RequestMethod.POST, params = "create")
	public ModelAndView save(@Valid int endorsementId, @Valid String comment) {
		ModelAndView result;

		try {
			Endorsement endorsement = this.endorsementService.findOne(endorsementId);
			List<String> comments = endorsement.getComments();
			if (comment != null && comment != "") {
				comments.add(comment);
			}
			List<String> spam = new ArrayList<String>();
			Boolean bol;
			spam = this.configurationService.getSpamWords();

			bol = this.configurationService.isStringSpam(comment, spam);

			endorsement.setComments(comments);
			this.endorsementService.save(endorsement);

			this.configurationService.computeScore(endorsement.getWrittenBy());
			this.configurationService.computeScore(endorsement.getWrittenTo());

			result = new ModelAndView("endorsement/customer/listComments");
			result.addObject("comments", endorsement.getComments());
			result.addObject("endorsementId", endorsementId);

		} catch (Throwable oops) {
			result = new ModelAndView("endorsement/customer/listComments");
			Endorsement endorsement = this.endorsementService.findOne(endorsementId);
			result.addObject("comments", endorsement.getComments());
			result.addObject("endorsementId", endorsementId);

		}
		return result;
	}

	//MODELANDVIEW
	protected ModelAndView createEditModelAndView1(Endorsement endorsement) {
		ModelAndView result;

		result = this.createEditModelAndView1(endorsement, null);

		return result;
	}

	protected ModelAndView createEditModelAndView1(Endorsement endorsement, String messageCode) {
		ModelAndView result;

		result = new ModelAndView("endorsement/customer/create");
		result.addObject("endorsement", endorsement);
		result.addObject("message", messageCode);

		return result;
	}
	//----------------------------DELETE----------------------------
	//--------------------------------------------------------------
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam int endorsementId) {

		ModelAndView result;
		Endorsement endorsement;

		endorsement = this.endorsementService.findOne(endorsementId);
		Assert.notNull(endorsement);
		result = this.createEditModelAndView(endorsement);
		result.addObject("endorsementId", endorsementId);

		return result;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(Endorsement endorsement, BindingResult binding) {

		ModelAndView result;

		try {
			this.customerService.deleteEndorsment(endorsement);

			String currentUsername = LoginService.getPrincipal().getUsername();

			List<Endorsement> endorsements = this.customerService.showEndorsments1();

			result = new ModelAndView("endorsement/customer/list");
			result.addObject("endorsements", endorsements);
			result.addObject("currentUsername", currentUsername);
			result.addObject("requestURI", "endorsement/customer/list.do");
		} catch (Throwable oops) {
			result = this.createEditModelAndView(endorsement, "endorsement.commit.error");

		}
		return result;
	}

	//MODELANDVIEW
	protected ModelAndView createEditModelAndView(Endorsement endorsement) {
		ModelAndView result;

		result = this.createEditModelAndView(endorsement, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(Endorsement endorsement, String messageCode) {
		ModelAndView result;

		result = new ModelAndView("endorsement/customer/delete");
		result.addObject("endorsement", endorsement);
		result.addObject("message", messageCode);

		return result;
	}

	//	
	//	@RequestMapping(value = "/create", method = RequestMethod.GET)
	//	public ModelAndView create() {
	//		ModelAndView result;
	//		Endorsement endorsement;
	//
	//		HandyWorker handyWorker = this.handyWorkerService.getHandyWorkerByUsername(LoginService.getPrincipal().getUsername());
	//		List<Customer> customers = this.handyWorkerService.getCustomersByHandyWorker(handyWorker);
	//
	//		endorsement = this.endorsementService.create();
	//		endorsement.setWrittenBy(handyWorker);
	//		result = this.createEditModelAndView(endorsement);
	//		result.addObject("customers", customers);
	//
	//		return result;
	//	}
	//
	//	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	//	public ModelAndView edit(@RequestParam int endorsementId) {
	//		ModelAndView result;
	//		Endorsement endorsement = this.endorsementService.findOne(endorsementId);
	//
	//		result = this.createEditModelAndView(endorsement);
	//
	//		return result;
	//	}

	//
	//	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	//	public ModelAndView save(@Valid Endorsement endorsement, BindingResult binding, @RequestParam String comment) {
	//		ModelAndView result;
	//		HandyWorker handyWorker = this.handyWorkerService.getHandyWorkerByUsername(LoginService.getPrincipal().getUsername());
	//		List<Customer> customers = this.handyWorkerService.getCustomersByHandyWorker(handyWorker);
	//		List<String> c = endorsement.getComments();
	//		if (!comment.trim().isEmpty() && !comment.trim().equals(",")) {
	//			c.add(comment.substring(1));
	//		}
	//		if (binding.hasErrors()) {
	//			result = this.createEditModelAndView(endorsement);
	//			result.addObject("customers", customers);
	//		} else {
	//			try {
	//				endorsement.setComments(this.handyWorkerService.filterComments(c));
	//				this.handyWorkerService.createEndorsment(endorsement);
	//				result = new ModelAndView("redirect:list.do");
	//
	//			} catch (Throwable oops) {
	//				result = this.createEditModelAndView(endorsement, "endorsement.commit.error");
	//				result.addObject("customers", customers);
	//			}
	//		}
	//		return result;
	//	}
	//	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "edit")
	//	public ModelAndView edit(@Valid Endorsement endorsement, BindingResult binding, @RequestParam String comment) {
	//		ModelAndView result;
	//		List<String> c = endorsement.getComments();
	//		if (!comment.trim().isEmpty() && !comment.trim().equals(",")) {
	//			c.add(comment.substring(1));
	//		}
	//		if (binding.hasErrors()) {
	//			result = this.createEditModelAndView(endorsement);
	//		} else {
	//			try {
	//				endorsement.setComments(c);
	//				this.handyWorkerService.updateEndorsment(endorsement);
	//				result = new ModelAndView("redirect:listComments.do?endorsementId=" + endorsement.getId());
	//
	//			} catch (Throwable oops) {
	//				result = this.createEditModelAndView(endorsement, "endorsement.commit.error");
	//			}
	//		}
	//		return result;
	//	}
	//	
	//	
	//		@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	//		public ModelAndView delete(@Valid Endorsement endorsement, BindingResult binding) {
	//			ModelAndView result;
	//	
	//			try {
	//				this.handyWorkerService.deleteEndorsment(endorsement);
	//				result = new ModelAndView("redirect:list.do");
	//	
	//			} catch (Throwable oops) {
	//				result = this.createEditModelAndView(endorsement, "note.commit.error");
	//			}
	//	
	//			return result;
	//		}
	//		
	//	//CreateEditModelAndView
	//	protected ModelAndView createEditModelAndView(Endorsement endorsement) {
	//		ModelAndView result;
	//
	//		result = this.createEditModelAndView(endorsement, null);
	//
	//		return result;
	//	}
	//

}
