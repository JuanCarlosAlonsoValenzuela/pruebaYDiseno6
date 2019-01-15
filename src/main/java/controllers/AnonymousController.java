
package controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ConfigurationService;
import services.CustomerService;
import services.HandyWorkerService;
import services.SponsorService;
import domain.Configuration;
import domain.Customer;
import domain.HandyWorker;
import domain.Sponsor;

@Controller
@RequestMapping("/anonymous")
public class AnonymousController extends AbstractController {

	@Autowired
	private CustomerService			customerService;

	@Autowired
	private HandyWorkerService		handyWorkerService;

	@Autowired
	private SponsorService			sponsorService;

	@Autowired
	private ConfigurationService	configurationService;


	public AnonymousController() {
		super();
	}

	//-------------------------------------------------------------------------------------------	
	//-------------------------- HANDY WORKER ---------------------------------------------------		

	//Create
	@RequestMapping(value = "/createHandyWorker", method = RequestMethod.GET)
	public ModelAndView createHandyWorker() {
		ModelAndView result;
		HandyWorker handyWorker;

		handyWorker = this.handyWorkerService.createHandyWorker();
		result = this.createEditModelAndView(handyWorker);

		return result;
	}

	@RequestMapping(value = "/createHandyWorker", method = RequestMethod.POST, params = "save")
	public ModelAndView saveHandyWorker(@Valid HandyWorker handyWorker, BindingResult binding) {
		ModelAndView result;
		Md5PasswordEncoder encoder;
		encoder = new Md5PasswordEncoder();
		Configuration configuration = this.configurationService.getConfiguration();
		String prefix = configuration.getSpainTelephoneCode();

		if (binding.hasErrors()) {
			result = this.createEditModelAndView(handyWorker);
		} else {
			try {
				handyWorker.getUserAccount().setPassword(encoder.encodePassword(handyWorker.getUserAccount().getPassword(), null));
				if (handyWorker.getEmail().matches("[\\w.%-]+\\<[\\w.%-]+\\@+\\>|[\\w.%-]+")) {

					result = new ModelAndView("redirect:/anonymous/createHandyWorker.do");

				} else if (handyWorker.getPhoneNumber().matches("(\\+[0-9]{1,3})(\\([0-9]{1,3}\\))([0-9]{4,})$") || handyWorker.getPhoneNumber().matches("(\\+[0-9]{1,3})([0-9]{4,})$")) {
					this.handyWorkerService.saveCreate(handyWorker);
				} else if (handyWorker.getPhoneNumber().matches("([0-9]{4,})$")) {
					handyWorker.setPhoneNumber(prefix + handyWorker.getPhoneNumber());
					this.handyWorkerService.saveCreate(handyWorker);
				} else {
					result = new ModelAndView("redirect:/anonymous/createHandyWorker.do");
				}
				result = new ModelAndView("redirect:/security/login.do");
			} catch (Throwable oops) {
				result = this.createEditModelAndView(handyWorker, "handyWorker.commit.error");
			}
		}
		return result;
	}
	//CreateEditModelAndView
	protected ModelAndView createEditModelAndView(HandyWorker handyWorker) {
		ModelAndView result;

		result = this.createEditModelAndView(handyWorker, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(HandyWorker handyWorker, String messageCode) {
		ModelAndView result;

		result = new ModelAndView("anonymous/createHandyWorker");
		result.addObject("handyWorker", handyWorker);
		result.addObject("message", messageCode);

		return result;
	}

	//------------------------ CUSTOMER -----------------------------------------------------	

	//Create
	@RequestMapping(value = "/createCustomer", method = RequestMethod.GET)
	public ModelAndView createCustomer() {
		ModelAndView result;
		Customer customer;

		customer = this.customerService.create();
		result = this.createEditModelAndView(customer);

		return result;
	}

	//Save
	@RequestMapping(value = "/createCustomer", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid Customer customer, BindingResult binding) {

		ModelAndView result;
		Md5PasswordEncoder encoder;
		encoder = new Md5PasswordEncoder();
		Configuration configuration = this.configurationService.getConfiguration();

		String prefix = configuration.getSpainTelephoneCode();
		if (binding.hasErrors()) {
			result = this.createEditModelAndView(customer);
		} else {
			try {
				customer.getUserAccount().setPassword(encoder.encodePassword(customer.getUserAccount().getPassword(), null));
				if (customer.getEmail().matches("[\\w.%-]+\\<[\\w.%-]+\\@+\\>|[\\w.%-]+")) {

					result = new ModelAndView("redirect:/anonymous/createCustomer.do");

				} else if (customer.getPhoneNumber().matches("(\\+[0-9]{1,3})(\\([0-9]{1,3}\\))([0-9]{4,})$") || customer.getPhoneNumber().matches("(\\+[0-9]{1,3})([0-9]{4,})$")) {
					this.customerService.saveCreate(customer);
				} else if (customer.getPhoneNumber().matches("([0-9]{4,})$")) {
					customer.setPhoneNumber(prefix + customer.getPhoneNumber());
					this.customerService.saveCreate(customer);
				} else {
					result = new ModelAndView("redirect:/anonymous/createCustomer.do");
				}
				result = new ModelAndView("redirect:/security/login.do");
			} catch (Throwable oops) {
				result = this.createEditModelAndView(customer, "customer.commit.error");
			}
		}
		return result;
	}
	//CreateEditModelAndView
	protected ModelAndView createEditModelAndView(Customer customer) {
		ModelAndView result;

		result = this.createEditModelAndView(customer, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(Customer customer, String messageCode) {
		ModelAndView result;

		result = new ModelAndView("anonymous/createCustomer");
		result.addObject("customer", customer);

		result.addObject("message", messageCode);

		return result;
	}

	//---------------------- SPONSOR -------------------------------------------------------		

	//Create
	@RequestMapping(value = "/createSponsor", method = RequestMethod.GET)
	public ModelAndView createSponsor() {
		ModelAndView result;
		Sponsor sponsor;

		sponsor = this.sponsorService.create();
		result = this.createEditModelAndView(sponsor);

		return result;
	}

	//Save
	@RequestMapping(value = "/createSponsor", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid Sponsor sponsor, BindingResult binding) {

		ModelAndView result;
		Md5PasswordEncoder encoder;
		encoder = new Md5PasswordEncoder();
		Configuration configuration = this.configurationService.getConfiguration();

		String prefix = configuration.getSpainTelephoneCode();

		if (binding.hasErrors()) {
			result = this.createEditModelAndView(sponsor);
		} else {
			try {
				sponsor.getUserAccount().setPassword(encoder.encodePassword(sponsor.getUserAccount().getPassword(), null));
				if (sponsor.getEmail().matches("[\\w.%-]+\\<[\\w.%-]+\\@+\\>|[\\w.%-]+")) {

					result = new ModelAndView("redirect:/anonymous/createSponsor.do");

				} else if (sponsor.getPhoneNumber().matches("(\\+[0-9]{1,3})(\\([0-9]{1,3}\\))([0-9]{4,})$") || sponsor.getPhoneNumber().matches("(\\+[0-9]{1,3})([0-9]{4,})$")) {
					this.sponsorService.saveCreate(sponsor);
				} else if (sponsor.getPhoneNumber().matches("([0-9]{4,})$")) {
					sponsor.setPhoneNumber(prefix + sponsor.getPhoneNumber());
					this.sponsorService.saveCreate(sponsor);
				} else {
					result = new ModelAndView("redirect:/anonymous/createSponsor.do");
				}
				result = new ModelAndView("redirect:/security/login.do");
			} catch (Throwable oops) {
				result = this.createEditModelAndView(sponsor, "sponsor.commit.error");
			}
		}
		return result;
	}

	//Create Model And View
	protected ModelAndView createEditModelAndView(Sponsor sponsor) {
		ModelAndView result;

		result = this.createEditModelAndView(sponsor, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(Sponsor sponsor, String messageCode) {

		ModelAndView result;

		result = new ModelAndView("anonymous/createSponsor");
		result.addObject("sponsor", sponsor);
		result.addObject("message", messageCode);

		return result;

	}

}
